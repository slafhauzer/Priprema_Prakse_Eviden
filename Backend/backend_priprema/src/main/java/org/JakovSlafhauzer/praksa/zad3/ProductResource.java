package org.JakovSlafhauzer.praksa.zad3;

import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.*;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    public Response getProducts(
            @QueryParam("minPrice") Double minPrice,
            @QueryParam("maxPrice") Double maxPrice,
            @QueryParam("category") String category,
            @QueryParam("sort") String sort
    ) {
        Sort panacheSort = null;
        if (sort != null && !sort.isBlank()) {
            if (sort.equalsIgnoreCase("name")) {
                panacheSort = Sort.by("name");
            } else if (sort.equalsIgnoreCase("price")) {
                panacheSort = Sort.by("price");
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "sort must be 'name' or 'price'"))
                        .build();
            }
        }

        List<String> conditions = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        if (category != null && !category.isBlank()) {
            conditions.add("category = :category");
            params.put("category", category);
        }
        if (minPrice != null) {
            conditions.add("price >= :minPrice");
            params.put("minPrice", minPrice);
        }
        if (maxPrice != null) {
            conditions.add("price <= :maxPrice");
            params.put("maxPrice", maxPrice);
        }

        String query = String.join(" and ", conditions);

        List<Product> result;

        if (conditions.isEmpty()) {
            result = (panacheSort == null) ? Product.listAll() : Product.listAll(panacheSort);
        } else {
            if (panacheSort == null) {
                result = Product.list(query, params);
            } else {
                result = Product.list(query, panacheSort, params);
            }
        }

        return Response.ok(result).build();
    }

    @POST
    @Transactional
    public Response createProduct(@Valid CreateProductRequest request) {
        Product p = new Product();
        p.name = request.name().trim();
        p.price = request.price();
        p.category = request.category().trim();

        p.persist();

        return Response.created(URI.create("/products/" + p.id))
                .entity(p)
                .status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteProduct(@PathParam("id") long id) {
        Product p = Product.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Product with id " + id + " not found"))
                    .build();
        }

        p.delete();
        return Response.noContent().build();
    }
}

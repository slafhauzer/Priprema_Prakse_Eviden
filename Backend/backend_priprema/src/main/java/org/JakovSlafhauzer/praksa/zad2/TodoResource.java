package org.JakovSlafhauzer.praksa.zad2;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    TodoService todoService;

    @GET
    public Response getAll() {
        List<Todo> todos = todoService.getAll();
        return Response.ok(todos).build();
    }

    @POST
    public Response create(CreateTodoRequest request) {
        if (request == null || request.title() == null || request.title().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Title is required"))
                    .build();
        }

        Todo created = todoService.create(request.title(), request.completed());

        return Response
                .created(URI.create("/todos/" + created.id()))
                .entity(created)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") long id, UpdateTodoRequest request) {
        var updatedOpt = todoService.update(
                id,
                request != null ? request.title() : null,
                request != null ? request.completed() : null
        );

        if (updatedOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Todo with id " + id + " not found"))
                    .build();
        }

        return Response.ok(updatedOpt.get()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        boolean deleted = todoService.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Todo with id " + id + " not found"))
                    .build();
        }

        return Response.noContent().build();
    }
}

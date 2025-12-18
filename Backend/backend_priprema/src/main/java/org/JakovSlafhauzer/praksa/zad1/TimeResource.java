package org.JakovSlafhauzer.praksa.zad1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
public class TimeResource {

    @GET
    @Path("/now")
    public Map<String, String> now() {
        LocalDateTime now = LocalDateTime.now();

        Map<String, String> response = new HashMap<>();
        response.put("now", now.toString());

        return response;
    }

    @GET
    @Path("/add-days")
    public Map<String, String> addDays(@QueryParam("days") long days) {
        LocalDate date = LocalDate.now().plusDays(days);

        Map<String, String> response = new HashMap<>();
        response.put("todayPlusDays", date.toString());
        response.put("daysAdded", String.valueOf(days));

        return response;
    }

    @GET
    @Path("/diff")
    public Map<String, Object> diff(
            @QueryParam("start") String start,
            @QueryParam("end") String end
    ) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("start", startDate.toString());
        response.put("end", endDate.toString());
        response.put("daysBetween", daysBetween);

        return response;
    }
}

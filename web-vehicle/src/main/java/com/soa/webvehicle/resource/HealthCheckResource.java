package com.soa.webvehicle.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/health/check")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckResource {

    @GET
    public Response health() {
        return Response.ok("{\"status\":\"UP\"}").build();
    }
}


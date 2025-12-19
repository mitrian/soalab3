package com.soa.webvehicle.exception;

import com.soa.ejbvehicle.exception.VehicleNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class VehicleNotFoundExceptionMapper implements ExceptionMapper<VehicleNotFoundException> {
    @Override
    public Response toResponse(VehicleNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type("application/xml")
                .build();
    }
}


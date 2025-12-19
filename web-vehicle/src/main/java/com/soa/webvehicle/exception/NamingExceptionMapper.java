package com.soa.webvehicle.exception;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NamingExceptionMapper implements ExceptionMapper<NamingException> {
    @Override
    public Response toResponse(NamingException exception) {
        System.err.println("=== NamingException in Mapper ===");
        exception.printStackTrace();
        System.err.println("=================================");
        
        String message = "Failed to connect to EJB server: " + exception.getMessage();
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity("<error><message>" + escapeXml(message) + "</message></error>")
                .type("application/xml")
                .build();
    }
    
    private String escapeXml(String str) {
        if (str == null) return "";
        return str.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&apos;");
    }
}


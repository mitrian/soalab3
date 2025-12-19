package com.soa.webvehicle.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    
    @Override
    public Response toResponse(BadRequestException exception) {
        System.err.println("=== BadRequestException ===");
        System.err.println("Message: " + exception.getMessage());
        if (exception.getCause() != null) {
            System.err.println("Cause: " + exception.getCause().getMessage());
            exception.getCause().printStackTrace();
        }
        exception.printStackTrace();
        System.err.println("==========================");
        
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("INVALID_XML_FORMAT");
        
        String message = "Invalid XML format. ";
        if (exception.getMessage() != null) {
            message += exception.getMessage();
        } else if (exception.getCause() != null && exception.getCause().getMessage() != null) {
            message += exception.getCause().getMessage();
        } else {
            message += "Please check the XML structure and data correctness.";
        }
        errorResponse.setMessage(message);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_XML)
                .build();
    }

    @XmlRootElement(name = "error")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ErrorResponse {
        @XmlElement
        private String error;
        
        @XmlElement
        private String message;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


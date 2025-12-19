package com.soa.webvehicle.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Provider
public class MessageNotReadableExceptionMapper implements ExceptionMapper<javax.ws.rs.ProcessingException> {
    
    @Override
    public Response toResponse(javax.ws.rs.ProcessingException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("INVALID_XML_FORMAT");
        
        String message = "Invalid XML format. ";
        if (exception.getMessage() != null) {
            if (exception.getMessage().contains("Unexpected character")) {
                message += "Disallowed characters were detected. " +
                        "Please make sure special characters (&, <, >, \", ') are properly escaped.";
            } else {
                message += "Please check the XML structure and data correctness.";
            }
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


package com.soa.webvehicle.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }
        
        ErrorMapResponse errorResponse = new ErrorMapResponse();
        errorResponse.setErrors(errors);
        
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type("application/xml")
                .build();
    }

    @XmlRootElement(name = "errors")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ErrorMapResponse {
        @XmlElement
        private Map<String, String> errors = new HashMap<>();

        public Map<String, String> getErrors() {
            return errors;
        }

        public void setErrors(Map<String, String> errors) {
            this.errors = errors;
        }
    }
}


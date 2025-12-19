package com.soa.webvehicle.exception;

import com.soa.ejbvehicle.exception.VehicleNotFoundException;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.UndeclaredThrowableException;

@Provider
public class UndeclaredThrowableExceptionMapper implements ExceptionMapper<UndeclaredThrowableException> {
    @Override
    public Response toResponse(UndeclaredThrowableException exception) {
        System.err.println("=== UndeclaredThrowableException ===");
        System.err.println("Message: " + exception.getMessage());
        
        // Dig deeper into the exception chain
        Throwable cause = exception.getCause();
        Throwable realCause = extractRealCause(cause);
        
        if (realCause != null) {
            System.err.println("Real Cause: " + realCause.getClass().getName());
            System.err.println("Real Cause Message: " + realCause.getMessage());
            System.err.println("Real Cause StackTrace:");
            realCause.printStackTrace();
        }
        if (cause != null && cause != realCause) {
            System.err.println("Intermediate Cause: " + cause.getClass().getName());
            System.err.println("Intermediate Cause Message: " + cause.getMessage());
        }
        exception.printStackTrace();
        System.err.println("===================================");
        
        // Check if the real cause is VehicleNotFoundException
        if (realCause instanceof VehicleNotFoundException) {
            String message = realCause.getMessage();
            if (message == null || message.isEmpty()) {
                message = "Vehicle not found";
            }
            
            String errorResponse = "<error><message>" + escapeXml(message) + "</message><type>" + 
                    escapeXml(VehicleNotFoundException.class.getName()) + "</type></error>";
            
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .type("application/xml")
                    .build();
        }
        
        String message = "EJB call failed";
        String causeType = exception.getClass().getName();
        String causeMessage = "";
        
        if (realCause != null) {
            causeType = realCause.getClass().getName();
            causeMessage = realCause.getMessage();
            if (causeMessage == null || causeMessage.isEmpty()) {
                message = "EJB call failed: " + realCause.getClass().getSimpleName();
            } else {
                message = causeMessage;
            }
        } else if (cause != null) {
            causeType = cause.getClass().getName();
            causeMessage = cause.getMessage();
            if (causeMessage == null || causeMessage.isEmpty()) {
                message = "EJB call failed: " + cause.getClass().getSimpleName();
            } else {
                message = causeMessage;
            }
        }
        
        String errorResponse = "<error><message>" + escapeXml(message) + "</message><type>" + 
                escapeXml(exception.getClass().getName());
        if (realCause != null) {
            errorResponse += "</type><cause>" + escapeXml(causeType) + 
                    ": " + escapeXml(causeMessage != null ? causeMessage : "null") + "</cause>";
            if (realCause.getCause() != null) {
                errorResponse += "<rootCause>" + escapeXml(realCause.getCause().getClass().getName()) + 
                        ": " + escapeXml(realCause.getCause().getMessage() != null ? realCause.getCause().getMessage() : "null") + "</rootCause>";
            }
            errorResponse += "</error>";
        } else if (cause != null) {
            errorResponse += "</type><cause>" + escapeXml(causeType) + 
                    ": " + escapeXml(causeMessage != null ? causeMessage : "null") + "</cause></error>";
        } else {
            errorResponse += "</type></error>";
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type("application/xml")
                .build();
    }
    
    private Throwable extractRealCause(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        
        // If it's InvocationTargetException, get the target exception
        if (throwable instanceof java.lang.reflect.InvocationTargetException) {
            Throwable target = ((java.lang.reflect.InvocationTargetException) throwable).getTargetException();
            if (target != null) {
                return extractRealCause(target);
            }
        }
        
        // If it's EJBException, get the cause
        if (throwable instanceof EJBException) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                return extractRealCause(cause);
            }
        }
        
        // If it's a NamingException with a cause, dig deeper
        if (throwable instanceof javax.naming.NamingException) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                return extractRealCause(cause);
            }
        }
        
        // Return the throwable itself if no deeper cause found
        return throwable;
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


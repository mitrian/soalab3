package com.soa.webvehicle.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        // Логируем полный стек трейс для отладки
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String stackTrace = sw.toString();
        
        System.err.println("=== EXCEPTION ===");
        System.err.println("Message: " + exception.getMessage());
        System.err.println("Class: " + exception.getClass().getName());
        
        // Извлекаем реальное исключение из UndeclaredThrowableException
        Throwable cause = exception.getCause();
        if (cause != null) {
            System.err.println("Cause: " + cause.getClass().getName());
            System.err.println("Cause Message: " + cause.getMessage());
            System.err.println("Cause StackTrace:");
            cause.printStackTrace();
        }
        
        System.err.println("StackTrace:");
        System.err.println(stackTrace);
        System.err.println("================");
        
        // Используем cause для более информативного сообщения
        String message = exception.getMessage();
        if (message == null || message.isEmpty()) {
            if (cause != null && cause.getMessage() != null) {
                message = cause.getMessage();
            } else {
                message = "Internal server error: " + exception.getClass().getSimpleName();
            }
        }
        
        // Включаем информацию о cause в ответ
        String errorResponse = "<error><message>" + escapeXml(message) + "</message><type>" + 
                exception.getClass().getName();
        if (cause != null) {
            errorResponse += "</type><cause>" + escapeXml(cause.getClass().getName()) + 
                    ": " + escapeXml(cause.getMessage() != null ? cause.getMessage() : "null") + "</cause></error>";
        } else {
            errorResponse += "</type></error>";
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
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


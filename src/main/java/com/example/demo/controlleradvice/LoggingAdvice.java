package com.example.demo.controlleradvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.ContentCachingRequestWrapper;

@RestControllerAdvice
public class LoggingAdvice {

    @ModelAttribute
    public void logRequest( HttpServletRequest request) throws IOException {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            return; // Ensure the request is wrapped
        }

        ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;

        logRequestDetails(cachingRequest);
    }

    private void logRequestDetails(ContentCachingRequestWrapper request) throws IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryParams = extractQueryParams(request);
        String headers = extractHeaders(request);
        String body = extractBody(request);

        System.out.println("Request Log:");
        System.out.println("Method: " + method);
        System.out.println("URI: " + uri);
        System.out.println("Query Params: " + queryParams);
        System.out.println("Headers: " + headers);
        System.out.println("Body: " + body);
    }

    private String extractBody(ContentCachingRequestWrapper request) throws UnsupportedEncodingException
    {
        byte[] content = request.getContentAsByteArray();
        return content.length > 0 ? new String(content, request.getCharacterEncoding()) : "<empty>";
    }

    private String extractQueryParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> queryParams = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            queryParams.put(entry.getKey(), String.join(",", entry.getValue()));
        }
        return queryParams.toString();
    }

    private String extractHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        return headers.toString();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.status(500).body("Error: " + ex.getMessage());
    }
}

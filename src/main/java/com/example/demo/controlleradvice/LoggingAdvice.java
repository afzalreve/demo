package com.example.demo.controlleradvice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class LoggingAdvice {

    @ModelAttribute
    public void logRequest(HttpServletRequest request) throws IOException {
        // Wrap the request to cache the body if not already wrapped
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        logRequestDetails((ContentCachingRequestWrapper) request);
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

    private String extractBody(ContentCachingRequestWrapper request) throws IOException {
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

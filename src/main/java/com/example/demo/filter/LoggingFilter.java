package com.example.demo.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

//@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Wrap request and response to cache their contents
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpServletResponse);

        // Continue with the filter chain
        chain.doFilter(wrappedRequest, wrappedResponse);

        // Log API Endpoint
        String method = wrappedRequest.getMethod();
        String uri = wrappedRequest.getRequestURI();
        System.out.println("HTTP Method: " + method);
        System.out.println("API Endpoint: " + uri);

        // Log Query Parameters
        String queryParams = wrappedRequest.getQueryString();
        System.out.println("Query Parameters: " + (queryParams != null ? queryParams : "None"));

        // Log Headers
        System.out.println("Headers: ");
        Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = wrappedRequest.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }

        // Log Client IP
        String clientIp = wrappedRequest.getRemoteAddr();
        System.out.println("Client IP: " + clientIp);

        // Log Request Body
        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + (requestBody.isEmpty() ? "None" : requestBody));

        // Log Response Status and Body
        int status = wrappedResponse.getStatus();
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("Response Status: " + status);
        System.out.println("Response Body: " + (responseBody.isEmpty() ? "None" : responseBody));

        // Copy response content back to the original response
        wrappedResponse.copyBodyToResponse();
    }
}

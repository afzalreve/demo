package com.example.demo.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
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
        logger.info("HTTP Method: {}", method);
        logger.info("API Endpoint: {}", uri);

        // Log Query Parameters
        String queryParams = wrappedRequest.getQueryString();
        logger.info("Query Parameters: {}", queryParams != null ? queryParams : "None");

        // Log Headers
        logger.info("Headers: ");
        Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = wrappedRequest.getHeader(headerName);
            logger.info("{}: {}", headerName, headerValue);
        }

        // Log Client IP
        String clientIp = wrappedRequest.getRemoteAddr();
        logger.info("Client IP: {}", clientIp);

        // Log Request Body
        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        logger.info("Request Body: {}", requestBody.isEmpty() ? "None" : requestBody);

        // Log HttpSession Details
        HttpSession session = wrappedRequest.getSession(false); // Use false to avoid creating a new session
        if (session != null) {
            logger.info("Session ID: {}", session.getId());
            logger.info("Session Creation Time: {}", session.getCreationTime());
            logger.info("Session Last Accessed Time: {}", session.getLastAccessedTime());
            logger.info("Session Attributes:");
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                logger.info("{}: {}", attributeName, attributeValue);
            }
        } else {
            logger.info("No active session.");
        }

        // Log Response Status and Body
        int status = wrappedResponse.getStatus();
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);
        logger.info("Response Status: {}", status);
        logger.info("Response Body: {}", responseBody.isEmpty() ? "None" : responseBody);

        // Copy response content back to the original response
        wrappedResponse.copyBodyToResponse();
    }
}

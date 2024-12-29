package com.example.demo.config;

import com.example.demo.interceptor.AuditInterceptor;
import com.example.demo.util.CachedBodyHttpServletRequest;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuditInterceptor auditInterceptor;

    public WebConfig(AuditInterceptor auditInterceptor) {
        this.auditInterceptor = auditInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor)
                .addPathPatterns("/api/users/**");
    }

    // Filter to wrap the request
    @Bean
    public Filter cachedBodyRequestFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
            {
                if (!(request instanceof CachedBodyHttpServletRequest)) {
                    HttpServletRequest cachedBodyRequest = new CachedBodyHttpServletRequest(request);
                    filterChain.doFilter(cachedBodyRequest, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        };
    }
}

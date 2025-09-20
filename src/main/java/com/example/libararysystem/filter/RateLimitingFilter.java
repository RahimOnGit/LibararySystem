package com.example.libararysystem.filter;

import com.example.libararysystem.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.util.RateLimiter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    public RateLimitingFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }


    @Override
    protected void doFilterInternal (HttpServletRequest request , HttpServletResponse response , FilterChain filterChain)
            throws ServletException , IOException
    {
        String clientIP = rateLimitService.getClientIP(request);

        if (!rateLimitService.isAllowed(clientIP)) {
            response.setStatus(429);
            response.getWriter().println("Too many requests ..please try again later.");
        return;
        }
        filterChain.doFilter(request, response);
    }
}

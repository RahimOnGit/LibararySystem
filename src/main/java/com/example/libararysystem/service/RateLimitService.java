package com.example.libararysystem.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RateLimitService {
    private final Map<String, List<Long>> requestTimes = new HashMap<>();

    private final int MAX_REQUESTS = 5; //5 req
    private final long TIME_WINDOW = 60000;

    public boolean isAllowed(String clientIP) {
        long now = System.currentTimeMillis();

        //get or create list of time request for this ip
        List<Long> times = requestTimes.getOrDefault(clientIP,new ArrayList<>());

        //remove timestamp older than 1 min
        times.removeIf(time->now-time>TIME_WINDOW);

        if(times.size()>MAX_REQUESTS) {
            return false; //too many reqs
        }

        times.add(now);
        requestTimes.put(clientIP,times);
        return true;

    }

    public String getClientIP(HttpServletRequest request) {
        String forwarded = request.getHeader("x-forwarded-for");
        if(forwarded!=null)
        {
            return forwarded.split(",")[0];
        }
        return request.getRemoteAddr();
    }

}

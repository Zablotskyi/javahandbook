package com.example.javahandbook.interceptor;

import com.example.javahandbook.service.SiteStatisticsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class VisitCounterInterceptor implements HandlerInterceptor {

    private final SiteStatisticsService siteStatisticsService;

    public VisitCounterInterceptor(SiteStatisticsService siteStatisticsService) {
        this.siteStatisticsService = siteStatisticsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String uri = request.getRequestURI();

        if (shouldCountVisit(uri)) {
            siteStatisticsService.incrementVisitsCount();
        }

        return true;
    }

    private boolean shouldCountVisit(String uri) {
        return !uri.startsWith("/css/")
                && !uri.startsWith("/js/")
                && !uri.startsWith("/images/")
                && !uri.startsWith("/uploads/")
                && !uri.startsWith("/admin")
                && !uri.startsWith("/login")
                && !uri.startsWith("/logout")
                && !uri.equals("/favicon.ico");
    }
}
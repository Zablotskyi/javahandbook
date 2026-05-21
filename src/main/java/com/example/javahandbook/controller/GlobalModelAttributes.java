package com.example.javahandbook.controller;

import com.example.javahandbook.model.MenuSection;
import com.example.javahandbook.service.ArticleService;
import com.example.javahandbook.service.SiteStatisticsService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final ArticleService articleService;
    private final SiteStatisticsService siteStatisticsService;

    public GlobalModelAttributes(ArticleService articleService,
                                 SiteStatisticsService siteStatisticsService) {
        this.articleService = articleService;
        this.siteStatisticsService = siteStatisticsService;
    }

    @ModelAttribute("menuSections")
    public List<MenuSection> menuSections() {
        return articleService.getMenuSections();
    }

    @ModelAttribute("visitsCount")
    public Long visitsCount() {
        return siteStatisticsService.getVisitsCount();
    }
}
package com.example.javahandbook.controller;

import com.example.javahandbook.model.MenuSection;
import com.example.javahandbook.service.ArticleService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final ArticleService articleService;

    public GlobalModelAttributes(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("menuSections")
    public List<MenuSection> menuSections() {
        return articleService.getMenuSections();
    }
}
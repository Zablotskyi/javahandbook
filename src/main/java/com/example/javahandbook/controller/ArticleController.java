package com.example.javahandbook.controller;

import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/{slug}")
    public String article(@PathVariable String slug, Model model) {
        ArticleData article = articleService.findBySlug(slug);

        if (article == null) {
            return "redirect:/";
        }

        model.addAttribute("title", article.title());
        model.addAttribute("description", article.description());
        model.addAttribute("content", article.content());
        model.addAttribute("activePage", article.slug());

        return "article";
    }
}
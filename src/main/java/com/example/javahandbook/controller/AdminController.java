package com.example.javahandbook.controller;

import com.example.javahandbook.dto.ArticleForm;
import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final ArticleService articleService;

    public AdminController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("activePage", "admin");
        model.addAttribute("articleForm", new ArticleForm());
        return "admin";
    }

    @PostMapping("/admin/articles")
    public String addArticle(ArticleForm articleForm) {

        String slug = normalizeSlug(articleForm.getSlug());

        ArticleData articleData = new ArticleData(
                articleForm.getTitle(),
                articleForm.getDescription(),
                slug,
                articleForm.getMenuGroup(),
                articleForm.getContent()
        );

        articleService.addArticle(articleData);

        return "redirect:/articles/" + slug;
    }

    private String normalizeSlug(String slug) {
        return slug
                .trim()
                .toLowerCase()
                .replace(" ", "-");
    }
}
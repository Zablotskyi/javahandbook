package com.example.javahandbook.controller;

import com.example.javahandbook.dto.CommentForm;
import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.service.ArticleCommentService;
import com.example.javahandbook.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleCommentService articleCommentService;

    public ArticleController(ArticleService articleService,
                             ArticleCommentService articleCommentService) {
        this.articleService = articleService;
        this.articleCommentService = articleCommentService;
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

        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("comments", articleCommentService.findApprovedCommentTreeByArticleSlug(slug));

        return "article";
    }

    @PostMapping("/articles/{slug}/comments")
    public String addComment(@PathVariable String slug,
                             CommentForm commentForm,
                             RedirectAttributes redirectAttributes) {
        ArticleData article = articleService.findBySlug(slug);

        if (article == null) {
            return "redirect:/";
        }

        articleCommentService.addComment(slug, commentForm);

        redirectAttributes.addFlashAttribute(
                "commentMessage",
                "Дякуємо! Ваш коментар відправлено на модерацію."
        );

        return "redirect:/articles/" + slug + "#comments";
    }
}
package com.example.javahandbook.controller;

import com.example.javahandbook.dto.ArticleForm;
import com.example.javahandbook.dto.ArticleListItem;
import com.example.javahandbook.dto.MenuGroupForm;
import com.example.javahandbook.entity.MenuGroup;
import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.service.ArticleCommentService;
import com.example.javahandbook.service.ArticleService;
import com.example.javahandbook.service.MenuGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {

    private final ArticleService articleService;
    private final MenuGroupService menuGroupService;
    private final ArticleCommentService articleCommentService;

    public AdminController(ArticleService articleService,
                           MenuGroupService menuGroupService,
                           ArticleCommentService articleCommentService) {
        this.articleService = articleService;
        this.menuGroupService = menuGroupService;
        this.articleCommentService = articleCommentService;
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("activePage", "");
        model.addAttribute("activeAdminPage", "dashboard");

        model.addAttribute("articlesCount", getArticleListItems().size());
        model.addAttribute("menuGroupsCount", menuGroupService.findAll().size());
        model.addAttribute("pendingCommentsCount", articleCommentService.findPendingComments().size());
        model.addAttribute("approvedCommentsCount", articleCommentService.findApprovedComments().size());

        return "admin";
    }

    @GetMapping("/admin/articles")
    public String adminArticles(Model model) {
        model.addAttribute("activePage", "");
        model.addAttribute("activeAdminPage", "articles");

        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("articles", getArticleListItems());
        model.addAttribute("menuGroups", menuGroupService.findAll());

        return "admin-articles";
    }

    @GetMapping("/admin/articles/edit/{slug}")
    public String editArticle(@PathVariable String slug, Model model) {
        ArticleData article = articleService.findBySlug(slug);

        if (article == null) {
            return "redirect:/admin/articles";
        }

        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle(article.title());
        articleForm.setDescription(article.description());
        articleForm.setSlug(article.slug());
        articleForm.setContent(article.content());
        articleForm.setMenuGroup(article.menuGroup());

        model.addAttribute("activePage", "");
        model.addAttribute("activeAdminPage", "articles");
        model.addAttribute("articleForm", articleForm);
        model.addAttribute("editingSlug", article.slug());
        model.addAttribute("articles", getArticleListItems());
        model.addAttribute("menuGroups", menuGroupService.findAll());

        return "admin-articles";
    }

    @PostMapping("/admin/articles")
    public String saveArticle(ArticleForm articleForm) {
        ArticleData articleData = new ArticleData(
                articleForm.getTitle(),
                articleForm.getDescription(),
                articleForm.getSlug(),
                articleForm.getMenuGroup(),
                articleForm.getContent()
        );

        articleService.addArticle(articleData);

        return "redirect:/admin/articles";
    }

    @PostMapping("/admin/articles/update/{slug}")
    public String updateArticle(@PathVariable String slug,
                                ArticleForm articleForm) {
        ArticleData articleData = new ArticleData(
                articleForm.getTitle(),
                articleForm.getDescription(),
                articleForm.getSlug(),
                articleForm.getMenuGroup(),
                articleForm.getContent()
        );

        articleService.updateArticle(slug, articleData);

        return "redirect:/admin/articles";
    }

    @PostMapping("/admin/articles/delete/{slug}")
    public String deleteArticle(@PathVariable String slug) {
        articleService.deleteBySlug(slug);
        return "redirect:/admin/articles";
    }

    @GetMapping("/admin/menu-groups")
    public String adminMenuGroups(Model model) {
        model.addAttribute("activePage", "");
        model.addAttribute("activeAdminPage", "menu");

        model.addAttribute("menuGroupForm", new MenuGroupForm());
        model.addAttribute("menuGroups", menuGroupService.findAll());

        return "admin-menu-groups";
    }

    @GetMapping("/admin/menu-groups/edit/{code}")
    public String editMenuGroup(@PathVariable String code, Model model) {
        MenuGroup menuGroup = menuGroupService.findByCode(code);

        if (menuGroup == null) {
            return "redirect:/admin/menu-groups";
        }

        MenuGroupForm menuGroupForm = new MenuGroupForm();
        menuGroupForm.setCode(menuGroup.getCode());
        menuGroupForm.setTitle(menuGroup.getTitle());
        menuGroupForm.setSortOrder(menuGroup.getSortOrder());

        model.addAttribute("activePage", "");
        model.addAttribute("activeAdminPage", "menu");
        model.addAttribute("menuGroupForm", menuGroupForm);
        model.addAttribute("editingMenuGroupCode", menuGroup.getCode());
        model.addAttribute("menuGroups", menuGroupService.findAll());

        return "admin-menu-groups";
    }

    @PostMapping("/admin/menu-groups")
    public String saveMenuGroup(MenuGroupForm menuGroupForm) {
        menuGroupService.addMenuGroup(
                menuGroupForm.getCode(),
                menuGroupForm.getTitle(),
                menuGroupForm.getSortOrder()
        );

        return "redirect:/admin/menu-groups";
    }

    @PostMapping("/admin/menu-groups/update/{code}")
    public String updateMenuGroup(@PathVariable String code,
                                  MenuGroupForm menuGroupForm) {
        menuGroupService.updateMenuGroup(
                code,
                menuGroupForm.getCode(),
                menuGroupForm.getTitle(),
                menuGroupForm.getSortOrder()
        );

        return "redirect:/admin/menu-groups";
    }

    @PostMapping("/admin/menu-groups/delete/{code}")
    public String deleteMenuGroup(@PathVariable String code) {
        menuGroupService.deleteMenuGroupIfEmpty(code);
        return "redirect:/admin/menu-groups";
    }

    @GetMapping("/admin/comments")
    public String adminComments(Model model) {
        model.addAttribute("activePage", "");
        model.addAttribute("activeAdminPage", "comments");

        model.addAttribute("pendingComments", articleCommentService.findPendingComments());
        model.addAttribute("approvedComments", articleCommentService.findApprovedComments());

        return "admin-comments";
    }

    @PostMapping("/admin/comments/approve/{id}")
    public String approveComment(@PathVariable Long id) {
        articleCommentService.approveComment(id);
        return "redirect:/admin/comments";
    }

    @PostMapping("/admin/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        articleCommentService.deleteComment(id);
        return "redirect:/admin/comments";
    }

    private List<ArticleListItem> getArticleListItems() {
        return articleService.findAll()
                .stream()
                .map(article -> new ArticleListItem(
                        article.title(),
                        article.description(),
                        article.slug(),
                        article.menuGroup(),
                        articleService.getMenuGroupTitle(article.menuGroup())
                ))
                .toList();
    }
}
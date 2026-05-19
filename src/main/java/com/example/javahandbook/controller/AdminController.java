package com.example.javahandbook.controller;

import com.example.javahandbook.dto.ArticleForm;
import com.example.javahandbook.dto.ArticleListItem;
import com.example.javahandbook.dto.MenuGroupForm;
import com.example.javahandbook.entity.MenuGroup;
import com.example.javahandbook.model.ArticleData;
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

    public AdminController(ArticleService articleService,
                           MenuGroupService menuGroupService) {
        this.articleService = articleService;
        this.menuGroupService = menuGroupService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("activePage", "admin");
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("menuGroupForm", new MenuGroupForm());
        model.addAttribute("editMode", false);
        model.addAttribute("menuGroupEditMode", false);
        addAdminModelAttributes(model);

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

        return "redirect:/admin";
    }

    @GetMapping("/admin/articles/edit/{slug}")
    public String editArticle(@PathVariable String slug, Model model) {
        ArticleData article = articleService.findBySlug(slug);

        if (article == null) {
            return "redirect:/admin";
        }

        ArticleForm articleForm = new ArticleForm();
        articleForm.setOriginalSlug(article.slug());
        articleForm.setTitle(article.title());
        articleForm.setSlug(article.slug());
        articleForm.setDescription(article.description());
        articleForm.setMenuGroup(article.menuGroup());
        articleForm.setContent(article.content());

        model.addAttribute("activePage", "admin");
        model.addAttribute("articleForm", articleForm);
        model.addAttribute("menuGroupForm", new MenuGroupForm());
        model.addAttribute("editMode", true);
        model.addAttribute("menuGroupEditMode", false);
        addAdminModelAttributes(model);

        return "admin";
    }

    @PostMapping("/admin/articles/update")
    public String updateArticle(ArticleForm articleForm) {
        String originalSlug = normalizeSlug(articleForm.getOriginalSlug());
        String newSlug = normalizeSlug(articleForm.getSlug());

        ArticleData articleData = new ArticleData(
                articleForm.getTitle(),
                articleForm.getDescription(),
                newSlug,
                articleForm.getMenuGroup(),
                articleForm.getContent()
        );

        articleService.updateArticle(originalSlug, articleData);

        return "redirect:/admin";
    }

    @PostMapping("/admin/articles/delete/{slug}")
    public String deleteArticle(@PathVariable String slug) {
        articleService.deleteBySlug(slug);
        return "redirect:/admin";
    }

    @PostMapping("/admin/menu-groups")
    public String addMenuGroup(MenuGroupForm menuGroupForm) {
        menuGroupService.addMenuGroup(
                menuGroupForm.getCode(),
                menuGroupForm.getTitle(),
                menuGroupForm.getSortOrder()
        );

        return "redirect:/admin";
    }

    @GetMapping("/admin/menu-groups/edit/{code}")
    public String editMenuGroup(@PathVariable String code, Model model) {
        MenuGroup menuGroup = menuGroupService.findByCode(code);

        if (menuGroup == null) {
            return "redirect:/admin";
        }

        MenuGroupForm menuGroupForm = new MenuGroupForm();
        menuGroupForm.setOriginalCode(menuGroup.getCode());
        menuGroupForm.setCode(menuGroup.getCode());
        menuGroupForm.setTitle(menuGroup.getTitle());
        menuGroupForm.setSortOrder(menuGroup.getSortOrder());

        model.addAttribute("activePage", "admin");
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("menuGroupForm", menuGroupForm);
        model.addAttribute("editMode", false);
        model.addAttribute("menuGroupEditMode", true);
        addAdminModelAttributes(model);

        return "admin";
    }

    @PostMapping("/admin/menu-groups/update")
    public String updateMenuGroup(MenuGroupForm menuGroupForm) {
        menuGroupService.updateMenuGroup(
                menuGroupForm.getOriginalCode(),
                menuGroupForm.getCode(),
                menuGroupForm.getTitle(),
                menuGroupForm.getSortOrder()
        );

        return "redirect:/admin";
    }

    @PostMapping("/admin/menu-groups/delete/{code}")
    public String deleteMenuGroup(@PathVariable String code, Model model) {
        boolean deleted = menuGroupService.deleteMenuGroupIfEmpty(code);

        if (!deleted) {
            model.addAttribute("activePage", "admin");
            model.addAttribute("articleForm", new ArticleForm());
            model.addAttribute("menuGroupForm", new MenuGroupForm());
            model.addAttribute("editMode", false);
            model.addAttribute("menuGroupEditMode", false);
            model.addAttribute("errorMessage", "Неможливо видалити розділ, тому що в ньому є статті. Спочатку перемістіть або видаліть статті цього розділу.");
            addAdminModelAttributes(model);

            return "admin";
        }

        return "redirect:/admin";
    }

    private void addAdminModelAttributes(Model model) {
        model.addAttribute("articles", getArticleListItems());
        model.addAttribute("menuGroups", menuGroupService.findAll());
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

    private String normalizeSlug(String slug) {
        return slug
                .trim()
                .toLowerCase()
                .replace(" ", "-");
    }
}
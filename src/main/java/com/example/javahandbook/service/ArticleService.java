package com.example.javahandbook.service;

import com.example.javahandbook.entity.Article;
import com.example.javahandbook.entity.MenuGroup;
import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.model.MenuSection;
import com.example.javahandbook.repository.ArticleRepository;
import com.example.javahandbook.repository.MenuGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MenuGroupRepository menuGroupRepository;

    public ArticleService(ArticleRepository articleRepository,
                          MenuGroupRepository menuGroupRepository) {
        this.articleRepository = articleRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public ArticleData findBySlug(String slug) {
        return articleRepository.findBySlug(slug)
                .map(this::toArticleData)
                .orElse(null);
    }

    public List<ArticleData> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(this::toArticleData)
                .toList();
    }

    public void addArticle(ArticleData articleData) {
        Article article = new Article(
                articleData.title(),
                articleData.slug(),
                articleData.description(),
                articleData.menuGroup(),
                articleData.content()
        );

        articleRepository.save(article);
    }

    @Transactional
    public void updateArticle(String originalSlug, ArticleData articleData) {
        Article article = articleRepository.findBySlug(originalSlug)
                .orElse(null);

        if (article == null) {
            addArticle(articleData);
            return;
        }

        article.setTitle(articleData.title());
        article.setSlug(articleData.slug());
        article.setDescription(articleData.description());
        article.setMenuGroup(articleData.menuGroup());
        article.setContent(articleData.content());

        articleRepository.save(article);
    }

    @Transactional
    public void deleteBySlug(String slug) {
        articleRepository.deleteBySlug(slug);
    }

    public String getMenuGroupTitle(String menuGroupCode) {
        return menuGroupRepository.findByCode(menuGroupCode)
                .map(MenuGroup::getTitle)
                .orElse(menuGroupCode);
    }

    public List<MenuSection> getMenuSections() {
        List<MenuSection> sections = new ArrayList<>();
        List<ArticleData> allArticles = findAll();

        List<MenuGroup> menuGroups = menuGroupRepository.findAllByOrderBySortOrderAscTitleAsc();

        for (MenuGroup menuGroup : menuGroups) {
            String groupCode = menuGroup.getCode();
            String groupTitle = menuGroup.getTitle();

            List<ArticleData> groupArticles = allArticles.stream()
                    .filter(article -> article.menuGroup().equals(groupCode))
                    .toList();

            sections.add(new MenuSection(groupCode, groupTitle, groupArticles));
        }

        return sections;
    }

    private ArticleData toArticleData(Article article) {
        return new ArticleData(
                article.getTitle(),
                article.getDescription(),
                article.getSlug(),
                article.getMenuGroup(),
                article.getContent()
        );
    }
}
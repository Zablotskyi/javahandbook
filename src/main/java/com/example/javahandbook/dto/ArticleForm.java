package com.example.javahandbook.dto;

public class ArticleForm {

    private String title;
    private String slug;
    private String description;
    private String menuGroup;
    private String content;

    public ArticleForm() {
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getDescription() {
        return description;
    }

    public String getMenuGroup() {
        return menuGroup;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMenuGroup(String menuGroup) {
        this.menuGroup = menuGroup;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
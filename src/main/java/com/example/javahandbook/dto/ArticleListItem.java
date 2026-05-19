package com.example.javahandbook.dto;

public record ArticleListItem(
        String title,
        String description,
        String slug,
        String menuGroup,
        String menuGroupTitle
) {
}
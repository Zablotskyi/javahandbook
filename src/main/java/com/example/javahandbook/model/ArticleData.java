package com.example.javahandbook.model;

public record ArticleData(
        String title,
        String description,
        String slug,
        String menuGroup,
        String content
) {
}
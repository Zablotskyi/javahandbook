package com.example.javahandbook.model;

import java.util.List;

public record MenuSection(
        String code,
        String title,
        List<ArticleData> articles
) {
}
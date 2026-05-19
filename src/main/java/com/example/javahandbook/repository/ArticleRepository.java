package com.example.javahandbook.repository;

import com.example.javahandbook.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findBySlug(String slug);

    void deleteBySlug(String slug);

    long countByMenuGroup(String menuGroup);

    @Modifying
    @Query("update Article a set a.menuGroup = :newCode where a.menuGroup = :oldCode")
    void updateMenuGroupCode(String oldCode, String newCode);
}
package com.example.javahandbook.repository;

import com.example.javahandbook.entity.ArticleComment;
import com.example.javahandbook.entity.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    List<ArticleComment> findAllByArticleSlugAndStatusOrderByCreatedAtAsc(
            String articleSlug,
            CommentStatus status
    );

    List<ArticleComment> findAllByStatusOrderByCreatedAtDesc(CommentStatus status);
}
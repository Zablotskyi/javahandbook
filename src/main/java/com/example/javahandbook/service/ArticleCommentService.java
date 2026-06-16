package com.example.javahandbook.service;

import com.example.javahandbook.dto.CommentForm;
import com.example.javahandbook.dto.CommentNode;
import com.example.javahandbook.entity.ArticleComment;
import com.example.javahandbook.entity.CommentStatus;
import com.example.javahandbook.model.ArticleData;
import com.example.javahandbook.repository.ArticleCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;
    private final ArticleService articleService;
    private final EmailNotificationService emailNotificationService;

    public ArticleCommentService(ArticleCommentRepository articleCommentRepository,
                                 ArticleService articleService,
                                 EmailNotificationService emailNotificationService) {
        this.articleCommentRepository = articleCommentRepository;
        this.articleService = articleService;
        this.emailNotificationService = emailNotificationService;
    }

    public List<CommentNode> findApprovedCommentTreeByArticleSlug(String articleSlug) {
        List<ArticleComment> approvedComments =
                articleCommentRepository.findAllByArticleSlugAndStatusOrderByCreatedAtAsc(
                        articleSlug,
                        CommentStatus.APPROVED
                );

        Map<Long, CommentNode> nodesById = new LinkedHashMap<>();
        List<CommentNode> rootComments = new ArrayList<>();

        for (ArticleComment comment : approvedComments) {
            nodesById.put(comment.getId(), new CommentNode(comment));
        }

        for (ArticleComment comment : approvedComments) {
            CommentNode currentNode = nodesById.get(comment.getId());
            Long parentCommentId = comment.getParentCommentId();

            if (parentCommentId == null || !nodesById.containsKey(parentCommentId)) {
                rootComments.add(currentNode);
            } else {
                nodesById.get(parentCommentId).addReply(currentNode);
            }
        }

        return rootComments;
    }

    public List<ArticleComment> findPendingComments() {
        return articleCommentRepository.findAllByStatusOrderByCreatedAtDesc(CommentStatus.PENDING);
    }

    public List<ArticleComment> findApprovedComments() {
        return articleCommentRepository.findAllByStatusOrderByCreatedAtDesc(CommentStatus.APPROVED);
    }

    public void addComment(String articleSlug, CommentForm commentForm) {
        String authorName = normalizeAuthorName(commentForm.getAuthorName());
        String content = normalizeContent(commentForm.getContent());
        Long parentCommentId = normalizeParentCommentId(articleSlug, commentForm.getParentCommentId());

        if (authorName.isBlank() || content.isBlank()) {
            return;
        }

        ArticleComment comment = new ArticleComment(
                articleSlug,
                parentCommentId,
                authorName,
                content
        );

        comment.setStatus(CommentStatus.PENDING);

        ArticleComment savedComment = articleCommentRepository.save(comment);

        ArticleData article = articleService.findBySlug(articleSlug);

        if (article != null) {
            emailNotificationService.sendNewCommentNotification(article, savedComment);
        }
    }

    @Transactional
    public void approveComment(Long commentId) {
        ArticleComment comment = articleCommentRepository.findById(commentId)
                .orElse(null);

        if (comment == null) {
            return;
        }

        comment.setStatus(CommentStatus.APPROVED);
        articleCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        articleCommentRepository.deleteById(commentId);
    }

    private Long normalizeParentCommentId(String articleSlug, Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }

        ArticleComment parentComment = articleCommentRepository.findById(parentCommentId)
                .orElse(null);

        if (parentComment == null) {
            return null;
        }

        if (!parentComment.getArticleSlug().equals(articleSlug)) {
            return null;
        }

        if (parentComment.getStatus() != CommentStatus.APPROVED) {
            return null;
        }

        return parentCommentId;
    }

    private String normalizeAuthorName(String authorName) {
        if (authorName == null) {
            return "";
        }

        return authorName.trim();
    }

    private String normalizeContent(String content) {
        if (content == null) {
            return "";
        }

        return content.trim();
    }
}
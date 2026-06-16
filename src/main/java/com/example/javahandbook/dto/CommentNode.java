package com.example.javahandbook.dto;

import com.example.javahandbook.entity.ArticleComment;

import java.util.ArrayList;
import java.util.List;

public class CommentNode {

    private ArticleComment comment;
    private List<CommentNode> replies = new ArrayList<>();

    public CommentNode(ArticleComment comment) {
        this.comment = comment;
    }

    public ArticleComment getComment() {
        return comment;
    }

    public List<CommentNode> getReplies() {
        return replies;
    }

    public void addReply(CommentNode reply) {
        replies.add(reply);
    }
}
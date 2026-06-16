package com.example.javahandbook.dto;

public class CommentForm {

    private String authorName;
    private String content;
    private Long parentCommentId;

    public CommentForm() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
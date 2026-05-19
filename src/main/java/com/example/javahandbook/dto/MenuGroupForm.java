package com.example.javahandbook.dto;

public class MenuGroupForm {

    private String originalCode;
    private String code;
    private String title;
    private Integer sortOrder;

    public MenuGroupForm() {
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
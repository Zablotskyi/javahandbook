package com.example.javahandbook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "site_statistics")
public class SiteStatistics {

    @Id
    private Long id;

    private Long visitsCount;

    public SiteStatistics() {
    }

    public SiteStatistics(Long id, Long visitsCount) {
        this.id = id;
        this.visitsCount = visitsCount;
    }

    public Long getId() {
        return id;
    }

    public Long getVisitsCount() {
        return visitsCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVisitsCount(Long visitsCount) {
        this.visitsCount = visitsCount;
    }
}
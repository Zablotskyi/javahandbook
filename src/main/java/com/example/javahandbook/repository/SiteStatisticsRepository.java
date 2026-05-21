package com.example.javahandbook.repository;

import com.example.javahandbook.entity.SiteStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteStatisticsRepository extends JpaRepository<SiteStatistics, Long> {
}
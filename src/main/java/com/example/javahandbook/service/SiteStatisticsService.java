package com.example.javahandbook.service;

import com.example.javahandbook.entity.SiteStatistics;
import com.example.javahandbook.repository.SiteStatisticsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SiteStatisticsService {

    private static final Long MAIN_STATISTICS_ID = 1L;

    private final SiteStatisticsRepository siteStatisticsRepository;

    public SiteStatisticsService(SiteStatisticsRepository siteStatisticsRepository) {
        this.siteStatisticsRepository = siteStatisticsRepository;
    }

    @Transactional
    public void incrementVisitsCount() {
        SiteStatistics statistics = getOrCreateStatistics();

        Long currentCount = statistics.getVisitsCount();

        if (currentCount == null) {
            currentCount = 0L;
        }

        statistics.setVisitsCount(currentCount + 1);

        siteStatisticsRepository.save(statistics);
    }

    public Long getVisitsCount() {
        SiteStatistics statistics = getOrCreateStatistics();

        if (statistics.getVisitsCount() == null) {
            return 0L;
        }

        return statistics.getVisitsCount();
    }

    private SiteStatistics getOrCreateStatistics() {
        return siteStatisticsRepository.findById(MAIN_STATISTICS_ID)
                .orElseGet(() -> siteStatisticsRepository.save(
                        new SiteStatistics(MAIN_STATISTICS_ID, 0L)
                ));
    }
}
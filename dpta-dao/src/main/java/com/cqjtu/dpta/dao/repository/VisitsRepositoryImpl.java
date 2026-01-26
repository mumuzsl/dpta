package com.cqjtu.dpta.dao.repository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: mumu
 * @date: 2026/1/24 21:43
 */
@Service
public class VisitsRepositoryImpl implements VisitsRepository {
    @Override
    public int countByDistrIdAndDateBetween(Long distrId, LocalDateTime date, LocalDateTime date2) {
        return 0;
    }

    @Override
    public int countByUser(Long distrId, LocalDateTime date1, LocalDateTime date2) {
        return 0;
    }

    @Override
    public int countByDateBetween(LocalDateTime date, LocalDateTime date2) {
        return 0;
    }

    @Override
    public int countAllByUser(LocalDateTime date1, LocalDateTime date2) {
        return 0;
    }

    @Override
    public Visits save(Visits visits) {
        return visits;
    }
}

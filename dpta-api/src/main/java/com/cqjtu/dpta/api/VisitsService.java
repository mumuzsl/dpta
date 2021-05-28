package com.cqjtu.dpta.api;

import com.cqjtu.dpta.dao.repository.Visits;

/**
 * author: mumu
 * date: 2021/5/26
 */
public interface VisitsService {

    Visits add(Long shopId);

    Visits add(Long shopId, String userId);

    Visits add(Long distrId, Long shopId, String userId);
}

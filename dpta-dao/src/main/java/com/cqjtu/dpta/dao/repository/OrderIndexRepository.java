package com.cqjtu.dpta.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * author: mumu
 * date: 2021/5/13
 */
// @Repository
public interface OrderIndexRepository {

    // @Query("{\"bool\":{\"must\":[{\"match\":{\"distrId\":\"?0\"}},{\"match\":{\"deleted\":\"?2\"}}," +
    //         "{\"bool\":{\"should\":[{\"match\":{\"_id\":\"?1\"}},{\"match_phrase\":{\"receiver\":\"?1\"}},{\"nested\":{\"path\":\"details\",\"query\":{\"bool\":{\"should\":[{\"match_phrase\":{\"details.name\":\"?1\"}}]}}}},{\"match\":{\"phone\":\"?1\"}},{\"match_phrase\":{\"shopNm\":\"?1\"}}]}}]}}")
    Page<OrderIndex> searchByDistr(Long distrId, String keyword, Integer deleted, Pageable pageable);

    // @Query("{\"bool\":{\"should\":[{\"match\":{\"_id\":\"?0\"}},{\"match\":{\"receiver\":\"?0\"}},{\"nested\":{\"path\":\"details\",\"query\":{\"bool\":{\"should\":[{\"match_phrase\":{\"details.name\":\"?0\"}}]}}}},{\"match\":{\"phone\":\"?0\"}},{\"match_phrase\":{\"shopNm\":\"?0\"}},{\"match_phrase\":{\"distrNm\":\"?0\"}}]}})")
    Page<OrderIndex> searchAllOrder(String keyword, Pageable pageable);

    Optional<OrderIndex> findById(Long id);

    void save(OrderIndex orderIndex);

    Iterable<OrderIndex> findAll();

    void deleteById(Long id);
}

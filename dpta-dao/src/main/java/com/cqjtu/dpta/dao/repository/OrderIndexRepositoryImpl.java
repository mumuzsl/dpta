package com.cqjtu.dpta.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author: mumu
 * @date: 2026/1/24 21:31
 */
@Service
public class OrderIndexRepositoryImpl implements OrderIndexRepository {
    @Override
    public Page<OrderIndex> searchByDistr(Long distrId, String keyword, Integer deleted, Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(), pageable, 0L);
    }

    @Override
    public Page<OrderIndex> searchAllOrder(String keyword, Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(), pageable, 0L);
    }

    @Override
    public Optional<OrderIndex> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(OrderIndex orderIndex) {

    }

    @Override
    public Iterable<OrderIndex> findAll() {
        return new ArrayList<>();
    }

    @Override
    public void deleteById(Long id) {

    }
}

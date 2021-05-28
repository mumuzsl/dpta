package com.cqjtu.dpta.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * author: mumu
 * date: 2021/5/26
 */
public interface TokenRepository extends MongoRepository<Token, String> {
}

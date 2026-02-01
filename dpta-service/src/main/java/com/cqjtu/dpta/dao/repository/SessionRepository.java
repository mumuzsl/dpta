package com.cqjtu.dpta.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * author: mumu
 * date: 2021/5/26
 */
@Repository
public interface SessionRepository extends MongoRepository<Session, String> {
}

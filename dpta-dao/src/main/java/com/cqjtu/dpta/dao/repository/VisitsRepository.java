package com.cqjtu.dpta.dao.repository;

import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/21
 */
@Repository
public interface VisitsRepository extends MongoRepository<Visits, String> {

    int countByDistrIdAndDateBetween(Long distrId, LocalDateTime date, LocalDateTime date2);

    @CountQuery("{\n" +
            "    distrId: ?0,\n" +
            "    userId: {\n" +
            "        $ne: null\n" +
            "    },\n" +
            "    date: {\n" +
            "        $gt: ?1,\n" +
            "        $lt: ?2\n" +
            "    }\n" +
            "}")
    int countByUser(Long distrId, LocalDateTime date1, LocalDateTime date2);

    int countByDateBetween(LocalDateTime date, LocalDateTime date2);

    @CountQuery("{\n" +
            "    userId: {\n" +
            "        $ne: null\n" +
            "    },\n" +
            "    date: {\n" +
            "        $gt: ?0,\n" +
            "        $lt: ?1\n" +
            "    }\n" +
            "}")
    int countAllByUser(LocalDateTime date1, LocalDateTime date2);

}

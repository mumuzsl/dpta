package com.cqjtu.dpta.dao.repository;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * author: mumu
 * date: 2021/5/26
 */
@Document("session")
@Data
public class Session {

    @Indexed(unique = true)
    private String id;

    private String username;

    private Long userId;

}

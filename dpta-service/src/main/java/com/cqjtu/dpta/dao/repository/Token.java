package com.cqjtu.dpta.dao.repository;

import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/26
 */
//@Document("token")
public class Token {

    @Indexed(unique = true)
    private String username;

    private String token;

    private LocalDateTime loginTime;


}

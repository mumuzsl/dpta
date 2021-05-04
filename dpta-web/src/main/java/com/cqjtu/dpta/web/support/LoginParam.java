package com.cqjtu.dpta.web.support;

import lombok.Data;

/**
 * author: mumu
 * date: 2021/4/29
 */
@Data
public class LoginParam {
    private String token;
    private String username;
    private String password;
}

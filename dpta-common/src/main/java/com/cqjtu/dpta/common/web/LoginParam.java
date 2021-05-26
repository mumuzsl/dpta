package com.cqjtu.dpta.common.web;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;

/**
 * author: mumu
 * date: 2021/4/29
 */
@Data
public class LoginParam {
    //    private String token;
    private String nickname;
    private String username;
    private String password;

    public boolean isInvalid() {
        return StringUtils.isEmpty(username) || StringUtils.isEmpty(password);
    }

    public boolean isInvalid(Predicate<LoginParam> predicate) {
        return predicate.test(this);
    }

}

package com.cqjtu.dpta.common.web;

import com.cqjtu.dpta.common.util.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: mumu
 * date: 2021/4/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private String token;

    private String name;

    private String avatar;

    private String role;

    private Long id;

    public long id() {
        return id;
    }

    public static Info of(String token) {
        Info info = new Info();
        info.setToken(token);
        info.setId(TokenUtils.longId(token));
        return info;
    }
}

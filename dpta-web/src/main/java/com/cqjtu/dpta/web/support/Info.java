package com.cqjtu.dpta.web.support;

import com.cqjtu.dpta.web.security.TokenUtils;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * author: mumu
 * date: 2021/4/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    public Info(String token) {
        this.token = token;
    }

    public Info(Object token) {
        this((String) token);
    }

    private String token;

    private String name;

    private String avatar;

    public long longId() {
        return TokenUtils.longId(token);
    }
}

package com.cqjtu.dpta.common.web;

import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.cqjtu.dpta.common.util.TokenUtils;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.Serializable;

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

    public long longId() {
        return TokenUtils.longId(token);
    }

    public static Info of(String token) {
        Info info = new Info();
        info.setToken(token);
        return info;
    }
}

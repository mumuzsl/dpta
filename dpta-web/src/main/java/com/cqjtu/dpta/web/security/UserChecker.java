package com.cqjtu.dpta.web.security;

import java.util.Map;

/**
 * author: mumu
 * date: 2021/4/25
 */
public interface UserChecker {

    void check(MinUser muse, Map<String, Object> map);

}

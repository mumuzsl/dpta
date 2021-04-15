package com.cqjtu.dpta.common.extension;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.springframework.data.domain.Pageable;

/**
 * author: mumu
 * date: 2021/4/13
 */
public class SearchPage<T> extends Page<T> {
    private String keyword;

    public SearchPage(long current, long size) {
        super(current, size);
    }

    public SearchPage(long current, long size, String keyword) {
        super(current, size);
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static <T> SearchPage<T> toPage(Pageable pageable) {
        return new SearchPage<T>(pageable.getPageNumber(), pageable.getPageSize());
    }

    public static <T> SearchPage<T> toPage(Pageable pageable, String keyword) {
        return new SearchPage<T>(pageable.getPageNumber(), pageable.getPageSize(), keyword);
    }
}

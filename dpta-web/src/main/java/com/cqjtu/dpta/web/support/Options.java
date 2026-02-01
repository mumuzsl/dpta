package com.cqjtu.dpta.web.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: mumu
 * date: 2021/5/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Options<T> {
    public T value;

    public String label;

}

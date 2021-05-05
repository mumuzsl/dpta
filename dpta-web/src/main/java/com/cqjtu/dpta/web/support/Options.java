package com.cqjtu.dpta.web.support;

import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

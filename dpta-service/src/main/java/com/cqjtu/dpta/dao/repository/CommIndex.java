package com.cqjtu.dpta.dao.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * author: mumu
 * date: 2021/5/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Document(indexName = "comm")
public class CommIndex {

    private Long id;

    private String name;

    private Integer count;

    private BigDecimal price;

}

package com.cqjtu.dpta.dao.dto;

import com.cqjtu.dpta.dao.entity.OrderD;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * author: mumu
 * date: 2021/5/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDDto extends OrderD {
    private String goodsName;

    private String goodsCoverImg;
//    private PafComm pafComm;
//    private Sku sku;
}

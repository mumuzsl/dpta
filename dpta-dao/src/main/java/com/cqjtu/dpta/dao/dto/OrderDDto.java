package com.cqjtu.dpta.dao.dto;

import com.cqjtu.dpta.dao.entity.OrderD;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.Sku;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * author: mumu
 * date: 2021/5/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDDto extends OrderD {
    private PafComm pafComm;

    private Sku sku;
}

package com.cqjtu.dpta.dao.dto;

import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.dao.entity.Shop;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * author: mumu
 * date: 2021/5/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDto extends Order {

    private Shop shop;

    private List<OrderDDto> details;

}

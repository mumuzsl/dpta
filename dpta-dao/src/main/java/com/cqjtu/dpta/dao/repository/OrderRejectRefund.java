package com.cqjtu.dpta.dao.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * author: mumu
 * date: 2021/5/28
 */
@Data
@Document("order_reject_refund")
public class OrderRejectRefund {
    @Id
    private String id;

    private Long orderId;

    private String reason;

    private LocalDateTime updateTime;
}

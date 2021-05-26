package com.cqjtu.dpta.dao.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * author: mumu
 * date: 2021/5/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "order")
public class OrderIndex {
    private Long distrId;

    private String distrNm;

    /**
     * 编码
     */
    @Id
    private Long id;

    /**
     * 创建日期
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime datm;

    /**
     * 买家电话
     */
    private String phone;

    /**
     * 收件人
     */
    private String receiver;

    private String shopNm;

    @Field(type = FieldType.Nested)
    private List<CommIndex> details;

    private Integer deleted;

    private Integer state;

    private BigDecimal amount;

    private BigDecimal profit;
}

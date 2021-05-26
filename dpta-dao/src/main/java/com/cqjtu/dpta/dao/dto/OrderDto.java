package com.cqjtu.dpta.dao.dto;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.cqjtu.dpta.dao.entity.emus.OrderState;
import com.cqjtu.dpta.dao.support.IdSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * author: mumu
 * date: 2021/5/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("order_dto")
public class OrderDto {
    /**
     * 编码
     */
    @Id
    @JSONField(serializeUsing = IdSerialize.class)
    private Long id;

    private Long distrId;

    /**
     * 商铺编码
     */
    private Long shopId;

    /**
     * 创建日期
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime datm;

    /**
     * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
     */
    private Integer state;

    /**
     * 分润金额
     */
    private BigDecimal profit;

    /**
     * 核销日期
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime verifyTm;

    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 核销本金
     */
    private BigDecimal baseP;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 支付时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime payTime;

    /**
     * 买家电话
     */
    private String phone;

    /**
     * 收件人
     */
    private String receiver;

    private String shopNm;

    /**
     * 快递费
     */
    private BigDecimal expressMoney;

    /**
     * 快递单号
     */
    private String expressNo;

    private List<OrderDDto> details;

    private Integer deleted;

    public String getStateStr() {
        OrderState orderState = OrderState.valueOf(getState());
        return orderState == null ? "" : orderState.label();
    }

    public String getPhoneStr() {
        return DesensitizedUtil.mobilePhone(getPhone());
    }

    public String getAddressStr() {
        return StrUtil.replace(getAddress(), "\\d", m -> "*");
    }

    public String getReceiverStr() {
        String receiver = getReceiver();
        return StrUtil.hide(receiver, 1, receiver.length());
    }
}

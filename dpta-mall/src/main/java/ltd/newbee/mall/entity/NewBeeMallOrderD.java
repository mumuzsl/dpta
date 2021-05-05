package ltd.newbee.mall.entity;


import java.math.BigDecimal;

/**
 * @author ly
 * @date 2021-05-04 21:29
 */

public class NewBeeMallOrderD {
    private Long id;

    private BigDecimal price;

    private Long orderId;

    private Long commId;

    private Integer count;

    private Integer state;

    private BigDecimal profit;

    private Long skuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCommId() {
        return commId;
    }

    public void setCommId(Long commId) {
        this.commId = commId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public String toString() {
        return "NewBeeMallOrderD{" +
                "id=" + id +
                ", price=" + price +
                ", orderId=" + orderId +
                ", commId=" + commId +
                ", count=" + count +
                ", state=" + state +
                ", profit=" + profit +
                ", skuId=" + skuId +
                '}';
    }
}

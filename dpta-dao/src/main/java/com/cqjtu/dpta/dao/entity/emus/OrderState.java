package com.cqjtu.dpta.dao.entity.emus;

/**
 * author: mumu
 * date: 2021/5/10
 */
public enum OrderState {

    WAIT_PAY(0, "待支付"),//0.
    PAYED(1, "已支付"),// 1.
    //    DISTRIBUTION_FINISH(2, "已发货"), // 2.
    STOCK_FINISH(2, "已发货"),// 2
    REFUNDING(3, "退款中"),// 3
    SUCCESS(4, "交易成功"),// 4.
    OVRE_TIME_CLOSE(-1, "超时关闭"),// -1.
    HAND_CLOSE(-2, "手动关闭"),// -2.
    REFUND_CLOSE(-3, "退款关闭"),// -3.
    ;

//    static Map<String, OrderState> map = new HashMap<>(values().length);
//
//    static {
//        Arrays.stream(OrderState.values()).forEach(state -> map.put(String.valueOf(state.state), state));
//    }

    private final int state;
    private final String label;

    OrderState(int state, String label) {
        this.state = state;
        this.label = label;
    }

    public static OrderState valueOf(int state) {
        for (OrderState value : values()) {
            if (value.state == state) {
                return value;
            }
        }
        return null;
    }

    public int state() {
        return state;
    }

    public String label() {
        return label;
    }
}

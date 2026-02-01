package com.cqjtu.dpta.dao.entity.emus;

/**
 * author: mumu
 * date: 2021/5/16
 */
public enum ResveDType {

    PAYMENT(1, "付款"),
    REPAYMENT(2, "还款"),
    RECHARGE(3, "充值"),
    CASH_OUT(4, "提现"),
    COMMISSION(5, "佣金划入"),
    ;

    private final int value;
    private final String label;

    ResveDType(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static ResveDType valueOf(int type) {
        for (ResveDType value : values()) {
            if (value.value == type) {
                return value;
            }
        }
        return null;
    }

    public int value() {
        return value;
    }

    public String label() {
        return label;
    }
}

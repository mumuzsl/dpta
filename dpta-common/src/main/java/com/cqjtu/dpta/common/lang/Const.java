package com.cqjtu.dpta.common.lang;

/**
 * author: mumu
 * date: 2021/4/14
 */
public interface Const {
    // 启用
    int ENABLE = 1;

    // 禁用
    int DISABLE = 0;

    // 下架
    int OUTSELL = 0;

    // 上架
    int INSELL = 1;

    // 授信状态
    int NOT_VERIFY = 0;  // 未审核
    int REJECT = -1;     // 驳回
    int ADOPT = 1;       // 审核通过
    int FORBIDDEN = 2;   // 禁用

    int PAYMENT = 1;   // 使用授信/预备金付款
    int REPAYMENT = 2; // 还款恢复授信或使用预备金还款


    int RECHARGE = 3;    // 充值预备金
    int CASH_OUT = 4;    // 预备金提现
    int COMMISSION = 5;  // 佣金划入

    int PAID = 1;     // 已付款
    int VERIFY = 20;   // 已核销
    int SETTLE = 30;   // 已结算

    // 订单状态:
    int WAIT_PAY = 0;//0.待支付
    int PAYED = 1;// 1.已支付
    int DISTRIBUTION_FINISH = 2; // 2.配货完成
    int STOCK_FINISH = 3;// 3:出库成功
    int SUCCESS = 4;// 4.交易成功
    int CLOSE = -1;// -1.手动关闭
    int OVRE_TIME_CLOSE = -2;// -2.超时关闭
    int SHOP_CLOSE = -3;// -3.商家关闭
}


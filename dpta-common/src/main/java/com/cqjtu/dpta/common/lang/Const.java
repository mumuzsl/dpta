package com.cqjtu.dpta.common.lang;

import com.cqjtu.dpta.common.util.Status;

/**
 * author: mumu
 * date: 2021/4/14
 */
public interface Const {
    int ENABLE = 1;
    int DISABLE = 0;
    int OUTSELL = 0;
    int INSELL = 1;

    // 授信状态
    int NOT_VERIFY = 0;  // 未审核
    int REJECT = -1;     // 驳回
    int ADOPT = 1;       // 审核通过
    int FORBIDDEN = 2;   // 禁用

    // 授信明细状态
    int PAYMENT = 1;   // 使用授信付款
    int REPAYMENT = 2; // 还款恢复授信

}

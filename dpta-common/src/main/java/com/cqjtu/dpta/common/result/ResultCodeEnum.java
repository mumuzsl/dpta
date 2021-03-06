package com.cqjtu.dpta.common.result;

/**
 * 统一返回结果状态信息类
 * author: mumu
 * date: 2021/4/12
 */
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    PARAM_ERROR(202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_UPDATE_ERROR(205, "数据版本异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),

    CODE_ERROR(210, "验证码错误"),
    //    LOGIN_MOBLE_ERROR(211, "账号不正确"),
    LOGIN_DISABLED_ERROR(212, "改用户已被禁用"),
    REGISTER_MOBLE_ERROR(213, "手机号已被使用"),
    LOGIN_AURH(214, "需要登录"),
    LOGIN_ACL(215, "没有权限"),

    URL_ENCODE_ERROR(216, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(217, "非法回调请求"),
    FETCH_ACCESSTOKEN_FAILD(218, "获取accessToken失败"),
    FETCH_USERINFO_ERROR(219, "获取用户信息失败"),
    //LOGIN_ERROR( 23005, "登录失败"),

    PAY_RUN(220, "支付中"),
    CANCEL_ORDER_FAIL(225, "取消订单失败"),
    CANCEL_ORDER_NO(225, "不能取消预约"),

    HOSCODE_EXIST(230, "医院编号已经存在"),
    NUMBER_NO(240, "可预约号不足"),
    TIME_NO(250, "当前时间不可以预约"),

    SIGN_ERROR(300, "签名错误"),
    HOSPITAL_OPEN(310, "医院未开通，暂时不能访问"),
    HOSPITAL_LOCK(320, "医院被锁定，暂时不能访问"),

    KEY_ERROR(4001, "关键参数错误"),
    STOCK_ERROR(4002, "库存不足"),
    OUT_SELL_ERROR(4003, "商品下架"),

    USER_ERROR(50000, "与用户相关的错误"),
    USER_NOT_FOUNT_USER(50001, "没有该用户"),
    USER_LOGIN_PARAM_ERROR(50002, "用户名或密码格式不正确"),
    USER_PASSWORD_NOT_MATCH(50003, "密码不正确"),
    USER_REGISTERED(50004, "没有token或token无效"),
    USER_PHONE_ERROR(50005, "无效手机号"),
    USER_NO_OR_IllEGAL_TOKEN(50008, "没有token或token无效"),

    DATA_BIG_DAY_ERROR(60001,"该接口不支持过大时间跨度查询"),
    ;

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}


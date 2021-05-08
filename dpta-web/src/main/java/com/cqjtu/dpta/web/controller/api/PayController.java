package com.cqjtu.dpta.web.controller.api;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.vo.AliPayBean;
import com.cqjtu.dpta.common.web.Info;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/distr/api/alipay")
public class PayController {

    // 支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址
    private final String NOTIFY_URL = "http://www.baidu.com";
    // 支付宝同步通知路径，也就是当付款完毕后跳转到本项目的页面，可以不是公网地址
    private final String RETURN_URL = "http://localhost:8080/";
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(0, 0);

    @Resource
    AliPayBean aliPayBean;

    @GetMapping("pay")
    public Result pay(Info info,
                      @RequestParam("amount") String amount,
                      @RequestParam("return_url") String returnUrl,
                      HttpServletResponse httpResponse) throws IOException, AlipayApiException {
        // 实例化客户端，填入所需参数
//        AlipayClient alipayClient = new DefaultAlipayClient(aliPayBean.getServerUrl(), aliPayBean.getAppId(), aliPayBean.getPrivateKey(), aliPayBean.getFormat(), aliPayBean.getCharset(), aliPayBean.getPublicKey(), aliPayBean.getSignType());

        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(aliPayBean.getServerUrl());  //gateway:支付宝网关（固定）https://openapi.alipay.com/gateway.do
        certAlipayRequest.setAppId(aliPayBean.getAppId());  //APPID 即创建应用后生成,详情见创建应用并获取 APPID
        certAlipayRequest.setPrivateKey(aliPayBean.getPrivateKey());  //开发者应用私钥，由开发者自己生成
        certAlipayRequest.setFormat(aliPayBean.getFormat());  //参数返回格式，只支持 json 格式
        certAlipayRequest.setCharset(aliPayBean.getCharset());  //请求和签名使用的字符编码格式，支持 GBK和 UTF-8
        certAlipayRequest.setSignType(aliPayBean.getSignType());  //商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐商家使用 RSA2。
        certAlipayRequest.setCertPath(aliPayBean.getAppCertPath()); //应用公钥证书路径（app_cert_path 文件绝对路径）
        certAlipayRequest.setAlipayPublicCertPath(aliPayBean.getAlipayCertPath()); //支付宝公钥证书文件路径（alipay_cert_path 文件绝对路径）
        certAlipayRequest.setRootCertPath(aliPayBean.getAlipayRootCertPath());  //支付宝CA根证书文件路径（alipay_root_cert_path 文件绝对路径）
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 在公共参数中设置回跳和通知地址
        request.setReturnUrl(returnUrl);
//        request.setNotifyUrl(NOTIFY_URL);
//        //根据订单编号,查询订单相关信息
//        Order order = payService.selectById(orderId);
//        //商户订单号，商户网站订单系统中唯一订单号，必填
//        String out_trade_no = order.getOrderId().toString();
//        //付款金额，必填
//        String total_amount = order.getOrderPrice().toString();
//        //订单名称，必填
//        String subject = order.getOrderName();
//        resveDService.getInsertId().toString()
        String out_trade_no = SNOWFLAKE.nextIdStr();
        String total_amount = amount;
        String subject = "充值预备金";
        //商品描述，可空
        String body = String.valueOf(info.id());
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
//        httpResponse.setContentType("text/html;charset=" + aliPayBean.getCharset());
//        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
//        httpResponse.getWriter().flush();
//        httpResponse.getWriter().close();
        return Result.ok(form);
    }

    @Resource
    ResveService resveService;

    @PostMapping("notify")
    public String notif(@RequestParam(value = "trade_status", required = false) String trade_status,
                        @RequestParam(value = "total_amount", required = false) BigDecimal amount,
                        @RequestParam(value = "body", required = false) Long distrId) {
        if (trade_status.equals("TRADE_SUCCESS")) {
            resveService.useResve(distrId, amount, Const.RECHARGE);
            return "success";
        }
        return "fail";
    }

    @PostMapping("transfer")
    public void transfer() throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(aliPayBean.getServerUrl());  //gateway:支付宝网关（固定）https://openapi.alipay.com/gateway.do
        certAlipayRequest.setAppId(aliPayBean.getAppId());  //APPID 即创建应用后生成,详情见创建应用并获取 APPID
        certAlipayRequest.setPrivateKey(aliPayBean.getPrivateKey());  //开发者应用私钥，由开发者自己生成
        certAlipayRequest.setFormat(aliPayBean.getFormat());  //参数返回格式，只支持 json 格式
        certAlipayRequest.setCharset(aliPayBean.getCharset());  //请求和签名使用的字符编码格式，支持 GBK和 UTF-8
        certAlipayRequest.setSignType(aliPayBean.getSignType());  //商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐商家使用 RSA2。
        certAlipayRequest.setCertPath(aliPayBean.getAppCertPath()); //应用公钥证书路径（app_cert_path 文件绝对路径）
        certAlipayRequest.setAlipayPublicCertPath(aliPayBean.getAlipayCertPath()); //支付宝公钥证书文件路径（alipay_cert_path 文件绝对路径）
        certAlipayRequest.setRootCertPath(aliPayBean.getAlipayRootCertPath());  //支付宝CA根证书文件路径（alipay_root_cert_path 文件绝对路径）
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"201806300002\"," +
                "\"trans_amount\":1.68," +
                "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
                "\"biz_scene\":\"DIRECT_TRANSFER\"," +
                "\"order_title\":\"分销商提现\"," +
                "\"payee_info\":{" +
                "\"identity\":\"528981196501116428\"," +
                "\"identity_type\":\"ALIPAY_USER_ID\"," +
                "\"name\":\"沙箱环境\"" +
                "}," +
                "\"remark\":\"预备金提现\"," +
                "\"business_params\":\"{\\\"payer_show_name\\\":\\\"服务代理\\\"}\"" +
                "}");
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
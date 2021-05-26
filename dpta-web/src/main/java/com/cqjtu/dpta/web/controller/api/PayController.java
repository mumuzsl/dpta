package com.cqjtu.dpta.web.controller.api;

import cn.hutool.json.JSONString;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.cqjtu.dpta.api.ResveDService;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.vo.AliPayBean;
import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.dao.entity.ResveD;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/alipay")
public class PayController {

    // 支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址
    private final String NOTIFY_URL = "http://www.baidu.com";
    // 支付宝同步通知路径，也就是当付款完毕后跳转到本项目的页面，可以不是公网地址
    private final String RETURN_URL = "http://www.baidu.com";

    @Resource
    AlipayClient alipayClient;

    @GetMapping("pay")
    public void pay(String distrId, String out_trade_no, BigDecimal amount, HttpServletResponse httpResponse) throws IOException, AlipayApiException {

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);
//        String.format("%.2f", amount)
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", out_trade_no);
        params.put("product_code", "FAST_INSTANT_TRADE_PAY");
        params.put("total_amount", amount);
        params.put("subject", "充值预备金");
//        params.put("body",distrId);

        request.setBizContent(JSONObject.toJSONString(params));

        String body = "";
        try {
            body = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.getWriter().write(body);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

//        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
    }

    @Resource
    ResveService resveService;
    @PostMapping("notify")
    public String notif (@RequestParam(value = "trade_status",required = false) String trade_status,
                       @RequestParam(value = "total_amount",required = false) BigDecimal amount,
                       @RequestParam(value = "body",required = false) Long distrId) {
        if (trade_status.equals("TRADE_SUCCESS")) {
            resveService.useResve(distrId,amount,Const.RECHARGE);
            return "success";
        }
        return "fail";
    }

    @PostMapping("transfer")
    public void transfer (@RequestBody Map<String,String> pa) throws AlipayApiException {
        Long distrId = Long.valueOf(pa.get("distrId"));
        BigDecimal amount = new BigDecimal(pa.get("amount"));
        String out_biz_no = pa.get("out_biz_no");
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();

        Map<String,String> map = new HashMap<>();
        map.put("identity","xrmchf7247@sandbox.com");
        map.put("identity_type","ALIPAY_LOGON_ID");
        map.put("name","xrmchf7247");
        Map<String, Object> params = new HashMap<>();
        params.put("out_biz_no", out_biz_no);
        params.put("trans_amount", String.format("%.2f", amount));
        params.put("product_code", "TRANS_ACCOUNT_NO_PWD");
        params.put("biz_scene", "DIRECT_TRANSFER");
        params.put("order_title", "预备金提现");
        params.put("payee_info", map);
        request.setBizContent(JSONObject.toJSONString(params));
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);

        if(response.isSuccess()){
            resveService.useResve(distrId,amount,Const.CASH_OUT);
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
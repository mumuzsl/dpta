package com.cqjtu.dpta.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.web.Info;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/distr/api/alipay")
public class PayController {

    // 支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址
    private final String NOTIFY_URL = "http://www.baidu.com";
    // 支付宝同步通知路径，也就是当付款完毕后跳转到本项目的页面，可以不是公网地址
    private final String RETURN_URL = "http://localhost:8081/distr/api/alipay/notify?";

    @Resource
    AlipayClient alipayClient;

    @GetMapping("pay")
    public void pay(@RequestParam BigDecimal amount,
                    @RequestParam String url,
//                    @RequestParam Long distrId,
                    @RequestParam(required = false, defaultValue = "") String subPath,
                    Info info,
                    HttpServletResponse httpResponse) throws IOException, AlipayApiException {

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL + "distrId=" + info.id() + "&amount=" + amount + "&sub_path=" + subPath + "&url=" + url);
//        request.setNotifyUrl(NOTIFY_URL);
//        String.format("%.2f", amount)
        Map<String, Object> params = new HashMap<>();
//        params.put("out_trade_no", out_trade_no);
        params.put("out_trade_no", DptaUtils.defautlNextIdStr());
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

    @GetMapping("notify")
    public String notif(@RequestParam(value = "trade_status", required = false) String trade_status,
                        @RequestParam("total_amount") BigDecimal amount,
                        @RequestParam Long distrId,
                        @RequestParam("out_trade_no") Long dealId,
                        @RequestParam String url,
                        @RequestParam(name = "sub_path", required = false, defaultValue = "") String subPath) {
//        if (trade_status.equals("TRADE_SUCCESS")) {
        resveService.useResve(distrId, amount, Const.RECHARGE, resveD -> resveD.setDealId(dealId));
        return "redirect:" + url + "#" + subPath;
//        }
//        return url + "?fail";
    }

    @Resource
    DistrService distrService;

    @PostMapping("transfer")
    @ResponseBody
    public Result transfer(@RequestBody Map<String, String> pa,
                           Info info) throws AlipayApiException {
//        Long distrId = Long.valueOf(pa.get("distrId"));
        Long distrId = info.id();
        BigDecimal amount = new BigDecimal(pa.get("amount"));
//        String out_biz_no = pa.get("out_biz_no");
        String out_biz_no = DptaUtils.defautlNextIdStr();
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();

        String account = distrService.getById(distrId).getAccount();
        Map<String, String> map = new HashMap<>();
        map.put("identity", account);
        map.put("identity_type", "ALIPAY_LOGON_ID");
        map.put("name", "xrmchf7247");
        Map<String, Object> params = new HashMap<>();
        params.put("out_biz_no", out_biz_no);
        params.put("trans_amount", String.format("%.2f", amount));
        params.put("product_code", "TRANS_ACCOUNT_NO_PWD");
        params.put("biz_scene", "DIRECT_TRANSFER");
        params.put("order_title", "预备金提现");
        params.put("payee_info", map);
        request.setBizContent(JSONObject.toJSONString(params));
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);

        if (response.isSuccess()) {
            resveService.useResve(distrId, amount, Const.CASH_OUT);
            System.out.println("调用成功");
            return Result.ok("调用成功");
        } else {
            System.out.println("调用失败");
            return Result.fail("调用失败");
        }
    }
}
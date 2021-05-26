package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ly
 * @date 2021-04-28 19:47
 */
@Controller
@RequestMapping("/admin")
public class  PlatAnalysisController {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;

    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;

    @GetMapping("/plat_analysis")
    public String plat_analysisPage(HttpServletRequest request, Model model) {
        request.setAttribute("path", "plat_analysis");
        Integer shopSum = newBeeMallUserService.shopSum();
        Integer orderSum = newBeeMallOrderService.orderSum();
        Integer rOrderSum = newBeeMallOrderService.rOrderSum();
        model.addAttribute("shopSum",shopSum);
        model.addAttribute("orderSum",orderSum);
        model.addAttribute("rOrderSum",rOrderSum);

        return "admin/plat_analysis";
    }

    @RequestMapping("/show")
    @ResponseBody
    public List<Object> findById(Model model){
        ArrayList<String> dates = new ArrayList<>();
        List<String> chart_date = new ArrayList<>();
        List<Integer> chart_order = new ArrayList<>();
        List<Integer> chart_people = new ArrayList<>();
        List<Object> shuju = new ArrayList<>();

        List<NewBeeMallOrder> newBeeMallOrders = newBeeMallOrderService.allOrder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //格式化规则
        for (int i=0;i<newBeeMallOrders.size();i++){
            Date date = newBeeMallOrders.get(i).getCreateTime();//获得你要处理的时间 Date型
            String strDate= sdf.format(date); //格式化成yyyy-MM-dd格式的时间字符串
            dates.add(strDate);
        }
        chart_date=sort(dates);
        for (int j=0;j<chart_date.size();j++){
            chart_order.add(newBeeMallOrderService.oneDayOrderSum(chart_date.get(j)));
            chart_people.add(newBeeMallUserService.oneDayPeopleSum(chart_date.get(j)));
        }
        shuju.add(chart_date);
        shuju.add(chart_order);
        shuju.add(chart_people);
        return shuju;
    }

    /**
     * 去掉ArrayList中的重复年份
     * @param al
     * @return
     */
    public ArrayList sort(ArrayList al){
        ArrayList list = new ArrayList();
        for (int i=0;i<al.size();i++){
            if(!list.contains(al.get(i))){
                list.add(al.get(i));
            }
        }
        return list;
    }


    @RequestMapping("/show/pie")
    @ResponseBody
    public List<Object> findByIdPie(Model model){
        return null;
    }

    @RequestMapping("/show/bar")
    @ResponseBody
    public List<Integer> findBybar(Model model){
        List barData = new ArrayList();

        return barData;
    }


}

package com.cqjtu.dpta;

import com.cqjtu.dpta.api.OrderDService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.dao.mapper.CommRMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mumu
 * @date 2021/3/4 15:01
 */
@SpringBootTest
public class DptaApplicationTests {

    @Resource
    private CommRMapper commRMapper;

    @Resource
    private PafCommService pafCommService;

    @Resource
    private OrderDService orderDService;

    @Test
    void d(){
//        OrderDServiceImpl orderDService = new OrderDServiceImpl();
        System.out.println(orderDService.getOrderSum());
    }

    @Test
    void b(){
        List data = new ArrayList<>();
        List<String> distrNms = new ArrayList<>();
        List<BigDecimal> comPs = new ArrayList<>();
        List<String> commNames = new ArrayList<>();
        for (int i = 0;i<pafCommService.getDistrTop().size();i++){
            distrNms.add(pafCommService.getDistrTop().get(i).getDistrNm());

            commNames.add(pafCommService.getMaxShop(pafCommService.getDistrTop().get(i).getDistrNm()));

            comPs.add(pafCommService.getDistrTop().get(i).getComP());
        }
        data.add(distrNms);
//        data.add(commNames);
        data.add(comPs);
        System.out.println(data);
    }

    @Test
    void a(){
        List data = new ArrayList<>();
        List<String> commNames = new ArrayList<>();
        List<String> specs = new ArrayList<>();
        List<BigDecimal> rePrices = new ArrayList<>();
        List<Integer> saVolumes = new ArrayList<>();
        for (int i=0;i<pafCommService.getShopTop().size();i++){
            commNames.add(pafCommService.getShopTop().get(i).getCommName());
            specs.add(pafCommService.getShopTop().get(i).getSpec());
            rePrices.add(pafCommService.getShopTop().get(i).getRePrice());
            saVolumes.add(pafCommService.getShopTop().get(i).getSaVolume());
        }
        data.add(commNames);
        data.add(specs);
        data.add(rePrices);
        data.add(saVolumes);
        System.out.println(data);
    }

//    @Test
//    void contextLoads() {
//        Page<CommR> userPage = new Page<>(1, 2);
//        IPage<CommR> iPage = commRMapper.bindSort(userPage);
//        System.out.println("????????????" + iPage.getPages());
//        System.out.println("???????????????" + iPage.getTotal());
//        if (iPage.getRecords().size() != 0) {
//            List<CommR> mpUserList1 = iPage.getRecords();
//            mpUserList1.forEach(System.out::println);
//        } else {
//            System.out.println("????????????????????????");
//        }
//    }

//    @Test
//    void credS(){
//        creditd.setCreditId((long) 10002);
//        creditd.setAmount(BigDecimal.valueOf(1234.00));
//        creditd.setDCreId(Long.valueOf("6001"));
//        creditd.setType(1);
//        creditd.setCreateTm(LocalDateTime.parse("2021-4-18"));
//        creditDService.save(creditd);
//    }


//    @Resource
//    DistrController distrController;
//
//    @Test
//    void modif1() {
//        Distr distr1 = new Distr();
//        distr1.setDistrId(1L);
//        distr1.setPhone("110");
//        Object r1 = distrController.modif(distr1);
//        System.out.println(r1);
//    }
//
//    @Test
//    void modif4() {
//        Distr distr1 = new Distr();
//        distr1.setDistrId(1001L);
//        distr1.setPhone("110");
//        Object r1 = distrController.modif(distr1);
//        System.out.println(r1);
//    }
//
//    @Test
//    void modif5() {
//        try {
//            Distr distr1 = new Distr();
////            distr1.setDistrId(1L);
////            distr1.setPhone("110");
//            Object r1 = distrController.modif(null);
//            System.out.println(r1);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            Distr distr1 = new Distr();
////            distr1.setDistrId(1L);
////            distr1.setPhone("110");
//            Object r1 = distrController.modif(distr1);
//            System.out.println(r1);
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//        }
//        try {
//            Distr distr1 = new Distr();
//            distr1.setDistrId(1L);
////            distr1.setPhone("110");
//            Object r1 = distrController.modif(distr1);
//            System.out.println(r1);
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//        }
//        try {
//            Distr distr1 = new Distr();
//            distr1.setDistrId(1L);
//            distr1.setPhone("110");
//            Object r1 = distrController.modif(distr1);
//            System.out.println(r1);
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//        }
//    }
//
//    @Test
//    void modif2() {
//        Distr distr2 = new Distr();
//        Object r2 = distrController.modif(distr2);
//        System.out.println(r2);
//    }
//
//    @Test
//    void modif3() {
//        Object r3 = distrController.modif(null);
//        System.out.println(r3);
//    }
}

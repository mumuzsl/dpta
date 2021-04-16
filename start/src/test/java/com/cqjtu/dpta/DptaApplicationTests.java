package com.cqjtu.dpta;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.api.CommRService;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.mapper.CommRMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mumu
 * @date 2021/3/4 15:01
 */
@SpringBootTest
public class DptaApplicationTests {

    @Resource
    private CommRMapper commRMapper;
    @Test
    void contextLoads() {
        Page<CommR> userPage = new Page<>(1, 2);
        IPage<CommR> iPage = commRMapper.bindSort(userPage);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        if (iPage.getRecords().size() != 0) {
            List<CommR> mpUserList1 = iPage.getRecords();
            mpUserList1.forEach(System.out::println);
        } else {
            System.out.println("数据已经加载完成");
        }
    }

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

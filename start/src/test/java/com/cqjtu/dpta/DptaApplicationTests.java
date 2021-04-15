package com.cqjtu.dpta;

import com.cqjtu.dpta.dao.entity.Distr;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author mumu
 * @date 2021/3/4 15:01
 */
@SpringBootTest
public class DptaApplicationTests {

    @Test
    void contextLoads() {
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

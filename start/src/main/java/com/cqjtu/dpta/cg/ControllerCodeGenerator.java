package com.cqjtu.dpta.cg;

import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cqjtu.dpta.api.support.CrudService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/3/20
 */
public class ControllerCodeGenerator {

    public static void main(String[] args) {
        Setting setting = new Setting(ConstValue.DEFAULT_SETTING);

        // 从配置文件中读取项目路径
        String projectPath = setting.containsKey(ConstValue.PROJECT_PATH) ? setting.getStr(ConstValue.PROJECT_PATH) : ConstValue.DEFAULT_PROJECT_PATH;
        // 从配置文件中读取模板文件夹路径
        String templatesPath = setting.containsKey(ConstValue.PTEMPLATES_PATH) ? setting.getStr(ConstValue.PTEMPLATES_PATH) : ConstValue.DEFAULT_TEMPLATES_PATH;

        projectPath += "/dpta";

        // 设置自定义的controller模板
        TemplateConfig tc = new TemplateConfig()
                .setController(templatesPath + "/controller2.java");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/dpta?serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("132135138zsl");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperServiceClass(CrudService.class);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix("t_");
//        strategy.setExclude("t_distr");
        strategy.setInclude("t_deal");

        Map<String, String> map = new HashMap<>();
        map.put(ConstVal.CONTROLLER_PATH, projectPath + "\\dpta-web\\src\\main\\java\\com\\cqjtu\\dpta\\web\\controller\\api");

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.cqjtu.dpta");
        pc.setController("web.controller.api");
        pc.setService("api");
        pc.setServiceImpl("service");
        pc.setEntity("dao.entity");
        pc.setMapper("dao.mapper");
        pc.setPathInfo(map);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "\\start\\src\\main\\java");
        gc.setAuthor("mumu");
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setServiceName("%sService");

        InjectionConfig ic = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("uri", "api");
                this.setMap(map);
            }
        };
        mpg.setCfg(ic);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setTemplate(tc);
        mpg.setDataSource(dsc);
        mpg.setStrategy(strategy);
        mpg.setPackageInfo(pc);
        mpg.setGlobalConfig(gc);

        mpg.execute();
    }

}

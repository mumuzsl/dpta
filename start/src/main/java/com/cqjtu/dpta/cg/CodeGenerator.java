package com.cqjtu.dpta.cg;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * 该类基于MyBatis-Plus提供的支持实现代码生成
 * 该类仅适用于本项目生成代码
 * 使用该类生成代码前可以在code-generator.setting文件中修改配置
 * code-generator.setting的位置默认在resources/code-generator下
 * 修改code-generator.setting文件的位置需要到代码中进行修改，建议使用默认位置
 * code-generator.setting文件不存在时，请手动创建
 * <p>
 * author: mumu
 * date: 2021/3/18
 */
public class CodeGenerator {


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
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix("t_");
//        strategy.setInclude("t_comm_r");
        strategy.setInclude("t_distr_user");

        Map<String, String> map = new HashMap<>();
        map.put(ConstVal.MAPPER_PATH, projectPath + "\\dpta-dao\\src\\main\\java\\com\\cqjtu\\dpta\\dao\\mapper");
//        map.put(ConstVal.ENTITY_PATH, projectPath + "\\dpta-dao\\src\\main\\java\\com\\cqjtu\\dpta\\dao\\entity");
//        map.put(ConstVal.SERVICE_PATH, projectPath + "\\dpta-api\\src\\main\\java\\com\\cqjtu\\dpta\\api");
//        map.put(ConstVal.SERVICE_IMPL_PATH, projectPath + "\\dpta-service\\src\\main\\java\\com\\cqjtu\\dpta\\service");
//        map.put(ConstVal.CONTROLLER_PATH, projectPath + "\\dpta-web\\src\\main\\java\\com\\cqjtu\\dpta\\web\\controller\\api");

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

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        InjectionConfig ic = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("uri", "api/data");
                this.setMap(map);
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        String projectPathTmp = projectPath;
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPathTmp + "\\dpta-dao\\src\\main\\resources\\mapper\\" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        ic.setFileOutConfigList(focList);
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

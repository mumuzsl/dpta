package ${package.Controller};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>@RestController
<#else>
    @Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if><#if cfg.uri?? && cfg.uri != "">/${cfg.uri}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else><#if superControllerClass??>public class ${table.controllerName} extends ${superControllerClass} {
<#else>public class ${table.controllerName} {

    @Resource
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<${entity}> page = ${table.serviceName?uncap_first}.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody ${entity} ${entity?uncap_first}) {
        boolean result = ${table.serviceName?uncap_first}.updateById(${entity?uncap_first});
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody ${entity} ${entity?uncap_first}) {
        boolean result = ${table.serviceName?uncap_first}.save(${entity?uncap_first});
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = ${table.serviceName?uncap_first}.removeByIds(ids);
        return Result.judge(result);
    }

</#if>}
</#if>

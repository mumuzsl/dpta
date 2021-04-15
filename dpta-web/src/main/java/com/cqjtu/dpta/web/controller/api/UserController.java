package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.UserService;
import com.cqjtu.dpta.dao.entity.User;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    public IPage<User> page(@PageableDefault Pageable pageable) {
        return userService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody User user) {
        boolean result = userService.updateById(user);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody User user) {
        boolean result = userService.save(user);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = userService.removeByIds(ids);
        return Result.judge(result);
    }

}

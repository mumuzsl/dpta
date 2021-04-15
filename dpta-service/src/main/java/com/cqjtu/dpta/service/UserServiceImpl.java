package com.cqjtu.dpta.service;

import com.cqjtu.dpta.dao.entity.User;
import com.cqjtu.dpta.dao.mapper.UserMapper;
import com.cqjtu.dpta.api.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author mumu
 * @since 2021-04-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

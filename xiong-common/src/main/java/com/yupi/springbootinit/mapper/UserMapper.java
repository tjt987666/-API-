package com.yupi.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.springbootinit.model.entity.User;
import org.springframework.stereotype.Component;

/**
 * 用户数据库操作
 */
@Component
public interface UserMapper extends BaseMapper<User> {

}





package com.ddd.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddd.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserMapper extends BaseMapper<User> {
}
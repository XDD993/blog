package com.ddd.service;


import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.User;

/**
 * @author 35238
 * @date 2023/7/22 0022 21:38
 */
public interface BlogLoginService {
    ResponseResult login(User user);

	ResponseResult logout();
}
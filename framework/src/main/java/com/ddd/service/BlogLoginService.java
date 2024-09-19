package com.ddd.service;


import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

	ResponseResult logout();
}
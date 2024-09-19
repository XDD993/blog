package com.ddd.controller;


import com.ddd.domain.ResponseResult;
import com.ddd.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

	@Autowired
	private ISysUserService userService;


	/**
	 * 查询用户的个人信息
	 * @return
	 */
	@GetMapping("/userInfo")
	public ResponseResult userInfo(){
		ResponseResult result =  userService.userInfo();
		return result;
	}
}

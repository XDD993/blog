package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.User;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.handler.exception.SystemException;
import com.ddd.service.BlogLoginService;
import com.ddd.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	//BlogLoginService是我们在service目录写的接口
	private LoginService loginService;

	/**
	 * 使用Security进行登录认证的步骤：
	 * 1：首先在用户登录的时候会先调用Authentication这个方法 去进行用户的一个认证
	 * 2：然后会调用UserDetailsService进行一个用户的权限信息判断查找 并返回一个UserDetails对象
	 * 3：通过对比看密码是否正确 然后把该用户的权限信息设置到Authentication这个对象当中 并返回
	 * @param user
	 * @return
	 */
	@PostMapping("/user/login")
	//ResponseResult是我们在huanf-framework工程里面写的实体类
	public ResponseResult login(@RequestBody User user){
		if (user.getUserName()==null){
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		}
		return loginService.login(user);
	}

	@PostMapping("/logout")
	public ResponseResult logout(){
		return loginService.logout();
	}

}

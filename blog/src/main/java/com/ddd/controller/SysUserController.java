package com.ddd.controller;


import com.ddd.annotation.systemLog;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.SysUser;
import com.ddd.domain.entity.User;
import com.ddd.domain.vo.UserInfoVo;
import com.ddd.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	@systemLog(businessName = "查询用户的个人信息")
	@GetMapping("/userInfo")
	public ResponseResult userInfo(){
		ResponseResult result =  userService.userInfo();
		return result;
	}
	@systemLog(businessName = "更新用户的个人信息")
	@PutMapping ("/userInfo")
	public ResponseResult updateUserInfo(@RequestBody UserInfoVo user){
		ResponseResult result = userService.updateUserInfo(user);
		return result;
	}

	@systemLog(businessName = "注册用户")
	@PostMapping("/register")
	public ResponseResult register(@RequestBody SysUser user){
		ResponseResult result = userService.register(user);
		return result;
	}
}

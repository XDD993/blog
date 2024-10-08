package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Role;
import com.ddd.domain.entity.SysUser;
import com.ddd.domain.entity.User;
import com.ddd.domain.vo.UserInfoAndRoleIdsVo;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.handler.exception.SystemException;
import com.ddd.service.IRoleService;
import com.ddd.service.ISysUserService;
import com.ddd.service.IUserRoleService;
import com.ddd.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

	@Autowired
	private ISysUserService userService;
	@Autowired
	private IRoleService roleService;

	@GetMapping("/list")
	public ResponseResult list(User user,Integer pageNum,Integer pageSize){
		return userService.selectUserPage(user,pageNum,pageSize);
	}

	@PostMapping
	public ResponseResult add(@RequestBody SysUser user){
		if(!StringUtils.hasText(user.getUserName())){
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		}
	/*	if (!userService.checkUserNameUnique(user.getUserName())){
			throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
		}
		if (!userService.checkPhoneUnique(user)){
			throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
		}
		if (!userService.checkEmailUnique(user)){
			throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
		}*/
		return userService.add(user);
	}

	@GetMapping("/{userId}")
	public ResponseResult getInfo(@PathVariable("userId")Long userId){
		List<Role> list = roleService.list();
		SysUser user = userService.getById(userId);
		List<Long> roleIds = roleService.selectRoleIdByUserId(userId);
		UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user, list, roleIds);
		return ResponseResult.okResult(vo);
	}
	@PutMapping
	public ResponseResult edit(@RequestBody SysUser user){
		userService.updateUser(user);
		return ResponseResult.okResult();
	}

	@DeleteMapping("/{userIds}")
	public ResponseResult remove(@PathVariable List<Long> userIds) {
		if(userIds.contains(SecurityUtils.getUserId())){
			return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
		}
		userService.removeByIds(userIds);
		return ResponseResult.okResult();
	}
}

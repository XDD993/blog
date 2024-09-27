package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Menu;
import com.ddd.domain.entity.User;
import com.ddd.domain.vo.AdminUserInfoVo;
import com.ddd.domain.vo.RoutersVo;
import com.ddd.domain.vo.UserInfoVo;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.handler.exception.SystemException;
import com.ddd.service.BlogLoginService;
import com.ddd.service.IMenuService;
import com.ddd.service.IRoleService;
import com.ddd.service.LoginService;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

	@Autowired
	//BlogLoginService是我们在service目录写的接口
	private LoginService loginService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IRoleService roleService;


	@PostMapping("/user/login")
	//ResponseResult是我们在huanf-framework工程里面写的实体类
	public ResponseResult login(@RequestBody User user){
		if (user.getUserName()==null){
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		}
		return loginService.login(user);
	}

	@PostMapping("/user/logout")
	public ResponseResult logout(){

		return loginService.logout();
	}

	/**
	 * 查询当前用户拥有的权限和角色
	 * @return
	 */
	@GetMapping("/getInfo")
	public ResponseResult<AdminUserInfoVo> getInfo(){
		User user = SecurityUtils.getLoginUser().getUser();
		//查询权限
		List<String> perms = menuService.selectPermsByUserId(user.getId());
		//查询角色
		List<String> roles = roleService.selectRoleKeyByUserId(user.getId());
		UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
		AdminUserInfoVo vo = new AdminUserInfoVo(perms,roles,userInfoVo);
		return ResponseResult.okResult(vo);
	}

	/**
	 * 动态路由查询当前用户的菜单列表 前端展示
	 * @return
	 */
	@GetMapping("/getRouters")
	public ResponseResult<RoutersVo> getRouters(){
		Long userId = SecurityUtils.getUserId();
		List<Menu> menus = menuService.getRouters(userId);
		return ResponseResult.okResult(new RoutersVo(menus));
	}


}

package com.ddd.service.impl;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.LoginUser;
import com.ddd.domain.entity.User;
import com.ddd.domain.vo.BlogUserLoginVo;
import com.ddd.domain.vo.UserInfoVo;
import com.ddd.service.BlogLoginService;
import com.ddd.service.LoginService;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.JwtUtil;
import com.ddd.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RedisCache redisCache;

	@Override
	public ResponseResult login(User user) {
		//封装登录的用户名和密码
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
		//在进行这一步之前 会先去执行UserDetailsServiceImpl判断拿到用户信息
		Authentication authenticate = authenticationManager.authenticate(token);
		if(Objects.isNull(authenticate)){
			throw new RuntimeException("用户名或密码错误");
		}
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
		String userId = loginUser.getUser().getId().toString();
		String jwt = JwtUtil.createJWT(userId);
		redisCache.setCacheObject("login:"+userId,loginUser);
//		UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//		BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
		Map<String, String> map = new HashMap<>();
		map.put("token",jwt);
		//封装响应返回
		return ResponseResult.okResult(map);
	}

	@Override
	public ResponseResult logout() {
		//用户的Token已经保存在了存入到SecurityContextHolder中
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		//再通过Redis进行删除信息
		Long userId = loginUser.getUser().getId();
		redisCache.deleteObject("login:"+userId);
		return ResponseResult.okResult();
	}
}

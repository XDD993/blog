package com.ddd.handler.security;

import com.alibaba.fastjson.JSON;
import com.ddd.domain.ResponseResult;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
//认证失败的处理器
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
		//输出异常信息
		authenticationException.printStackTrace();
		ResponseResult result = null;
		//登录会出现账号验证问题还有登录的问题 要分别处理异常信息
		if (authenticationException instanceof BadCredentialsException){
			//密码错误
			result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authenticationException.getMessage());
		}else if (authenticationException instanceof InsufficientAuthenticationException){
			//没有登录
			result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
		}else{
			result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
		}
		WebUtils.renderString(response,JSON.toJSONString(result));
	}
}

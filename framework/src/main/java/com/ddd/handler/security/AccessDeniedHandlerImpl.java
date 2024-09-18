package com.ddd.handler.security;

import com.alibaba.fastjson.JSON;
import com.ddd.domain.ResponseResult;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//授权失败的异常处理器
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		accessDeniedException.printStackTrace();
		ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
		//前端只认识Json格式 需要将返回的数据转换为json格式
		WebUtils.renderString(response, JSON.toJSONString(result));
	}
}

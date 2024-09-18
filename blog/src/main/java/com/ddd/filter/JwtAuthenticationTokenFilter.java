package com.ddd.filter;

import com.alibaba.fastjson.JSON;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.LoginUser;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.utils.JwtUtil;
import com.ddd.utils.RedisCache;
import com.ddd.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
//这个过滤器用于判断点击的模块是否需要登录权限
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private RedisCache redisCache;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("token");
		//判断上面那行有没有拿到token值
		if(!StringUtils.hasText(token)){
			//说明该接口不需要登录，直接放行，不拦截
			filterChain.doFilter(request,response);
			return;
		}
		Claims claims =null;
		try {
			claims = JwtUtil.parseJWT(token);
		} catch (Exception e) {
			e.printStackTrace();
			//报异常之后，把异常响应给前端，需要重新登录。ResponseResult、AppHttpCodeEnum、WebUtils是我们在huanf-framework工程写的类
			ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
			WebUtils.renderString(response, JSON.toJSONString(result));
			return;
		}

		String userid = claims.getSubject();
		//在redis中，通过key来获取value，注意key我们是加过前缀的，取的时候也要加上前缀
		LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userid);
		//如果在redis获取不到值，说明登录是过期了，需要重新登录
		if(Objects.isNull(loginUser)){
			ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
			WebUtils.renderString(response, JSON.toJSONString(result));
			return;
		}

		//把从redis获取到的value，存入到SecurityContextHolder(Security官方提供的类)
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		filterChain.doFilter(request,response);
	}
}

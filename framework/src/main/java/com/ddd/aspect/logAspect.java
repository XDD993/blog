package com.ddd.aspect;

import com.alibaba.fastjson.JSON;
import com.ddd.annotation.systemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect//当前的类是一个切面类 由切入点和通知组成
@Slf4j
public class logAspect {
	@Pointcut("@annotation(com.ddd.annotation.systemLog)")
	//这是一个切入点，
	public void pt(){

	}

	@Around("pt()")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
		Object res =null;
		try {
			//打印方法执行前的输出信息
			handleBefore(joinPoint);
			res =joinPoint.proceed();
			//打印方法执行完之后返回的参数
			afterBefore(res);
		} finally {
			log.info("=======================end=======================" + System.lineSeparator());
		}
		return res;
	}

	private void afterBefore(Object res) {
		log.info("返回参数   : {}",JSON.toJSONString(res));
	}


	private void handleBefore(ProceedingJoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		//获取systemLog注解对象
		systemLog systemLog = getSystemLog(joinPoint);

		log.info("======================Start======================");//下面的几个log输出，第一个参数{}表示占位符，具体的值是第二个参数的变量
		// 打印请求 URL
		log.info("请求URL   : {}",request.getRequestURL());
		// 打印描述信息，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解的描述信息
		log.info("接口描述   : {}",systemLog.businessName());
		// 打印 Http method
		log.info("请求方式   : {}",request.getMethod());
		// 打印调用 controller 的全路径(全类名)、方法名
		log.info("请求类名   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
		// 打印请求的 IP
		log.info("访问IP    : {}",request.getRemoteHost());
		// 打印请求入参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
		log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));
	}

	private systemLog getSystemLog(ProceedingJoinPoint joinPoint) {
		MethodSignature signature =  (MethodSignature)joinPoint.getSignature();
		systemLog systemLog = signature.getMethod().getAnnotation(systemLog.class);
		return systemLog;
	}

}

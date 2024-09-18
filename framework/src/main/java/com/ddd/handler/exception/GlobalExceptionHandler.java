package com.ddd.handler.exception;


import com.ddd.domain.ResponseResult;
import com.ddd.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//复合注解@ResponseBody+@ControllerAdvice
@Slf4j
//这个是一个全局的异常处理类
public class GlobalExceptionHandler {
	@ExceptionHandler(SystemException.class)
	public ResponseResult systemExceptionHandler(SystemException e){
		log.error("出现了异常"+e);
		return ResponseResult.errorResult(e.getCode(),e.getMsg());
	}
	@ExceptionHandler(Exception.class)
	public ResponseResult exceptionHandler(Exception e){
		log.error("出现了异常"+e);
		return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
	}
}

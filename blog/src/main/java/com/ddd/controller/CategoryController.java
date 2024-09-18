package com.ddd.controller;


import com.ddd.domain.ResponseResult;
import com.ddd.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-13
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;


//	查询分类接口
	@GetMapping("/getCategoryList")
	public ResponseResult getCategoryList(){
		ResponseResult result = categoryService.getCategoryList();
		return result;
	}


}

package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Category;
import com.ddd.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@GetMapping("/listAllCategory")
	public ResponseResult listAllCategory(){
		ResponseResult result = categoryService.listAllCategory();
		return result;
	}

}

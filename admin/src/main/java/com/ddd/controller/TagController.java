package com.ddd.controller;


import com.ddd.domain.ResponseResult;
import com.ddd.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-24
 */
@RestController
@RequestMapping("/tag")
public class TagController {

	@Autowired
	private ITagService tagService;
	@GetMapping("/list")
	public ResponseResult list(){
		return ResponseResult.okResult(tagService.list());
	}
}

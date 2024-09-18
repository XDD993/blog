package com.ddd.controller;


import com.ddd.domain.ResponseResult;
import com.ddd.service.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 友链 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-18
 */
@RestController
@RequestMapping("/link")
public class LinkController {

	@Autowired
	private ILinkService linkService;

	@GetMapping("/getAllLink")
	public ResponseResult getAllLink(){
		ResponseResult  result = linkService.getAllLink();
		return result;
	}

}

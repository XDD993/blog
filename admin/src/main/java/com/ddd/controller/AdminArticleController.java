package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.AddArticleDto;
import com.ddd.domain.entity.Article;
import com.ddd.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class AdminArticleController {

	@Autowired
	private IArticleService articleService;

	@PostMapping
	public ResponseResult add(@RequestBody AddArticleDto article){
		return articleService.add(article);

	}

}

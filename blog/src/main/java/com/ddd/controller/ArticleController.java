package com.ddd.controller;


import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Article;
import com.ddd.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private IArticleService articleService;

	@GetMapping("/list")
	public List<Article> test(){
		return articleService.list();
	}

	/**
	 * 查询浏览量最高的10篇文章
	 * @return
	 */
	@GetMapping("/hotArticleList")
	public ResponseResult hotArticleList(){
		ResponseResult  result =articleService.hotArticleList();
		return result;
	}
	@GetMapping("/articleList")
	public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
		ResponseResult  result =articleService.articleList(pageNum,pageSize,categoryId);
		return result;
	}

	@GetMapping("/{id}")
	public ResponseResult getArticleDetail(@PathVariable("id") Long id){
		ResponseResult  result =articleService.getArticleDetail(id);
		return result;
	}

}

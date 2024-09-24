package com.ddd.controller;


import com.ddd.annotation.systemLog;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Article;
import com.ddd.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@PutMapping("/updateViewCount/{id}")
	@systemLog(businessName = "根据文章id从mysql查询文章")//接口描述，用于'日志记录'功能
	public ResponseResult updateViewCount(@PathVariable("id") Long id){
		return articleService.updateViewCount(id);
	}

}

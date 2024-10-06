package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.AddArticleDto;
import com.ddd.domain.dto.ArticleDto;
import com.ddd.domain.entity.Article;
import com.ddd.domain.vo.PageVo;
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

	/**
	 * 查询文章列表
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult list(Article article,Integer pageNum,Integer pageSize){
		PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
		return ResponseResult.okResult(pageVo);
	}

	@GetMapping("/{id}")
	public ResponseResult getInfo(@PathVariable("id") Long id){
		ResponseResult result = articleService.getInfo(id);
		return result;
	}

	@PutMapping
	public ResponseResult edit(@RequestBody ArticleDto articleDto){
		articleService.edit(articleDto);
		return ResponseResult.okResult();
	}

	@DeleteMapping
	public ResponseResult remove(@RequestParam(value = "ids")String ids) {
		if (!ids.contains(",")) {
			articleService.removeById(ids);
		} else {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				articleService.removeById(id);
			}
		}
		return ResponseResult.okResult();
	}
}

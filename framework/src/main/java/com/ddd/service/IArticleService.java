package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.AddArticleDto;
import com.ddd.domain.dto.ArticleDto;
import com.ddd.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddd.domain.vo.PageVo;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-12
 */
public interface IArticleService extends IService<Article> {

//	查询浏览量最高的10篇文章
	ResponseResult hotArticleList();

	ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

	ResponseResult getArticleDetail(Long id);

	ResponseResult updateViewCount(Long id);

	ResponseResult add(AddArticleDto articleDto);

	PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

	ResponseResult getInfo(Long id);

	void edit(ArticleDto articleDto);
}

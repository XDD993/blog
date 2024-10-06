package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.constants.SystemCanstants;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.AddArticleDto;
import com.ddd.domain.dto.ArticleDto;
import com.ddd.domain.entity.Article;
import com.ddd.domain.entity.ArticleTag;
import com.ddd.domain.entity.Category;
import com.ddd.domain.vo.*;
import com.ddd.mapper.ArticleMapper;
import com.ddd.mapper.ArticleTagMapper;
import com.ddd.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ddd.service.IArticleTagService;
import com.ddd.service.ICategoryService;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.RedisCache;
import com.ddd.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private RedisCache redisCache;
	@Autowired
	private IArticleTagService articleTagService;
	@Autowired
	private ArticleTagMapper articleTagMapper;

	@Override
	public ResponseResult hotArticleList() {
		Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
		List<Article> list = cacheMap.entrySet().stream().map(obj -> new Article(Long.valueOf(obj.getKey()), obj.getValue().longValue())).collect(Collectors.toList());
		updateBatchById(list);
		LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
		wrapper.orderByDesc(Article::getViewCount);
		Page<Article> page = new Page<>(SystemCanstants.ARTICLE_STATUS_CURRENT, SystemCanstants.ARTICLE_STATUS_SIZE);
		page(page, wrapper);
		List<Article> records = page.getRecords();
		List<HotArticleVO> vos = BeanCopyUtils.copyBeanList(records, HotArticleVO.class);
		return ResponseResult.okResult(vos);
	}

	@Override
	public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
		LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
		wrapper.eq(Article::getStatus,SystemCanstants.ARTICLE_STATUS_NORMAL);
		wrapper.orderByDesc(Article::getIsTop);
		Page<Article> page = new Page<>(pageNum,pageSize);
		page(page,wrapper);
		List<Article> records = page.getRecords().stream().map(o -> {
			Category category = categoryService.getById(o.getCategoryId());
			o.setCategoryName(category.getName());
			return o;
		}).collect(Collectors.toList());
		List<ArticleListVo> vos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
		PageVo pageVo = new PageVo(vos, page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	@Override
	public ResponseResult getArticleDetail(Long id) {
		Article article = getById(id);
		Integer value = redisCache.getCacheMapValue("article:viewCount", id.toString());
		article.setViewCount(value.longValue());
		ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
		Category category = categoryService.getById(articleDetailVo.getCategoryId());
		if (category!=null){
			articleDetailVo.setCategoryName(category.getName());
		}

		return ResponseResult.okResult(articleDetailVo);
	}

	@Override
	public ResponseResult updateViewCount(Long id) {
		redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult add(AddArticleDto articleDto) {
		Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
		Long userId = SecurityUtils.getUserId();
		article.setCreateBy(userId);
		article.setUpdateBy(userId);
		Date date = new Date();
		article.setCreateTime(date);
		article.setUpdateTime(date);
		save(article);
		//还要添加文章关联表信息
		List<ArticleTag> list = articleDto.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
		articleTagService.saveOrUpdateBatch(list);
		return ResponseResult.okResult();
	}

	@Override
	public PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(StringUtils.hasText(article.getTitle()),Article::getTitle,article.getTitle());
		wrapper.eq(StringUtils.hasText(article.getSummary()),Article::getSummary,article.getSummary());
		Page page = new Page(pageNum,pageSize);
		page(page,wrapper);
		PageVo vo = new PageVo();
		vo.setRows(page.getRecords());
		vo.setTotal(page.getTotal());
		return vo;
	}

	@Override
	public ResponseResult getInfo(Long id) {
		Article article = getById(id);
		List<ArticleTag> articleTagList = articleTagService.list(new QueryWrapper<ArticleTag>().eq("article_id", article.getId()));
		List<Long> list = articleTagList.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());
		ArticleByIdVo articleByIdVo = BeanCopyUtils.copyBean(article, ArticleByIdVo.class);
		articleByIdVo.setTags(list);
		return ResponseResult.okResult(articleByIdVo);
	}

	@Override
	public void edit(ArticleDto articleDto) {
		//先更新文章的信息
		Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
		updateById(article);
		//再删除文章和标签表的相关信息
		articleTagService.remove(new QueryWrapper<ArticleTag>().eq("article_id", article.getId()));
		//再重新更新回去
		List<ArticleTag> list = articleDto.getTags().stream().map(tagId -> new ArticleTag(articleDto.getId(), tagId)).collect(Collectors.toList());
		articleTagMapper.saveBatchList(list);

	}


}

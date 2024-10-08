package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.constants.SystemCanstants;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Article;
import com.ddd.domain.entity.Category;
import com.ddd.domain.vo.CategoryVo;
import com.ddd.domain.vo.PageVo;
import com.ddd.mapper.CategoryMapper;
import com.ddd.service.IArticleService;
import com.ddd.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

	@Autowired
	private IArticleService articleService;

	@Override
	public ResponseResult getCategoryList() {

		List<Article> list = articleService.list(new QueryWrapper<Article>().eq("status", SystemCanstants.ARTICLE_STATUS_NORMAL));
		Set<Long> collect = list.stream().map(obj -> obj.getCategoryId()).collect(Collectors.toSet());
		List<Category> categoryList = listByIds(collect).stream().filter(obj -> obj.getStatus().equals(SystemCanstants.STATUS_NORMAL)).collect(Collectors.toList());
		List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
		return ResponseResult.okResult(categoryVos);
	}

	@Override
	public ResponseResult listAllCategory() {
		List<Category> list = list(new QueryWrapper<Category>().eq("status", "0"));
		return ResponseResult.okResult(list);
	}

	@Override
	public ResponseResult selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(StringUtils.hasText(category.getName()),Category::getName,category.getName());
		wrapper.eq(StringUtils.hasText(category.getStatus()),Category::getStatus,category.getStatus());
		Page page = new Page(pageNum,pageSize);
		page(page,wrapper);
		return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
	}
}

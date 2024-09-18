package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-13
 */
public interface ICategoryService extends IService<Category> {

	ResponseResult getCategoryList();
}

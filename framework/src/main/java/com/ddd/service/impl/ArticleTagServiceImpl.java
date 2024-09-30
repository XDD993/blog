package com.ddd.service.impl;

import com.ddd.domain.entity.ArticleTag;
import com.ddd.mapper.ArticleTagMapper;
import com.ddd.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签关联表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-27
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

}

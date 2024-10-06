package com.ddd.mapper;

import com.ddd.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章标签关联表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-27
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

	int saveBatchList(@Param("list") List<ArticleTag> list);

}

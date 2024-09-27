package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.TagListDto;
import com.ddd.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddd.domain.vo.PageVo;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-24
 */
public interface ITagService extends IService<Tag> {

	ResponseResult<PageVo> pageList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

	ResponseResult add(TagListDto tagListDto);
}

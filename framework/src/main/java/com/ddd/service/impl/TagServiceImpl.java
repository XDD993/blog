package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.TagListDto;
import com.ddd.domain.entity.Tag;
import com.ddd.domain.vo.PageVo;
import com.ddd.mapper.TagMapper;
import com.ddd.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-24
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
	@Override
	public ResponseResult<PageVo> pageList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
		QueryWrapper<Tag> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.hasText(tagListDto.getName()),"name",tagListDto.getName());
		wrapper.eq(StringUtils.hasText(tagListDto.getRemark()),"remark",tagListDto.getRemark());
		Page<Tag> page = new Page<>();
		page.setCurrent(pageNum);
		page.setSize(pageSize);
		page(page,wrapper);
		return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
	}

	@Override
	public ResponseResult add(TagListDto tagListDto) {
		if (tagListDto.getName()!=null && tagListDto.getRemark()!=null){
			Tag tag = BeanCopyUtils.copyBean(tagListDto, Tag.class);
			Long userId = SecurityUtils.getUserId();
			Date date = new Date();
			tag.setCreateBy(userId);
			tag.setCreateTime(date);
			tag.setUpdateBy(userId);
			tag.setUpdateTime(date);
			save(tag);
			return ResponseResult.okResult();
		}
		return ResponseResult.errorResult(500,"标签名或备注不能为空");
	}
}

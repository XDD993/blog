package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.constants.SystemCanstants;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Link;
import com.ddd.domain.vo.LinkVo;
import com.ddd.domain.vo.PageVo;
import com.ddd.mapper.LinkMapper;
import com.ddd.service.ILinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-18
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements ILinkService {

	@Override
	public ResponseResult getAllLink() {

		List<Link> links = list(new QueryWrapper<Link>().eq("status", SystemCanstants.LINK_STATUS_NORMAL));
		List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
		return ResponseResult.okResult(linkVos);
	}

	@Override
	public ResponseResult selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(link.getName()),Link::getName,link.getName());
		wrapper.eq(Objects.nonNull(link.getStatus()),Link::getStatus,link.getStatus());
		Page page = new Page(pageNum,pageSize);
		page(page,wrapper);
		return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
	}
}

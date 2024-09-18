package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddd.constants.SystemCanstants;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Link;
import com.ddd.domain.vo.LinkVo;
import com.ddd.mapper.LinkMapper;
import com.ddd.service.ILinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

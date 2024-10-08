package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-18
 */
public interface ILinkService extends IService<Link> {

	ResponseResult getAllLink();

	ResponseResult selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}

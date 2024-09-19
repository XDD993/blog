package com.ddd.service.impl;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.SysUser;
import com.ddd.domain.vo.UserInfoVo;
import com.ddd.mapper.SysUserMapper;
import com.ddd.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Override
	public ResponseResult userInfo() {
		Long userId = SecurityUtils.getUserId();
		SysUser user = getById(userId);
		UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
		return ResponseResult.okResult(userInfoVo);
	}
}

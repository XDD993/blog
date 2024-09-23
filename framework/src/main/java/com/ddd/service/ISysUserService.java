package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddd.domain.vo.UserInfoVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
public interface ISysUserService extends IService<SysUser> {

	ResponseResult userInfo();

	ResponseResult updateUserInfo(UserInfoVo user);

	ResponseResult register(SysUser user);
}

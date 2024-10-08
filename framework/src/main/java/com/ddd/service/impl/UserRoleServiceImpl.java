package com.ddd.service.impl;

import com.ddd.domain.entity.UserRole;
import com.ddd.mapper.UserRoleMapper;
import com.ddd.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-08
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}

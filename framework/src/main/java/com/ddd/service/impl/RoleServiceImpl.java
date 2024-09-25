package com.ddd.service.impl;

import com.ddd.domain.entity.Role;
import com.ddd.mapper.RoleMapper;
import com.ddd.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

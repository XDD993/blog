package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddd.domain.entity.RoleMenu;
import com.ddd.mapper.RoleMenuMapper;
import com.ddd.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-07
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
	@Override
	public void deleteRoleMenuByRoleId(Long id) {
		LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RoleMenu::getRoleId,id);
		remove(queryWrapper);
	}
}

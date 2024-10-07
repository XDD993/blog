package com.ddd.service;

import com.ddd.domain.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色和菜单关联表 服务类
 * </p>
 *
 * @author author
 * @since 2024-10-07
 */
public interface IRoleMenuService extends IService<RoleMenu> {

	void deleteRoleMenuByRoleId(Long id);
}

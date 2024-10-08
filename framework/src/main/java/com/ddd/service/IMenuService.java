package com.ddd.service;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
public interface IMenuService extends IService<Menu> {

	List<String> selectPermsByUserId(Long id);

	List<Menu> getRouters(Long userId);

	List<Menu>  selectMenuList(Menu menu);

	boolean hasChild(Long menuId);

	List<Long> selectMenuListByRoleId(Long roleId);
}


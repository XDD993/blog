package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Menu;
import com.ddd.mapper.MenuMapper;
import com.ddd.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
	@Override
	public List<String> selectPermsByUserId(Long id) {
		//如果是id为1代表是管理员返回全部perms
		if (id == 1){
			LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
			wrapper.in(Menu::getMenuType,"C","F");
			wrapper.eq(Menu::getStatus,"0");
			List<Menu> list = list(wrapper);
			List<String> perms = list.stream().map(Menu::getPerms).collect(Collectors.toList());
			return perms;
		}
		List<String> otherPerms = getBaseMapper().selectPermsByOtherUserId(id);
		return otherPerms;
	}

	@Override
	public List<Menu> getRouters(Long userId) {
		List<Menu> menus =null;
		//要先判断是管理员还是普通用户
		if (userId.equals(1L)){
			//查询出所有的菜单目录
			menus = getBaseMapper().selectAllRouterMenu();
		}else{
			menus =getBaseMapper().selectOtherRouterMenuTreeByUserId(userId);
		}
		List<Menu> menuTree =  buildMenuTree(menus);
		return menuTree;
	}

	private List<Menu> buildMenuTree(List<Menu> menus) {
		List<Menu> list = menus.stream().
				filter(obj -> obj.getParentId().equals(0L)).
				map(obj -> obj.setChildren(getChildren(obj, menus))).collect(Collectors.toList());
		return list;
	}

	private List<Menu> getChildren(Menu obj, List<Menu> menus) {
		List<Menu> list = menus.stream()
				.filter(m -> m.getParentId().equals(obj.getId()))
				.map(m -> m.setChildren(getChildren(m, menus)))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public List<Menu>  selectMenuList(Menu menu) {
		LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
		wrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
		wrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
		List<Menu> list = list(wrapper);
		return list;
	}

	@Override
	public boolean hasChild(Long menuId) {
		int count = count(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, menuId));
		return count!=0;
	}

	@Override
	public List<Long> selectMenuListByRoleId(Long roleId) {
		return getBaseMapper().selectMenuListByRoleId(roleId);
	}
}

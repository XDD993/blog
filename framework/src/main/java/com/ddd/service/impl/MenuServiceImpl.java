package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddd.domain.entity.Menu;
import com.ddd.mapper.MenuMapper;
import com.ddd.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
}

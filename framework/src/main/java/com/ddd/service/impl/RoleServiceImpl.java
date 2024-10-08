package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Role;
import com.ddd.domain.entity.RoleMenu;
import com.ddd.domain.vo.PageVo;
import com.ddd.mapper.RoleMapper;
import com.ddd.service.IRoleMenuService;
import com.ddd.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private IRoleMenuService roleMenuService;

	@Override
	public List<String> selectRoleKeyByUserId(Long id) {
		//如果是管理员返回admin
		if (id == 1) {
			List<String> list = new ArrayList<>();
			list.add("admin");
			return list;
		}
		List<String> roles = getBaseMapper().selectRoleKeyByOtherUserId(id);
		return roles;
	}

	@Override
	public ResponseResult getList(Role role, Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
		wrapper.eq(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());
		Page page = new Page(pageNum,pageSize);
		page(page,wrapper);
		return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
	}

	@Override
	public void insertRole(Role role) {
		save(role);
		System.out.println(role.getId());
		if(role.getMenuIds()!=null&&role.getMenuIds().length>0){
			insertRoleMenu(role);
		}
	}

	private void insertRoleMenu(Role role) {
		List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
				.map(memuId -> new RoleMenu(role.getId(), memuId))
				.collect(Collectors.toList());
		roleMenuService.saveBatch(roleMenuList);
	}

	@Override
	public void updateRole(Role role) {
		updateById(role);
		roleMenuService.deleteRoleMenuByRoleId(role.getId());
		insertRoleMenu(role);
	}

	@Override
	public List<Long> selectRoleIdByUserId(Long userId) {
		return getBaseMapper().selectRoleIdByUserId(userId);
	}
}

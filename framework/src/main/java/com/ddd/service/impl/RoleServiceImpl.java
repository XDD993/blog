package com.ddd.service.impl;

import com.ddd.domain.entity.Role;
import com.ddd.mapper.RoleMapper;
import com.ddd.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}

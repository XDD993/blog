package com.ddd.service;

import com.ddd.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
public interface IRoleService extends IService<Role> {

	List<String> selectRoleKeyByUserId(Long id);
}

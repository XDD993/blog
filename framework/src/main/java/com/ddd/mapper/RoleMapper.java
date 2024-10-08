package com.ddd.mapper;

import com.ddd.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
public interface RoleMapper extends BaseMapper<Role> {

	List<String> selectRoleKeyByOtherUserId(Long userId);

	List<Long> selectRoleIdByUserId(Long userId);

}

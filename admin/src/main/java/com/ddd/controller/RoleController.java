package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.dto.ChangeRoleStatusDto;
import com.ddd.domain.entity.Role;
import com.ddd.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	@GetMapping("/list")
	public ResponseResult list(Role role,Integer pageNum,Integer pageSize){
		return roleService.getList(role,pageNum,pageSize);
	}

	@PutMapping("/changeStatus")
	public ResponseResult edit(@RequestBody ChangeRoleStatusDto roleStatusDto){
		Role role = new Role();
		role.setStatus(roleStatusDto.getStatus());
		role.setId(roleStatusDto.getRoleId());
		roleService.updateById(role);
		return ResponseResult.okResult();
	}

	@PostMapping
	public ResponseResult add( @RequestBody Role role) {
		roleService.insertRole(role);
		return ResponseResult.okResult();
	}

	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@PutMapping
	public ResponseResult edit(@RequestBody Role role) {
		roleService.updateRole(role);
		return ResponseResult.okResult();
	}

	/**
	 * 获取角色详情
	 * @param roleId
	 * @return
	 */
	@GetMapping("/{roleId}")
	public ResponseResult getInfo(@PathVariable("roleId")Long roleId){
		Role role = roleService.getById(roleId);
		return ResponseResult.okResult(role);
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseResult remove(@PathVariable(name = "id") Long id) {
		roleService.removeById(id);
		return ResponseResult.okResult();
	}
}

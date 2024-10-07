package com.ddd.controller;

import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.Menu;
import com.ddd.domain.vo.MenuTreeVo;
import com.ddd.domain.vo.MenuVo;
import com.ddd.domain.vo.RoleMenuTreeSelectVo;
import com.ddd.service.IMenuService;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;

	@GetMapping("/list")
	public ResponseResult list(Menu menu) {
		List<Menu> menuList = menuService.selectMenuList(menu);
		List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
		return ResponseResult.okResult(menuVos);
	}

	@PostMapping
	public ResponseResult add(@RequestBody Menu menu) {
		menuService.save(menu);
		return ResponseResult.okResult();
	}

	@GetMapping("/{menuId}")
	public ResponseResult getInfo(@PathVariable("menuId") Long menuId) {
		Menu menu = menuService.getById(menuId);
		return ResponseResult.okResult(menu);
		}

	@PutMapping()
	public ResponseResult edit(@RequestBody Menu menu){
		if (menu.getId().equals(menu.getParentId())) {
			return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
		}
		menuService.updateById(menu);
		return ResponseResult.okResult();
	}

	@DeleteMapping("/{menuId}")
	public ResponseResult delete(@PathVariable ("menuId")Long menuId){
		if (menuService.hasChild(menuId)) {
			return ResponseResult.errorResult(500,"存在子菜单不允许删除");
		}
		menuService.removeById(menuId);
		return ResponseResult.okResult();
	}

	@GetMapping("/treeselect")
	public  ResponseResult getAllMenuList(){
		List<Menu> list = menuService.list();
		List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(list);
		return ResponseResult.okResult(menuTreeVos);
	}

	/**
	 * 修改角色查询角色的菜单列表树
	 * @param roleId
	 * @return
	 */
	@GetMapping("/roleMenuTreeselect/{roleId}")
	public ResponseResult roleMenuTreeSelect(@PathVariable("roleId")Long roleId){
		List<Menu> menus = menuService.selectMenuList(new Menu());
		List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
		List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
		RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
		return ResponseResult.okResult(vo);

	}
}

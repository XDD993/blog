package com.ddd.mapper;

import com.ddd.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-09-25
 */
public interface MenuMapper extends BaseMapper<Menu> {


	 List<String> selectPermsByOtherUserId(Long id);

}

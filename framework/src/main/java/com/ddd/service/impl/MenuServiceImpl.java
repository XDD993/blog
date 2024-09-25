package com.ddd.service.impl;

import com.ddd.domain.entity.Menu;
import com.ddd.mapper.MenuMapper;
import com.ddd.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

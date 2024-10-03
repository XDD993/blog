package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddd.domain.entity.LoginUser;
import com.ddd.domain.entity.User;
import com.ddd.mapper.MenuMapper;
import com.ddd.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * 用户登录的时候去查询名字和密码是否相符
	 * @param s
	 * @return
	 * @throws UsernameNotFoundException
	 */

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", username));
		if (Objects.isNull(user)){
			throw new RuntimeException("用户不存在");
		}
		//如果是后台的请求 需要进行权限校验
		if (user.getType().equals("1")){
			List<String> list = menuMapper.selectPermsByOtherUserId(user.getId());
			return new LoginUser(user,list);
		}
		return new LoginUser(user,null);
	}
}

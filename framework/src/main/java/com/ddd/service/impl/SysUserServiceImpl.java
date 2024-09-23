package com.ddd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddd.domain.ResponseResult;
import com.ddd.domain.entity.SysUser;
import com.ddd.domain.vo.UserInfoVo;
import com.ddd.enums.AppHttpCodeEnum;
import com.ddd.handler.exception.SystemException;
import com.ddd.mapper.SysUserMapper;
import com.ddd.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddd.utils.BeanCopyUtils;
import com.ddd.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseResult userInfo() {
		Long userId = SecurityUtils.getUserId();
		SysUser user = getById(userId);
		UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
		return ResponseResult.okResult(userInfoVo);
	}

	@Override
	public ResponseResult updateUserInfo(UserInfoVo user) {
		SysUser sysUser = BeanCopyUtils.copyBean(user, SysUser.class);
		updateById(sysUser);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult register(SysUser user) {
		if (!StringUtils.hasText(user.getUserName())){
			throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
		}
		if (!StringUtils.hasText(user.getNickName())){
			throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
		}
		if (!StringUtils.hasText(user.getPassword())){
			throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
		}
		if (!StringUtils.hasText(user.getEmail())){
			throw new SystemException(AppHttpCodeEnum.EMALI_NOT_NULL);
		}
		//判断用户传给我们的用户名是否在数据库已经存在。userNameExist方法是下面定义的
		if(userNameExist(user.getUserName())){
			//SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
			throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
		}
		//判断用户传给我们的昵称是否在数据库已经存在。NickNameExist方法是下面定义的
		if(NickNameExist(user.getNickName())){
			//SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
			throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
		}
		//判断用户传给我们的邮箱是否在数据库已经存在。NickNameExist方法是下面定义的
		if(EmailExist(user.getEmail())){
			//SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
			throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
		}
		//判断用户传给我们的手机号码是否在数据库已经存在。PhonenumberExist方法是下面定义的
		if(PhonenumberExist(user.getPhonenumber())){
			//SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
			throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
		}

		//经过上面的判断，可以确保用户传给我们的用户名和昵称是数据库不存在的，且相关字段都不为空。就可以存入数据库
		//注意用户传给我们的密码是明文，对于密码我们要转成密文之后再存入数据库。注意加密要和解密用同一套算法
		//在huanf-blog工程的securityConfig类里面有解密算法，当时我们写了一个passwordEncoder方法，并且注入到了spring容器
		String encodePassword = passwordEncoder.encode(user.getPassword());//加密
		user.setPassword(encodePassword);
		//存入数据库。save方法是mybatisplus提供的方法
		save(user);

		//封装响应返回
		return ResponseResult.okResult();
	}

	//'判断用户传给我们的用户名是否在数据库已经存在' 的方法
	private boolean userNameExist(String userName) {
		//要知道是否存在，首先就是根据条件去数据库查
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		//拿用户写的用户名跟数据库里面的用户名进行比较
		queryWrapper.eq(SysUser::getUserName,userName);
		//如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
		boolean result = count(queryWrapper) > 0;
		//为true就说明已存在
		return result;
	}

	//'判断用户传给我们的昵称是否在数据库已经存在' 的方法
	private boolean NickNameExist(String nickName) {
		//要知道是否存在，首先就是根据条件去数据库查
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		//拿用户写的昵称跟数据库里面的昵称进行比较
		queryWrapper.eq(SysUser::getNickName,nickName);
		//如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
		boolean result = count(queryWrapper) > 0;
		//为true就说明已存在
		return result;
	}

	//'判断用户传给我们的邮箱是否在数据库已经存在' 的方法
	private boolean EmailExist(String email) {
		//要知道是否存在，首先就是根据条件去数据库查
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		//拿用户写的昵称跟数据库里面的昵称进行比较
		queryWrapper.eq(SysUser::getEmail,email);
		//如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
		boolean result = count(queryWrapper) > 0;
		//为true就说明已存在
		return result;
	}

	//'判断用户传给我们的手机号码是否在数据库已经存在' 的方法
	private boolean PhonenumberExist(String Phonenumber) {
		//要知道是否存在，首先就是根据条件去数据库查
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		//拿用户写的手机号码跟数据库里面的手机号码进行比较
		queryWrapper.eq(SysUser::getPhonenumber,Phonenumber);
		//如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
		boolean result = count(queryWrapper) > 0;
		//为true就说明已存在
		return result;
	}

}

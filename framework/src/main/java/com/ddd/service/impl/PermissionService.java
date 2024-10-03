package com.ddd.service.impl;

import com.ddd.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ps")
public class PermissionService {

	public boolean hasPermission(String permission){
		if (SecurityUtils.getUserId().equals("1")){
			return true;
		}else {
			return SecurityUtils.getLoginUser().getPermissions().contains(permission);
		}

	}
}

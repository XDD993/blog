package com.ddd.domain.vo;


import com.ddd.domain.entity.Role;
import com.ddd.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {
    private SysUser user;
    private List<Role> roles;
    private List<Long> roleIds;
}
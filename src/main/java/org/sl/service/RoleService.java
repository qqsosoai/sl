package org.sl.service;

import org.sl.bean.Role;

import java.util.List;

/**
 * Created by hasee on 2017/10/1.
 */
public interface RoleService {
    /**
     * 查询所有角色列表
     * @return
     */
    List<Role> findRolesAll();
}

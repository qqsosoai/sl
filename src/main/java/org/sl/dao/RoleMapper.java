package org.sl.dao;

import org.sl.bean.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hasee on 2017/10/1.
 * 角色Dao接口
 */
@Repository
public interface RoleMapper {
    List<Role> fundRolesAll();
}

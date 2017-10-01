package org.sl.service.impl;

import org.apache.log4j.Logger;
import org.sl.bean.Role;
import org.sl.dao.RoleMapper;
import org.sl.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hasee on 2017/10/1.
 * 处理角色的业务逻辑层
 */
@Service
public class RoleServiceImpl implements RoleService{
    private Logger logger=Logger.getLogger(RoleServiceImpl.class);
    @Resource
    private RoleMapper mapper;
    public List<Role> findRolesAll() {
        return mapper.fundRolesAll();
    }
}

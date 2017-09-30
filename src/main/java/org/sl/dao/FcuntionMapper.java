package org.sl.dao;

import org.sl.bean.Function;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 * 功能Dao层
 */
@Repository
public interface FcuntionMapper {
    /**
     * 根据角色ID查询一级菜单
     * @param roleId 角色ID
     * @return 一级菜单集合
     */
    List<Function> findMainFunctionByRoleId(Serializable roleId);

    /**
     * 根据一级菜单查询二级菜单
     * @param function 一级菜单
     * @return 二级菜单集合
     */
    List<Function> findChildFcuntion(Function function);
}

package org.sl.service;

import org.sl.bean.Function;
import org.sl.bean.User;
import org.sl.util.Menu;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 */
public interface FunctionService {
    List<Menu> findUserMenuByRoleId(Integer roleId)throws Exception;
}

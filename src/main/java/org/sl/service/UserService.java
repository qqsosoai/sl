package org.sl.service;

import org.sl.bean.Function;
import org.sl.bean.User;

import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 */
public interface UserService {
    /**
     * 根据登录用户名查询用户是否存在
     * @param user
     * @return 返回记录数
     */
    int findByLoginCount(User user)throws Exception;

    /**
     * 根据用户名密码查询
     * @param user
     * @return 返回用户完整信息
     */
    User findByUser(User user)throws Exception;
}

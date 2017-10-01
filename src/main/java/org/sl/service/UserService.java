package org.sl.service;

import org.sl.bean.Function;
import org.sl.bean.User;
import org.sl.util.PageUtil;

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

    /**
     * 修改用户密码
     * @param user 用户对象
     * @return
     * @throws Exception
     */
    boolean updateUser(User user)throws Exception;

    /**
     * 根据用户信息查询用户集合
     * @param user 用户条件
     * @return 符合条件的用户集合
     * @throws Exception
     */
    List<User> findByUsers(User user, PageUtil util)throws Exception;

    /**
     * 根据用户条件查询记录数
     * @param user 用户条件
     * @return 符合条件的记录
     * @throws Exception
     */
    Integer findByUsersCount(User user)throws Exception;
}

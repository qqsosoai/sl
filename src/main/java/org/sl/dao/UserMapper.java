package org.sl.dao;

import org.apache.ibatis.annotations.Param;
import org.sl.bean.User;
import org.sl.util.PageUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 *用户Dao层
 */
@Repository
public interface UserMapper {
    /**
     * 根据用户信息查询用户
     * @param user 用户信息
     * @return 用户完整信息
     */
    User findByUser(User user)throws Exception;

    /**
     * 根据用户信息查询用户集合
     * @param user 用户条件
     * @return 用户集合
     */
    List<User> findByUsers(@Param("user") User user, @Param("pageIndex") Integer pageIndex,
                           @Param("pageSize")Integer pageSize)throws Exception;

    /**
     * 根据用户账号查询总记录数
     * @param user 用户信息
     * @return 用户记录数
     */
    int findByLoginCount(User user)throws Exception;

    /**
     * 修改用户
     * @param user
     * @return 影响行数
     */
    int updateByUser(User user)throws Exception;

    int addUser(User user)throws Exception;

}

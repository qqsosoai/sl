package org.sl.dao;

import org.sl.bean.User;
import org.springframework.stereotype.Repository;

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
    User findByUser(User user);

    /**
     * 根据用户账号查询总记录数
     * @param user 用户信息
     * @return 用户记录数
     */
    int findByLoginCount(User user);

    /**
     * 修改用户
     * @param user
     * @return 影响行数
     */
    int updateByUser(User user);
}

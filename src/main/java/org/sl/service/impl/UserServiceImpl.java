package org.sl.service.impl;

import org.apache.log4j.Logger;
import org.sl.bean.User;
import org.sl.dao.UserMapper;
import org.sl.service.UserService;
import org.sl.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by hasee on 2017/9/30.
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger logger=Logger.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper mapper;


    public int findByLoginCount(User user)throws Exception {
        return mapper.findByLoginCount(user);
    }

    public User findByUser(User user) throws Exception{
        return mapper.findByUser(user);
    }

    public boolean updateUser(User user) throws Exception {
        if (mapper.updateByUser(user)>0)
            return true;
        return false;
    }

    public List<User> findByUsers(User user, PageUtil util) throws Exception {
        Integer index=(util.getPageIndex()-1)*util.getPageSize();
        return mapper.findByUsers(user,index,util.getPageSize());
    }

    public Integer findByUsersCount(User user) throws Exception {
        return mapper.findByLoginCount(user);
    }


}

package org.sl.controller;

import org.apache.log4j.Logger;
import org.sl.bean.User;
import org.sl.service.UserService;
import org.sl.util.Constants;
import org.sl.util.md5.MyMd5;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by hasee on 2017/9/30.
 * 针对于用户信息的controller
 */
@Controller
public class UserController{
    private Logger logger=Logger.getLogger(UserController.class);
    @Resource
    private UserService service;

    @RequestMapping(value = "/backend/modifyPwd.html",method = RequestMethod.POST)//修改用户登录密码
    @ResponseBody
    public Object updatePwd(String password,String newPassword,HttpSession session){
        if (StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(newPassword)){//判断前台数据是否正常
            return "notFind";
        }
        User user = (User) session.getAttribute(Constants.SESSION_LOGIN_USER);
        if (!MyMd5.isMd5String(password,user.getPassword())){//原密码是否匹配
            return "pwdEor";
        }
        String newpass = MyMd5.toMd5String(newPassword);//将新密码加密
        User user1=new User(user.getId(),newpass);
        try {
            if (!service.updateUser(user1)){//修改失败
                return "notFind";
            }
            user.setPassword(newpass);
            return "success";
        } catch (Exception e) {//出现异常
            e.printStackTrace();
            return "notFind";
        }
    }

}

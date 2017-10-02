package org.sl.controller;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.HttpResponse;
import org.apache.log4j.Logger;
import org.sl.bean.Role;
import org.sl.bean.User;
import org.sl.service.RoleService;
import org.sl.service.UserService;
import org.sl.util.Constants;
import org.sl.util.Menu;
import org.sl.util.PageUtil;
import org.sl.util.md5.MyMd5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 * 针对于用户信息的controller
 */
@Controller
public class UserController{
    private Logger logger=Logger.getLogger(UserController.class);
    @Resource
    private UserService service;
    @Resource
    private RoleService roleService;

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
            String result = service.updateUser(user1) ? "success":"notFind" ;
            user.setPassword(newpass);
            return result;
        } catch (Exception e) {//出现异常
            e.printStackTrace();
            return "notFind";
        }
    }
    @RequestMapping(value = "/backend/userlist.html",method = RequestMethod.GET)//请求用户主页面
    public String toUserList(HttpSession session, Model model){
        List<Menu> menus = (List<Menu>) session.getAttribute(Constants.SESSION_LOGIN_USER_MENU);
        if (menus==null){
            return "redirect:/login.html";
        }
        PageUtil util=new PageUtil();//分页对象
        try {
            User user=new User();
            util.setSqlCount(service.findByUsersCount(user));
            List<User> userList = service.findByUsers(user, util);//查询所有用户
            logger.debug(userList);
            model.addAttribute("userList",userList);
            List<Role> rolesAll = roleService.findRolesAll();//查询所有角色
            model.addAttribute("roleList",rolesAll);
            model.addAttribute("page",util);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("jsonMenu",JSON.toJSONString(menus));
        return "/backend/userList";
    }
    @RequestMapping(value = "/backend/userlist.html",method = RequestMethod.POST)
    //处理用户主页面分页请求
    public void UserAjax(PageUtil util, User user,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        if (util==null||user==null){
            return;
        }
        try {
            util.setSqlCount(service.findByUsersCount(user));
            List<User> users = service.findByUsers(user, util);
            String jsonUtil = JSON.toJSONString(util);
            if (user==null || users.size()<1){
                writer.print("["+jsonUtil+","+"{\"flag\":\"false\"}]");
                writer.flush();writer.close();
                return;
            }
            String json = JSON.toJSONString(users).replace("]", "," + jsonUtil + "]");
            logger.debug(json);
            writer.print(json);
            writer.flush();writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}

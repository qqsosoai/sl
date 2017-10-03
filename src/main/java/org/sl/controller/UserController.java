package org.sl.controller;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.HttpResponse;
import org.apache.log4j.Logger;
import org.sl.bean.DataDictionary;
import org.sl.bean.Role;
import org.sl.bean.User;
import org.sl.service.DataDictionaryService;
import org.sl.service.RoleService;
import org.sl.service.UserService;
import org.sl.util.*;
import org.sl.util.md5.MyMd5;
import org.sl.util.redis.CacheApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Resource
    private DataDictionaryService dictionaryService;
    @Resource(name = "redisApi")
    private CacheApi cache;

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
            model.addAttribute("roleList",rolesAll);//添加所有角色
            model.addAttribute("page",util);//添加页面信息
            List<DataDictionary> dictionaries=null;//获取所有证件类型
            if (cache.exist("Dictionaries"+ DictionariesTypeConstants.CERTIFICATE_TYPE)){
                //查询缓存
                dictionaries= (List<DataDictionary>)
                        cache.get("Dictionaries"+ DictionariesTypeConstants.CERTIFICATE_TYPE);
            }else{
                //查询数据库
                DataDictionary dictionary=new DataDictionary();
                dictionary.setTypeCode(DictionariesTypeConstants.CERTIFICATE_TYPE);
                dictionaries= dictionaryService.findByDataDictionarys(dictionary);
                cache.set("Dictionaries"+ DictionariesTypeConstants.CERTIFICATE_TYPE,dictionaries);
            }
            model.addAttribute("cardTypeList",dictionaries);//添加所有证件类型
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
    @RequestMapping("/backend/loadUserTypeList.html")//处理获取会员类型
    public void getUserType(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        List<DataDictionary> userType=null;
        //判断缓存中存不存在
        if (cache.exist("Dictionaries"+DictionariesTypeConstants.USER_TYPE)){
            userType= (List<DataDictionary>)
                    cache.get("Dictionaries"+DictionariesTypeConstants.USER_TYPE);
        }else{//缓存中不存在，查询数据库放入缓存
            DataDictionary dictionary=new DataDictionary();
            dictionary.setTypeCode(DictionariesTypeConstants.USER_TYPE);
            try {
                userType=dictionaryService.findByDataDictionarys(dictionary);//查询会员类型
                cache.set("Dictionaries"+DictionariesTypeConstants.USER_TYPE,userType);//添加缓存
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String s = JSON.toJSONString(userType);
        writer.print(s);
        writer.flush();writer.close();
    }
    @RequestMapping("/backend/logincodeisexit.html")//判断该用户名是否存在
    public void loginCodeExist(HttpServletResponse response,User user) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            if (user==null){//请求错误
                writer.print("eor");
                return;
            }
            if (user.getId()<0)
                user.setId(null);
            if (service.findByLoginCount(user)>0){
                writer.print("exist");//用户名已存在
                return;
            }else{//用户名不存在
                writer.print("ok");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            writer.print("eor");
            return;
        }finally {
            writer.flush();
            writer.close();
        }
    }
    @RequestMapping("/backend/adduser.html")
    public String addUser(HttpSession session,User user){
        User loginUser = (User) session.getAttribute(Constants.SESSION_LOGIN_USER);
        if (loginUser==null){
            return "redirect:/login.html";
        }
        if (user==null){
            return "redirect:/backend/userlist.html";
        }
        String card = user.getIdCard();
        String ps = card.substring(card.length() - 6);
        ps=MyMd5.toMd5String(ps);
        user.setPassword(ps);
        user.setPassword2(ps);
        user.setReferId(loginUser.getReferId());
        try {
            boolean b = service.addUser(user);
            logger.debug(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/backend/userlist.html";
    }
}

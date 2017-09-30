package org.sl.controller;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.sl.bean.User;
import org.sl.service.FunctionService;
import org.sl.service.UserService;
import org.sl.util.Constants;
import org.sl.util.Menu;
import org.sl.util.md5.MyMd5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 * 用户有关于登录的Controller
 */
@Controller
public class LoginController extends BaseController {
    private Logger logger=Logger.getLogger(LoginController.class);
    @Resource
    private UserService service;
    @Resource
    private FunctionService functionService;
    @RequestMapping(value = "login.html",method = RequestMethod.GET)//请求主页面
    public String toLogin(){
        return "login";
    }
    @RequestMapping(value = "login.html",method = RequestMethod.POST)//请求登录
    @ResponseBody
    public Object toLogin(String userString, HttpSession session){
        if (userString==null){//信息不完整
            return "notFind";
        }
        try {
            User user = JSON.parseObject(userString, User.class);
            logger.debug(user);
            if (user==null){
                return "notFind";
            }
            user.setIsStart(1);
            if (service.findByLoginCount(user)<1){//用户名不正确
                return "usernameEor";
            }
            User loginUser = service.findByUser(user);
            if (!MyMd5.isMd5String(user.getPassword(),loginUser.getPassword())){//密码不正确
                return "pwdEor";
            }
            session.setAttribute(Constants.SESSION_LOGIN_USER,loginUser);
            setUser(loginUser);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "notFind";
        }
    }
    @RequestMapping("main.html")//请求主页面
    public String toMain(Model model,HttpSession session){
        User user = getUser();
        if (user==null){
            return "redirect:/login.html";
        }
        try {
            List<Menu> menus = functionService.findUserMenuByRoleId(user.getRoleId());
            session.setAttribute(Constants.SESSION_LOGIN_USER_MENU,menus);
            String json = JSON.toJSONString(menus);
            logger.debug(json);
            model.addAttribute("jsonMenu",json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main";
    }

    @RequestMapping("/logout.html")//注销登录
    public String logout(HttpSession session){
        session.invalidate();
        this.setUser(null);
        return "redirect:/lgin.html";
    }
}

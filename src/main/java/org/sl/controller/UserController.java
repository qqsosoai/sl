package org.sl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Random;

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
    @RequestMapping("/backend/adduser.html")//处理用户添加
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
    @RequestMapping("/backend/upload.html")
    @ResponseBody
    public String ajaxFileUpload(@RequestParam(value = "a_fileInputID",required = false)
                                  MultipartFile cardFile,
                                 @RequestParam(value = "a_fileInputBank", required = false)
                                  MultipartFile bankFile,
                                 @RequestParam(value = "m_fileInputID",required = false)
                                  MultipartFile mCardFile,
                                 @RequestParam(value = "m_fileInputBank",required = false)
                                  MultipartFile mBankFile,
                                 HttpServletRequest request){

        if (cardFile!=null) {//判断用户上传增加身份证
            String result = isUpload(cardFile);
            if (result!=null)
                return result;
            return upload(cardFile,request);
        } else if(bankFile!=null){//判断用户上传增加银行卡
            String result = isUpload(bankFile);
            if (result!=null)
                return result;
            return upload(bankFile,request);
        } else if(mCardFile!=null){//判断用户上传修改身份证
            String result = isUpload(mCardFile);
            if (result!=null)
                return result;
            return upload(mCardFile,request);
        } else if(mBankFile!=null){//判断用户上传修改银行卡
            String result = isUpload(mBankFile);
            if (result!=null)
                return result;
            return upload(mBankFile,request);
        }
        return "3";
    }
    //判断文件上传是否符合规范,获取字典表上传文件大小判断，判断用户上传的图片格式
    private String isUpload(MultipartFile file){
        int fileSize=50000;
        try {
            if (cache.exist("Dictionaries"+DictionariesTypeConstants.UPLOAD_PRIVATE_MAN)){
                List<DataDictionary> list = (List<DataDictionary>) cache.get(
                        "Dictionaries" + DictionariesTypeConstants.UPLOAD_PRIVATE_MAN);
                fileSize=Integer.parseInt(list.get(0).getValueName());
            }else{
                DataDictionary dictionary=new DataDictionary();
                dictionary.setTypeCode(DictionariesTypeConstants.UPLOAD_PRIVATE_MAN);//获取上传大小
                List<DataDictionary> list = dictionaryService.findByDataDictionarys(dictionary);
                fileSize=Integer.parseInt(list.get(0).getValueName());
                cache.set("Dictionaries"+DictionariesTypeConstants.UPLOAD_PRIVATE_MAN,list);//添加缓存
            }
            if (file.getSize()>fileSize){
                return "1";
            }
            String suffix = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!(suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png")
                ||suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg"))){
                return "2";
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "3";
        }
    }
    //保存方法
    private String upload(MultipartFile file,HttpServletRequest request){
        String path=request.getSession().getServletContext()
                .getRealPath("static"+ File.separator+"uploadFiles");//上传文件路径
        String fileName=System.currentTimeMillis()+ new Random().nextInt(10000000)+"_IDcard.jpg";
        try {
            File targetFile=new File(path,fileName);
            file.transferTo(targetFile);
            return request.getContextPath()+"/static/uploadfiles/"+fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "3";
        }
    }
}

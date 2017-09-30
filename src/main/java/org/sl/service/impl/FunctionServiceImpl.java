package org.sl.service.impl;

import org.apache.log4j.Logger;
import org.sl.bean.Function;
import org.sl.dao.FunctionMapper;
import org.sl.service.FunctionService;
import org.sl.util.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 */
@Service
public class FunctionServiceImpl implements FunctionService {
    private Logger logger=Logger.getLogger(FunctionServiceImpl.class);
    @Resource
    private FunctionMapper mapper;

    //根据角色ID查询所有菜单
    public List<Menu> findUserMenuByRoleId(Integer roleId) throws Exception {
        List<Menu> menus=new ArrayList<Menu>();
        List<Function> mainFunction = mapper.findMainFunctionByRoleId(roleId);
        for(Function f:mainFunction){
            Menu menu=new Menu();
            menu.setMainMenu(f);
            f.setRoleId(roleId);
            List<Function> childFcuntion = mapper.findChildFcuntion(f);
            if (childFcuntion.size()<1){
                continue;
            }
            menu.setChildMenu(childFcuntion);
            menus.add(menu);
        }
        return menus;
    }
}

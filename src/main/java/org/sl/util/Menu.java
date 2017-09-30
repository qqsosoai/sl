package org.sl.util;

import org.sl.bean.Function;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hasee on 2017/9/30.
 * 菜单工具类
 */
public class Menu implements Serializable,Cloneable {
    private Function mainMenu;//主菜单
    private List<Function> childMenu;//子菜单

    public Menu() {
    }

    public Function getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Function mainMenu) {
        this.mainMenu = mainMenu;
    }

    public List<Function> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Function> childMenu) {
        this.childMenu = childMenu;
    }
}

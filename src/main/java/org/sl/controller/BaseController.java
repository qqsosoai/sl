package org.sl.controller;

import org.sl.bean.User;

/**
 * Created by hasee on 2017/9/30.
 */
public class BaseController {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.bs.afterservice.bean;

import java.io.Serializable;

/**
 * Description:添加售后人员的Bean类
 * AUTHOR: Champion Dragon
 * created at 2018/3/19
 **/

public class CustserBean implements Serializable {
    private int _id;
    private String name;

    public boolean ischeck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    private String username;
    private boolean ischeck;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

package com.bs.afterservice.bean;

/**
 * Description: 登录记录的Bean类
 * AUTHOR: Champion Dragon
 * created at 2018/3/10
 **/

public class LoginBean {
    private int _id;
    private String name;
    private String time;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}

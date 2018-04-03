package com.bs.afterservice.bean;

import java.io.Serializable;

/**
 * Description: user信息的bean类
 * AUTHOR: Champion Dragon
 * created at 2018/2/26
 **/

public class UserInfo implements Serializable {
    String headpath;

    public void setHeadpath(String headpath) {
        this.headpath = headpath;
    }

    public String getHeadpath() {

        return headpath;
    }
}

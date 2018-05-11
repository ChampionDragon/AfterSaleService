package com.bs.afterservice.rvstrtwo;

import java.io.Serializable;
import java.util.List;

/**
 * Description:模拟数据的类
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/

public class DataBean implements Serializable{
    public static final int PARENT_ITEM = 0;//父布局
    public static final int CHILD_ITEM = 1;//子布局

    private int type;// 显示类型
    private boolean isExpand;// 是否展开
    private boolean isHide;//是否隐藏

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    private DataBean childBean;

    private String ID;
    private String parentLeftTxt;
    private String childLeftTxt;
    private String childRightTxt;
    private List<String> listdata;//全部数据

    public List<String> getListdata() {
        return listdata;
    }

    public void setListdata(List<String> listdata) {
        this.listdata = listdata;
    }


    public String getParentLeftTxt() {
        return parentLeftTxt;
    }

    public void setParentLeftTxt(String parentLeftTxt) {
        this.parentLeftTxt = parentLeftTxt;
    }

    public String getChildRightTxt() {
        return childRightTxt;
    }

    public void setChildRightTxt(String childRightTxt) {
        this.childRightTxt = childRightTxt;
    }

    public String getChildLeftTxt() {
        return childLeftTxt;
    }

    public void setChildLeftTxt(String childLeftTxt) {
        this.childLeftTxt = childLeftTxt;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public DataBean getChildBean() {
        return childBean;
    }

    public void setChildBean(DataBean childBean) {
        this.childBean = childBean;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}

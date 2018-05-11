package com.bs.afterservice.rvstrtwo;

/**
 * Description: 父布局Item点击监听接口
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/

public interface ItemClickListener {
    /**
     * 展开子Item
     * @param bean
     */
    void onExpandChildren(DataBean bean);

    /**
     * 隐藏子Item
     * @param bean
     */
    void onHideChildren(DataBean bean);
}

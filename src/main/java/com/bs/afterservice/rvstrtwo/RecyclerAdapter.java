package com.bs.afterservice.rvstrtwo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bs.afterservice.R;

import java.util.List;

/**
 * Description: RecyclerView的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<DataBean> dataBeanList;
    private LayoutInflater mInflater;
    private OnScrollListener mOnScrollListener;
    private DataBean temData;

    public RecyclerAdapter(Context context, List<DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        /*viewType是getItemViewType(int position)方法的返回值*/
        switch (viewType) {
            case DataBean.PARENT_ITEM:
                view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
                return new ParentViewHolder(context, view);
            case DataBean.CHILD_ITEM:
                view = mInflater.inflate(R.layout.recycleview_item_child, parent, false);
                return new ChildViewHolder(context, view);
            default:
                view = mInflater.inflate(R.layout.recycleview_item_parent, parent, false);
                return new ParentViewHolder(context, view);
        }
    }

    /**
     * 根据不同的类型绑定View
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case DataBean.PARENT_ITEM:
                ParentViewHolder parentViewHolder = (ParentViewHolder) holder;
                parentViewHolder.bindView(dataBeanList.get(position), position, itemClickListener);
                break;
            case DataBean.CHILD_ITEM:
                ChildViewHolder childViewHolder = (ChildViewHolder) holder;
                childViewHolder.bindView(dataBeanList.get(position), position);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataBeanList.get(position).getType();
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onExpandChildren(DataBean bean) {
            /*如果临时DataBean对象temData不为空且是展开状态我就将它收起*/
            if (temData != null && temData.isExpand()) {
                DataBean dataBean = dataBeanList.get(getCurrentPosition(temData.getID()));
                dataBean.setHide(true);//设置一个是否能隐藏的标志位
                dataBean.setExpand(false);
                onHideChildren(dataBean);//必须先隐藏再刷新不然程序会死掉报错，因为在RecyclerView在调用，
                //scrollToPosition(pos)时不能刷新控件,onHideChildren(DataBean bean)里面接口回调了这个方法。
                notifyItemChanged(getCurrentPosition(temData.getID()));
            }
            //记录上一个DataBean对象
            temData = bean;

            int position = getCurrentPosition(bean.getID());//确定当前点击的item位置
            DataBean children = getChildDataBean(bean);//获取要展示的子布局数据对象，注意区分onHideChildren方法中的getChildBean()。
            if (children == null) {
                return;
            }
            add(children, position + 1);//在当前的item下方插入
            if (position == dataBeanList.size() - 2 && mOnScrollListener != null) { //如果点击的item为最后一个
                mOnScrollListener.scrollTo(position + 1);//向下滚动，使子布局能够完全展示
            }
        }

        @Override
        public void onHideChildren(DataBean bean) {
            int position = getCurrentPosition(bean.getID());//确定当前点击的item位置
            DataBean children = bean.getChildBean();//获取子布局对象
            if (children == null) {
                return;
            }
            remove(position + 1);//删除
            if (mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position);
            }
        }
    };

    /**
     * 在父布局下方插入一条数据
     *
     * @param bean
     * @param position
     */
    public void add(DataBean bean, int position) {
        dataBeanList.add(position, bean);
        notifyItemInserted(position);
    }

    /**
     * 移除子布局数据
     *
     * @param position
     */
    protected void remove(int position) {
        dataBeanList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 确定当前点击的item位置并返回
     *
     * @param uuid
     * @return
     */
    protected int getCurrentPosition(String uuid) {
        for (int i = 0; i < dataBeanList.size(); i++) {
            if (uuid.equalsIgnoreCase(dataBeanList.get(i).getID())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 封装子布局数据对象并返回
     * 注意，此处只是重新封装一个DataBean对象，为了标注Type为子布局数据，进而展开，展示数据
     * 要和onHideChildren方法里的getChildBean()区分开来
     *
     * @param bean
     * @return
     */
    private DataBean getChildDataBean(DataBean bean) {
        DataBean child = new DataBean();
        child.setType(1);
        child.setParentLeftTxt(bean.getParentLeftTxt());
//      child.setChildLeftTxt(bean.getChildLeftTxt());
        child.setChildRightTxt(bean.getChildRightTxt());
        child.setListdata(bean.getListdata());
        return child;
    }

    /**
     * 滚动监听接口
     */
    public interface OnScrollListener {
        void scrollTo(int pos);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }
}

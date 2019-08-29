package com.cn.danceland.myapplication.view.adapter;

/**
 * 此接口是让RecyclerView添加多条目的支持
 * Created by chenj on 2016/7/28.
 */
public interface MultiItemTypeSupport<T> {
    int getItemViewType(int position, T item);//通过position来返回条目的类型
    int getItemLayoutId(int itemType);//根据要显示的条目类型，去返回相应的布局id
}

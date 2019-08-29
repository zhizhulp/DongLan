package com.cn.danceland.myapplication.view.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenj on 2016/7/28.
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    private MultiItemTypeSupport mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas ,MultiItemTypeSupport multiItemTypeSupport) {
        //layoutId是在onCreateViewHolder中加载布局的，因为这里还要重写该方法，所以不需要传layoutId
        super(context, -1, datas);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        //根据position和java bean对象中的某个字段值就可以该条目是什么类型的了
        //类型可以从0开始，依次往上加，每个数字对应一种类型
        return mMultiItemTypeSupport.getItemViewType(position,mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //通过viewType获取不同条目的布局id
        int itemLayoutId = mMultiItemTypeSupport.getItemLayoutId(viewType);
        //通过布局id去创建ViewHolder
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, itemLayoutId);

        return viewHolder;
    }

}

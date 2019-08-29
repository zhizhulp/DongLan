package com.cn.danceland.myapplication.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cn.danceland.myapplication.R
import com.cn.danceland.myapplication.bean.Content

open class MyTrainingAdapter(layoutResId: Int, data: MutableList<Content>?) : BaseQuickAdapter<Content, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: Content?) {
        helper?.setText(R.id.tv_title, item?.title)
        helper?.setText(R.id.tv_content, item?.content)
        val options = RequestOptions().placeholder(R.drawable.loading_img)
        Glide.with(mContext).load(item?.img_url).apply(options).into(helper?.getView(R.id.im_bg) as ImageView)
    }
}
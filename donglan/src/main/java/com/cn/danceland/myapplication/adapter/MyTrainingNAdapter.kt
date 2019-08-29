package com.cn.danceland.myapplication.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cn.danceland.myapplication.R
import com.cn.danceland.myapplication.bean.ContentA
import com.cn.danceland.myapplication.utils.TimeUtils
import java.util.*

class MyTrainingNAdapter(layoutResId: Int, data: MutableList<ContentA>?) : BaseQuickAdapter<ContentA, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: ContentA?) {
        //不能使用GlideApp
        val options = RequestOptions().placeholder(R.drawable.loading_img)
        Glide.with(mContext).load(item?.pic_url).apply(options).into(helper?.getView(R.id.iv_image) as ImageView)
        helper.setText(R.id.tv_tiltle, item?.title)
        helper.setText(R.id.tv_time, TimeUtils.dateToString(item?.release_date?.toLong()?.let { Date(it) }))
        helper.setText(R.id.read_number_tv, item?.watch_count.toString())
        helper.setText(R.id.share_number_tv, item?.share_count.toString())
    }
}
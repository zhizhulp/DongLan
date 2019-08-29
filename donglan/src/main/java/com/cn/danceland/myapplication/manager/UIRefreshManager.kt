package com.cn.danceland.myapplication.manager

import android.content.Context
import com.cn.danceland.myapplication.R
import com.cn.danceland.myapplication.view.ClassicsHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

open class UIRefreshManager(val context: Context, val refreshLayout: SmartRefreshLayout) {

    fun setRefreshHeader() {
        val header = ClassicsHeader(context)
        header.setHeaderTextColor(context.resources.getColor(R.color.text_gray1))
        refreshLayout.setRefreshHeader(header)
    }

    fun setRefreshRooter() {
        val classicsFooter = ClassicsFooter(context)
        classicsFooter.setProgressDrawable(context.resources.getDrawable(R.drawable.listview_loading_anim))
        refreshLayout.setRefreshFooter(classicsFooter)
    }

    fun setOnRefreshListener(refreshListener: OnRefreshListener) {
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        refreshLayout.setOnLoadMoreListener(loadMoreListener);
    }

    fun stopRefresh(success: Boolean) {
        refreshLayout.finishRefresh(success)
        //refreshLayout.setNoMoreData(false)
    }

    /**
     * 停止加载更多
     * @param isEnd true 没有更多数据 false 还有数据
     */
    fun stopLoadMore(isEnd: Boolean) {
        if (isEnd) refreshLayout.finishLoadMoreWithNoMoreData()
        else refreshLayout.finishLoadMore()
    }

    fun enableLoadMore(enable: Boolean) {
        refreshLayout.setEnableLoadMore(enable)
        refreshLayout.setEnableAutoLoadMore(enable)
    }

    fun autoRefresh(){
        refreshLayout.autoRefresh()
    }

    fun autoLoadMore(){
        refreshLayout.autoLoadMore()
    }

}
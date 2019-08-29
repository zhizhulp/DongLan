package com.cn.danceland.myapplication.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.cn.danceland.myapplication.MyApplication
import com.cn.danceland.myapplication.R
import com.cn.danceland.myapplication.activity.base.BaseActivity
import com.cn.danceland.myapplication.adapter.MyTrainingAdapter
import com.cn.danceland.myapplication.bean.Content
import com.cn.danceland.myapplication.bean.MyTraining
import com.cn.danceland.myapplication.manager.UIRefreshManager
import com.cn.danceland.myapplication.utils.Constants
import com.cn.danceland.myapplication.utils.MyStringRequest
import com.cn.danceland.myapplication.utils.ToastUtils
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_my_trainning.*

class MyTrainingActivity : BaseActivity() {
    var tag = "MyTrainingActivity";
    lateinit var adapter: MyTrainingAdapter
    lateinit var refreshManager: UIRefreshManager
    var page = 0;
    var errorBeforePage = 0;
    var isRefresh: Boolean = true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trainning)
        initView()
    }

    private fun requestData() {
        val request: MyStringRequest = object : MyStringRequest(Request.Method.POST, Constants.plus(Constants.TRAINTYPE_QUERYPAGE), Response.Listener {
            val trainingBean = Gson().fromJson(it, MyTraining::class.java)
            Log.d(tag, "response-->$trainingBean")
            refreshManager.stopRefresh(true)
            refreshManager.stopLoadMore(trainingBean.code == 0 && trainingBean.data.last)
            if (trainingBean.code == 0) {
                val content = trainingBean.data.content
                if (isRefresh) adapter.replaceData(content)
                else adapter.addData(content)
                page++
                errorBeforePage = page
            }
        }, Response.ErrorListener {
            Log.d(tag, "error")
            if (isRefresh && errorBeforePage > 0) page = errorBeforePage
            refreshManager.stopRefresh(false)
            refreshManager.stopLoadMore(false)
            ToastUtils.showToastShort(this@MyTrainingActivity.resources.getString(R.string.NETWORK_EROOR))
        }) {
            override fun getParams(): MutableMap<String, String> {
                val map = mutableMapOf<String, String>()
                map["page"] = page.toString();
                map["size"] = Constants.PER_PAGE_SIZE.toString()
                return map
            }
        }
        MyApplication.getHttpQueues().add(request)
    }

    private fun initView() {
        adapter = MyTrainingAdapter(R.layout.my_training_item, null)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyTrainingActivity)
            adapter = this@MyTrainingActivity.adapter
        }
        adapter.bindToRecyclerView(recyclerView)
        adapter.setEmptyView(R.layout.default_empty)
        adapter.setOnItemClickListener { _, _, position ->
            val content = adapter.getItem(position)
            content?.id?.let { MyTrainingNActivity.startActivity(it, this@MyTrainingActivity) }
        }
        refreshManager = UIRefreshManager(this, refreshLayout)
        refreshManager.let {
            refreshManager.setRefreshHeader()
            refreshManager.setRefreshRooter()
            refreshManager.enableLoadMore(true)
            refreshManager.autoRefresh()
            refreshManager.setOnRefreshListener(OnRefreshListener {
                page = 0
                isRefresh = true
                Log.d(tag, "onRefresh-->$page")
                requestData()
            })
            refreshManager.setOnLoadMoreListener(OnLoadMoreListener {
                isRefresh = false
                Log.d(tag, "onLoadMore-->$page")
                requestData()
            })
        }
    }

}

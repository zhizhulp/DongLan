package com.cn.danceland.myapplication.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.cn.danceland.myapplication.MyApplication
import com.cn.danceland.myapplication.R
import com.cn.danceland.myapplication.activity.base.BaseActivity
import com.cn.danceland.myapplication.adapter.MyTrainingNAdapter
import com.cn.danceland.myapplication.bean.ContentA
import com.cn.danceland.myapplication.bean.MyTrainingN
import com.cn.danceland.myapplication.bean.ShareBean
import com.cn.danceland.myapplication.manager.UIRefreshManager
import com.cn.danceland.myapplication.utils.Constants
import com.cn.danceland.myapplication.utils.LogUtil
import com.cn.danceland.myapplication.utils.MyStringRequest
import com.cn.danceland.myapplication.utils.ToastUtils
import com.google.gson.Gson
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_my_trainning.*

const val TRAIN_TYPE_ID = "id"

class MyTrainingNActivity : BaseActivity() {
    var id = 0;
    var tag = "MyTrainingNActivity";
    lateinit var adapter: MyTrainingNAdapter
    lateinit var refreshManager: UIRefreshManager
    var datas: MutableList<ContentA> = mutableListOf();
    var page = 0;
    var errorBeforePage = 0;
    var isRefresh: Boolean = true;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mytraining_n)
        id = intent.getIntExtra(TRAIN_TYPE_ID,0)
        Log.d(tag,"receive id --> $id")
        initView()
    }

    private fun requestData() {
        val request: MyStringRequest = object : MyStringRequest(Request.Method.POST, Constants.plus(Constants.TRAIN_QUERYPAGE), Response.Listener {
            val trainingBean = Gson().fromJson(it, MyTrainingN::class.java)
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
            ToastUtils.showToastShort(this@MyTrainingNActivity.resources.getString(R.string.NETWORK_EROOR))
        }) {
            override fun getParams(): MutableMap<String, String> {
                val map = mutableMapOf<String, String>()
                map["train_type_id"] = id.toString()
                map["page"] = page.toString()
                map["size"] = Constants.PER_PAGE_SIZE.toString()
                return map
            }
        }
        MyApplication.getHttpQueues().add(request)
    }


    private fun initView() {
        adapter = MyTrainingNAdapter(R.layout.my_training_n_item, datas)
        adapter.setOnItemClickListener { _, _, position ->
            val contentA = datas[position]
            val shareBean = ShareBean()
            shareBean.img_url =contentA.pic_url
            shareBean.title = contentA.title
            shareBean.url = contentA.url
            shareBean.description = contentA.description
            this@MyTrainingNActivity.startActivity(Intent(this@MyTrainingNActivity, NewsDetailsActivity::class.java)
                    .putExtra("url", contentA.url)
                    .putExtra("shareBean", shareBean)
                    .putExtra("title",  contentA.title)
                    .putExtra("img_url", contentA.pic_url))

        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyTrainingNActivity)
            adapter = this@MyTrainingNActivity.adapter
        }
        adapter.bindToRecyclerView(recyclerView)
        adapter.setEmptyView(R.layout.default_empty)

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

    companion object {
        fun startActivity(id: Int, activity: Activity) {
            val intent = Intent(activity, MyTrainingNActivity::class.java)
            intent.putExtra(TRAIN_TYPE_ID, id)
            activity.startActivity(intent)
        }
    }
}

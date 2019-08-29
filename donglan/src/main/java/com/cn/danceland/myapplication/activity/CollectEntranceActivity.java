package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.adapter.CollectListviewAdapter;
import com.cn.danceland.myapplication.bean.RequestCollectDataBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jzvd.JZVideoPlayer;

/**
 * 我的收藏
 * Created by yxx on 2018-10-24.
 */

public class CollectEntranceActivity extends BaseActivity {
    private Context context;
    private PullToRefreshListView pullToRefresh;
    private ProgressDialog dialog;
    private RelativeLayout rl_error;

    private CollectListviewAdapter collectListviewAdapter;
    public List<RequestCollectDataBean.Data.Content> data = new ArrayList<RequestCollectDataBean.Data.Content>();
    private int mCurrentPage = 0;//当前请求页

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    // dialog.dismiss();
                    collectListviewAdapter.notifyDataSetChanged();
                    pullToRefresh.onRefreshComplete();

                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_entrance);
        context = this;
        initView();
    }

    private void initView() {
        pullToRefresh = findViewById(R.id.pullToRefresh);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载……");
        rl_error = findViewById(R.id.rl_error);

        if (collectListviewAdapter == null) {
            collectListviewAdapter = new CollectListviewAdapter(data, context, CollectEntranceActivity.this);
        }
        pullToRefresh.setAdapter(collectListviewAdapter);
        pullToRefresh.getRefreshableView().setEmptyView(rl_error);
        //加入头布局
        //      pullToRefresh.getRefreshableView().addHeaderView(initHeadview(userInfo));

        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        init();


        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        new FinishRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }
        });
        pullToRefresh.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //    JZVideoPlayer.onScrollReleaseAllVideos(view, firstVisibleItem, visibleItemCount, totalItemCount);
                //   LogUtil.i("firstVisibleItem="+firstVisibleItem+"visibleItemCount="+visibleItemCount+"totalItemCount="+totalItemCount);


                JZVideoPlayer.onScrollAutoTiny(view, firstVisibleItem, visibleItemCount, 1);
            }
        });
        initData();
    }

    private boolean isEnd;

    /**
     * 一秒钟延迟
     */
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (!isEnd) {//还有数据请求
                    initData();
                }
                dialog.dismiss();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            collectListviewAdapter.notifyDataSetChanged();
            pullToRefresh.onRefreshComplete();
        }
    }

    private void init() {
        // 设置下拉刷新文本
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        // 设置上拉刷新文本
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

    }

    private void setEnd() {
        isEnd = true;//没数据了
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
        pullToRefresh.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    private void initData() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_COLLECT_QUERY), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);

                Gson gson = new Gson();
                RequestCollectDataBean bean = gson.fromJson(s, RequestCollectDataBean.class);
                // LogUtil.i(requsetDynInfoBean.toString());
                if (bean.getSuccess()) {
                    data = bean.getData().getContent();
                    mCurrentPage = mCurrentPage + 1;
                    if (data.size() > 0) {
                        if (data.size() < 10) {
                            setEnd();
                        }
                        collectListviewAdapter.addLastList(data);

                    } else {
                        if (mCurrentPage == 0) {
                            Message message = Message.obtain();
                            message.what = 3;
                            handler.sendMessage(message);
                        }
                        setEnd();
                    }
                    collectListviewAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToastShort("请求失败：" + bean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", mCurrentPage + "");//页数
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);

    }

}

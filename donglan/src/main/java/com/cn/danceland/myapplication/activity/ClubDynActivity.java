package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.adapter.ClubNewsListviewAdapter;
import com.cn.danceland.myapplication.bean.RequsetClubDynBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shy on 2018/3/21 09:49
 * Email:644563767@qq.com
 */


public class ClubDynActivity extends BaseActivity {

    private PullToRefreshListView pullToRefresh;
    private ProgressDialog dialog;
    private List<RequsetClubDynBean.Data.Content> data = new ArrayList<>();
    private ClubNewsListviewAdapter newsListviewAdapter;
    private int mCurrentPage = 0;//起始请求页
    private boolean isEnd = false;
    Gson gson = new Gson();
    private TextView tv_error;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_dyn);
        initView();
        initData();

    }

    private void initData() {
        findNews(mCurrentPage);
    }

    private void initView() {

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pullToRefresh = findViewById(R.id.pullToRefresh1);

        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        pullToRefresh.getRefreshableView().setEmptyView(listEmptyView);

        init();

        if (newsListviewAdapter == null) {
            newsListviewAdapter = new ClubNewsListviewAdapter(data, this);
        }

        pullToRefresh.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);

        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        new UpRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }
        });
        // pullToRefresh.setVisibility(View.GONE);
        pullToRefresh.setAdapter(newsListviewAdapter);

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

    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //  findSelectionDyn_Down(1);
            init();
            mCurrentPage = 0;
            isEnd = false;
            findNews(mCurrentPage);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            dialog.dismiss();
            newsListviewAdapter.notifyDataSetChanged();
            pullToRefresh.onRefreshComplete();
        }
    }

    class StrBean {
        public String page;
    }

    private void findNews(final int currentPage) {
        StrBean strBean = new StrBean();
        strBean.page = currentPage + "";

        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_CLUBDYNAMIC_URL), gson.toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());

                RequsetClubDynBean newsDataBean = gson.fromJson(jsonObject.toString(), RequsetClubDynBean.class);
                if (newsDataBean.getSuccess()) {
                    data = newsDataBean.getData().getContent();


                    //    LogUtil.i(data.toString());
                    if (currentPage == 0) {
                        newsListviewAdapter.setData(data);

                        newsListviewAdapter.notifyDataSetChanged();
                        //   pullToRefresh.setVisibility(View.VISIBLE);

                    } else {
                        LogUtil.i("addLastList");
                        newsListviewAdapter.addLastList(data);
                        newsListviewAdapter.notifyDataSetChanged();
                    }
                    LogUtil.i(newsDataBean.getData().getLast() + "");
                    if (newsDataBean.getData().getLast()) {
                        setEnd();
                    } else {
                        mCurrentPage = currentPage + 1;
                    }

//                    if (data.size() > 0 && data.size() < 10) {
//                        setEnd();
//                    } else {
//                        mCurrentPage = currentPage + 1;
//                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    private void setEnd() {
        LogUtil.i("没数据了");
        isEnd = true;//没数据了
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
        // pullToRefresh.setMode(PullToRefreshBase.Mode.DISABLED);
    }


    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                findNews(mCurrentPage);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //   dialog.dismiss();
            // newsListviewAdapter.notifyDataSetChanged();
            if (isEnd) {//没数据了
                pullToRefresh.onRefreshComplete();
            }
            pullToRefresh.onRefreshComplete();
        }
    }
}

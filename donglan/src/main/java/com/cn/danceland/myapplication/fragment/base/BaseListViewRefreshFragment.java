package com.cn.danceland.myapplication.fragment.base;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shy on 2018/12/4 10:18
 * Email:644563767@qq.com
 * 下拉刷新基类
 */


public abstract class BaseListViewRefreshFragment extends BaseFragment {

    private TextView tv_tiltle;
    private PullToRefreshListView pullToRefresh;
    private boolean isEnd;
    private int mCurrentPage=0;
    ListAdapter listAdapter;
    @Override
    public View initViews() {
        View view =View.inflate(mActivity, R.layout.fragment_base_refresh,null);
        tv_tiltle =view. findViewById(R.id.donglan_title);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);
        //   View listEmptyView = View.inflate(this, R.layout.no_info_layout, (ViewGroup) pullToRefresh.getRefreshableView().getParent());
        View listEmptyView = view.findViewById(R.id.rl_error);
        listEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        TextView tv_error = listEmptyView.findViewById(R.id.tv_error);
        ImageView imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        if (setAtapter()==null){
            LogUtil.i("adapter is null");
        }
        listAdapter=setAtapter();
        pullToRefresh.setAdapter(listAdapter);
        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        init();
        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        new UpRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }
        });
        return view;
    }

    public ListAdapter getlistadapter(){
        return this.listAdapter;
    }

    public ListAdapter getListAdapter() {
        return listAdapter;
    }

    public PullToRefreshListView getPullToRefresh() {
        return pullToRefresh;
    }

    public void setPullToRefresh(PullToRefreshListView pullToRefresh) {
        this.pullToRefresh = pullToRefresh;
    }

    public void setListAdapter(ListAdapter listAdapter) {
        this.listAdapter = listAdapter;
        pullToRefresh.setAdapter(listAdapter);
    }

    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            init();
            mCurrentPage = 0;

            initData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pullToRefresh.onRefreshComplete();
        }
    }


    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                initData();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //   myListAatapter.notifyDataSetChanged();
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



    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public void setTitle(String title) {
        tv_tiltle.setText(title);
    }

    public void setpullToRefreshEnd() {
        isEnd = true;//没数据了
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
        pullToRefresh.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    public abstract ListAdapter setAtapter();

    public abstract void initRereshData();

    @Override
    public void initData() {
        initRereshData();
    }

    @Override
    public void onClick(View v) {

    }
}

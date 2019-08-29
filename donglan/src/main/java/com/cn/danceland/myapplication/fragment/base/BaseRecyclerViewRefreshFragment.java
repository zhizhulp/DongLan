package com.cn.danceland.myapplication.fragment.base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.view.ClassicsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shy on 2018/12/4 10:18
 * Email:644563767@qq.com
 * 下拉刷新基类
 */


public abstract class BaseRecyclerViewRefreshFragment extends BaseFragment {


    private SmartRefreshLayout refreshLayout;
    private RecyclerView mRecycler;
    private boolean isEnd;

    public CommonAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_base_reclcye_refresh, null);
        mRecycler = view.findViewById(R.id.recyclerView);

        refreshLayout = view.findViewById(R.id.refreshLayout);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);//刷新最重要的这句


        listAdapter = setAtapter();
        mRecycler.setAdapter(listAdapter);


        refreshLayout.setPrimaryColorsId(R.color.home_top_bg_color, R.color.white);//下拉刷新主题颜色 前面背景色 后面图色
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(mActivity).setEnableHorizontalDrag(true));//设置 Header 为 贝塞尔雷达 样式

        refreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableHeaderTranslationContent(true);//拖动Header的时候是否同时拖动内容（默认true）
        refreshLayout.setEnableFooterTranslationContent(true);//拖动Footer的时候是否同时拖动内容（默认true）
        refreshLayout.setEnableOverScrollDrag(false);//禁止越界拖动（1.0.4以上版本）
        ClassicsFooter classicsFooter = new ClassicsFooter(mActivity);
        classicsFooter.setProgressDrawable(getResources().getDrawable(R.drawable.listview_loading_anim));
        refreshLayout.setRefreshFooter(classicsFooter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                refreshlayout.setNoMoreData(false);//恢复加载更多的状态
                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
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

    public CommonAdapter getListAdapter() {
        return listAdapter;
    }

    public void setListAdapter(CommonAdapter listAdapter) {
        this.listAdapter = listAdapter;
        mRecycler.setAdapter(listAdapter);
    }


    public void setOnlyDownReresh() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            init();


            initData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            listAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh(0, false);//传入false表示刷新失败
        }
    }


    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                upDownRefreshData();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //   myListAatapter.notifyDataSetChanged();
            //    pullToRefresh.onRefreshComplete();

            listAdapter.notifyDataSetChanged();
            refreshLayout.finishLoadMore(0);//传入false表示加载失败
        }
    }


    private void init() {


    }


    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }


    //    public void setpullToRefreshEnd() {
//        isEnd = true;//没数据了
//        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
//                false, true);
//        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
//        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
//        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
//        endLabels.setLoadingDrawable(null);
//        pullToRefresh.setMode(PullToRefreshBase.Mode.DISABLED);
//    }
    @Subscribe
    public void onEventMainThread(IntEvent event) {

    }
    @Subscribe
    public void onEventMainThread(StringEvent event) {

    }
    public abstract CommonAdapter setAtapter();

    public abstract void initDownRefreshData();

    public abstract void upDownRefreshData();

    @Override
    public void initData() {
        initDownRefreshData();
    }

    @Override
    public void onClick(View v) {

    }

}

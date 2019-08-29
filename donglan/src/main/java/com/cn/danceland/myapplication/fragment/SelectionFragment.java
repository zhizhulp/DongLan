package com.cn.danceland.myapplication.fragment;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.AbsListView;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.cn.danceland.myapplication.MyApplication;
        import com.cn.danceland.myapplication.R;
        import com.cn.danceland.myapplication.activity.FitnessManActivity;
        import com.cn.danceland.myapplication.adapter.DynHeadviewRecylerViewAdapter;
        import com.cn.danceland.myapplication.adapter.MyDynListviewAdater;
        import com.cn.danceland.myapplication.bean.Data;
        import com.cn.danceland.myapplication.bean.RequestPushUserBean;
        import com.cn.danceland.myapplication.bean.RequsetDynInfoBean;
        import com.cn.danceland.myapplication.evntbus.EventConstants;
        import com.cn.danceland.myapplication.evntbus.IntEvent;
        import com.cn.danceland.myapplication.evntbus.StringEvent;
        import com.cn.danceland.myapplication.fragment.base.BaseFragment;
        import com.cn.danceland.myapplication.utils.Constants;
        import com.cn.danceland.myapplication.utils.LogUtil;
        import com.cn.danceland.myapplication.utils.MyStringRequest;
        import com.cn.danceland.myapplication.utils.SPUtils;
        import com.cn.danceland.myapplication.utils.ToastUtils;
        import com.google.gson.Gson;
        import com.handmark.pulltorefresh.library.ILoadingLayout;
        import com.handmark.pulltorefresh.library.PullToRefreshBase;
        import com.handmark.pulltorefresh.library.PullToRefreshListView;

        import org.greenrobot.eventbus.EventBus;
        import org.greenrobot.eventbus.Subscribe;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Timer;
        import java.util.TimerTask;

        import cn.jzvd.JZVideoPlayer;

/**
 * Created by shy on 2017/10/20 13:56
 * Email:644563767@qq.com
 * 精选页面
 */


public class SelectionFragment extends BaseFragment {
    private PullToRefreshListView pullToRefresh;
    private List<RequsetDynInfoBean.Data.Content> data = new ArrayList<>();
    private RequsetDynInfoBean requsetDynInfoBean = new RequsetDynInfoBean();
    private List<Data> pushUserDatas = new ArrayList<>();
    MyDynListviewAdater myDynListviewAdater;
    private RecyclerView mRecyclerView;
    ProgressDialog dialog;
    int mCurrentPage = 1;//起始请求页
    private DynHeadviewRecylerViewAdapter mRecylerViewAdapter;
    private View headView;
    private boolean isEnd = false;//是否没有数据了 默认值false
    private TextView tv_error;
    private ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册event事件
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //even事件处理
    @Subscribe
    public void onEventMainThread(IntEvent event) {
        LogUtil.i("收到消息" + event.getEventCode());


        switch (event.getEventCode()) {
            case EventConstants.ADD_DYN:  //发布动态
                break;
            case EventConstants.DEL_DYN://删除动态
                int pos = event.getMsg();
                myDynListviewAdater.data.remove(pos);
                myDynListviewAdater.notifyDataSetChanged();
                //    pullToRefresh.setRefreshing(true);
                break;
            case EventConstants.ADD_GUANZHU:
                //设置关注数

                break;
            case EventConstants.DEL_GUANZHU:
                //设置关注数-1

                break;

            case  8901://当前页
                // LogUtil.i("当前页"+event.getMsg());
                if (event.getMsg()==0){
                    pullToRefresh.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            JZVideoPlayer.onScrollAutoTiny(view, firstVisibleItem, visibleItemCount, 1);
                        }
                    });
                }else {
                    pullToRefresh.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView absListView, int i) {

                        }

                        @Override
                        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                        }
                    });
                }
                break;

            case 8902:
                pullToRefresh.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {

                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                    }
                });

                break;

            default:
                break;
        }

    }

    @Subscribe
    public void onEventMainThread(StringEvent event) {
        LogUtil.i("收到消息" + event.getMsg());
        int dynpos = -1;
        switch (event.getEventCode()) {


            case EventConstants.ADD_ZAN_DYN_HOME://点赞
                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {
                        dynpos = i;
                    }
                }

                if (dynpos >= 0) {
                    myDynListviewAdater.data.get(dynpos).setPraise(true);
                    myDynListviewAdater.data.get(dynpos).setPriaseNumber(myDynListviewAdater.data.get(dynpos).getPriaseNumber() + 1);
                    myDynListviewAdater.notifyDataSetChanged();
                }

                break;
            case EventConstants.DEL_ZAN_DYN_HOME://取消点赞
                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {
                        dynpos = i;
                    }
                }
                if (dynpos >= 0) {
                    myDynListviewAdater.data.get(dynpos).setPraise(false);
                    myDynListviewAdater.data.get(dynpos).setPriaseNumber(myDynListviewAdater.data.get(dynpos).getPriaseNumber() - 1);
                    myDynListviewAdater.notifyDataSetChanged();
                }
                break;
            case EventConstants.ADD_ZAN_USER_HOME://点赞
                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {
                        dynpos = i;
                    }
                }

                if (dynpos >= 0) {
                    myDynListviewAdater.data.get(dynpos).setPraise(true);
                    myDynListviewAdater.data.get(dynpos).setPriaseNumber(myDynListviewAdater.data.get(dynpos).getPriaseNumber() + 1);
                    myDynListviewAdater.notifyDataSetChanged();
                }

                break;
            case EventConstants.DEL_ZAN_USER_HOME://取消点赞
                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {
                        dynpos = i;
                    }
                }
                if (dynpos >= 0) {
                    myDynListviewAdater.data.get(dynpos).setPraise(false);
                    myDynListviewAdater.data.get(dynpos).setPriaseNumber(myDynListviewAdater.data.get(dynpos).getPriaseNumber() - 1);
                    myDynListviewAdater.notifyDataSetChanged();
                }
                break;
            case EventConstants.DEL_DYN_DYN_HOME://在动态主页删除动态

                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {

                        dynpos = i;
                    }

                }
                if (dynpos >= 0) {

                    myDynListviewAdater.data.remove(dynpos);
                    myDynListviewAdater.notifyDataSetChanged();

                }
                break;
            case EventConstants.ADD_GUANZHU:

                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getAuthor(), event.getMsg())) {

                        myDynListviewAdater.data.get(i).setFollower(true);

                    }
                }

                myDynListviewAdater.notifyDataSetChanged();


                break;

            case EventConstants.DEL_GUANZHU:

                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getAuthor(), event.getMsg())) {
                        myDynListviewAdater.data.get(i).setFollower(false);
                    }
                }
                myDynListviewAdater.notifyDataSetChanged();

                break;

            case EventConstants.ADD_COMMENT:

                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {

                        dynpos = i;
                    }

                }
                if (dynpos >= 0) {

                    myDynListviewAdater.data.get(dynpos).setReplyNumber(myDynListviewAdater.data.get(dynpos).getReplyNumber() + 1);
                    myDynListviewAdater.notifyDataSetChanged();

                }


                break;
            case EventConstants.DEL_COMMENT:
                for (int i = 0; i < myDynListviewAdater.data.size(); i++) {
                    if (TextUtils.equals(myDynListviewAdater.data.get(i).getId(), event.getMsg())) {

                        dynpos = i;
                    }

                }
                if (dynpos >= 0) {

                    myDynListviewAdater.data.get(dynpos).setReplyNumber(myDynListviewAdater.data.get(dynpos).getReplyNumber() - 1);
                    myDynListviewAdater.notifyDataSetChanged();

                }
                break;
            default:
                break;
        }

    }

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_selection, null);
        pullToRefresh = v.findViewById(R.id.pullToRefresh);

        View    listEmptyView= v.findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        tv_error.setText("精选动态无内容");
        pullToRefresh.getRefreshableView().setEmptyView(listEmptyView);
        headView = initHeadview();
        dialog = new ProgressDialog(mActivity);
        dialog.setMessage("加载中……");
//        dialog.show();
//

        //    data = getData();


        myDynListviewAdater = new MyDynListviewAdater(mActivity, (ArrayList<RequsetDynInfoBean.Data.Content>) data);
        pullToRefresh.setAdapter(myDynListviewAdater);
        //加入头布局
        /// pullToRefresh.getRefreshableView().addHeaderView(initHeadview());
        pullToRefresh.getRefreshableView().setHeaderDividersEnabled(false);
        pullToRefresh.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);


        init();

        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                JZVideoPlayer.releaseAllVideos();
                TimerTask task = new TimerTask(){
                    public void run(){
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask(){
                    public void run(){
                        new UpRefresh().execute();
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

                JZVideoPlayer.onScrollAutoTiny(view, firstVisibleItem, visibleItemCount, 1);
            }
        });

        return v;
    }


    private View initHeadview() {

        View headview = View.inflate(mActivity, R.layout.recycleview_headview, null);


        headview.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FitnessManActivity.class);
                startActivity(intent);
            }
        });

        //创建默认的线性LayoutManager
        mRecyclerView = headview.findViewById(R.id.my_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mRecylerViewAdapter = new DynHeadviewRecylerViewAdapter(mActivity, pushUserDatas);
        mRecyclerView.setAdapter(mRecylerViewAdapter);
        return headview;
    }

    /**
     * 加载数据
     */
    @Override
    public void initData() {
        dialog.show();
        findSelectionDyn_Down(mCurrentPage-1);
        // findPushUser();
    }


    @Override
    public void onClick(View view) {

    }


    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            isEnd=false;
            mCurrentPage=1;
            findSelectionDyn_Down(0);
            init();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            myDynListviewAdater.notifyDataSetChanged();
            // pullToRefresh.onRefreshComplete();
        }
    }


    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                findSelectionDyn_Up(mCurrentPage-1);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            myDynListviewAdater.notifyDataSetChanged();
            if (isEnd) {//没数据了
                pullToRefresh.onRefreshComplete();
            }

            // pullToRefresh.onRefreshComplete();
        }
    }


    /**
     * 一秒钟延迟
     */
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
//                Thread.sleep(1000);

                //       dialog.dismiss();

            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            myDynListviewAdater.notifyDataSetChanged();
            // pullToRefresh.onRefreshComplete();
        }
    }

    /**
     * 查找推荐用户
     */
    private void findPushUser() {


        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_PUSH_MSG), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Gson gson = new Gson();
                RequestPushUserBean pushUserBean = new RequestPushUserBean();
                pushUserBean = gson.fromJson(s, RequestPushUserBean.class);

                LogUtil.i(pushUserBean.toString());
                pushUserDatas = pushUserBean.getData();
                if (pushUserDatas.size() > 0) {
                    mRecylerViewAdapter.setData(pushUserDatas);
                    mRecylerViewAdapter.notifyDataSetChanged();
                    pullToRefresh.getRefreshableView().addHeaderView(headView);
                } else {
                    ToastUtils.showToastShort("没有数据");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                LogUtil.i(volleyError.toString());

            }
        }) {

        };
        MyApplication.getHttpQueues().add(request);

    }


    /***
     * 下拉查找精选动态
     * @param page
     */
    private void findSelectionDyn_Down(final int page) {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_JINGXUAN_DT_MSG), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                dialog.dismiss();
                pullToRefresh.onRefreshComplete();
                Gson gson = new Gson();
                requsetDynInfoBean = gson.fromJson(s, RequsetDynInfoBean.class);
                LogUtil.i(requsetDynInfoBean.toString());
                if (requsetDynInfoBean.getSuccess()) {

                    if (requsetDynInfoBean.getData().getItems() != null) {


                        data = requsetDynInfoBean.getData().getItems();
                        myDynListviewAdater.setData((ArrayList<RequsetDynInfoBean.Data.Content>) data);
                        myDynListviewAdater.notifyDataSetChanged();
                    }

                    mCurrentPage = 2;//下次从第二页请求
                } else {
                    ToastUtils.showToastShort(requsetDynInfoBean.getErrorMsg());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("查看网络连接");
                dialog.dismiss();
                pullToRefresh.onRefreshComplete();
                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", "");//用户id
                map.put("page", page + "");//页数
                return map;
            }



        };
        MyApplication.getHttpQueues().add(request);

    }

    /***
     * 上拉查找精选动态
     */
    private void findSelectionDyn_Up(final int page) {

        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_JINGXUAN_DT_MSG), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                pullToRefresh.onRefreshComplete();

                Gson gson = new Gson();
                //  RequsetDynInfoBean requsetDynInfoBean=new RequsetDynInfoBean();
                requsetDynInfoBean = gson.fromJson(s, RequsetDynInfoBean.class);

                if (requsetDynInfoBean.getSuccess()) {
                    data = requsetDynInfoBean.getData().getItems();
                    // requsetDynInfoBean.getData().getItems().toString();
                    myDynListviewAdater.addLastList((ArrayList<RequsetDynInfoBean.Data.Content>) data);
                    myDynListviewAdater.notifyDataSetChanged();
                    LogUtil.i(data.size()+"");
                    if (data.size() >= 0&&data.size()<10) {
                        isEnd = true;
                        ToastUtils.showToastShort("到底啦");
                        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                                false, true);
                        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
                        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
                        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
                        endLabels.setLoadingDrawable(null);

                    } else {

                        //     LogUtil.i(data.toString());

                        mCurrentPage = mCurrentPage + 1;
                    }
                } else {
                    ToastUtils.showToastShort("请求失败：" + requsetDynInfoBean.getErrorMsg());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("查看网络连接");
                pullToRefresh.onRefreshComplete();
                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", SPUtils.getString(Constants.MY_USERID, ""));//用户id
                map.put("page", page + "");//页数
                return map;
            }



        };
        MyApplication.getHttpQueues().add(request);


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


}

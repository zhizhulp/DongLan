package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.cn.danceland.myapplication.adapter.UserListviewAdapter;
import com.cn.danceland.myapplication.bean.RequsetUserListBean;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shy on 2017/11/21 15:08
 * Email:644563767@qq.com
 */


public class UserListActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullToRefresh;
    List<RequsetUserListBean.Data.Content> data = new ArrayList<RequsetUserListBean.Data.Content>();
    UserListviewAdapter mListviewAdapter;
    ProgressDialog dialog;
    private int mCurrentPage = 0;//当前请求页
    private String userId;
    private String msgId;
    private boolean isdyn = false;
    private TextView tv_tiltle;
    int type = 1;//1是关注，2是粉丝，3是点赞。默认是1
    private ImageView imageView;
    private TextView tv_error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册event事件
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_user_list);

        userId = getIntent().getStringExtra("id");
        msgId = getIntent().getStringExtra("msgId");
        isdyn = getIntent().getBooleanExtra("isdyn", false);
        type = getIntent().getIntExtra("type", 1);


        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        LogUtil.i("userId"+userId);
        LogUtil.i("msgId"+msgId);
        LogUtil.i("SPUtils.getString(Constants.MY_USERID, null))"+SPUtils.getString(Constants.MY_USERID, null));
        String isMyStr="";
        if (TextUtils.equals(userId, SPUtils.getString(Constants.MY_USERID, null))) {
            isMyStr="我的";
        }else{
            isMyStr="";
        }
        switch (type) {
            case 1:
                tv_tiltle.setText(isMyStr+"关注");
                findGZuserList(userId, 0);
                break;
            case 2:
                tv_tiltle.setText(isMyStr+"粉丝");
                findFansUserList(userId, 0);
                break;
            case 3:
                tv_tiltle.setText(isMyStr+"点赞");
                findZanUserList(msgId, 0);
                break;
            default:
                break;
        }


    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {

        if (TextUtils.equals(userId, SPUtils.getString(Constants.MY_USERID, ""))) {
            LogUtil.i(":收到" + event.getEventCode());
            LogUtil.i("TYPE" + type);
            switch (event.getEventCode()) {
                case EventConstants.ADD_DYN:  //设置动态数+1

                    break;
                case EventConstants.DEL_DYN:

                    break;
                case EventConstants.ADD_GUANZHU:
                    if (type == 1) {
                        mCurrentPage = 0;
                        initData();
                    }
                    break;
                case EventConstants.DEL_GUANZHU:
                    if (type == 1) {
                        mCurrentPage = 0;
                        initData();
                    }
                    break;
                default:
                    break;
            }
        }


    }

    private void initView() {
        tv_tiltle = findViewById(R.id.donglan_title);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        //   View listEmptyView = View.inflate(this, R.layout.no_info_layout, (ViewGroup) pullToRefresh.getRefreshableView().getParent());
        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        switch (type) {
            case 1:
                tv_error.setText("你还没有关注的人");
                break;
            case 2:
                tv_error.setText("您还没有粉丝");
                break;
            case 3:
                tv_error.setText("没有数据");
                break;
            default:
                break;
        }

        pullToRefresh.getRefreshableView().setEmptyView(listEmptyView);
        dialog = new ProgressDialog(this);

        dialog.setMessage("正在加载……");

        mListviewAdapter = new UserListviewAdapter(this, data, type);
        pullToRefresh.setAdapter(mListviewAdapter);
        //加入头布局
        //  pullToRefresh.getRefreshableView().addHeaderView(initHeadview());

        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        init();


        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask(){
                    public void run(){
                        new FinishRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                switch (type) {
                    case 1:
                        findGZuserList(userId, mCurrentPage);
                        break;
                    case 2:
                        findFansUserList(userId, mCurrentPage);
                        break;
                    case 3:
                        findZanUserList(userId, mCurrentPage);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mListviewAdapter.notifyDataSetChanged();
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

    private boolean isEnd;

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

    private RequsetUserListBean UserListBean = new RequsetUserListBean();

    class StrBean {
        public Integer page;
        public Integer size;
        public String reply_msg_id;
        public String msg_id;
        public String user_id;
        public String follower;
    }


    /**
     * 查找关注人数
     *
     * @param followerId//userid
     * @param page//页数
     */
    private void findGZuserList(final String followerId, final int page) {
        dialog.show();

        StrBean strBean = new StrBean();
        strBean.page = page;
        strBean.follower = followerId;
        strBean.size = 20;
        LogUtil.i(new Gson().toJson(strBean));
        MyJsonObjectRequest jsonRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_GUANZHU_USER_LIST_MSG), new Gson().toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                dialog.dismiss();
                Gson gson = new Gson();

                UserListBean = gson.fromJson(jsonObject.toString(), RequsetUserListBean.class);
                //   LogUtil.i(UserListBean.toString());
                if (UserListBean.getSuccess()) {
                    data = UserListBean.getData().getItems();
                    // requsetDynInfoBean.getData().getItems().toString();

                    if (data.size() > 0) {
                        if (data.size() < 20) {
                            setEnd();
                        }

                        if (mCurrentPage == 0) {
                            mListviewAdapter.setData((ArrayList<RequsetUserListBean.Data.Content>) data, type);
                        } else {
                            mListviewAdapter.addLastList((ArrayList<RequsetUserListBean.Data.Content>) data);
                        }

                        mListviewAdapter.notifyDataSetChanged();

                        mCurrentPage = mCurrentPage + 1;
                    } else {
                        //  ToastUtils.showToastShort("到底啦");
                        setEnd();
                    }
                } else {
                    ToastUtils.showToastShort("请求失败：" + UserListBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                LogUtil.i(volleyError.toString());

                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        }) ;
        MyApplication.getHttpQueues().add(jsonRequest);
    }


    /**
     * 查找粉丝人数
     *
     * @param userId//userid
     * @param page//页数
     */
    private void findFansUserList(final String userId, final int page) {
        dialog.show();
        StrBean strBean = new StrBean();
        strBean.page = page;
        strBean.user_id = userId;
        strBean.size = 20;
        LogUtil.i(new Gson().toJson(strBean));
        MyJsonObjectRequest jsonRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_FANS_USER_LIST_MSG), new Gson().toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                dialog.dismiss();
                Gson gson = new Gson();
                LogUtil.i(jsonObject.toString());
                UserListBean = gson.fromJson(jsonObject.toString(), RequsetUserListBean.class);
                if (UserListBean.getSuccess()) {
                    data = UserListBean.getData().getItems();
                    // requsetDynInfoBean.getData().getItems().toString();

                    SPUtils.setInt(Constants.MY_FANS, UserListBean.getData().getTotalElements());
                    EventBus.getDefault().post(new StringEvent("", EventConstants.UPDATE_FANS));
                    if (data.size() > 0) {
                        if (data.size() < 20) {
                            setEnd();
                        }

                        mListviewAdapter.addLastList((ArrayList<RequsetUserListBean.Data.Content>) data);
                        mListviewAdapter.notifyDataSetChanged();

                        mCurrentPage = mCurrentPage + 1;
                    } else {
                        ToastUtils.showToastShort("到底啦");
                        setEnd();
                    }
                } else {
                    ToastUtils.showToastShort("请求失败：" + UserListBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                LogUtil.i(volleyError.toString());
                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");

            }
        });
        MyApplication.getHttpQueues().add(jsonRequest);
    }


    /**
     * 查找点赞人数（全部）
     *
     * @param msgId//消息id
     * @param page//页数
     */
    private void findZanUserList(final String msgId, final int page) {
        dialog.show();


        StrBean strBean = new StrBean();
        strBean.page = page;
        strBean.msg_id = msgId;
        strBean.size = 20;

        MyJsonObjectRequest jsonRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_ZAN_USER_LIST_MSG), new Gson().toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                dialog.dismiss();
                Gson gson = new Gson();
                LogUtil.i(jsonObject.toString());
                UserListBean = gson.fromJson(jsonObject.toString(), RequsetUserListBean.class);
                //    LogUtil.i(jsonObject.toString());
                if (UserListBean.getSuccess()) {
                    data = UserListBean.getData().getItems();
                    // requsetDynInfoBean.getData().getItems().toString();

                    if (data.size() > 0) {
                        if (data.size() < 20) {
                            setEnd();
                        }

                        mListviewAdapter.addLastList((ArrayList<RequsetUserListBean.Data.Content>) data, type);
                        mListviewAdapter.notifyDataSetChanged();

                        mCurrentPage = mCurrentPage + 1;
                    } else {
                        ToastUtils.showToastShort("到底啦");
                        setEnd();
                    }
                } else {
                    ToastUtils.showToastShort("请求失败：" + UserListBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                LogUtil.i(volleyError.toString());

                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        });
        MyApplication.getHttpQueues().add(jsonRequest);
    }
}

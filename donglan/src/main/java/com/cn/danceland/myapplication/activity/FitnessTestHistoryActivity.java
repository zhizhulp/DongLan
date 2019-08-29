package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.FitnessTestHistoryBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 体测分析-历史记录
 * Created by yxx on 2018-09-27.
 */

public class FitnessTestHistoryActivity extends BaseActivity {
    private Context context;
    private DongLanTitleView title;
    private TextView name_tv;
    private PullToRefreshListView mListView;
    private RelativeLayout rl_error;
    private ImageView iv_error;
    private TextView tv_error;
//    private TextView rightTv;

    private NoticeAdapter adapter;

    private List<FitnessTestHistoryBean.Content> datalist = new ArrayList<>();
    private int mCurrentPage = 0;//起始请求页
    private boolean isEnd = false;

    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_listview_layout);
        context = this;
        requsetInfo = (RequsetFindUserBean.Data) getIntent().getSerializableExtra("requsetInfo");//前面搜索到的对象
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
//        rightTv = findViewById(R.id.donglan_right_tv);
//        rightTv.setVisibility(View.VISIBLE);
//        rightTv.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
        title.setTitle("历史记录");
//        rightTv.setText("体测分析");
        name_tv = findViewById(R.id.name_tv);
        mListView = findViewById(R.id.lv_message);
        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(context).load(R.drawable.img_error).into(iv_error);

        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("暂无数据");

        mListView.getRefreshableView().setEmptyView(rl_error);
        adapter = new NoticeAdapter();
        mListView.setAdapter(adapter);
        mListView.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

        init_pullToRefresh();
        try {
            find_all_data(mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        rightTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, FitnessTestNoticeActivity.class).putExtra("requsetInfo", requsetInfo));
//            }
//        });
    }

    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            init_pullToRefresh();
            mCurrentPage = 0;
            try {
                find_all_data(mCurrentPage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            mListView.onRefreshComplete();
        }
    }

    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                try {
                    find_all_data(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mListView.onRefreshComplete();
            if (isEnd) {//没数据了
                mListView.onRefreshComplete();
            }
        }
    }

    private void init_pullToRefresh() {
        // 设置下拉刷新文本
        ILoadingLayout startLabels = mListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        // 设置上拉刷新文本
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
    }

    /**
     * 查询数据
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_all_data(final int pageCount) throws JSONException {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_FITNESS_LIST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                FitnessTestHistoryBean datainfo = new FitnessTestHistoryBean();
                Gson gson = new Gson();
                datainfo = gson.fromJson(s.toString(), FitnessTestHistoryBean.class);

                if (datainfo.getSuccess()) {

                    if ((mCurrentPage + 1) >= datainfo.getData().getTotalPages()) {
                        isEnd = true;
                        setEnd();
                    } else {
                        isEnd = false;
                        init_pullToRefresh();
                    }

                    if (mCurrentPage == 0) {
                        datalist = datainfo.getData().getContent();
                        adapter.notifyDataSetChanged();
                    } else {
                        datalist.addAll(datainfo.getData().getContent());
                        adapter.notifyDataSetChanged();
                    }
                    mCurrentPage = mCurrentPage + 1;
                } else {
                    ToastUtils.showToastLong(datainfo.getErrorMsg());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort(volleyError.toString());
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("size", 10 + "");
                map.put("page", pageCount + "");
                map.put("member_id", requsetInfo.getId() + "");
                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    private void setEnd() {
        LogUtil.i("没数据了");
        isEnd = true;//没数据了
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
    }

    class StrBean {
        public String page;
    }

    class NoticeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new NoticeAdapter.ViewHolder();
                convertView = View.inflate(context, R.layout.item_public_arrow_layout, null);
                vh.content_one_tv = convertView.findViewById(R.id.content_one_tv);
                vh.content_two_tv = convertView.findViewById(R.id.content_two_tv);
                vh.content_ll = convertView.findViewById(R.id.content_ll);
                convertView.setTag(vh);
            } else {
                vh = (NoticeAdapter.ViewHolder) convertView.getTag();
            }
            vh.content_one_tv.setText(TimeUtils.timeStamp2Date(datalist.get(position).getTest_time(), "yyyy-MM-dd HH:mm"));
            vh.content_two_tv.setText("工作人员：" + datalist.get(position).getTeach_name());
            vh.content_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, FitnessResultsSummaryActivity.class)
                            .putExtra("requsetInfo", requsetInfo)
                            .putExtra("saveId", datalist.get(position).getId() + ""));
                }
            });
            return convertView;
        }

        class ViewHolder {
            public TextView content_one_tv;
            public TextView content_two_tv;
            public LinearLayout content_ll;
        }
    }
}

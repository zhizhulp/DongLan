package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.feedback.FeedBack;
import com.cn.danceland.myapplication.bean.feedback.FeedBackCond;
import com.cn.danceland.myapplication.bean.feedback.FeedBackRequest;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by feng on 2018/3/26.
 */

public class AdviseRecordActivity extends BaseActivity {

    PullToRefreshListView pullToRefresh;
    private FeedBackRequest request;
    private Gson gson;
    Data data;
    List<FeedBack> list;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;
    ProgressDialog dialog;
    RecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advise_record);

        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        request = new FeedBackRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        initView();
        queryList();

    }

    private void initView() {

        pullToRefresh = findViewById(R.id.pullToRefresh);
        View listEmptyView = findViewById(R.id.rl_no_info);
        pullToRefresh.getRefreshableView().setEmptyView(listEmptyView);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载……");
//        pullToRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
//        rl_error = findViewById(R.id.rl_error);
//        iv_error = rl_error.findViewById(R.id.iv_error);
//        Glide.with(this).load(R.drawable.img_error).into(iv_error);
//        tv_error = rl_error.findViewById(R.id.tv_error);
//        tv_error.setText("您还没有反馈任何信息");
//        pullToRefresh.getRefreshableView().setEmptyView(tv_error);

        list = new ArrayList<>();
        adapter = new RecordAdapter(list);
        pullToRefresh.setAdapter(adapter);
//        pullToRefresh.setEmptyView(rl_error);
        //加入头布局
        //      pullToRefresh.getRefreshableView().addHeaderView(initHeadview(userInfo));
        pullToRefresh.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
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
    }

    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            init();
            list = new ArrayList<>();
            queryList();
            dialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            adapter.notifyDataSetChanged();
            pullToRefresh.onRefreshComplete();
        }
    }

    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
//            try {
//                if (!isEnd) {//还有数据请求
//                    findSelfDT();
//                }
//                dialog.dismiss();
//
//            } catch (Exception e) {
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
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

    /**
     * @方法说明:按条件查询意见反馈列表
     **/
    public void queryList() {
        FeedBackCond cond = new FeedBackCond();
        cond.setPage(0);
        cond.setSize(20);
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                LogUtil.i(json.toString());
                DLResult<List<FeedBack>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<FeedBack>>>() {
                }.getType());
                if (result.isSuccess()) {
                    list = result.getData();
                    if (list != null) {
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        }, rl_error, iv_error, tv_error);
    }


    private class RecordAdapter extends BaseAdapter {

        List<FeedBack> list;

        RecordAdapter(List<FeedBack> list) {

            this.list = list;

        }

        public void setList(List<FeedBack> list) {
            this.list = list;
        }

        public void addLastList(List<FeedBack> lists) {
            list.addAll(lists);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(AdviseRecordActivity.this, R.layout.advise_record_item, null);
                viewHolder = new ViewHolder();
                viewHolder.type_iv = convertView.findViewById(R.id.type_iv);
                viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
                viewHolder.fankui_time = convertView.findViewById(R.id.fankui_time);
                viewHolder.huifu_time = convertView.findViewById(R.id.huifu_time);
                viewHolder.tv_status = convertView.findViewById(R.id.tv_status);
                viewHolder.item_layout_cv = convertView.findViewById(R.id.item_layout_cv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(AdviseRecordActivity.this, 60f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 11f));
            } else if (position == list.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 5f), DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 5f), DensityUtils.dp2px(AdviseRecordActivity.this, 16f), DensityUtils.dp2px(AdviseRecordActivity.this, 11f));
            }
            viewHolder.item_layout_cv.setLayoutParams(layoutParams);
            if (list.get(position).getType() == 1) {
                viewHolder.tv_type.setText("批评");
                viewHolder.type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_criticism_icon));
            } else if (list.get(position).getType() == 2) {
                viewHolder.tv_type.setText("表扬");
                viewHolder.type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_praise_icon));
            } else if (list.get(position).getType() == 3) {
                viewHolder.tv_type.setText("建议");
                viewHolder.type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_advice_icon));
            } else if (list.get(position).getType() == 4) {
                viewHolder.tv_type.setText("投诉");
                viewHolder.type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_complaint_icon));
            } else {
                viewHolder.tv_type.setText("未知类型");
                viewHolder.type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_advice_icon));
            }

            if (list.get(position).getStatus() == 1) {
                viewHolder.tv_status.setText("已回复");
                viewHolder.tv_status.setTextColor(getResources().getColor(R.color.colorGray21));
                viewHolder.tv_status.setBackground(getResources().getDrawable(R.drawable.advise_status_white_bg));
            } else if (list.get(position).getStatus() == 2) {
                viewHolder.tv_status.setText("未回复");
                viewHolder.tv_status.setTextColor(getResources().getColor(R.color.white));
                viewHolder.tv_status.setBackground(getResources().getDrawable(R.drawable.advise_status_red_bg));
            } else {
                viewHolder.tv_status.setText("状态未知");
                viewHolder.tv_status.setTextColor(getResources().getColor(R.color.white));
                viewHolder.tv_status.setBackground(getResources().getDrawable(R.drawable.advise_status_red_bg));
            }

            if (list.get(position).getOpinion_date() != null) {
                viewHolder.fankui_time.setText("反馈时间：" + TimeUtils.dateToString(list.get(position).getOpinion_date()));
            }
            if (list.get(position).getReply_date() != null) {
                viewHolder.huifu_time.setText("回复时间：" + TimeUtils.dateToString(list.get(position).getReply_date()));
            }
            viewHolder.item_layout_cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(AdviseRecordActivity.this, AdviseDetailActivity.class).putExtra("id", list.get(position).getId()));
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView type_iv;
        TextView tv_type, fankui_time, huifu_time, tv_status;
        CardView item_layout_cv;
    }
}

package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestScanerCodeHandsBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
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
 * ListViewAlertDialog  有×的
 * <p>
 * return 选中数据
 * <p>
 * Created by ${yxx} on 2018/8/31.
 */

public class AlertDialogToListActivity extends BaseActivity {
    private Context context;
    private TextView dialog_title_tv;
    private PullToRefreshListView mListView;
    private TextView ok_tv;
    private TextView cancel_tv;
    private ImageView close_img;//关闭
    private RelativeLayout rl_error;
    private ImageView iv_error;
    private TextView tv_error;

    private ListAdapter adapter;

    private String from = "扫码入场";//来自哪页 扫码入场
    private String genderStr;//性别

    private Gson gson = new Gson();
    private List<RequestScanerCodeHandsBean.ChildData> handsListData = new ArrayList<>();

    private int selectIdx = -1;
    private int mCurrentPage = 0;//起始请求页
    private boolean isEnd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog_to_list);
        context = this;
        from = getIntent().getStringExtra("from");
        genderStr = getIntent().getStringExtra("gender");
        initView();
        try {
            find_all_data(mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        dialog_title_tv = findViewById(R.id.dialog_title_tv);
        ok_tv = findViewById(R.id.ok_tv);
        cancel_tv = findViewById(R.id.cancel_tv);
        close_img = findViewById(R.id.close_img);
        mListView = findViewById(R.id.listview);
        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("暂无数据");

        if (!TextUtils.isEmpty(from) && from.equals("扫码入场")) {
            dialog_title_tv.setText("选择手牌");
            ok_tv.setVisibility(View.GONE);
            cancel_tv.setVisibility(View.GONE);
        } else {
            dialog_title_tv.setText("");
        }

        adapter = new ListAdapter();
        mListView.setAdapter(adapter);

        mListView.getRefreshableView().setEmptyView(rl_error);
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

        ok_tv.setOnClickListener(onClickListener);
        cancel_tv.setOnClickListener(onClickListener);
        close_img.setOnClickListener(onClickListener);
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

    /**
     * 查询数据
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_all_data(final int pageCount) throws JSONException {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_HAND_CARD), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                RequestScanerCodeHandsBean datainfo = new RequestScanerCodeHandsBean();
                Gson gson = new Gson();
                datainfo = gson.fromJson(s.toString(), RequestScanerCodeHandsBean.class);

                if (datainfo.getSuccess()) {

                    if ((mCurrentPage + 1) >= datainfo.getData().getTotalPages()) {
                        isEnd = true;
                        setEnd();
                    } else {
                        isEnd = false;
                        init_pullToRefresh();
                    }

                    if (mCurrentPage == 0) {
                        handsListData = datainfo.getData().getContent();
                        adapter.notifyDataSetChanged();
                    } else {
                        handsListData.addAll(datainfo.getData().getContent());
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
                map.put("gender", genderStr + "");
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
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

    private void setEnd() {
        LogUtil.i("没数据了");
        isEnd = true;//没数据了
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_tv:
                    if (selectIdx == -1) {//请选择
                        ToastUtils.showToastShort("请选择");
                        return;
                    }
                    Intent intent = new Intent(context, ScanerCodeDetailActivity.class);
                    intent.putExtra("selectId", handsListData.get(selectIdx).getId());//返回选中数据
                    intent.putExtra("selectCode", handsListData.get(selectIdx).getCode());//返回选中数据
                    intent.putExtra("selectArea", handsListData.get(selectIdx).getArea());//返回选中数据
                    setResult(ScanerCodeDetailActivity.SELECT_HAND_DATA, intent);
                    finish();
                case R.id.cancel_tv:
                    AlertDialogToListActivity.this.finish();
                    break;
                case R.id.close_img:
                    AlertDialogToListActivity.this.finish();
                    break;
            }
        }
    };

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return handsListData.size();
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
            ListAdapter.ViewHolder vh = null;
            if (convertView == null) {
                vh = new ListAdapter.ViewHolder();
                convertView = View.inflate(context, R.layout.list_dialog_notice_item, null);
                vh.title_tv = convertView.findViewById(R.id.title_tv);
                vh.item_layout = convertView.findViewById(R.id.item_layout);
                convertView.setTag(vh);
            } else {
                vh = (ListAdapter.ViewHolder) convertView.getTag();
            }
            vh.title_tv.setText(handsListData.get(position).getCode() + "-" + handsListData.get(position).getArea());
            vh.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectIdx = position;
                    Intent intent = new Intent(context, ScanerCodeDetailActivity.class);
                    intent.putExtra("selectId", handsListData.get(selectIdx).getId() + "");//返回选中数据
                    intent.putExtra("selectCode", handsListData.get(selectIdx).getCode());//返回选中数据
                    intent.putExtra("selectArea", handsListData.get(selectIdx).getArea());//返回选中数据
                    setResult(ScanerCodeDetailActivity.SELECT_HAND_DATA, intent);
                    finish();
                }
            });
            return convertView;
        }

        class ViewHolder {
            public TextView title_tv;
            public LinearLayout item_layout;
        }
    }


    /**
     * 手牌列表
     */
    class HandsStrBean {
        public int size;
        public int page;
        public String gender;
    }
}

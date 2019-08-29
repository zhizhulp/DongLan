package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.adapter.ConsultAdapter;
import com.cn.danceland.myapplication.bean.ConsultBean;
import com.cn.danceland.myapplication.bean.ConsultDataBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我想咨询
 * Created by ${yxx} on 2018/8/30.
 */

public class ConsultListActivity extends BaseActivity {
    private final int RESULT_DIALOG = 1;
    private Context context;
    private DongLanTitleView title;//title
    private PullToRefreshListView mListView;
    private ProgressDialog dialog;
    private TextView btn_want;
    private ConsultAdapter adapter;
    private TextView tv_error;
    private ImageView imageView;
    private LinearLayout btn_want_layout;

    private List<ConsultBean> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_or_recommend);
        context = this;
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title.setTitle(context.getResources().getString(R.string.consult_list_text));
        mListView = findViewById(R.id.pullToRefresh1);
        dialog = new ProgressDialog(context);
        btn_want = findViewById(R.id.btn_want);
        btn_want_layout = findViewById(R.id.btn_want_layout);

        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        tv_error.setText("没有数据");
        mListView.getRefreshableView().setEmptyView(listEmptyView);
        dialog.setMessage("加载中……");
        mListView.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);//DISABLED和普通listView一样用

        btn_want.setText(context.getResources().getString(R.string.consult_text));

        if (adapter == null) {
            adapter = new ConsultAdapter(context, datas);
        }
        mListView.setAdapter(adapter);
        btn_want_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddConsultOrRecommendActivity.class);
                intent.putExtra("from", "咨询列表");
                startActivityForResult(intent, RESULT_DIALOG);
            }
        });

        queryData();
    }

    private void queryData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_MY_CONSULT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("返回的数据:" + s);
                ConsultDataBean lastBean = new Gson().fromJson(s, ConsultDataBean.class);
                datas = lastBean.getData();
                if (datas != null && datas.size() != 0) {
                    adapter.setData(datas);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtil.i(volleyError.toString());
                } else {
                    LogUtil.i("NULL");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_DIALOG) {
            queryData();
        }
    }
}

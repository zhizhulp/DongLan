package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.BlackListBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tencent.qcloud.ui.CircleImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BlackListActivity extends BaseActivity {
    PullToRefreshListView listView;
    DongLanTitleView dongLanTitleView;
    List<BlackListBean.Data> dataList = new ArrayList<>();
    MyAdapter myAdapter;

    private TextView tv_error;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list1);
        listView = (PullToRefreshListView) findViewById(R.id.list);
        dongLanTitleView = findViewById(R.id.dl_title);
        dongLanTitleView.setTitle("黑名单");

        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        tv_error.setText("没有数据");
        listView.getRefreshableView().setEmptyView(listEmptyView);
        listView.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        listView.setMode(PullToRefreshBase.Mode.DISABLED);//DISABLED和普通listView一样用

        findBlack();
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);


    }

    private class Strbean {
        public String blocked_id;
    }

    private void findBlack() {

        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_BLACKLIST_URL), new Gson().toJson(new Strbean()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                BlackListBean blackListBean = new Gson().fromJson(jsonObject.toString(), BlackListBean.class);
                dataList = blackListBean.getData();
                myAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(request);

    }

    private void delBlack(final int pos) {

        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.DEL_BLACKLIST_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                RequestSimpleBean simpleBean = new Gson().fromJson(s, RequestSimpleBean.class);
                if (simpleBean.getSuccess()) {
                    dataList.remove(pos);
                    myAdapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", dataList.get(pos).getId() + "");
                return map;
            }
        };

        MyApplication.getHttpQueues().add(request);

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = View.inflate(BlackListActivity.this, R.layout.listview_item_blacklist, null);
            TextView name = view.findViewById(R.id.name);
            TextView tv_cancel = view.findViewById(R.id.tv_cancel);
            CircleImageView avatar = view.findViewById(R.id.avatar);
            CardView item_layout_cv = view.findViewById(R.id.item_layout_cv);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(BlackListActivity.this, 80f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 11f));
            } else if (position == dataList.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 5f), DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 5f), DensityUtils.dp2px(BlackListActivity.this, 16f), DensityUtils.dp2px(BlackListActivity.this, 11f));
            }
            item_layout_cv.setLayoutParams(layoutParams);

            Glide.with(BlackListActivity.this).load(dataList.get(position).getSelf_avatar_path()).into(avatar);
            name.setText(dataList.get(position).getNick_name());
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delBlack(position);
                }
            });
            return view;
        }
    }
}

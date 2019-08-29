package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.cn.danceland.myapplication.bean.FitnessHistoryBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/1/8.
 */

public class FitnessHistoryActivity extends BaseActivity {
    ListView lv_history;
    Gson gson;
    String member_no;
    List<FitnessHistoryBean.Content> content;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitnesshistory);
        initHost();
        initView();
        initData();
    }

    private void initHost() {
        gson = new Gson();

        member_no = getIntent().getStringExtra("member_no");

    }

    private void initData() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FITNESS_HITORY), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                FitnessHistoryBean fitnessHistoryBean = gson.fromJson(s, FitnessHistoryBean.class);
                if(fitnessHistoryBean!=null){
                    if(fitnessHistoryBean.getData()!=null){
                        content = fitnessHistoryBean.getData().getContent();
                        if(content!=null){
                            lv_history.setAdapter(new MyAdapter(content));
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                rl_error.setVisibility(View.VISIBLE);
                tv_error.setText("网络异常");
                Glide.with(FitnessHistoryActivity.this).load(R.drawable.img_error7).into(iv_error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> map = new HashMap<String,String>();
                map.put("memberNo",member_no);

                return map;
            }
        };

        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initView() {

        lv_history = findViewById(R.id.lv_history);
        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(content!=null){
                    String id1 = content.get(position).getId()+"";
                    startActivity(new Intent(FitnessHistoryActivity.this,FitnessTestActivity.class).putExtra("bcaId",id1));
                    finish();
                }
            }
        });
        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(this).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("暂无体测记录");
        lv_history.setEmptyView(rl_error);

    }

    private class MyAdapter extends BaseAdapter{

        List<FitnessHistoryBean.Content> list;
        MyAdapter(List<FitnessHistoryBean.Content> list){
            this.list = list;

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
        public View getView(int position, View convertView, ViewGroup parent) {

            View inflate = LayoutInflater.from(FitnessHistoryActivity.this).inflate(R.layout.history_item, null);
            TextView cishu = inflate.findViewById(R.id.cishu);
            TextView time = inflate.findViewById(R.id.time);
            CardView item_layout_cv = inflate.findViewById(R.id.item_layout_cv);
            cishu.setText("第"+(list.size()-position)+"次");
            time.setText(list.get(position).getDate());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(FitnessHistoryActivity.this, 80f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 11f));
            } else if (position == content.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 5f), DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 5f), DensityUtils.dp2px(FitnessHistoryActivity.this, 16f), DensityUtils.dp2px(FitnessHistoryActivity.this, 11f));
            }

            item_layout_cv.setLayoutParams(layoutParams);
            return inflate;
        }
    }
}

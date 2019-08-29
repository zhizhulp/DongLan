package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.CabinetBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by feng on 2018/1/31.
 */

public class CabinetActivity extends BaseActivity {

    ListView cabinet_lv;
    ImageView cabinet_back;
    CabinetBean cabinetBean;
    Gson gson;
    List<CabinetBean.Data> dataList;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabinet);
        initView();
    }

    private void initView() {

        gson = new Gson();

        cabinet_lv = findViewById(R.id.cabinet_lv);
        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(this).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("如需购买租柜，请到门店前台");

        cabinet_lv.setEmptyView(rl_error);
        getData();
    }


    private void getData() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDMyLOCKERS), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.e("zzf", s);
                cabinetBean = gson.fromJson(s, CabinetBean.class);
                dataList = cabinetBean.getData();
                if (dataList != null) {
                    cabinet_lv.setAdapter(new MyAdapter(dataList));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                rl_error.setVisibility(View.VISIBLE);
                tv_error.setText("网络异常");
                Glide.with(CabinetActivity.this).load(R.drawable.img_error7).into(iv_error);
            }
        }) {
          
        };

        MyApplication.getHttpQueues().add(stringRequest);
    }


    private class MyAdapter extends BaseAdapter {
        List<CabinetBean.Data> list;

        MyAdapter(List<CabinetBean.Data> list) {
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

            View inflate = LayoutInflater.from(CabinetActivity.this).inflate(R.layout.cabinet_item, null);
           ImageView img_guizi = inflate.findViewById(R.id.img_guizi);
            TextView cabinet_num = inflate.findViewById(R.id.cabinet_num);
            TextView starttime = inflate.findViewById(R.id.starttime);
            TextView overtime = inflate.findViewById(R.id.overtime);
            //TextView tv_locker_zone = inflate.findViewById(R.id.tv_locker_zone);

            cabinet_num.setText("柜号：" + list.get(position).getLocker_name());

            starttime.setText("开始日期：" + TimeUtils.millToDate(list.get(position).getStart_date()));

            overtime.setText("结束日期：" + TimeUtils.millToDate(list.get(position).getEnd_date()));
            //tv_locker_zone.setText(list.get(position).getLocker_zone_name());


            return inflate;
        }
    }
}

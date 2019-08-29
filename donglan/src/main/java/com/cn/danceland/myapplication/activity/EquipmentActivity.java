package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.EquipmentBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/1/2.
 */

public class EquipmentActivity extends BaseActivity {
    ListView lv_equ;
    Data info;
    String branchId;
    Gson gson;
    String memberId, member_no;
    List<EquipmentBean.Data.Content> content;
    TextView tv_ticeyi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment);
        initHost();
        initView();
    }

    private void initView() {

        lv_equ = findViewById(R.id.lv_equ);
        tv_ticeyi = findViewById(R.id.tv_ticeyi);

        initData();

        lv_equ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (content != null && content.get(position) != null) {

                    if (content.get(position).getStatus() == 1) {
                        commitEquNo(content.get(position).getBca_no());
                    } else if (content.get(position).getStatus() == 2) {
                        ToastUtils.showToastShort("该体测仪已被占用！");
                    } else {
                        ToastUtils.showToastShort("请确认体测仪是否工作正常！");
                    }

                }
            }
        });

    }

    private void commitEquNo(final String eqn) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.CONNECTEQU), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("连接失败")) {
                    ToastUtils.showToastShort("连接失败！");
                } else {
                    startActivity(new Intent(EquipmentActivity.this, TestingActivity.class).putExtra("deviceId", eqn).putExtra("memberId", memberId).putExtra("member_no", member_no));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i("volleyError"+volleyError);
                ToastUtils.showToastShort("请检查手机网络！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("deviceId", eqn);
                map.put("memberId", memberId);
                return map;
            }

        };

        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.GETEQUIPMENT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                EquipmentBean equipmentBean = gson.fromJson(s, EquipmentBean.class);
                if (equipmentBean != null && equipmentBean.getData() != null) {
                    content = equipmentBean.getData().getContent();
                    lv_equ.setAdapter(new MyListAdapter(content));
                } else {
                    tv_ticeyi.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i("volleyError"+volleyError);
                ToastUtils.showToastShort("请检查手机网络连接！");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("page", "0");
                map.put("branch_id", info.getPerson().getDefault_branch());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initHost() {

        memberId = getIntent().getStringExtra("memberId");
        member_no = getIntent().getStringExtra("member_no");
        gson = new Gson();
        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        if (info != null) {
            branchId = info.getPerson().getDefault_branch();
        }
    }

    private class MyListAdapter extends BaseAdapter {
        List<EquipmentBean.Data.Content> list;

        MyListAdapter(List<EquipmentBean.Data.Content> list) {
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
            convertView = LayoutInflater.from(EquipmentActivity.this).inflate(R.layout.equip_item, null);
            TextView equ_item = convertView.findViewById(R.id.equ_item);
            TextView equ_status = convertView.findViewById(R.id.equ_status);

            if (list.get(position) != null) {
                equ_item.setText(list.get(position).getBca_name());
                if (list.get(position).getStatus() == 1) {
                    equ_status.setText("体测");
                    equ_status.setTextColor(getResources().getColor(R.color.white));
                    equ_status.setBackground(getResources().getDrawable(R.drawable.adcise_status_red_bg));
                } else if (list.get(position).getStatus() == 2) {
                    equ_status.setText("占用");
                    equ_status.setTextColor(getResources().getColor(R.color.white));
                    equ_status.setBackground(getResources().getDrawable(R.drawable.adcise_status_gary_bg));
                } else if (list.get(position).getStatus() == 3) {
                    equ_status.setText("离线");
                    equ_status.setTextColor(getResources().getColor(R.color.colorGray22));
                    equ_status.setBackground(getResources().getDrawable(R.drawable.adcise_status_white_bg));
                }
            }


            return convertView;
        }
    }
}

package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.PublicBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 门店 文字推送
 * Created by yxx on 2018-12-07.
 */

public class TextPushActivity extends BaseActivity {
    private Context context;
    private DongLanTitleView dongLanTitleView;
    private EditText title_et;
    private EditText content_et;
    private LinearLayout rl_commit;

    private int from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_push);
        context = this;
        if (this.getIntent() != null) {
            from = this.getIntent().getIntExtra("from", 0);
        }
        initView();
    }

    private void initView() {
        dongLanTitleView = findViewById(R.id.title);
        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        rl_commit = findViewById(R.id.rl_commit);
        dongLanTitleView.getMoreTv().setText("记录");

        switch (from) { //推送范围(100:店长推送;101:会籍推送;102:教练推送;103:服务部推送)
            case 100:
                dongLanTitleView.setTitle("店长推送");
                break;
            case 101:
                dongLanTitleView.setTitle("会籍推送");
                break;
            case 102:
                dongLanTitleView.setTitle("教练推送");
                break;
            case 103:
                dongLanTitleView.setTitle("服务部推送");
                break;
        }
        rl_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });
        dongLanTitleView.getMoreTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TextPushListActivity.class).putExtra("from",from));
            }
        });
    }

    private void commitData() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.TEXT_PUSH_SAVE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                PublicBean datainfo = new Gson().fromJson(s.toString(), PublicBean.class);
                if (datainfo.getSuccess() && datainfo.getCode().equals("0")) {
                    ToastUtils.showToastShort("推送成功");
                    finish();
                } else {
                    ToastUtils.showToastLong(datainfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastLong("推送失败");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("push_range", from + "");
                map.put("title", title_et.getText().toString().trim());//标题
                map.put("content", content_et.getText().toString().trim());//推送内容
                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }
}

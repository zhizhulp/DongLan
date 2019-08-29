package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestConfirmPwdBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MD5Utils;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.cn.danceland.myapplication.R.id.btn_commit;

/**
 * Created by shy on 2017/10/13 11:42
 * Email:644563767@qq.com
 */


public class ConfirmPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_password;
    private TextView tv_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);
        initView();
    }

    private void initView() {
        findViewById(btn_commit).setOnClickListener(this);
        tv_phone = findViewById(R.id.tv_phone);
        et_password = findViewById(R.id.et_password);
        tv_phone.setText(getIntent().getStringExtra("phone"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btn_commit:
                checkPwd();
                break;
            default:
                break;
        }
    }

    private void checkPwd() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_VERIFY_PHONE_PASS), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestConfirmPwdBean loginInfoBean = gson.fromJson(s, RequestConfirmPwdBean.class);
                LogUtil.i(loginInfoBean.toString());
                LogUtil.i(loginInfoBean.getCode() + "");
                if (loginInfoBean.getSuccess()) {
                    startActivity(new Intent(ConfirmPasswordActivity.this, ResetPhoneActivity.class)
                            .putExtra("phone", tv_phone.getText().toString())
                            .putExtra("password", et_password.getText().toString())
                            .putExtra("form", 1));
                    finish();
                } else {
                    ToastUtils.showToastShort("输入有误，请重新输入");
                    et_password.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                LogUtil.e(volleyError.toString());
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone_no", tv_phone.getText().toString().trim());
                map.put("password", MD5Utils.encode(et_password.getText().toString().trim()));
                LogUtil.i(map.toString());
                return map;
            }
        };
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }
}

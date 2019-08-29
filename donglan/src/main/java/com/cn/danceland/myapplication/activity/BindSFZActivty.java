package com.cn.danceland.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestNewBindBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.AlertDialogCustom;
import com.cn.danceland.myapplication.view.CommitButton;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by shy on 2018/12/21 18:21
 * Email:644563767@qq.com
 * 验证身份证
 */


public class BindSFZActivty extends BaseActivity {

    private EditText et_sfz;
    String phone;
    private Dialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_sfz);
        EventBus.getDefault().register(this);
        initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 1015:
                finish();
                break;

            default:
                break;
        }
    }

    private void initview() {
        phone = getIntent().getStringExtra("phone");
        et_sfz = findViewById(R.id.et_identity_card);
        CommitButton commitButton = findViewById(R.id.dlbtn_commit);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_sfz.getText().toString().trim())) {
                    ToastUtils.showToastShort("请输入您的证件后六位");
                    return;
                }
                yanzhengshenfen();
            }
        });
    }

    /**
     * 验证身份
     */
    private void yanzhengshenfen() {
        MyStringNoTokenRequest myStringNoTokenRequest = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.VALIDATE_IDENTITYCARD_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestNewBindBean newBindBean = new Gson().fromJson(s, RequestNewBindBean.class);
                if (newBindBean.getCode() == 1) {
                    showClearDialog("输入证件号不符，请联系门店或重试");
                }
                if (newBindBean.getCode() == 2) {//通过
                    startActivity(new Intent(BindSFZActivty.this, SetPswdActivity.class).putExtra("id", newBindBean.getData().getPerson().getId())
                            .putExtra("login_type", 1)
                            .putExtra("phone", phone));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("identityCard", et_sfz.getText().toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(myStringNoTokenRequest);

    }

    private void showClearDialog(String msg) {
        dialog = new AlertDialogCustom("确认", "取消").CreateDialog(this, "提示", msg, new AlertDialogCustom.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();

            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
            }
        });
    }


}

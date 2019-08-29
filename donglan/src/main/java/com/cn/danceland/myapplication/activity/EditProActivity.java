package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feng on 2018/4/18.
 */

public class EditProActivity extends BaseActivity {

    DongLanTitleView editpro_title;
    EditText et_edit;
    Button btn_edit;
    Data info;
    String str_sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpro);

        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        initView();
    }

    private void initView() {

        editpro_title = findViewById(R.id.editpro_title);
        editpro_title.setTitle("编辑简介");
        et_edit = findViewById(R.id.et_edit);
        et_edit.setText(getIntent().getStringExtra("hint"));
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_edit.getText()!=null){
                    str_sign = et_edit.getText().toString();

                    commitSelf(Constants.plus(Constants.MODIFY_SIGN),"sign",str_sign);
                }
            }
        });
    }


    public void commitSelf(String url, final String mapkey, final String mapvalue) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("true")) {
                    info.getPerson().setSign(str_sign);
                    DataInfoCache.saveOneCache(info, Constants.MY_INFO);
                    setResult(13,new Intent().putExtra("sign",str_sign));
                    finish();
                    ToastUtils.showToastShort("修改成功！");
                } else {
                    ToastUtils.showToastShort("修改失败！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("修改失败！请检查网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(mapkey, mapvalue);
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }
}

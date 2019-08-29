package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RootBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by feng on 2018/5/17.
 */

public class LoginNumberActivity extends BaseActivity {
    Timer timer;
    TextView tv_number,tv_update;
    int m,time = 60;
    Handler handler;
    Gson gson;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaginnumber);

        gson = new Gson();
        handler = new Handler();
        initView();

        // 初始化定时器
        timer = new Timer();

    }
    // 停止定时器
    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void initView() {

        tv_number = findViewById(R.id.tv_number);
        tv_update = findViewById(R.id.tv_update);
        DongLanTitleView loginnumer_title = findViewById(R.id.loginnumer_title);
        loginnumer_title.setTitle("登录验证");
//        tv_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getData();
//            }
//        });
        getData();
    }


    private void getData(){

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.DYNAMICCODE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(s.contains("true")){
                    m++;
                    time = 60;
                    RootBean rootBean = gson.fromJson(s, RootBean.class);
                    if(rootBean!=null && rootBean.data!=null){
                        char[] chars = rootBean.data.toCharArray();
                        tv_number.setText(chars[0]+" "+chars[1]+" "+chars[2]+" "+chars[3]+" "+chars[4]+" "+chars[5]);
                    }

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    time = time-1;
                                    tv_update.setText(time+"s后将重新获取");
                                    if(time == 0){
                                        if(m>=4){
                                            finish();
                                        }
                                        cancel();
                                        getData();
                                    }
                                }
                            });

                        }
                    },0,1000);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError!=null){
                    LogUtil.i(volleyError.toString());
                }
            }
        }){

        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

}

package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.feedback.FeedBack;
import com.cn.danceland.myapplication.bean.feedback.FeedBackRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 意见反馈详情
 * Created by feng on 2018/3/26.
 */

public class AdviseDetailActivity extends BaseActivity {

    TextView tv_type, fankui_time, huifu_time, tv_status, tv_fankui, tv_huifu;
    private FeedBackRequest request;
    private Gson gson;
    long recordId;
    private ImageView type_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advise_detail);
        request = new FeedBackRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        recordId = getIntent().getLongExtra("id", 0);
        initView();
    }

    private void initView() {

        tv_type = findViewById(R.id.tv_type);
        type_iv = findViewById(R.id.type_iv);
        fankui_time = findViewById(R.id.fankui_time);
        huifu_time = findViewById(R.id.huifu_time);
        tv_status = findViewById(R.id.tv_status);
        tv_fankui = findViewById(R.id.tv_fankui);
        tv_huifu = findViewById(R.id.tv_huifu);
        findById();
    }

    /**
     * @方法说明:按主键查询单个意见反馈
     **/
    public void findById() {
        Long id = null;
        // TODO 准备数据
        id = recordId;
        request.findById(id, new Response.Listener<String>() {
            public void onResponse(String res) {
                DLResult<FeedBack> result = gson.fromJson(res, new TypeToken<DLResult<FeedBack>>() {
                }.getType());
                if (result.isSuccess()) {
                    FeedBack feedBack = result.getData();
                    if (feedBack.getType() == 1) {
                        tv_type.setText("批评");
                        type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_criticism_icon));
                    } else if (feedBack.getType() == 2) {
                        tv_type.setText("表扬");
                        type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_praise_icon));
                    } else if (feedBack.getType() == 3) {
                        tv_type.setText("建议");
                        type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_advice_icon));
                    } else if (feedBack.getType() == 4) {
                        tv_type.setText("投诉");
                        type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_complaint_icon));
                    } else {
                        tv_type.setText("未知类型");
                        type_iv.setImageDrawable(getResources().getDrawable(R.drawable.item_advice_icon));
                    }
                    if (feedBack.getStatus() == 1) {
                        tv_status.setText("已回复");
                        tv_status.setTextColor(getResources().getColor(R.color.colorGray21));
                        tv_status.setBackground(getResources().getDrawable(R.drawable.advise_status_white_bg));
                    } else if (feedBack.getStatus() == 2) {
                        tv_status.setText("未回复");
                        tv_status.setTextColor(getResources().getColor(R.color.white));
                        tv_status.setBackground(getResources().getDrawable(R.drawable.advise_status_red_bg));
                    } else {
                        tv_status.setText("状态未知");
                        tv_status.setTextColor(getResources().getColor(R.color.white));
                        tv_status.setBackground(getResources().getDrawable(R.drawable.advise_status_red_bg));
                    }

                    if (feedBack.getOpinion_date() != null) {
                        fankui_time.setText("反馈时间：" + TimeUtils.dateToString(feedBack.getOpinion_date()));
                    }
                    if (feedBack.getReply_date() != null) {
                        huifu_time.setText("回复时间：" + TimeUtils.dateToString(feedBack.getReply_date()));
                    }

                    tv_fankui.setText(feedBack.getContent());
                    tv_huifu.setText(feedBack.getReply_content());

                } else {
                    ToastUtils.showToastShort("请检查手机网络！");
                }
            }
        });

    }
}

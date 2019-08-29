package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.volley.Response;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.feedback.FeedBack;
import com.cn.danceland.myapplication.bean.feedback.FeedBackRequest;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

/**
 * 意见反馈
 * Created by feng on 2018/3/13.
 */

public class AdviseActivity extends BaseActivity {

    RadioGroup advise_rg;
    private FeedBackRequest request;
    private Gson gson;
    Integer type = 2;
    ContainsEmojiEditText advise_ed;
    Data data;
    LinearLayout rl_commit;
    TextView feed_record;
    RadioButton rb_0;
    private DongLanTitleView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.advise);

        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        request = new FeedBackRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        initView();

    }

    private void initView() {

        advise_rg = findViewById(R.id.advise_rg);
        advise_rg.setOnCheckedChangeListener(onCheckedChangeListener);

        rb_0 = findViewById(R.id.rb_0);
        rb_0.setChecked(true);

        advise_ed = findViewById(R.id.advise_ed);
        rl_commit = findViewById(R.id.rl_commit);
        rl_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(advise_ed.getText().toString())) {
                    ToastUtils.showToastShort("请输入您的意见或建议");
                    return;
                }
                save();
            }
        });

        title = findViewById(R.id.title);
        feed_record = title.findViewById(R.id.donglan_right_tv);
        feed_record.setText("记录");
        feed_record.setVisibility(View.VISIBLE);
        feed_record.setTextColor(getResources().getColor(R.color.home_enter_total_text_color));
        feed_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdviseActivity.this, AdviseRecordActivity.class));
            }
        });
    }


    /**
     * @方法说明:新增意见反馈
     **/
    public void save() {
        String content = advise_ed.getText().toString();
        FeedBack feedBack = new FeedBack();
        feedBack.setType(type);
        feedBack.setContent(content);
        feedBack.setBranch_id((long) data.getMember().getBranch_id());
        feedBack.setContact_way(data.getMember().getPhone_no());

        request.save(feedBack, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<Integer> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Integer>>() {
                }.getType());
                if (result.isSuccess()) {
                    ToastUtils.showToastShort("提交成功！");
                    finish();
                } else {
                    ToastUtils.showToastShort("保存数据失败,请检查手机网络！");
                }
            }
        });

    }


    OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int checkedRadioButtonId = group.getCheckedRadioButtonId();
            switch (checkedRadioButtonId) {
                case R.id.rb_0://表扬
                    type = 2;
//                    rb_0.setCompoundDrawables(null,getResources().getDrawable(R.drawable.advise_selected_icon),null,null);
//                    rb_1.setCompoundDrawables(null,getResources().getDrawable(R.drawable.advise_selected_icon),null,null);
//                    rb_2.setCompoundDrawables(null,getResources().getDrawable(R.drawable.advise_selected_icon),null,null);
//                    rb_3.setCompoundDrawables(null,getResources().getDrawable(R.drawable.advise_selected_icon),null,null);
                    break;
                case R.id.rb_1://建议
                    type = 3;
                    break;
                case R.id.rb_2://批评
                    type = 1;
                    break;
                case R.id.rb_3://投诉
                    type = 4;
                    break;

            }

        }
    };
}

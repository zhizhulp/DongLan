package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;

/**
 * 新增咨询
 * Created by ${yxx} on 2018/8/30.
 */

public class AddConsultOrRecommendActivity extends BaseActivity {
    private Context context;
    private DongLanTitleView title;//title
    private ImageView item_join_layout;//加盟
    private ImageView item_buy_layout;//购买
    private ImageView item_train_layout;//培训

    private String from = "咨询列表";//来自哪页 咨询列表 推荐列表
    private String type = "1";//意向类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consult_or_recommend);
        context = this;
        from = getIntent().getStringExtra("from");
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        if (!TextUtils.isEmpty(from) && from.equals("咨询列表")) {
            title.setTitle(context.getResources().getString(R.string.consult_text));
        } else {
            title.setTitle(context.getResources().getString(R.string.recommend_text));
        }
        item_join_layout = findViewById(R.id.item_join_layout);
        item_buy_layout = findViewById(R.id.item_buy_layout);
        item_train_layout = findViewById(R.id.item_train_layout);
        item_join_layout = (ImageView) UIUtils.setViewRatio(context, item_join_layout, 750, 450);
        item_buy_layout = (ImageView) UIUtils.setViewRatio(context, item_buy_layout, 750, 450);
        item_train_layout = (ImageView) UIUtils.setViewRatio(context, item_train_layout, 750, 450);

        item_join_layout.setOnClickListener(onClickListener);
        item_buy_layout.setOnClickListener(onClickListener);
        item_train_layout.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, AlertDialogActivity.class);
            intent.putExtra("from", from);
            switch (view.getId()) {
                case R.id.item_join_layout://加盟 合作类型
                    type = "1";
                    intent.putExtra("type", type);
                    startActivity(intent);
                    break;
                case R.id.item_buy_layout://购买
                    type = "3";
                    intent.putExtra("type", type);
                    startActivity(intent);
                    break;
                case R.id.item_train_layout://培训
                    type = "2";
                    intent.putExtra("type", type);
                    startActivity(intent);
                    break;
            }
        }
    };
}

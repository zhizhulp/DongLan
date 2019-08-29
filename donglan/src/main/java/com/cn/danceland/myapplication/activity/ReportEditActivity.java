package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.cn.danceland.myapplication.view.DongLanTitleView;

/**
 * Created by feng on 2018/4/26.
 */

public class ReportEditActivity extends BaseActivity {

    DongLanTitleView editpro_title;
    ContainsEmojiEditText et_edit;
    Button btn_edit;
    String str_meet, str_clean, str_item_placement, str_body_build, str_sport_device, str_group_course, str_course, str_power, str_door, str_remark;
    private int intId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editpro);
        initView();

    }

    private void initHost() {
        intId = getIntent().getIntExtra("id", 0);
        if (intId == 11) {
            str_meet = getIntent().getStringExtra("str_meet");
            if (StringUtils.isNullorEmpty(str_meet)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_meet);
            }
        } else if (intId == 12) {
            str_clean = getIntent().getStringExtra("str_clean");
            if (StringUtils.isNullorEmpty(str_clean)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_clean);
            }
        } else if (intId == 13) {
            str_item_placement = getIntent().getStringExtra("str_item_placement");
            if (StringUtils.isNullorEmpty(str_item_placement)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_item_placement);
            }
        } else if (intId == 14) {
            str_body_build = getIntent().getStringExtra("str_body_build");
            if (StringUtils.isNullorEmpty(str_body_build)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_body_build);
            }
        } else if (intId == 15) {
            str_sport_device = getIntent().getStringExtra("str_sport_device");
            if (StringUtils.isNullorEmpty(str_sport_device)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_sport_device);
            }
        } else if (intId == 16) {
            str_group_course = getIntent().getStringExtra("str_group_course");
            if (StringUtils.isNullorEmpty(str_meet)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_meet);
            }
        } else if (intId == 17) {
            str_course = getIntent().getStringExtra("str_course");
            if (StringUtils.isNullorEmpty(str_course)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_course);
            }
        } else if (intId == 18) {
            str_power = getIntent().getStringExtra("str_power");
            if (StringUtils.isNullorEmpty(str_power)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_power);
            }
        } else if (intId == 19) {
            str_door = getIntent().getStringExtra("str_door");
            if (StringUtils.isNullorEmpty(str_door)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_door);
            }
        } else if (intId == 20) {
            str_remark = getIntent().getStringExtra("str_remark");
            if (StringUtils.isNullorEmpty(str_remark)) {
                et_edit.setText("");
            } else {
                et_edit.setText(str_remark);
            }
        }
    }


    private void initView() {
        editpro_title = findViewById(R.id.editpro_title);
        editpro_title.setTitle("填写总结");


        et_edit = findViewById(R.id.et_edit);
        initHost();
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1, new Intent().putExtra("tv", et_edit.getText().toString()));
                finish();
            }
        });

    }
}

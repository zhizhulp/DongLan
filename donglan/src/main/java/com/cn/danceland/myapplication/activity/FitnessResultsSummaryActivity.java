package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.adapter.FitnessResultsSummaryAdapter;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.FitnessResultsSummaryBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.utils.BitmapUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ShareUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.NoScrollListView;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体测分析-结果汇总
 * Created by yxx on 2018-09-20.
 */

public class FitnessResultsSummaryActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private ImageView iv_avatar;//头像
    private TextView name_tv;//姓名
    private ImageView iv_sex;//性别
    private TextView age_tv;//年龄
    private TextView tel_tv;//电话
    private TextView date_tv;//体测日期
    private TextView operator_tv;//操作人员
    private TextView stores_tv;//体测门店
    private TextView content_tv;//综合评价
    private ImageView frontal_iv;//正面照
    private ImageView side_iv;//侧面照
    private ImageView behind_iv;//背后照
    private NoScrollListView listview;//listview
    private LinearLayout ok_btn;//完成
    private DongLanTitleView title;//数据title

    private FitnessResultsSummaryAdapter adapter;//adapter

    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象
    private List<FitnessResultsSummaryBean.QuestionTypes> questionTypesList;

    private Data infoData;
    private String saveId;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_results_summary);
        AppManager.getAppManager().addActivity(this);
        context = this;
        requsetInfo = (RequsetFindUserBean.Data) getIntent().getSerializableExtra("requsetInfo");//前面搜索到的对象
        saveId = getIntent().getStringExtra("saveId");//保存后返回的id

        infoData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);//动岚个人资料

        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title.setTitle("结果汇总");
        title.setMoreIvOnClick(this);
        title.setMoreIvVisible(true);
        title.setMoreIvImg(R.drawable.img_dl_share_dyn);
        title.setMoreTvVisible(false);
        iv_avatar = findViewById(R.id.iv_avatar);
        name_tv = findViewById(R.id.name_tv);
        iv_sex = findViewById(R.id.iv_sex);
        age_tv = findViewById(R.id.age_tv);
        tel_tv = findViewById(R.id.tel_tv);
        date_tv = findViewById(R.id.date_tv);
        operator_tv = findViewById(R.id.operator_tv);
        stores_tv = findViewById(R.id.stores_tv);
        listview = findViewById(R.id.listview);
        content_tv = findViewById(R.id.content_tv);
        frontal_iv = findViewById(R.id.frontal_iv);
        side_iv = findViewById(R.id.side_iv);
        behind_iv = findViewById(R.id.behind_iv);
        ok_btn = findViewById(R.id.ok_btn);
        scrollView = findViewById(R.id.scrollView);

        frontal_iv = (ImageView) UIUtils.setViewRatio(context, frontal_iv, 316, 400);
        side_iv = (ImageView) UIUtils.setViewRatio(context, side_iv, 316, 400);
        behind_iv = (ImageView) UIUtils.setViewRatio(context, behind_iv, 316, 400);

        questionTypesList = new ArrayList<>();

        adapter = new FitnessResultsSummaryAdapter(context, questionTypesList);
        listview.setAdapter(adapter);
        LogUtil.i("MY_TOKEN--" + SPUtils.getString(Constants.MY_TOKEN, ""));
        LogUtil.i("saveId--" + saveId);
        queryData();

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭前面的页面   mmp 太多了点 后面肯定会用   预留吧
//                AppManager.getAppManager().finishActivity(
//                        AddFriendsActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        FitnessTestNoticeActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        BodyBaseActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        BodyDeatilActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        PhysicalTestActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        BodyWeiDuActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        BodyTiXingActivity.class);
//                AppManager.getAppManager().finishActivity(
//                        BodyZongHeActivity.class);
                AppManager.getAppManager().finishAllActivity();
            }
        });
    }

    /**
     * 查询数据
     */
    private void queryData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_BCAQUESTION_FIND_BYID), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("Response--" + s);
                FitnessResultsSummaryBean responseBean = new Gson().fromJson(s, FitnessResultsSummaryBean.class);
                List<FitnessResultsSummaryBean.QuestionTypes> data = responseBean.getData().getQuestionTypes();
                String frontal_path = responseBean.getData().getFrontal_url();// 正面照
                String side_path = responseBean.getData().getSide_url();// 侧面照
                String behind_path = responseBean.getData().getBehind_url();// 背后照
                String content = responseBean.getData().getContent();//综合评价
                if (data != null && data.size() != 0) {
                    LogUtil.i("data.size()" + data.size());
                    questionTypesList.clear();
                    name_tv.setText(requsetInfo.getCname() + "");//姓名
                    if (requsetInfo.getGender() == 1) {
                        iv_sex.setImageDrawable(getResources().getDrawable(R.drawable.img_sex1));
                    } else if (requsetInfo.getGender() == 2) {
                        iv_sex.setImageDrawable(getResources().getDrawable(R.drawable.img_sex2));
                    }
//                    if (requsetInfo.getGender()==1) {
//                        sex_tv.setText("男");//性别
//                    } else if (requsetInfo.getGender()==2) {
//                        sex_tv.setText("女");//性别
//                    } else {
//                        sex_tv.setText("未设置");//性别
//                    }
                    if (requsetInfo.getBirthday() != null) {
                        int age = TimeUtils.getAgeFromBirthTime(new Date(TimeUtils.date2TimeStamp(requsetInfo.getBirthday(), "yyyy-MM-dd")));
                        age_tv.setText(age + "岁");//年龄
                    }
                    tel_tv.setText(requsetInfo.getPhone_no() + "");//电话
                    //-------操作人员模块
                    operator_tv.setText(responseBean.getData().getTeach_name() + "");//操作人员
                    if (responseBean.getData().getTest_time() != null && responseBean.getData().getTest_time().length() > 0) {
                        date_tv.setText(TimeUtils.millToDate(Long.valueOf(responseBean.getData().getTest_time())));//体测日期
                    }
                    stores_tv.setText(responseBean.getData().getBranch_name() + "");//体测门店
                    //-------操作人员模块
                    if (content != null && content.length() > 0) {//综合评价
                        content_tv.setText(content);
                        content_tv.setVisibility(View.VISIBLE);
                    } else {
                        content_tv.setVisibility(View.GONE);
                    }
                    if (context != null) {//设置图片
                        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_donglan_loading);
                        RequestOptions options2 = new RequestOptions().placeholder(R.drawable.img_my_avatar);
                        if (requsetInfo.getAvatar_url() != null && requsetInfo.getAvatar_url().length() > 0) {//头像
                            Glide.with(context).load(requsetInfo.getAvatar_url()).apply(options2).into(iv_avatar);

                        }
                        if (frontal_path != null && frontal_path.length() > 0) {
                            Glide.with(context).load(frontal_path).apply(options).into(frontal_iv);
                        }
                        if (side_path != null && side_path.length() > 0) {
                            Glide.with(context).load(side_path).apply(options).into(side_iv);
                        }
                        if (behind_path != null && behind_path.length() > 0) {
                            Glide.with(context).load(behind_path).apply(options).into(behind_iv);
                        }
                    }
                    if (data.size() == 7) {
                        data.remove(data.size() - 1);
                        data.remove(data.size() - 1);
                    }
                    questionTypesList.addAll(data);
//                    for (int i = 0; i < data.size(); i++) {
//                        //5.体型形体分析   6.综合评价 因接口数据结构，单独处理，后面追加布局
//                        if ((data.get(i).getTypeValue() != null && !data.get(i).getTypeValue().equals("6"))
//                                || (data.get(i).getTypeValue() != null && !data.get(i).getTypeValue().equals("7"))) {
//                            questionTypesList.add(data.get(i));
//                        }
//                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtil.i(volleyError.toString());
                } else {
                    LogUtil.i("NULL");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", saveId);
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                if (PermissionsUtil.hasPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    saveBitmapFile(BitmapUtils.getBitmapByView(scrollView));
                } else {
                    PermissionsUtil.requestPermission(FitnessResultsSummaryActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            saveBitmapFile(BitmapUtils.getBitmapByView(scrollView));
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            ToastUtils.showToastShort("没有权限");
                        }
                    }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, false, null);
                }
                break;
        }
    }

    private void saveBitmapFile(Bitmap bitmap) {
        File saveFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "fitness_summary.jpg");
        if (saveFile.exists()) {
            saveFile.delete();
        } else {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

            // 最后通知图库更新
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(saveFile.getPath()))));
            ShareBean shareBean = new ShareBean();
            shareBean.img_url = saveFile.getAbsolutePath();
            shareBean.type = 10;
            shareBean.bus_id = infoData.getPerson().getDefault_branch();
            ShareUtils.create(FitnessResultsSummaryActivity.this).shareImg(shareBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

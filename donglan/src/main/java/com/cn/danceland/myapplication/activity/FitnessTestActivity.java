package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.FitnessTestBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.BitmapUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ShareUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.ViewUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by feng on 2017/12/27.
 */

public class FitnessTestActivity extends BaseActivity implements View.OnClickListener {
    PieChartView pie_chart;
    PieChartData pieChardata;
    RelativeLayout rl_age;
    Gson gson;
    Data myInfo;
    String member_no;
    String bcaId;
    TextView tv_age, tv_height_mubiao, tv_height_kongzhi, tv_fat_kongzhi, tv_muscle_kongzhi, tv_height_dengji, tv_fat_baifenbi, tv_fat_yaotunbi, tv_danbaizhi, tv_fat_yingyang, tv_wujiyan, tv_jichudaixie, tv_zuoshangzhi, tv_youshangzhi, tv_zuoxiazhi, tv_youxiazhi, tv_qugan, tv_neizang, tv_shuifenlv, tv_neiye, tv_waiye, tv_zuoshangzhishuifen, tv_zuoxiazhishuifen, tv_youshangzhishuifen, tv_youxiazhishuifen, tv_xishu, no_data, test_score, test_classify, test_time, tv_line1, tv_line2, tv_line3, tv_line4, tv_line5, tv_line6, tv_line7, tv_line8, tv_tizhong, tv_jirou, tv_tizhilv, tv_guzhi, tv_zongshuifen, tv_gugeji, tv_yaotunbi, tv_tizhishu;
    ImageView history;
    ScrollView sv;
    String xingbie, height, weight;
    ProgressBar base_line1, base_line2, base_line3, base_line4, base_line5, base_line6, base_line7, base_line8;
    int width, low, normal, high;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    private TextView moisture_tv;//饼状图 水分
    private TextView fat_tv;//饼状图 脂肪
    private TextView bone_tv;//饼状图 骨质
    private TextView protein_tv;//饼状图 蛋白质
    private TextView test_person;//操作人员
    private ImageView iv_triangle_mark1;
    private ImageView iv_triangle_mark2;
    private ImageView iv_triangle_mark3;
    private ImageView iv_triangle_mark4;
    private ImageView iv_triangle_mark5;
    private ImageView iv_triangle_mark6;
    private ImageView iv_triangle_mark7;
    private ImageView iv_triangle_mark8;
    private ImageView iv_triangle1;
    private ImageView iv_triangle2;
    private ImageView iv_triangle3;
    private ImageView iv_triangle4;
    private ImageView iv_triangle5;
    private ImageView iv_triangle6;
    private ImageView iv_triangle7;
    private ImageView iv_triangle8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitnesstest);
        initHost();
        initView();
        bcaId = getIntent().getStringExtra("bcaId");
        if (bcaId != null) {
            rl_age.setClickable(false);
            initHistory();
            history.setVisibility(View.VISIBLE);
        } else {
            initData();
            //history.setVisibility(View.GONE);
        }
    }

    private void initHost() {
        myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        if (xingbie == null) {
            xingbie = myInfo.getPerson().getGender() + "";
        }
        if (height == null) {
            height = myInfo.getPerson().getHeight();
        }
        if (weight == null) {
            weight = myInfo.getPerson().getWeight();
        }
        gson = new Gson();
        if (member_no == null) {
            member_no = myInfo.getPerson().getMember_no();
        }

        width = AppUtils.getWidth();

        low = width / 4;
        normal = width / 2 - 20;
        high = width * 3 / 4 - 40;
    }


    private void initHistory() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDONEHISTORY), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                if (s.contains("true")) {
                    FitnessTestBean fitnessTestBean = gson.fromJson(s, FitnessTestBean.class);
                    if (fitnessTestBean != null) {
                        FitnessTestBean.Data data = fitnessTestBean.getData();
                        if (data != null) {
                            sv.setVisibility(View.VISIBLE);
                            setData(data);
                        } else {
                            rl_error.setVisibility(View.VISIBLE);
                        }
                    } else {
                        rl_error.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl_error.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                rl_error.setVisibility(View.VISIBLE);
                ToastUtils.showToastShort("请检查网络！");
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("bcaId", bcaId);
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_BC_DATA), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                if (s.contains("true")) {
                    FitnessTestBean fitnessTestBean = gson.fromJson(s, FitnessTestBean.class);
                    if (fitnessTestBean != null) {
                        FitnessTestBean.Data data = fitnessTestBean.getData();
                        if (data != null) {
                            sv.setVisibility(View.VISIBLE);
                            history.setVisibility(View.VISIBLE);
                            setData(data);
                        } else {
                            rl_error.setVisibility(View.VISIBLE);
                        }
                    } else {
                        rl_error.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl_error.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                rl_error.setVisibility(View.VISIBLE);
                ToastUtils.showToastShort("请检查网络！");
                volleyError.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("memberNo", member_no);
                LogUtil.i(member_no);
                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void setData(FitnessTestBean.Data data) {
        tv_age.setText(data.getBodyage());
        tv_height_mubiao.setText(data.getStandard_weight());
        tv_height_kongzhi.setText(data.getWeight_control());
        tv_fat_kongzhi.setText(data.getFat_control());
        tv_muscle_kongzhi.setText(data.getMuscle_control());
        tv_height_dengji.setText(data.getLbm());
        tv_fat_baifenbi.setText(data.getPbf());
        tv_fat_yaotunbi.setText(data.getWhr());
        tv_danbaizhi.setText(data.getProtein());
        tv_fat_yingyang.setText(data.getFat());
        tv_wujiyan.setText(data.getBone());
        tv_jichudaixie.setText(data.getBmr());
        tv_zuoshangzhi.setText(data.getLa_fat());
        tv_youshangzhi.setText(data.getRa_fat());
        tv_zuoxiazhi.setText(data.getLl_fat());
        tv_youxiazhi.setText(data.getRl_fat());
        tv_qugan.setText(data.getTr_fat());
        tv_neizang.setText(data.getVfi());
        tv_shuifenlv.setText(data.getWater());
        tv_neiye.setText(data.getIcw());
        tv_waiye.setText(data.getEcw());
        tv_zuoshangzhishuifen.setText(data.getLa_water());
        tv_youshangzhishuifen.setText(data.getRa_water());
        tv_zuoxiazhishuifen.setText(data.getLl_water());
        tv_youxiazhishuifen.setText(data.getRl_water());
        tv_xishu.setText(data.getEdema());
        test_score.setText(data.getScore());
        test_time.setText(data.getDate());
        test_person.setText(data.getEmployee_name());
        if (weight == null) {
            weight = data.getWeight();
        }
        if (height == null) {
            height = data.getHeight();
        }
        if (xingbie == null) {
            xingbie = data.getMember_sex() + "";
        }
        if (weight != null || height != null) {
            setScale("体重", data.getWeight());
            setScale("肌肉", data.getMuscle());
            setScale("体脂百分比", data.getPbf());
            setScale("骨质", data.getBone());
            setScale("总水分", data.getWater());
            setScale("骨骼肌", data.getSmm());
            setScale("体质指数", data.getBmi());
            setScale("腰臀比", data.getWhr());
            setLine("体重", data.getWeight());
            setLine("肌肉", data.getMuscle());
            setLine("体脂百分比", data.getPbf());
            setLine("骨质", data.getBone());
            setLine("总水分", data.getWater());
            setLine("骨骼肌", data.getSmm());
            setLine("体质指数", data.getBmi());
            setLine("腰臀比", data.getWhr());
        }

        test_classify.setText(tiXing(Float.valueOf(data.getBmi()), Float.valueOf(data.getPbf()), xingbie));
        initPie(data);
    }

    private void setScale(String type, String value) {
        double realValue = Float.valueOf(value);//要计算的值
        int finalAvg = (int) Math.pow(realValue, 1);
        int viewWidth = iv_triangle_mark1.getWidth() - DensityUtils.dp2px(FitnessTestActivity.this, 40f); // 获取宽度-左右空白
        if ("体重".equals(type)) {
            finalAvg = (int) Math.pow((realValue - 60), 1);//60A起始值
            if (finalAvg < 0) {
                finalAvg = 0;
            }
            ViewUtils.setMargins(iv_triangle1, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg, 0, 0, 0);
            LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
            double min = 90;
            double max = 110;
            if (realValue < min) {
                iv_triangle1.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
            } else if (realValue >= min && realValue <= max) {
                iv_triangle1.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
            } else {
                iv_triangle1.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                ViewUtils.setMargins(iv_triangle1, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * (finalAvg + 8), 0, 0, 0);
            }
            iv_triangle_mark1.setBackground(getResources().getDrawable(R.drawable.fitnesstest_chizi));
        } else if ("肌肉".equals(type)) {
            finalAvg = (int) Math.pow((realValue - 60), 1);//60A起始值
            if (finalAvg < 0) {
                finalAvg = 0;
            }
            ViewUtils.setMargins(iv_triangle2, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg, 0, 0, 0);
            LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
            double min = 90;
            double max = 110;
            if (realValue < min) {
                iv_triangle2.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
            } else if (realValue >= min && realValue <= max) {
                iv_triangle2.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
            } else {
                iv_triangle2.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                ViewUtils.setMargins(iv_triangle2, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * (finalAvg + 8), 0, 0, 0);
            }
            iv_triangle_mark2.setBackground(getResources().getDrawable(R.drawable.fitnesstest_chizi));
        } else if ("体脂百分比".equals(type)) {
            if ("1".equals(xingbie)) {
                finalAvg = (int) Math.pow((realValue - 0 + 5), 1);//60A起始值
                if (finalAvg < 0) {
                    finalAvg = 0;
                }
                ViewUtils.setMargins(iv_triangle3, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * finalAvg, 0, 0, 0);
                LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * (finalAvg)) + "-宽-" + viewWidth + "-值-" + finalAvg);
                double min = 10;
                double max = 20;
                if (realValue < min) {
                    iv_triangle3.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
                } else if (realValue >= min && realValue <= max) {
                    iv_triangle3.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
                } else {
                    iv_triangle3.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                    ViewUtils.setMargins(iv_triangle3, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * (finalAvg + 2), 0, 0, 0);
                }
                iv_triangle_mark3.setBackground(getResources().getDrawable(R.drawable.fitnesstest_tizhibaifenbi_men));
            } else if ("2".equals(xingbie)) {
                finalAvg = (int) Math.pow((realValue - 0), 1);//60A起始值
                if ((finalAvg - 8) < 0) {
                    finalAvg = 13;
                }
                ViewUtils.setMargins(iv_triangle3, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 48) * finalAvg - 3, 0, 0, 0);
                LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 48) * (finalAvg)) + "-宽-" + viewWidth + "-值-" + finalAvg);
                double min = 18;
                double max = 28;
                if (realValue < min) {
                    iv_triangle3.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
                } else if (realValue >= min && realValue <= max) {
                    iv_triangle3.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
                } else {
                    iv_triangle3.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                    ViewUtils.setMargins(iv_triangle3, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 48) * (finalAvg), 0, 0, 0);
                }
                iv_triangle_mark3.setBackground(getResources().getDrawable(R.drawable.fitnesstest_tizhibaifenbi_women));
            }
        } else if ("骨质".equals(type)) {
            finalAvg = (int) Math.pow((realValue - 60), 1);//60A起始值
            if (finalAvg < 0) {
                finalAvg = 0;
            }
            ViewUtils.setMargins(iv_triangle4, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg, 0, 0, 0);
            LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
            double min = 90;
            double max = 110;
            if (realValue < min) {
                iv_triangle4.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
            } else if (realValue >= min && realValue <= max) {
                iv_triangle4.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
            } else {
                iv_triangle4.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                ViewUtils.setMargins(iv_triangle4, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * (finalAvg + 8), 0, 0, 0);
            }
            iv_triangle_mark4.setBackground(getResources().getDrawable(R.drawable.fitnesstest_chizi));
        } else if ("总水分".equals(type)) {
            finalAvg = (int) Math.pow((realValue - 60), 1);//60A起始值
            if (finalAvg < 0) {
                finalAvg = 0;
            }
            ViewUtils.setMargins(iv_triangle5, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg, 0, 0, 0);
            LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
            double min = 90;
            double max = 110;
            if (realValue < min) {
                iv_triangle5.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
            } else if (realValue >= min && realValue <= max) {
                iv_triangle5.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
            } else {
                iv_triangle5.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                ViewUtils.setMargins(iv_triangle5, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * (finalAvg + 8), 0, 0, 0);
            }
            iv_triangle_mark5.setBackground(getResources().getDrawable(R.drawable.fitnesstest_chizi));
        } else if ("骨骼肌".equals(type)) {
            finalAvg = (int) Math.pow((realValue - 60), 1);//60A起始值
            if (finalAvg < 0) {
                finalAvg = 0;
            }
            ViewUtils.setMargins(iv_triangle6, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg, 0, 0, 0);
            LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
            double min = 90;
            double max = 110;
            if (realValue < min) {
                iv_triangle6.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
            } else if (realValue >= min && realValue <= max) {
                iv_triangle6.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
            } else {
                iv_triangle6.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                ViewUtils.setMargins(iv_triangle6, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 90) * (finalAvg + 8), 0, 0, 0);
            }
            iv_triangle_mark6.setBackground(getResources().getDrawable(R.drawable.fitnesstest_chizi));
        } else if ("体质指数".equals(type)) {
            finalAvg = finalAvg - 9;//60A起始值
            if (finalAvg < 0) {
                finalAvg = 0;
            }
            ViewUtils.setMargins(iv_triangle7, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 27) * finalAvg, 0, 0, 0);
            LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 27) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
            double min = 18;
            double max = 24;
            if (realValue < min) {
                iv_triangle7.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
            } else if (realValue >= min && realValue <= max) {
                iv_triangle7.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
            } else {
                iv_triangle7.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                ViewUtils.setMargins(iv_triangle7, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 27) * (finalAvg + 2), 0, 0, 0);
            }
            iv_triangle_mark7.setBackground(getResources().getDrawable(R.drawable.fitnesstest_tizhizhishu));
        } else if ("腰臀比".equals(type)) {
            if ("1".equals(xingbie)) {
                finalAvg = (int) Math.pow((realValue * 100 - 70), 1);//60A起始值
                if (finalAvg < 0) {
                    finalAvg = 0;
                }
                ViewUtils.setMargins(iv_triangle8, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * finalAvg, 0, 0, 0);
                LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
                double min = 0.85;
                double max = 0.95;
                if (realValue < min) {
                    iv_triangle8.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
                } else if (realValue >= min && realValue <= max) {
                    iv_triangle8.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
                } else {
                    iv_triangle8.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                    ViewUtils.setMargins(iv_triangle8, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * (finalAvg + 1), 0, 0, 0);
                }
                iv_triangle_mark8.setBackground(getResources().getDrawable(R.drawable.fitnesstest_yaotunbi_men));
            } else if ("2".equals(xingbie)) {
                finalAvg = (int) Math.pow((realValue * 100 - 55), 1);//60A起始值
                if (finalAvg < 0) {
                    finalAvg = 0;
                }
                ViewUtils.setMargins(iv_triangle8, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * finalAvg, 0, 0, 0);
                LogUtil.i(type + "标尺--" + (DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * finalAvg) + "-宽-" + viewWidth + "-值-" + finalAvg);
                double min = 0.70;
                double max = 0.80;
                if (realValue < min) {
                    iv_triangle8.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_yellow_jiantou));
                } else if (realValue >= min && realValue <= max) {
                    iv_triangle8.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_green_jiantou));
                } else {
                    iv_triangle8.setImageDrawable(getResources().getDrawable(R.drawable.fitnesstest_red_jiantou));
                    ViewUtils.setMargins(iv_triangle8, DensityUtils.dp2px(FitnessTestActivity.this, 20f) + (viewWidth / 45) * (finalAvg + 1), 0, 0, 0);
                }
                iv_triangle_mark8.setBackground(getResources().getDrawable(R.drawable.fitnesstest_yaotunbi_women));
            }
        }
    }

    private void setLine(String type, String value) {
        double realValue = Float.valueOf(value);//要计算的值
        double dw = Double.valueOf(weight);
        double h = Double.valueOf(height);
        double sm1 = 0.00344 * h * h - 0.37678 * h + 14.40021;
        double sm2 = 0.00351 * h * h - 0.4661 * h + 23.04821;
        if ("体重".equals(type)) {
            tv_line1.setText(value);
            String mi = TimeUtils.convertMi(height);//转换成米
            double m = Double.valueOf(mi);
            double min = m * m * 18.5;
            double max = m * m * 23.9;
            //-----------------
//            double centre = min + (max - min) / 2;//标体的中线
//            int progress = (int) ((realValue - centre) / 10 + centre);
//            LogUtil.i("centre=" + centre);
//            LogUtil.i("体重min=" + min + "max=" + max + "value=" + value + "pro=" + progress);
//
//            if (realValue < min) {
//                setLowLine2(base_line1, tv_line1, tv_tizhong, progress);
//                tv_tizhong.setText("偏低");
//            } else if (realValue >= min && realValue <= max) {
//                setNormalLine2(base_line1, tv_line1, tv_tizhong, progress);
//                tv_tizhong.setText("正常");
//            } else {
//                setHighLine2(base_line1, tv_line1, tv_tizhong, progress);
//                tv_tizhong.setText("偏高");
//            }
            if (realValue < min) {
                setLowLine(base_line1, tv_line1, tv_tizhong);
                tv_tizhong.setText("偏低");
            } else if (realValue >= min && realValue <= max) {
                setNormalLine(base_line1, tv_line1, tv_tizhong);
                tv_tizhong.setText("正常");
            } else {
                setHighLine(base_line1, tv_line1, tv_tizhong);
                tv_tizhong.setText("偏高");
            }
        } else if ("肌肉".equals(type)) {
            tv_line2.setText(value);
            if ("1".equals(xingbie)) {
//                double centre = (sm1 - 5) + ((sm1 - 5) - (sm1 - 5)) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 1 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < (sm1 - 5)) {
//                    setLowLine2(base_line2, tv_line2, tv_jirou, progress);
//                    tv_jirou.setText("偏低");
//                } else if (realValue >= (sm1 - 5) && realValue <= (sm1 - 5)) {
//                    setNormalLine2(base_line2, tv_line2, tv_jirou, progress);
//                    tv_jirou.setText("正常");
//                } else {
//                    setHighLine2(base_line2, tv_line2, tv_jirou, progress);
//                    tv_jirou.setText("偏高");
//                }
                if (realValue < (sm1 - 5)) {
                    setLowLine(base_line2, tv_line2, tv_jirou);
                    tv_jirou.setText("偏低");
                } else if (realValue >= (sm1 - 5) && realValue <= (sm1 - 5)) {
                    setNormalLine(base_line2, tv_line2, tv_jirou);
                    tv_jirou.setText("正常");
                } else {
                    setHighLine(base_line2, tv_line2, tv_jirou);
                    tv_jirou.setText("偏高");
                }
            } else if ("2".equals(xingbie)) {
//                double centre = (sm2 - 3) + ((sm2 - 3) - (sm2 - 3)) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 1 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < (sm2 - 3)) {
//                    setLowLine2(base_line2, tv_line2, tv_jirou, progress);
//                    tv_jirou.setText("偏低");
//                } else if (realValue >= (sm2 - 3) && realValue <= (sm2 - 3)) {
//                    setNormalLine2(base_line2, tv_line2, tv_jirou, progress);
//                    tv_jirou.setText("正常");
//                } else {
//                    setHighLine2(base_line2, tv_line2, tv_jirou, progress);
//                    tv_jirou.setText("偏高");
//                }
                if (realValue < (sm2 - 3)) {
                    setLowLine(base_line2, tv_line2, tv_jirou);
                    tv_jirou.setText("偏低");
                } else if (realValue >= (sm2 - 3) && realValue <= (sm2 - 3)) {
                    setNormalLine(base_line2, tv_line2, tv_jirou);
                    tv_jirou.setText("正常");
                } else {
                    setHighLine(base_line2, tv_line2, tv_jirou);
                    tv_jirou.setText("偏高");
                }
            }

        } else if ("体脂百分比".equals(type)) {
            tv_line3.setText(value);
            if ("1".equals(xingbie)) {
//                double centre = 10 + (10 - 20) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 0.5 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < 10) {
//                    setLowLine2(base_line3, tv_line3, tv_tizhilv, progress);
//                    tv_tizhilv.setText("偏低");
//                } else if (realValue > 20) {
//                    setHighLine2(base_line3, tv_line3, tv_tizhilv, progress);
//                    tv_tizhilv.setText("偏高");
//                } else {
//                    setNormalLine2(base_line3, tv_line3, tv_tizhilv, progress);
//                    tv_tizhilv.setText("正常");
//                }
                if (realValue < 10) {
                    setLowLine(base_line3, tv_line3, tv_tizhilv);
                    tv_tizhilv.setText("偏低");
                } else if (realValue > 20) {
                    setHighLine(base_line3, tv_line3, tv_tizhilv);
                    tv_tizhilv.setText("偏高");
                } else {
                    setNormalLine(base_line3, tv_line3, tv_tizhilv);
                    tv_tizhilv.setText("正常");
                }
            } else if ("2".equals(xingbie)) {
//                double centre = 18+ (18 - 28) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 0.5 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < 18) {
//                    setLowLine2(base_line3, tv_line3, tv_tizhilv, progress);
//                    tv_tizhilv.setText("偏低");
//                } else if (realValue > 28) {
//                    setHighLine2(base_line3, tv_line3, tv_tizhilv, progress);
//                    tv_tizhilv.setText("偏高");
//                } else {
//                    setNormalLine2(base_line3, tv_line3, tv_tizhilv, progress);
//                    tv_tizhilv.setText("正常");
//                }
                if (realValue < 18) {
                    setLowLine(base_line3, tv_line3, tv_tizhilv);
                    tv_tizhilv.setText("偏低");
                } else if (realValue > 28) {
                    setHighLine(base_line3, tv_line3, tv_tizhilv);
                    tv_tizhilv.setText("偏高");
                } else {
                    setNormalLine(base_line3, tv_line3, tv_tizhilv);
                    tv_tizhilv.setText("正常");
                }
            }
        } else if ("骨质".equals(type)) {
            tv_line4.setText(value);
            double min = 0.045 * dw;
            double max = 0.055 * dw;
//            double centre = min+ (min - max) / 2;//标体的中线
//            int progress = (int) ((realValue - centre) / 1 + centre);
//            LogUtil.i("centre=" + centre);
//            LogUtil.i(type + "value=" + value + "pro=" + progress);
//            if (realValue < min) {
//                setLowLine2(base_line4, tv_line4, tv_guzhi, progress);
//                tv_guzhi.setText("偏低");
//            } else if (realValue > max) {
//                setHighLine2(base_line4, tv_line4, tv_guzhi, progress);
//                tv_guzhi.setText("偏高");
//            } else {
//                setNormalLine2(base_line4, tv_line4, tv_guzhi, progress);
//                tv_guzhi.setText("正常");
//            }
            if (realValue < min) {
                setLowLine(base_line4, tv_line4, tv_guzhi);
                tv_guzhi.setText("偏低");
            } else if (realValue > max) {
                setHighLine(base_line4, tv_line4, tv_guzhi);
                tv_guzhi.setText("偏高");
            } else {
                setNormalLine(base_line4, tv_line4, tv_guzhi);
                tv_guzhi.setText("正常");
            }
        } else if ("总水分".equals(type)) {
            tv_line5.setText(value);
            double min = 0.54 * dw;
            double max = 0.66 * dw;
//            double centre = min+ (min - max) / 2;//标体的中线
//            int progress = (int) ((realValue - centre) / 1 + centre);
//            LogUtil.i("centre=" + centre);
//            LogUtil.i(type + "value=" + value + "pro=" + progress);
//            if (realValue < min) {
//                setLowLine2(base_line5, tv_line5, tv_zongshuifen, progress);
//                tv_zongshuifen.setText("偏低");
//            } else if (realValue > max) {
//                setHighLine2(base_line5, tv_line5, tv_zongshuifen, progress);
//                tv_zongshuifen.setText("偏高");
//            } else {
//                setNormalLine2(base_line5, tv_line5, tv_zongshuifen, progress);
//                tv_zongshuifen.setText("正常");
//            }
            if (realValue < min) {
                setLowLine(base_line5, tv_line5, tv_zongshuifen);
                tv_zongshuifen.setText("偏低");
            } else if (realValue > max) {
                setHighLine(base_line5, tv_line5, tv_zongshuifen);
                tv_zongshuifen.setText("偏高");
            } else {
                setNormalLine(base_line5, tv_line5, tv_zongshuifen);
                tv_zongshuifen.setText("正常");
            }
        } else if ("骨骼肌".equals(type)) {
            tv_line6.setText(value);
            if ("1".equals(xingbie)) {
                double min = (sm1 - 5) * 0.75;
                double max = (sm1 + 5) * 0.75;
//                double centre = min+ (min - max) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 1 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < min) {
//                    setLowLine2(base_line6, tv_line6, tv_gugeji, progress);
//                    tv_gugeji.setText("偏低");
//                } else if (realValue > max) {
//                    setHighLine2(base_line6, tv_line6, tv_gugeji, progress);
//                    tv_gugeji.setText("偏高");
//                } else {
//                    setNormalLine2(base_line6, tv_line6, tv_gugeji, progress);
//                    tv_gugeji.setText("正常");
//                }
                if (realValue < min) {
                    setLowLine(base_line6, tv_line6, tv_gugeji);
                    tv_gugeji.setText("偏低");
                } else if (realValue > max) {
                    setHighLine(base_line6, tv_line6, tv_gugeji);
                    tv_gugeji.setText("偏高");
                } else {
                    setNormalLine(base_line6, tv_line6, tv_gugeji);
                    tv_gugeji.setText("正常");
                }
            } else if ("2".equals(xingbie)) {
                double min = (sm2 - 3) * 0.75;
                double max = (sm2 + 3) * 0.75;
//                double centre = min+ (min - max) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 1 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < min) {
//                    setLowLine2(base_line6, tv_line6, tv_gugeji, progress);
//                    tv_gugeji.setText("偏低");
//                } else if (realValue > max) {
//                    setHighLine2(base_line6, tv_line6, tv_gugeji, progress);
//                    tv_gugeji.setText("偏高");
//                } else {
//                    setNormalLine2(base_line6, tv_line6, tv_gugeji, progress);
//                    tv_gugeji.setText("正常");
//                }
                if (realValue < min) {
                    setLowLine(base_line6, tv_line6, tv_gugeji);
                    tv_gugeji.setText("偏低");
                } else if (realValue > max) {
                    setHighLine(base_line6, tv_line6, tv_gugeji);
                    tv_gugeji.setText("偏高");
                } else {
                    setNormalLine(base_line6, tv_line6, tv_gugeji);
                    tv_gugeji.setText("正常");
                }
            }

        } else if ("体质指数".equals(type)) {
            tv_line7.setText(value);
//            double centre = 18.5+ (18.5 - 23.9) / 2;//标体的中线
//            int progress = (int) ((realValue - centre) / 0.3 + centre);
//            LogUtil.i("centre=" + centre);
//            LogUtil.i(type + "value=" + value + "pro=" + progress);
//            if (realValue < 18.5) {
//                setLowLine2(base_line7, tv_line7, tv_tizhishu, progress);
//                tv_tizhishu.setText("体重较轻");
//            } else if (realValue > 23.9) {
//                setHighLine2(base_line7, tv_line7, tv_tizhishu, progress);
//                tv_tizhishu.setText("超重");
//            } else {
//                setNormalLine2(base_line7, tv_line7, tv_tizhishu, progress);
//                tv_tizhishu.setText("正常");
//            }
            if (realValue < 18.5) {
                setLowLine(base_line7, tv_line7, tv_tizhishu);
                tv_tizhishu.setText("体重较轻");
            } else if (realValue > 23.9) {
                setHighLine(base_line7, tv_line7, tv_tizhishu);
                tv_tizhishu.setText("超重");
            } else {
                setNormalLine(base_line7, tv_line7, tv_tizhishu);
                tv_tizhishu.setText("正常");
            }
        } else if ("腰臀比".equals(type)) {
            tv_line8.setText(value);
            if ("1".equals(xingbie)) {
//                double centre = 0.85+ (0.85 - 0.95) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) /0.05 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < 0.85) {
//                    setLowLine2(base_line8, tv_line8, tv_yaotunbi, progress);
//                    tv_yaotunbi.setText("梨型");
//                } else if (realValue > 0.95) {
//                    setHighLine2(base_line8, tv_line8, tv_yaotunbi, progress);
//                    tv_yaotunbi.setText("苹果型");
//                } else {
//                    setNormalLine2(base_line8, tv_line8, tv_yaotunbi, progress);
//                    tv_yaotunbi.setText("正常");
//                }
                if (realValue < 0.85) {
                    setLowLine(base_line8, tv_line8, tv_yaotunbi);
                    tv_yaotunbi.setText("梨型");
                } else if (realValue > 0.95) {
                    setHighLine(base_line8, tv_line8, tv_yaotunbi);
                    tv_yaotunbi.setText("苹果型");
                } else {
                    setNormalLine(base_line8, tv_line8, tv_yaotunbi);
                    tv_yaotunbi.setText("正常");
                }
            } else if ("2".equals(xingbie)) {
//                double centre = 0.7+ (0.7 - 0.8) / 2;//标体的中线
//                int progress = (int) ((realValue - centre) / 0.05 + centre);
//                LogUtil.i("centre=" + centre);
//                LogUtil.i(type + "value=" + value + "pro=" + progress);
//                if (realValue < 0.7) {
//                    setLowLine2(base_line8, tv_line8, tv_yaotunbi, progress);
//                    tv_yaotunbi.setText("梨型");
//                } else if (realValue > 0.8) {
//                    setHighLine2(base_line8, tv_line8, tv_yaotunbi, progress);
//                    tv_yaotunbi.setText("苹果型");
//                } else {
//                    setNormalLine2(base_line8, tv_line8, tv_yaotunbi, progress);
//                    tv_yaotunbi.setText("正常");
//                }
                if (realValue < 0.7) {
                    setLowLine(base_line8, tv_line8, tv_yaotunbi);
                    tv_yaotunbi.setText("梨型");
                } else if (realValue > 0.8) {
                    setHighLine(base_line8, tv_line8, tv_yaotunbi);
                    tv_yaotunbi.setText("苹果型");
                } else {
                    setNormalLine(base_line8, tv_line8, tv_yaotunbi);
                    tv_yaotunbi.setText("正常");
                }
            }
        }
    }

    private void setLowLine(ProgressBar pb, TextView tv, TextView tv2) {
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.blue_horizontal_progressbar));
        pb.setProgress(25);
        tv.setPadding(low, 0, 0, 0);
        tv.setTextColor(getResources().getColor(R.color.blue_color1));
        tv2.setTextColor(getResources().getColor(R.color.blue_color1));
    }

    private void setLowLine2(ProgressBar pb, TextView tv, TextView tv2, int progress) {
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.blue_horizontal_progressbar));
        pb.setProgress(progress);
        tv.setPadding(low, 0, 0, 0);
        tv.setTextColor(getResources().getColor(R.color.blue_color1));
        tv2.setTextColor(getResources().getColor(R.color.blue_color1));
    }

    private void setNormalLine(ProgressBar pb, TextView tv, TextView tv2) {
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_horizontal_progressbar));
        pb.setProgress(50);
        tv.setPadding(normal, 0, 0, 0);
        tv.setTextColor(getResources().getColor(R.color.green_color1));
        tv2.setTextColor(getResources().getColor(R.color.green_color1));
    }

    private void setNormalLine2(ProgressBar pb, TextView tv, TextView tv2, int progress) {
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_horizontal_progressbar));
        pb.setProgress(progress);
        tv.setPadding(normal, 0, 0, 0);
        tv.setTextColor(getResources().getColor(R.color.green_color1));
        tv2.setTextColor(getResources().getColor(R.color.green_color1));
    }

    private void setHighLine(ProgressBar pb, TextView tv, TextView tv2) {
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.red_horizontal_progressbar));
        pb.setProgress(75);
        tv.setPadding(high, 0, 0, 0);
        tv.setTextColor(getResources().getColor(R.color.red_color1));
        tv2.setTextColor(getResources().getColor(R.color.red_color1));
    }

    private void setHighLine2(ProgressBar pb, TextView tv, TextView tv2, int progress) {
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.red_horizontal_progressbar));
        pb.setProgress(progress);
        tv.setPadding(high, 0, 0, 0);
        tv.setTextColor(getResources().getColor(R.color.red_color1));
        tv2.setTextColor(getResources().getColor(R.color.red_color1));
    }

    private void initView() {
        //no_data = findViewById(R.id.no_data);
        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(this).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("请联系您的会籍或教练为您体测");
        sv = findViewById(R.id.sv);
        pie_chart = findViewById(R.id.pie_chart);
        DongLanTitleView dongLanTitleView = findViewById(R.id.title);
        dongLanTitleView.setMoreTvVisible(false);
        dongLanTitleView.setMoreIvOnClick(this);
        dongLanTitleView.setMoreIvImg(R.drawable.img_dl_share_dyn);
        history = dongLanTitleView.findViewById(R.id.iv_more);
//        history.setText("历史");
        history.setVisibility(View.GONE);
//        history.setTextColor(getResources().getColor(R.color.home_enter_total_text_color));
//        history.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(FitnessTestActivity.this, FitnessHistoryActivity.class).putExtra("member_no", member_no), 101);
//                finish();
//            }
//        });

        rl_age = findViewById(R.id.rl_age);
        rl_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitnessTestActivity.this, BodyAgeActivity.class);
                startActivity(intent);
            }
        });

        tv_age = findViewById(R.id.tv_age);
        tv_height_mubiao = findViewById(R.id.tv_height_mubiao);
        tv_height_kongzhi = findViewById(R.id.tv_height_kongzhi);
        tv_fat_kongzhi = findViewById(R.id.tv_fat_kongzhi);
        tv_muscle_kongzhi = findViewById(R.id.tv_muscle_kongzhi);
        tv_height_dengji = findViewById(R.id.tv_height_dengji);
        tv_fat_baifenbi = findViewById(R.id.tv_fat_baifenbi);
        tv_fat_yaotunbi = findViewById(R.id.tv_fat_yaotunbi);
        tv_danbaizhi = findViewById(R.id.tv_danbaizhi);
        tv_fat_yingyang = findViewById(R.id.tv_fat_yingyang);
        tv_wujiyan = findViewById(R.id.tv_wujiyan);
        tv_jichudaixie = findViewById(R.id.tv_jichudaixie);
        tv_zuoshangzhi = findViewById(R.id.tv_zuoshangzhi);
        tv_youshangzhi = findViewById(R.id.tv_youshangzhi);
        tv_zuoxiazhi = findViewById(R.id.tv_zuoxiazhi);
        tv_youxiazhi = findViewById(R.id.tv_youxiazhi);
        tv_qugan = findViewById(R.id.tv_qugan);
        tv_neizang = findViewById(R.id.tv_neizang);
        tv_shuifenlv = findViewById(R.id.tv_shuifenlv);
        tv_neiye = findViewById(R.id.tv_neiye);
        tv_waiye = findViewById(R.id.tv_waiye);
        tv_zuoshangzhishuifen = findViewById(R.id.tv_zuoshangzhishuifen);
        tv_zuoxiazhishuifen = findViewById(R.id.tv_zuoxiazhishuifen);
        tv_youshangzhishuifen = findViewById(R.id.tv_youshangzhishuifen);
        tv_youxiazhishuifen = findViewById(R.id.tv_youxiazhishuifen);
        tv_xishu = findViewById(R.id.tv_xishu);
        test_score = findViewById(R.id.test_score);
        test_classify = findViewById(R.id.test_classify);
        test_time = findViewById(R.id.test_time);
        test_person = findViewById(R.id.test_person);
        tv_line1 = findViewById(R.id.tv_line1);
        tv_line2 = findViewById(R.id.tv_line2);
        tv_line3 = findViewById(R.id.tv_line3);
        tv_line4 = findViewById(R.id.tv_line4);
        tv_line5 = findViewById(R.id.tv_line5);
        tv_line6 = findViewById(R.id.tv_line6);
        tv_line7 = findViewById(R.id.tv_line7);
        tv_line8 = findViewById(R.id.tv_line8);
        base_line1 = findViewById(R.id.base_line1);
        base_line2 = findViewById(R.id.base_line2);
        base_line3 = findViewById(R.id.base_line3);
        base_line4 = findViewById(R.id.base_line4);
        base_line5 = findViewById(R.id.base_line5);
        base_line6 = findViewById(R.id.base_line6);
        base_line7 = findViewById(R.id.base_line7);
        base_line8 = findViewById(R.id.base_line8);
        tv_tizhong = findViewById(R.id.tv_tizhong);
        tv_jirou = findViewById(R.id.tv_jirou);
        tv_tizhilv = findViewById(R.id.tv_tizhilv);
        tv_guzhi = findViewById(R.id.tv_guzhi);
        tv_zongshuifen = findViewById(R.id.tv_zongshuifen);
        tv_gugeji = findViewById(R.id.tv_gugeji);
        tv_tizhishu = findViewById(R.id.tv_tizhishu);
        tv_yaotunbi = findViewById(R.id.tv_yaotunbi);

        moisture_tv = findViewById(R.id.moisture_tv);//饼状图 水分
        fat_tv = findViewById(R.id.fat_tv);//饼状图 脂肪
        bone_tv = findViewById(R.id.bone_tv);//饼状图 骨质
        protein_tv = findViewById(R.id.protein_tv);//饼状图 蛋白质
        iv_triangle_mark1 = findViewById(R.id.iv_triangle_mark1);
        iv_triangle_mark2 = findViewById(R.id.iv_triangle_mark2);
        iv_triangle_mark3 = findViewById(R.id.iv_triangle_mark3);
        iv_triangle_mark4 = findViewById(R.id.iv_triangle_mark4);
        iv_triangle_mark5 = findViewById(R.id.iv_triangle_mark5);
        iv_triangle_mark6 = findViewById(R.id.iv_triangle_mark6);
        iv_triangle_mark7 = findViewById(R.id.iv_triangle_mark7);
        iv_triangle_mark8 = findViewById(R.id.iv_triangle_mark8);
        iv_triangle1 = findViewById(R.id.iv_triangle1);
        iv_triangle2 = findViewById(R.id.iv_triangle2);
        iv_triangle3 = findViewById(R.id.iv_triangle3);
        iv_triangle4 = findViewById(R.id.iv_triangle4);
        iv_triangle5 = findViewById(R.id.iv_triangle5);
        iv_triangle6 = findViewById(R.id.iv_triangle6);
        iv_triangle7 = findViewById(R.id.iv_triangle7);
        iv_triangle8 = findViewById(R.id.iv_triangle8);

    }


    private void initPie(FitnessTestBean.Data data) {
        /**
         * 初始化
         */
        List<SliceValue> values = new ArrayList<SliceValue>();
        List<Double> chartValues = new ArrayList<Double>();
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(false);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setSlicesSpacing(0);
        if (data.getWater() != null && data.getFat() != null && data.getBone() != null && data.getProtein() != null) {
            Double[] lv = {Double.valueOf(data.getWater()), Double.valueOf(data.getFat()), Double.valueOf(data.getBone()), Double.valueOf(data.getProtein())};
            Integer[] color = {getResources().getColor(R.color.blue_color2), getResources().getColor(R.color.yellow_color1), getResources().getColor(R.color.purple_color1), getResources().getColor(R.color.green_color2)};
            String[] str = {"水分", "脂肪", "骨质", "蛋白质"};
            for (int i = 0; i <= 3; i++) {
                SliceValue sliceValue = new SliceValue(lv[i].floatValue(), color[i]);//这里的颜色是我写了一个工具类 是随机选择颜色的
                sliceValue.setLabel(str[i] + " " + lv[i] + "%");
                values.add(sliceValue);
                chartValues.add(lv[i]);
            }
            Double sum = 0.0;
            DecimalFormat df = new DecimalFormat("#.00");
            for (Double dd : chartValues) {
                sum += dd;
            }
            String moistureStr = chartValues.get(0) + "[" + df.format(sum / 100 * chartValues.get(0)) + "%" + "]";
            String fatStr = chartValues.get(1) + "[" + df.format(sum / 100 * chartValues.get(1)) + "%" + "]";
            String boneStr = chartValues.get(2) + "[" + df.format(sum / 100 * chartValues.get(2)) + "%" + "]";
            String proteinStr = chartValues.get(3) + "[" + df.format(sum / 100 * chartValues.get(3)) + "%" + "]";

            moisture_tv.setText(moistureStr);//饼状图 水分
            fat_tv.setText(fatStr);//饼状图 脂肪
            bone_tv.setText(boneStr);//饼状图 骨质
            protein_tv.setText(proteinStr);//饼状图 蛋白质

            pieChardata.setValues(values);//填充数据
            pieChardata.setCenterCircleColor(0x00FFFFFF);//设置环形中间的颜色
            pieChardata.setCenterCircleScale(0.8f);//设置环形的大小级别
            pieChardata.setCenterText1Color(Color.BLACK);//文字颜色
            pieChardata.setCenterText1FontSize(12);//文字大小

            pie_chart.setPieChartData(pieChardata);
            pie_chart.setViewportCalculationEnabled(true);
            pie_chart.setChartRotationEnabled(false);//设置饼图是否可以手动旋转
            pie_chart.setValueSelectionEnabled(false);//选择饼图某一块变大
            pie_chart.setAlpha(1f);//设置透明度
            pie_chart.setCircleFillRatio(1f);//设置饼图大小

        }
    }

    //判断体形
    //bmi体质指数，pbf体脂百分比,gender==1男，2女
    private String tiXing(float bmi, float pbf, String gender) {
        String tixing = "体型未知";
        if ("1".equals(gender)) {
            if (bmi < 18.5) {
                if (pbf > 20) {
                    tixing = "隐性肥胖型";
                } else if (pbf >= 10 && pbf <= 20) {
                    tixing = "肌肉不足型";
                } else {
                    tixing = "消瘦型";
                }
            } else if (bmi >= 18.5 && bmi <= 23.9) {
                if (pbf > 20) {
                    tixing = "脂肪过多型";
                } else if (pbf >= 10 && pbf <= 20) {
                    tixing = "健康匀称型";
                } else {
                    tixing = "低脂肪型";
                }
            } else {
                if (pbf > 20) {
                    tixing = "肥胖型";
                } else if (pbf >= 10 && pbf <= 20) {
                    tixing = "超重肌肉型";
                } else {
                    tixing = "运动员型";
                }
            }
        } else if ("2".equals(gender)) {
            if (bmi < 18.5) {
                if (pbf > 28) {
                    tixing = "隐性肥胖型";
                } else if (pbf >= 18 && pbf <= 28) {
                    tixing = "肌肉不足型";
                } else {
                    tixing = "消瘦型";
                }
            } else if (bmi >= 18.5 && bmi <= 23.9) {
                if (pbf > 28) {
                    tixing = "脂肪过多型";
                } else if (pbf >= 18 && pbf <= 28) {
                    tixing = "健康匀称型";
                } else {
                    tixing = "低脂肪型";
                }
            } else {
                if (pbf > 28) {
                    tixing = "肥胖型";
                } else if (pbf >= 18 && pbf <= 28) {
                    tixing = "超重肌肉型";
                } else {
                    tixing = "运动员型";
                }
            }
        }
        return tixing;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                if (PermissionsUtil.hasPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    saveBitmapFile(BitmapUtils.getBitmapByView(sv));
                } else {
                    PermissionsUtil.requestPermission(FitnessTestActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            saveBitmapFile(BitmapUtils.getBitmapByView(sv));
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
        File saveFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "fitness_test.jpg");
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
            shareBean.bus_id = myInfo.getPerson().getDefault_branch();
            ShareUtils.create(FitnessTestActivity.this).shareImg(shareBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

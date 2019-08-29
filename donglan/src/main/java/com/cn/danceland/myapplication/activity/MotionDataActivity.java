package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.cn.danceland.myapplication.bean.MotionBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 我的 运动数据
 * Created by yxx on 2018-12-03.
 */

public class MotionDataActivity extends BaseActivity {
    private Context context;
    private DongLanTitleView dongLanTitleView;
    private RoundImageView circle_image;
    private TextView tv_nick_name, tv_male_age, tv_phone;
    private ImageView iv_sex;
    private PullToRefreshListView pullToRefresh;
    private ProgressDialog dialog;
    private RelativeLayout rl_error;
    private LinearLayout left_btn;
    private LinearLayout right_btn;
    private TextView left_text_btn;
    private TextView right_text_btn;

    private MotionDataAdapter myListAatapter;
    private List<MotionBean.Data.Content> datalist = new ArrayList<>();

    private Data myInfo;
    private int mCurrentPage = 0;//当前请求页
    private boolean isEnd = false;
    private boolean isAerobic = true;//有氧

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_data);
        context = this;
        myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        isAerobic = getIntent().getBooleanExtra("isAerobic", true);
        initView();
        initHeaderData();
    }

    private void initView() {
        dongLanTitleView = findViewById(R.id.title);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        left_btn = findViewById(R.id.left_btn);
        right_btn = findViewById(R.id.right_btn);
        left_text_btn = findViewById(R.id.left_text_btn);
        right_text_btn = findViewById(R.id.right_text_btn);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载……");
        rl_error = findViewById(R.id.rl_no_info);
        TextView more_iv = dongLanTitleView.getMoreTv();
        more_iv.setVisibility(View.VISIBLE);
        more_iv.setText("数据总览");

        pullToRefresh.getRefreshableView().setEmptyView(rl_error);
        myListAatapter = new MotionDataAdapter();
        pullToRefresh.setAdapter(myListAatapter);
        pullToRefresh.getRefreshableView().addHeaderView(initHeadview());
        pullToRefresh.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        new UpRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }
        });

        init_pullToRefresh();
        left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAerobic = true;
                refreshView();
            }
        });
        right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAerobic = false;
                refreshView();

            }
        });
        more_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, DataScreenActivity.class));
            }
        });
    }

    private void refreshView() {
        if (isAerobic) {
            left_btn.setBackground(getResources().getDrawable(R.drawable.powder_shadow_btn_bg));
            right_btn.setBackground(getResources().getDrawable(R.drawable.gray_shadow_btn_bg));
            left_text_btn.setTextColor(getResources().getColor(R.color.white));
            right_text_btn.setTextColor(getResources().getColor(R.color.colorGray21));
        } else {
            left_btn.setBackground(getResources().getDrawable(R.drawable.gray_shadow_btn_bg));
            right_btn.setBackground(getResources().getDrawable(R.drawable.powder_shadow_btn_bg));
            left_text_btn.setTextColor(getResources().getColor(R.color.colorGray21));
            right_text_btn.setTextColor(getResources().getColor(R.color.white));
        }
        datalist = new ArrayList<>();
        mCurrentPage = 0;
        try {
            find_all_data(mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            init_pullToRefresh();
            mCurrentPage = 0;
            try {
                find_all_data(mCurrentPage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            myListAatapter.notifyDataSetChanged();
            pullToRefresh.onRefreshComplete();
        }
    }

    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                try {
                    find_all_data(mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pullToRefresh.onRefreshComplete();
            if (isEnd) {//没数据了
                pullToRefresh.onRefreshComplete();
            }
        }
    }

    private void init_pullToRefresh() {
        // 设置下拉刷新文本
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        // 设置上拉刷新文本
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
    }

    /**
     * 查询数据
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_all_data(final int pageCount) throws JSONException {
        String url;//有氧
        if (isAerobic) {
            url = Constants.plus(Constants.QUERY_SH_AEROBIC);//有氧
        } else {
            url = Constants.plus(Constants.QUERY_SH_WEIGHT);//无氧
        }
        MyStringRequest request = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                MotionBean datainfo = new Gson().fromJson(s.toString(), MotionBean.class);
                if (datainfo.getSuccess()) {
                    SPUtils.setBoolean(Constants.SCANER_CODE_TRAIN_ISLOOK, false);//扫码训练  true最后一条没看
                    if ((mCurrentPage + 1) >= datainfo.getData().getTotalPages()) {
                        isEnd = true;
                        setEnd();
                    } else {
                        isEnd = false;
                        init_pullToRefresh();
                    }
                    if (mCurrentPage == 0) {
                        datalist = datainfo.getData().getContent();
//                        if(datalist!=null&&datalist.size()>0){//用于扫码训练 校验最后一条运动数据
//                            SPUtils.setString(Constants.SCANER_CODE_TRAIN_LASTID, datalist.get(0).getId());
//                        }
//                        if (datainfo.getData().getContent().size() == 0) {
//                            rl_error.setVisibility(View.VISIBLE);
//                        }else{
//                            rl_error.setVisibility(View.GONE);
//                        }
                        myListAatapter.notifyDataSetChanged();
                    } else {
                        datalist.addAll(datainfo.getData().getContent());
                        myListAatapter.notifyDataSetChanged();
                    }
                    mCurrentPage = mCurrentPage + 1;
                } else {
                    ToastUtils.showToastLong(datainfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("page", pageCount + "");
                map.put("person_id", myInfo.getPerson().getId());
                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    private void setEnd() {
        LogUtil.i("没数据了");
        isEnd = true;//没数据了
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
//        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
//        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
    }

    private View initHeadview() {
        View v = View.inflate(context, R.layout.bodybase_header, null);
        circle_image = v.findViewById(R.id.circle_image);
        tv_nick_name = v.findViewById(R.id.tv_nick_name);
        tv_male_age = v.findViewById(R.id.tv_male_age);
        tv_phone = v.findViewById(R.id.tv_phone);
        iv_sex = v.findViewById(R.id.iv_sex);
        return v;
    }

    private void initHeaderData() {
        if (myInfo != null) {
            Glide.with(context).load(myInfo.getPerson().getSelf_avatar_path()).into(circle_image);
            tv_nick_name.setText(myInfo.getPerson().getNick_name());
            if (TextUtils.equals(myInfo.getPerson().getGender(), "1")) {
                iv_sex.setImageResource(R.drawable.img_sex1);
            } else if (TextUtils.equals(myInfo.getPerson().getGender(), "2")) {
                iv_sex.setImageResource(R.drawable.img_sex2);
            } else {
                iv_sex.setVisibility(View.INVISIBLE);
            }

            if (myInfo.getPerson().getBirthday() != null) {
                Time time = new Time();
                time.setToNow();
                int age = time.year - Integer.valueOf(myInfo.getPerson().getBirthday().split("-")[0]);
                tv_male_age.setText(age + " 岁");
            }
            tv_phone.setText(myInfo.getPerson().getPhone_no());
        }
//        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.QUERY_USER_DYN_INFO_URL) + myInfo.getPerson().getId(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                RequestInfoBean requestInfoBean = new Gson().fromJson(s, RequestInfoBean.class);
//                if (requestInfoBean != null) {
//                    Data data = requestInfoBean.getData();
//                    if (data != null) {
//                        Glide.with(context).load(data.getPerson().getSelf_avatar_path()).into(circle_image);
//                        tv_nick_name.setText(data.getPerson().getNick_name());
//                        if (TextUtils.equals(data.getPerson().getGender(), "1")) {
//                            iv_sex.setImageResource(R.drawable.img_sex1);
//                        } else if (TextUtils.equals(data.getPerson().getGender(), "2")) {
//                            iv_sex.setImageResource(R.drawable.img_sex2);
//                        } else {
//                            iv_sex.setVisibility(View.INVISIBLE);
//                        }
//                        if (data.getPerson().getBirthday() != null) {
//                            Time time = new Time();
//                            time.setToNow();
//                            int age = time.year - Integer.valueOf(data.getPerson().getBirthday().split("-")[0]);
//                            tv_male_age.setText(age + " 岁");
//                        }
//                        tv_phone.setText(data.getPerson().getPhone_no());
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                ToastUtils.showToastShort("请检查手机网络！");
//            }
//        });
//        MyApplication.getHttpQueues().add(stringRequest);
    }

    class MotionDataAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            MotionDataAdapter.ViewHolder vh = null;
            if (convertView == null) {
                vh = new MotionDataAdapter.ViewHolder();
                convertView = View.inflate(context, R.layout.item_motion_data, null);
                vh.tv_title = convertView.findViewById(R.id.tv_title);
                vh.tv_time = convertView.findViewById(R.id.tv_time);
                vh.title_iv = convertView.findViewById(R.id.title_iv);
                vh.detail1_iv = convertView.findViewById(R.id.detail1_iv);
                vh.detail1_tv = convertView.findViewById(R.id.detail1_tv);
                vh.detail2_iv = convertView.findViewById(R.id.detail2_iv);
                vh.detail2_tv = convertView.findViewById(R.id.detail2_tv);
                vh.detail3_iv = convertView.findViewById(R.id.detail3_iv);
                vh.detail3_tv = convertView.findViewById(R.id.detail3_tv);
                vh.detail4_iv = convertView.findViewById(R.id.detail4_iv);
                vh.detail4_tv = convertView.findViewById(R.id.detail4_tv);
                vh.detail5_iv = convertView.findViewById(R.id.detail5_iv);
                vh.detail5_tv = convertView.findViewById(R.id.detail5_tv);
                vh.detail6_iv = convertView.findViewById(R.id.detail6_iv);
                vh.detail6_tv = convertView.findViewById(R.id.detail6_tv);
                vh.item_layout = convertView.findViewById(R.id.item_layout);
                vh.item_layout_cv = convertView.findViewById(R.id.item_layout_cv);
                convertView.setTag(vh);
            } else {
                vh = (MotionDataAdapter.ViewHolder) convertView.getTag();
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 124f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
            } else if (position == datalist.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
            }
            vh.item_layout_cv.setLayoutParams(layoutParams);
            vh.tv_title.setText(datalist.get(position).getName());
            vh.tv_time.setText("运动时间：" + TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(datalist.get(position).getStart_time(), "yyyy-MM-dd HH:mm:ss").toString(), "yyyy.MM.dd"));
            if (datalist.get(position).getSub_type() != null) {
                switch (datalist.get(position).getSub_type()) {//有氧
                    case "1":
                        vh.title_iv.setImageDrawable(getResources().getDrawable(R.drawable.treadmill_icon));
                        break;
                    case "2":
                        vh.title_iv.setImageDrawable(getResources().getDrawable(R.drawable.elliptical_machine_icon));
                        break;
                    case "3":
                        vh.title_iv.setImageDrawable(getResources().getDrawable(R.drawable.vertical_stationary_icon));
                        break;
                    case "4":
                        vh.title_iv.setImageDrawable(getResources().getDrawable(R.drawable.recumbent_cycle_icon));
                        break;
                    default://无氧
                        vh.title_iv.setImageDrawable(getResources().getDrawable(R.drawable.anaerobic_weightlifting));
                        break;
                }
            } else {
                vh.title_iv.setImageDrawable(getResources().getDrawable(R.drawable.anaerobic_weightlifting));
            }
            vh.detail1_tv.setText((Integer.valueOf(datalist.get(position).getTime()) / 60) + "分" + (Integer.valueOf(datalist.get(position).getTime()) % 60) + "秒");//运动时长 秒
            vh.detail3_tv.setText((Integer.valueOf(datalist.get(position).getCalorie()) / 1000) + "kcal");//卡路里
            vh.detail4_tv.setText(datalist.get(position).getHeart() + "bpm");//平均心率
            if (datalist.get(position).getSub_type() != null && (datalist.get(position).getSub_type().equals("1")
                    || datalist.get(position).getSub_type().equals("2")
                    || datalist.get(position).getSub_type().equals("3")
                    || datalist.get(position).getSub_type().equals("4"))) {//有氧
                float kmf = (Float.valueOf(datalist.get(position).getDistance())) / (float) 1000.00;//100步长  1000km
                DecimalFormat fnum = new DecimalFormat("##0.00");
                DecimalFormat fnum2 = new DecimalFormat("##0");
                String km = fnum.format(kmf);
                vh.detail2_tv.setText(km + "km");//运动距离 米
                vh.detail5_tv.setText(fnum2.format(Float.valueOf(datalist.get(position).getSpeed())) + "km/h");//平均速度
                if (datalist.get(position).getSub_type().equals("1")) {//跑步机
                    if (datalist.get(position).getIncline() != null) {
                        vh.detail6_tv.setText(datalist.get(position).getIncline() + "度");//平均坡度
                    } else {
                        vh.detail6_tv.setText("0度");
                    }
                } else {
                    if (datalist.get(position).getWatt() != null) {
                        vh.detail6_tv.setText(datalist.get(position).getWatt() + "watts");//瓦特
                    } else {
                        vh.detail6_tv.setText("0watts");
                    }
                }
                vh.detail2_iv.setImageDrawable(getResources().getDrawable(R.drawable.movement_distance_icon));
                vh.detail5_iv.setImageDrawable(getResources().getDrawable(R.drawable.average_velocity_icon));
                vh.detail6_iv.setImageDrawable(getResources().getDrawable(R.drawable.average_gradient_icon));
            } else {//无氧
                vh.detail2_tv.setText(datalist.get(position).getHeavy() + "kg");//平均重量 公斤
                vh.detail5_tv.setText((Integer.valueOf(datalist.get(position).getCooldown()) / 60) + "分" + (Integer.valueOf(datalist.get(position).getCooldown()) % 60) + "秒");//休息时间 秒
                vh.detail6_tv.setText(datalist.get(position).getTimes() + "次");//总次数
                vh.detail2_iv.setImageDrawable(getResources().getDrawable(R.drawable.movement_average_weight_icon));
                vh.detail5_iv.setImageDrawable(getResources().getDrawable(R.drawable.movement_half_time_icon));
                vh.detail6_iv.setImageDrawable(getResources().getDrawable(R.drawable.movement_total_icon));
            }

            vh.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            return convertView;
        }

        class ViewHolder {
            public TextView tv_title;
            public TextView tv_time;
            public ImageView title_iv;
            public ImageView detail1_iv;
            public TextView detail1_tv;
            public ImageView detail2_iv;
            public TextView detail2_tv;
            public ImageView detail3_iv;
            public TextView detail3_tv;
            public ImageView detail4_iv;
            public TextView detail4_tv;
            public ImageView detail5_iv;
            public TextView detail5_tv;
            public ImageView detail6_iv;
            public TextView detail6_tv;

            public LinearLayout item_layout;
            public CardView item_layout_cv;
        }
    }
}

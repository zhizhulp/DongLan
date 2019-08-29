package com.cn.danceland.myapplication.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.CourseActivity;
import com.cn.danceland.myapplication.activity.SmallTuankeDetailActivity;
import com.cn.danceland.myapplication.activity.TuanKeDetailActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.KeChengBiaoBean;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueConBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragmentEventBus;
import com.cn.danceland.myapplication.im.utils.TimeUtil;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by feng on 2018/1/11.
 */

public class TuanKeFragment extends BaseFragmentEventBus {
    FragmentManager fragmentManager;
    MyListView lv_tuanke;
    Gson gson = new Gson();
    Data info;
    String small;
    String from;
    int member_course_id;
    String yuyueStartTime, yuyueEndTime;

    boolean yuyue;
    MyAdapter myAdapter;
    List<KeChengBiaoBean.Data> xiaoTuanList;
    CourseActivity activity;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.tuanke, null);
        activity = (CourseActivity) getActivity();

//        try {
//            getData();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        rl_error = view.findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("店内还没有安排团课，请联系工作人员");
        lv_tuanke = view.findViewById(R.id.lv_tuanke);

//        myAdapter = new MyAdapter(xiaoTuanList);
//        lv_tuanke.setAdapter(myAdapter);
        lv_tuanke.setDividerHeight(0);
        //ListViewUtil.setListViewHeightBasedOnChildren(lv_tuanke);
        lv_tuanke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (xiaoTuanList != null) {
                    if ("小团课".equals(from)) {
                        startActivityForResult(new Intent(mActivity, SmallTuankeDetailActivity.class).putExtra("item", xiaoTuanList.get(position)).
                                putExtra("yuyueStartTime", yuyueStartTime).putExtra("member_course_id", member_course_id), 222);
                    } else {
                        if (xiaoTuanList.get(position).getSelf_appoint_count() == 0) {
                            xiaoTuanList.get(position).setSelf_appoint_count(self_appoint);
                        }
                        startActivityForResult(new Intent(mActivity, TuanKeDetailActivity.class).putExtra("groupId", xiaoTuanList.get(position).getId()).
                                putExtra("yuyueStartTime", yuyueStartTime).putExtra("item", xiaoTuanList.get(position)), 223);
                    }
                }
            }
        });


        return view;
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        LogUtil.i(event.getMsg());
        switch (event.getEventCode()) {
            case 4331:
//                initData(event.getMsg());
//                currentSelectDate=event.getMsg();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 222) {
            activity.showFragment("2", "0");
        } else if (resultCode == 223) {
            activity.showFragment("0", "0");
        }

    }

    int self_appoint;

    private void commitYuyue(KeChengBiaoBean.Data data, final RelativeLayout rl, final TextView tv) {
        if (data != null) {
            SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
            siJiaoYuYueConBean.setGroup_course_id(data.getId());
            if ("小团课".equals(from)) {
                siJiaoYuYueConBean.setMember_course_id(member_course_id);
            }
            siJiaoYuYueConBean.setCourse_type_id(data.getCourse_type_id());
            siJiaoYuYueConBean.setCourse_type_name(data.getCourse_type_name());
            siJiaoYuYueConBean.setDate(yuyueStartTime);

            siJiaoYuYueConBean.setStart_time(data.getStart_time());
            siJiaoYuYueConBean.setEnd_time(data.getEnd_time());
            String s = gson.toJson(siJiaoYuYueConBean);
            String url;
            if ("小团课".equals(from)) {
                url = Constants.plus(Constants.GROUPAPPOINT);
            } else {
                url = Constants.plus(Constants.FreeCourseApply);


            }

            if (TimeUtils.date2TimeStamp(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd") + 6 * 24 * 60 * 60 * 1000 < Long.valueOf(yuyueStartTime)) {

                ToastUtils.showToastShort("不能预约七日后的课程");
                return;
            }

            MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, url, s, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if (jsonObject.toString().contains("true")) {
                        if (!"小团课".equals(from)) {
                            self_appoint = 1;
                        }
                        rl.setClickable(false);
                        rl.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
                        tv.setTextColor(Color.WHITE);
                        tv.setText("已预约");
                        ToastUtils.showToastShort("预约成功！");
                    } else {
                        ToastUtils.showToastShort("预约失败！请重新预约！");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtils.showToastShort("预约失败！请重新预约！");
                }
            });
            MyApplication.getHttpQueues().add(jsonObjectRequest);
        }
    }

    public void refresh(String from, String startTime, String endTime, int course_type_id, int member_course_id) {
        this.member_course_id = member_course_id;
        this.from = from;
        yuyueStartTime = startTime;
        yuyueEndTime = endTime;
        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        if ("小团课".equals(from)) {
            siJiaoYuYueConBean.setCourse_type_id(course_type_id);
        }
        siJiaoYuYueConBean.setDate(startTime);
//        siJiaoYuYueConBean.setEnd_date(Long.valueOf(endTime));
        siJiaoYuYueConBean.setWeek(Integer.valueOf(TimeUtils.dateToWeek(TimeUtils.timeStamp2Date(startTime, "yyyy-MM-dd"))));

        String s = gson.toJson(siJiaoYuYueConBean);

        String url;
        if ("小团课".equals(from)) {
            url = Constants.plus(Constants.QUERYKECHENGBIAO);
        } else {
            url = Constants.plus(Constants.FreeCourse);//免费团课
        }

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, url, s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                KeChengBiaoBean keChengBiaoBean = gson.fromJson(jsonObject.toString(), KeChengBiaoBean.class);
                if (keChengBiaoBean != null) {
                    if (xiaoTuanList != null) {
                        xiaoTuanList.clear();
                    }
                    xiaoTuanList = keChengBiaoBean.getData();
                    lv_tuanke.setAdapter(new MyAdapter(xiaoTuanList));

                }
                lv_tuanke.setEmptyView(rl_error);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
                rl_error.setVisibility(View.VISIBLE);
                tv_error.setText("网络异常");
                Glide.with(mActivity).load(R.drawable.img_error7).into(iv_error);
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private class MyAdapter extends BaseAdapter {

        List<KeChengBiaoBean.Data> xiaoTuanList;

        MyAdapter(List<KeChengBiaoBean.Data> xiaoTuanList) {
            this.xiaoTuanList = xiaoTuanList;

        }


        @Override
        public int getCount() {
            return xiaoTuanList == null ? 0 : xiaoTuanList.size();

        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.tuanke_item, null);
                //    viewHolder.tuanke_img = convertView.findViewById(R.id.tuanke_img);
                viewHolder.tuanke_jibie = convertView.findViewById(R.id.tuanke_jibie);
                viewHolder.tuanke_leibie = convertView.findViewById(R.id.tuanke_leibie);
                viewHolder.tuanke_room = convertView.findViewById(R.id.tuanke_room);
                viewHolder.tuanke_name = convertView.findViewById(R.id.tuanke_name);
                viewHolder.tuanke_time = convertView.findViewById(R.id.tuanke_time);
                viewHolder.tuanke_yuyue = convertView.findViewById(R.id.tuanke_yuyue);
                viewHolder.tv_yuyue = convertView.findViewById(R.id.tv_yuyue);
                viewHolder.renshu = convertView.findViewById(R.id.renshu);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


//            background = (GradientDrawable)viewHolder.tuanke_yuyue.getBackground();
//            viewHolder.tuanke_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_white));

            //viewHolder.tuanke_yuyue.setBackgroundColor(Color.parseColor("#FF8C00"));
            viewHolder.tv_yuyue.setTextColor(getResources().getColor(R.color.white));
            viewHolder.tv_yuyue.setText("预约");
            String startTime, endTime;

            startTime = TimeUtils.MinuteToTime(Integer.valueOf(xiaoTuanList.get(position).getStart_time() + ""));

            endTime = TimeUtils.MinuteToTime(Integer.valueOf(xiaoTuanList.get(position).getEnd_time() + ""));
            viewHolder.tuanke_time.setText(startTime + "-" + endTime);
            viewHolder.tuanke_leibie.setText(xiaoTuanList.get(position).getCourse_type_name());
            viewHolder.tuanke_name.setText("上课教练:" + xiaoTuanList.get(position).getEmployee_name());

            if (xiaoTuanList.get(position).getLevel() != null) {
                viewHolder.tuanke_jibie.setText("课程级别:" + xiaoTuanList.get(position).getLevel());
            }
            if (xiaoTuanList.get(position).getRoom_name() != null) {
                viewHolder.tuanke_room.setText(xiaoTuanList.get(position).getRoom_name());
            } else {
                viewHolder.tuanke_room.setText("未知");
            }

            if (xiaoTuanList.get(position).getMax_count() == xiaoTuanList.get(position).getAppoint_count()) {
                //viewHolder.tuanke_yuyue.setBackgroundColor(Color.parseColor("#A9A9A9"));
                //viewHolder.tuanke_yuyue.setTextAppearance(mActivity,R.style.QuXiao);
                viewHolder.tuanke_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_white));
                viewHolder.tv_yuyue.setText("预约已满");
                viewHolder.tuanke_yuyue.setClickable(false);
            } else {
                viewHolder.tuanke_yuyue.setClickable(true);
            }


            if ("小团课".equals(from)) {
                viewHolder.renshu.setVisibility(View.VISIBLE);
                viewHolder.renshu.setText("人数(" + xiaoTuanList.get(position).getAppoint_count() + "/" + xiaoTuanList.get(position).getMax_count() + ")");
            } else {
                viewHolder.renshu.setVisibility(View.GONE);
            }
            //1553270400000 xiaoTuanList.get(position).getStart_time()*3600*1000
            String format = "yyyy-MM-dd";
            String dayStr = TimeUtils.timeStamp2Date(yuyueStartTime, format);//2019-3-22
            long hours = xiaoTuanList.get(position).getStart_time() * 60 * 1000;
            String hourStr = TimeUtils.timeToStr(hours, "HH:mm:ss");//14:00:00
            Long time = TimeUtils.date2TimeStamp(dayStr + " " + hourStr, "yyyy-MM-dd HH:mm:ss");

            if (time >= System.currentTimeMillis()) {
                if (xiaoTuanList.get(position).getSelf_appoint_count() > 0) {
                    yuyue = false;//有预约项，无法点击
                    //viewHolder.tuanke_yuyue.setBackgroundColor(Color.parseColor("#ADFF2F"));
                    //viewHolder.tuanke_yuyue.setTextAppearance(mActivity,R.style.YiYuYue);
                    viewHolder.tuanke_yuyue.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
                    viewHolder.tv_yuyue.setTextColor(getResources().getColor(R.color.white));
                    viewHolder.tv_yuyue.setText("已预约");
                } else {
                    yuyue = true;
                    viewHolder.tuanke_yuyue.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_sell_card));
                    viewHolder.tv_yuyue.setTextColor(getResources().getColor(R.color.color_white));
                    viewHolder.tv_yuyue.setText("预约");
                }
            } else {
//                yuyue = true;
//                viewHolder.tuanke_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_blue));
//                viewHolder.tv_yuyue.setTextColor(getResources().getColor(R.color.color_white));
//                viewHolder.tv_yuyue.setText("预约");
                viewHolder.tuanke_yuyue.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
                viewHolder.tv_yuyue.setTextColor(getResources().getColor(R.color.color_white));
                viewHolder.tv_yuyue.setText("已过期");
            }


            viewHolder.tuanke_yuyue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("小团课".equals(from)) {
                        startActivityForResult(new Intent(mActivity, SmallTuankeDetailActivity.class).putExtra("item", xiaoTuanList.get(position))
                                .putExtra("yuyueStartTime", yuyueStartTime).putExtra("member_course_id", member_course_id), 222);
                    } else {
                        if (Long.valueOf(yuyueStartTime) + xiaoTuanList.get(position).getStart_time() * 60000 >= System.currentTimeMillis()) {
                            if (xiaoTuanList.get(position).getSelf_appoint_count() == 0) {
                                commitYuyue(xiaoTuanList.get(position), viewHolder.tuanke_yuyue, viewHolder.tv_yuyue);
                            }
                        } else {
                            ToastUtils.showToastShort("已结束，无法预约");
                        }
                    }
                }
            });

//            if (StringUtils.isNullorEmpty(xiaoTuanList.get(position).getCover_img_url())) {
//                Glide.with(mActivity).load(R.drawable.sijiao_card).into(viewHolder.tuanke_img);
//            } else {
//                Glide.with(mActivity).load(xiaoTuanList.get(position).getCover_img_url()).into(viewHolder.tuanke_img);
//            }

            return convertView;
        }
    }

    class ViewHolder {
        ImageView tuanke_img;
        TextView tuanke_time, tuanke_leibie, tuanke_jibie, tuanke_room, tuanke_name, tv_yuyue, renshu;
        RelativeLayout tuanke_yuyue;

    }
}

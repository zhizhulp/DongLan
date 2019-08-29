package com.cn.danceland.myapplication.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueConBean;
import com.cn.danceland.myapplication.bean.TuanKeRecordBean;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/3/12.
 */

public class TuanKeRecordFragment extends BaseFragment {
    View view;
    Data data;
    Gson gson;
    ListView lv_tuanke;
    String startTime;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    @Override
    public View initViews() {

        view = View.inflate(mActivity, R.layout.tuankerecord, null);
        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        gson = new Gson();
        initView();
        //   initData();


        return view;
    }

    public void getStartTime(String startTime) {
        this.startTime = startTime;

    }

    @Override
    public void initData() {
        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        siJiaoYuYueConBean.setMember_no(data.getPerson().getMember_no());
        //siJiaoYuYueConBean.setDate(startTime);

        String s = gson.toJson(siJiaoYuYueConBean);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FREECOURSELIST), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.e("zzf", jsonObject.toString());
                TuanKeRecordBean tuanKeRecordBean = gson.fromJson(jsonObject.toString(), TuanKeRecordBean.class);
                if (tuanKeRecordBean != null) {

                    List<TuanKeRecordBean.Data> data = tuanKeRecordBean.getData();
                    if (data != null) {
                        lv_tuanke.setAdapter(new RecordAdapter(data));
                    } else {
                        ToastUtils.showToastShort("暂无记录！");
                    }
                } else {
                    ToastUtils.showToastShort("暂无记录！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("获取记录失败！");
            }
        }) {

        };
        MyApplication.getHttpQueues().add(jsonObjectRequest);


    }

    private void initView() {

        lv_tuanke = view.findViewById(R.id.lv_tuanke);
        rl_error = view.findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("您还没有预约团课");

        lv_tuanke.setEmptyView(rl_error);
    }

    @Override
    public void onClick(View v) {

    }


    private class RecordAdapter extends BaseAdapter {

        List<TuanKeRecordBean.Data> list;

        RecordAdapter(List<TuanKeRecordBean.Data> list) {
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
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
                convertView = View.inflate(mActivity, R.layout.sijiaorecord_item, null);
                viewHolder.course_name = convertView.findViewById(R.id.course_name);
                viewHolder.course_date = convertView.findViewById(R.id.course_date);
                viewHolder.course_num = convertView.findViewById(R.id.course_num);
                viewHolder.course_type = convertView.findViewById(R.id.course_type);
                viewHolder.course_jiaolian = convertView.findViewById(R.id.course_jiaolian);
                viewHolder.rl_button = convertView.findViewById(R.id.rl_button);
                viewHolder.rl_button_tv = convertView.findViewById(R.id.rl_button_tv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.course_name.setText(list.get(position).getCourse_type_name());
//            LogUtil.i(list.get(position).getDate() + "   "list.get(position).getCreate_date());
            String time = TimeUtils.timeStamp2Date(list.get(position).getCreate_date() + "", "yyyy.MM.dd HH:mm");
            viewHolder.course_date.setText("预约时间：" + time);
            viewHolder.course_type.setText("免费团课");
            viewHolder.course_jiaolian.setText("");
            viewHolder.course_num.setText("上课场地：" + list.get(position).getRoom_name());
            TuanKeRecordBean.Data item = list.get(position);
            final int status = item.getStatus();//1已报名2已取消
            long date = item.getDate();
            String format = "yyyy-MM-dd";
            String dayStr = TimeUtils.timeStamp2Date(date + "", format);//2019-3-22
            long hours = item.getStart_time() * 60 * 1000;
            String hourStr = TimeUtils.timeToStr(hours, "HH:mm:ss");//14:00:00
            final Long breakTime = TimeUtils.date2TimeStamp(dayStr + " " + hourStr, "yyyy-MM-dd HH:mm:ss");
            if (status == 1 && breakTime < System.currentTimeMillis()) {
                viewHolder.rl_button_tv.setText("已过期");
                viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
            }
            if (list.get(position).getStatus() == 2) {
                viewHolder.rl_button_tv.setText("已取消");
                viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
            }
            viewHolder.rl_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (list.get(position).getStatus() != 2) {
//                        viewHolder.rl_button.setVisibility(View.VISIBLE);
//                        showDialog(list.get(position).getId(), viewHolder.rl_button, viewHolder.rl_button_tv);
//                    } else {
//                        viewHolder.rl_button.setVisibility(View.GONE);
//                    }
                    if (status == 1 && breakTime >= System.currentTimeMillis())
                        showDialog(list.get(position).getId(), viewHolder.rl_button, viewHolder.rl_button_tv);
                }

            });
            return convertView;
        }
    }

    class ViewHolder {
        TextView course_name, course_date, course_type, course_jiaolian, rl_button_tv, course_num;
        RelativeLayout rl_button;
    }

    private void showDialog(final int id, final RelativeLayout rl, final TextView tv) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle("提示");
        dialog.setMessage("确定取消预约吗");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancleYuYue(id, rl, tv);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }


    private void cancleYuYue(final int id, final RelativeLayout rl, final TextView tv) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FREECANCELGROUP), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.e("zzf", s);
                if (s.contains("1")) {
                    ToastUtils.showToastShort("取消成功！");
                    tv.setText("已取消");
                    tv.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
                    rl.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                    rl.setClickable(false);
                } else {
                    ToastUtils.showToastShort("取消失败！请重新操作");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("applyId", id + "");
                return map;
            }

        };

        MyApplication.getHttpQueues().add(stringRequest);
    }


}

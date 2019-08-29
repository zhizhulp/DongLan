package com.cn.danceland.myapplication.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.cn.danceland.myapplication.bean.JiaoLianSiJiaoRecordBean;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueConBean;
import com.cn.danceland.myapplication.bean.YuYueResultBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragmentEventBus;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cn.danceland.myapplication.R.id.course_num;
import static com.cn.danceland.myapplication.R.id.rl_button_tv;

/**
 * 教练预约记录
 */

public class JiaolianSiJiaoRecordFragment extends BaseFragmentEventBus {

    MyListView lv_tuanke;
    View inflate;
    Data data;
    Gson gson;
    String startTime, role, auth;
    //GradientDrawable background;
    List< JiaoLianSiJiaoRecordBean.Data> contentList;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;
    String currentSelectDate;//当前选择日期
    MyAdapter recordAdapter;
    @Override
    public View initViews() {

        inflate = View.inflate(mActivity, R.layout.jiaolianyuyue, null);//界面类似，使用团课列表布局
        rl_error = inflate.findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("您还没有预约私教");


        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        contentList = new ArrayList<>();
        recordAdapter=new MyAdapter(contentList);
        gson = new Gson();
        lv_tuanke = inflate.findViewById(R.id.lv_mylistview);
        lv_tuanke.setAdapter(recordAdapter);
        lv_tuanke.setEmptyView(rl_error);
        currentSelectDate=getArguments().getString("currentSelectDate");
        LogUtil.i(currentSelectDate);
        initData(currentSelectDate);


        return inflate;
    }


    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        LogUtil.i(event.getMsg());
        switch (event.getEventCode()) {
            case 4331:
                initData(event.getMsg());
                currentSelectDate=event.getMsg();
                break;
            default:
                break;
        }
    }


    public void getStartTime(String startTime) {
        this.startTime = startTime;

    }


    public void getRoles(String role, String auth) {
        this.role = role;
        this.auth = auth;

    }
//
//    private void getGroupData(String startTime) {
//
//        SiJiaoYuYueConBean xiaotuankejilubean = new SiJiaoYuYueConBean();
//        if (role != null) {
//            xiaotuankejilubean.setEmployee_id(data.getEmployee().getId());
//        } else {
//            xiaotuankejilubean.setMember_no(data.getPerson().getMember_no());
//        }
//        if (startTime != null) {
//            xiaotuankejilubean.setDate(TimeUtils.date2TimeStamp(startTime,"yyyy-MM-dd")+"");
//        } else {
//            xiaotuankejilubean.setDate(System.currentTimeMillis() + "");
//        }
//
//        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FINDGROUPCOURSEAPPOINTLIST),
//                gson.toJson(xiaotuankejilubean).toString(), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                //if("2".equals(course_category)){
//                LogUtil.i(jsonObject.toString());
//                GroupRecordBean groupRecordBean = gson.fromJson(jsonObject.toString(), GroupRecordBean.class);
//                if (groupRecordBean != null) {
//                    List<GroupRecordBean.Data> data = groupRecordBean.getData();
//                    if (contentList == null) {
//                        contentList = new ArrayList<>();
//                    }
//                    if (data != null) {
//                        for (int i = 0; i < data.size(); i++) {
//                            SiJiaoRecordBean.Content content = new SiJiaoRecordBean().new Content();
//                            content.setId(data.get(i).getId());
//                            content.setCourse_type_name(data.get(i).getCourse_type_name());
//                            content.setCourse_date(Long.valueOf(data.get(i).getDate()));
//                            content.setEmployee_name(data.get(i).getEmployee_name());
//                            content.setCategory("2");
//                            content.setStatus(Integer.valueOf(data.get(i).getStatus()));
//                            content.setMember_name(data.get(i).getMember_name());
//                            content.setStart_time((int) data.get(i).getStart_time());
//                            content.setCount( data.get(i).getCount());
//                            //   LogUtil.i( data.get(i).getStart_time()+"");
//                            contentList.add(content);
//
//                        }
//                        if (contentList != null) {
//                            recordAdapter=new RecordAdapter(contentList);
//                            lv_tuanke.setAdapter(recordAdapter);
//                        } else {
//                            ToastUtils.showToastShort("当天无预约记录");
//                        }
//                    }
//                }
//                //}
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                LogUtil.e("zzf", volleyError.toString());
//            }
//        });
//        MyApplication.getHttpQueues().add(jsonObjectRequest);
//    }

    private void initData(final String startTime) {

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
 //       if (role != null) {
            siJiaoYuYueConBean.setEmployee_id(data.getEmployee().getId());
//        } else {
//            siJiaoYuYueConBean.setMember_no(data.getPerson().getMember_no());
//        }

        if (startTime != null) {
            siJiaoYuYueConBean.setCourse_date(TimeUtils.date2TimeStamp(startTime,"yyyy-MM-dd")+"");
        } else {
            siJiaoYuYueConBean.setCourse_date(System.currentTimeMillis() +"");
        }


        //siJiaoYuYueConBean.setEmployee_id(32);
        final String s = gson.toJson(siJiaoYuYueConBean);


        LogUtil.i(s);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_TEACH_COURSELIST), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
             //   LogUtil.e(jsonObject.toString());
                LogUtil.i(jsonObject.toString());
                if (contentList == null) {
                    contentList = new ArrayList<>();
                } else {
                    contentList.clear();
                }

                JiaoLianSiJiaoRecordBean siJiaoRecordBean = gson.fromJson(jsonObject.toString(), JiaoLianSiJiaoRecordBean.class);
                if (siJiaoRecordBean != null) {

                    if (siJiaoRecordBean.getData() != null) {
                        contentList =siJiaoRecordBean.getData();
                        LogUtil.i(contentList.size()+"");
                      //  lv_tuanke.setAdapter(recordAdapter);
                        recordAdapter.setdata(contentList);
                       recordAdapter.notifyDataSetChanged();
                    }
                }

              //  getGroupData(startTime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }



    @Override
    public void onClick(View v) {

    }




    private class MyAdapter extends BaseAdapter {

        List<JiaoLianSiJiaoRecordBean.Data> list;

        MyAdapter(List<JiaoLianSiJiaoRecordBean.Data> list) {
            this.list = list;
            LogUtil.i(list.toString());

        }
        public void setdata(List<JiaoLianSiJiaoRecordBean.Data> list){
            this.list = list;
        }
        @Override
        public int getCount() {
            LogUtil.i(list.size()+"");
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LogUtil.i("getview");
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.sijiaorecord_item, null);
                viewHolder.course_name = convertView.findViewById(R.id.course_name);
                viewHolder.course_date = convertView.findViewById(R.id.course_date);
                viewHolder.course_type = convertView.findViewById(R.id.course_type);
                viewHolder.course_jiaolian = convertView.findViewById(R.id.course_jiaolian);
                viewHolder.rl_button = convertView.findViewById(R.id.rl_button);
                viewHolder.rl_button_tv = convertView.findViewById(rl_button_tv);
                viewHolder.rl_qiandao = convertView.findViewById(R.id.rl_qiandao);
                viewHolder.rl_qiandao_tv = convertView.findViewById(R.id.rl_qiandao_tv);
                viewHolder.tv_ok = convertView.findViewById(R.id.tv_ok);
                viewHolder.course_num = convertView.findViewById(course_num);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LogUtil.i(list.get(position).getCourse_type_name());

            viewHolder.course_name.setText(list.get(position).getCourse_type_name());
            String time = TimeUtils.timeStamp2Date(list.get(position).getCourse_date() + "", null);
            String start_time;

            if (list.get(position).getStart_time() % 60 == 0) {
                start_time = list.get(position).getStart_time() / 60 + ":00";
            } else {
                start_time = list.get(position).getStart_time() / 60 + ":" + list.get(position).getStart_time() % 60;
            }
            viewHolder.course_date.setText("预约时间：" + time.split(" ")[0] + " " + start_time);

            switch(list.get(position).getCourse_category()){
            case 1:
                viewHolder.course_type.setText("单人私教");
                viewHolder.course_num.setText("购买节数:" +list.get(position).getCount()+"节");
                viewHolder.course_jiaolian.setText("上课会员:" + list.get(position).getMember_name());
            break;
            case 2:
                viewHolder.course_type.setText("团体私教");
                viewHolder.course_num.setText("上课人数:" +list.get(position).getAppoint_count()+"人");
                viewHolder.course_jiaolian.setText("上课教练:" + list.get(position).getEmployee_name());
            break;
            case 3:
                viewHolder.course_type.setText("免费团课");
                viewHolder.course_num.setText("上课人数:" +list.get(position).getCount()+"人");
                viewHolder.course_jiaolian.setText("上课教练:" + list.get(position).getEmployee_name());
                viewHolder.tv_ok.setVisibility(View.GONE);
                viewHolder.rl_qiandao.setVisibility(View.GONE);
                     viewHolder.rl_button.setVisibility(View.GONE);
            break;

            default:
            break;
            }





            viewHolder.tv_ok.setVisibility(View.GONE);
            viewHolder.rl_qiandao.setVisibility(View.GONE);
            viewHolder.rl_button.setVisibility(View.GONE);

            if (list.get(position).getAppointment_type()==2){//会员岳教练
                switch (list.get(position).getStatus()) {
                    case 1:
                        viewHolder.tv_ok.setVisibility(View.VISIBLE);
                        viewHolder.tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog(true, list.get(position).getId(), position);
                            }
                        });

                        viewHolder.rl_button.setVisibility(View.VISIBLE);
                        viewHolder.rl_button_tv.setText("取消");
                        viewHolder.rl_button_tv.setTextColor(getResources().getColor(R.color.white));
                        viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_sell_card));
                        viewHolder.rl_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog(false, list.get(position).getId(), position);
                            }
                        });

                        break;

                    case 2:
                        viewHolder.rl_qiandao.setVisibility(View.VISIBLE);
                        viewHolder.rl_button.setVisibility(View.GONE);
                        viewHolder.tv_ok.setVisibility(View.GONE);

                            viewHolder.rl_qiandao_tv.setText("签到");
                            viewHolder.rl_qiandao_tv.setTextColor(getResources().getColor(R.color.white));
                            viewHolder.rl_qiandao.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));

                        viewHolder.rl_qiandao.setOnClickListener(null);
                        break;

                    case 3:
                        viewHolder.tv_ok.setVisibility(View.GONE);
                        viewHolder.rl_qiandao.setVisibility(View.GONE);
                        viewHolder.rl_button.setVisibility(View.VISIBLE);
                        viewHolder.rl_button_tv.setText("已取消");
                        viewHolder.rl_button_tv.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
                        viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                        viewHolder.rl_button.setOnClickListener(null);
                        break;

                    case 4:
                        viewHolder.tv_ok.setVisibility(View.GONE);
                        viewHolder.rl_qiandao.setVisibility(View.GONE);
                        viewHolder.rl_button.setVisibility(View.VISIBLE);
                        viewHolder.rl_button.setOnClickListener(null);
                        viewHolder.rl_button_tv.setText("已签到");
                        viewHolder.rl_button_tv.setTextColor(getResources().getColor(R.color.white));
                        viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
                        break;

                    default:
                        break;
                }
            }else if (list.get(position).getAppointment_type()==1){//教练约会员
                switch (list.get(position).getStatus()) {
                    case 1:
//                        viewHolder.tv_ok.setVisibility(View.VISIBLE);
//                        viewHolder.tv_ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                showDialog(true, list.get(position).getId(), position);
//                            }
//                        });

                        viewHolder.rl_button.setVisibility(View.VISIBLE);
                        viewHolder.rl_button_tv.setText("取消");
                        viewHolder.rl_button_tv.setTextColor(getResources().getColor(R.color.white));
                        viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_sell_card));
                        viewHolder.rl_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog(false, list.get(position).getId(), position);
                            }
                        });

                        break;

                    case 2:
                        viewHolder.rl_qiandao.setVisibility(View.VISIBLE);
                        viewHolder.rl_button.setVisibility(View.GONE);
                        viewHolder.tv_ok.setVisibility(View.GONE);

                        viewHolder.rl_qiandao_tv.setText("签到");
                        viewHolder.rl_qiandao_tv.setTextColor(getResources().getColor(R.color.white));
                        viewHolder.rl_qiandao.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));

                        viewHolder.rl_qiandao.setOnClickListener(null);
                        break;

                    case 3:
                        viewHolder.tv_ok.setVisibility(View.GONE);
                        viewHolder.rl_qiandao.setVisibility(View.GONE);
                        viewHolder.rl_button.setVisibility(View.VISIBLE);
                        viewHolder.rl_button_tv.setText("已取消");
                        viewHolder.rl_button_tv.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
                        viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                        viewHolder.rl_button.setOnClickListener(null);
                        break;

                    case 4:
                        viewHolder.tv_ok.setVisibility(View.GONE);
                        viewHolder.rl_qiandao.setVisibility(View.GONE);
                        viewHolder.rl_button.setVisibility(View.VISIBLE);
                        viewHolder.rl_button.setOnClickListener(null);
                        viewHolder.rl_button_tv.setText("已签到");
                        viewHolder.rl_button_tv.setTextColor(getResources().getColor(R.color.white));
                        viewHolder.rl_button.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
                        break;

                    default:
                        break;
                }

            }
            return convertView;
        }
    }

    class ViewHolder {
        TextView course_name, course_date, course_type, course_jiaolian, rl_button_tv, rl_qiandao_tv, tv_ok, course_num;
        RelativeLayout rl_button, rl_qiandao;
    }

    private void showCancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("取消预约请联系教练");
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    private void enterYuYue(final String id, final int pos) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.ENTERCOURSE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.e("zzf", s);
                YuYueResultBean yuYueResultBean = gson.fromJson(s, YuYueResultBean.class);
                if (yuYueResultBean != null && yuYueResultBean.getData() > 0) {
                    ToastUtils.showToastShort("确认成功！");
                    initData(currentSelectDate);

//                    tv.setText("签到");
//                    tv.setTextColor(getResources().getColor(R.color.white));
//                    tv.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
//                    tv.setEnabled(false);
                } else {
                    ToastUtils.showToastShort("确认失败！请稍后再试");
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
                map.put("courseAppointId", id + "");
                if (role != null && !"".equals(role)) {
                    map.put("confirmType", "1");
                } else {
                    map.put("confirmType", "2");
                }
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);

    }

    private void cancelGroup(final int id, final int pos) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.GROUPCOURSEAPPOINTCANCEL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                YuYueResultBean yuYueResultBean = gson.fromJson(s, YuYueResultBean.class);
                if (yuYueResultBean != null && yuYueResultBean.getData() > 0) {
                    ToastUtils.showToastShort("取消成功！");
                    initData(currentSelectDate);
//                    rl1.setVisibility(View.GONE);
//                    tv.setText("已取消");
//                    tv.setTextColor(getResources().getColor(R.color.white));
//                    rl.setBackground(getResources().getDrawable(R.drawable.btn_bg_gray));
//                    rl.setClickable(false);
                } else {
                    ToastUtils.showToastShort("取消失败！请重新操作");
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
                HashMap<String, String> map = new HashMap<>();
                map.put("groupCourseAppointId", id + "");
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void showCancelGroup(final int id, final int pos) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle("提示");
        dialog.setMessage("确定取消预约吗");

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelGroup(id,pos);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }

    private void showDialog(final boolean enter, final String id, final int pos) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
        dialog.setTitle("提示");
        if (enter) {
            dialog.setMessage("确定上课吗");
        } else {
            dialog.setMessage("确定取消预约吗");
        }
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (enter) {
                    enterYuYue(id, pos);
                } else {
                    cancleYuYue(id, pos);
                }
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();

    }


    private void cancleYuYue(final String id, final int pos) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.APPOINTCANCEL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i( s);
                YuYueResultBean yuYueResultBean = gson.fromJson(s, YuYueResultBean.class);
                if (yuYueResultBean != null && yuYueResultBean.getData() > 0) {
                    ToastUtils.showToastShort("取消成功！");
                    initData(currentSelectDate);

//                    tv.setText("已取消");
//                    tv.setTextColor(getResources().getColor(R.color.white));
//                    rl.setBackground(getResources().getDrawable(R.drawable.btn_bg_gray));
//                    rl.setClickable(false);
                } else {
                    ToastUtils.showToastShort("取消失败！请重新操作");
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
                HashMap<String, String> map = new HashMap<>();
                map.put("courseAppointId", id + "");
                return map;
            }

        };

        MyApplication.getHttpQueues().add(stringRequest);
    }
}

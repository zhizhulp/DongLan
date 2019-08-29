package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.PotentialInfo;
import com.cn.danceland.myapplication.bean.RequsetSimpleBean;
import com.cn.danceland.myapplication.bean.RequsetUpcomingMaterParamBean;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by shy on 2018/1/11 19:59
 * Email:644563767@qq.com
 * 添加待办事项
 */


public class AddUpcomingMatterActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_type;
    private ContainsEmojiEditText et_content;
    private Gson gson = new Gson();
    private ListPopup listPopup;
    private RequsetBean requsetBean = new RequsetBean();
    private String time;
    PotentialInfo info;
    private TextView tv_time;
    String customer_type;
    private TextView tv_date;
    private String date;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_upcoming_matter);
        initView();
    }

    private void initView() {
        listPopup = new ListPopup(this);
        Bundle bundle = getIntent().getExtras();
        requsetBean.member_id = bundle.getString("id");
        requsetBean.person_id = bundle.getString("person_id");
        requsetBean.member_name = bundle.getString("member_name");
        requsetBean.member_no = bundle.getString("member_no");
        customer_type = bundle.getString("auth");
        requsetBean.customer_type = customer_type;
        requsetBean.auth = customer_type;
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_type = findViewById(R.id.tv_type);
        tv_type.setOnClickListener(this);
        tv_time = findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        tv_date = findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);

        et_content = findViewById(R.id.et_content);
        findViewById(R.id.dlbtn_commit).setOnClickListener(this);
        getDate();
        //LogUtil.i(SPUtils.getString("role_type",""));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.dlbtn_commit://保存


                if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
                    ToastUtils.showToastShort("待办内容不能为空");
                    return;
                }
                if (TextUtils.isEmpty(requsetBean.work_type_id)) {
                    ToastUtils.showToastShort("请选择待办类型");
                    return;
                }
                if (et_content.getText().toString().length() > 200) {
                    ToastUtils.showToastShort("输入文字数量:" + et_content.getText().toString().length() + "，超过上限");
                    return;
                }
                requsetBean.content = et_content.getText().toString();

                if (TextUtils.equals(tv_date.getText().toString(), "请选择时间") ) {
               //     requsetBean.warn_time = tv_date.getText().toString() ;
                    ToastUtils.showToastShort("请选择待办时间");
                    return;
                }

                requsetBean.role_type = SPUtils.getString("role_type", "");

                try {
                    add_upcoming_matter(gson.toJson(requsetBean).toString());
                    LogUtil.i(gson.toJson(requsetBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tv_type:
                findParams(customer_type);
                break;
            case R.id.tv_time:

                //  show_select_time_dialog();

                break;
            case R.id.tv_date:
                showDate();
                //   show_select_date_dialog();
                break;
            default:
                break;
        }
    }


    private void showDate() {

        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, "请选择时间");
        customDateAndTimePicker.showWindow();
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                String dateString = customDateAndTimePicker.getTimeString();
                tv_date.setText(dateString);

                requsetBean.warn_time = dateString.replace("年", "-").replace("月", "-").replace("日", " ").replace("时", ":").replace("分", ":00");
            }
        });

    }
//    private void show_select_time_dialog() {
//        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                //   LogUtil.i(i+"点"+i1+"分");
//                tv_time.setText(i + ":" + i1);
//            }
//        }, 0, 0, false);
//        dialog.show();
//    }

    private Calendar cal;
    private int year, month, day;

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);       //获取年月日时分秒

        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);
    }
//
//    private void show_select_date_dialog() {
//
//        DatePickerDialog dialog = new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year1, int month2, int day3) {
//                tv_date.setText(year1 + "-" + (month2 + 1) + "-" + day3);
//                String startDate = tv_date.getText().toString();
//                if (!TimeUtils.isDateOneBigger(year + "-" + (month + 1) + "-" + day, tv_date.getText().toString())) {
//
//                } else {
//                    tv_date.setText("请选择日期");
//                    ToastUtils.showToastShort("不能选择今天以前的日期");
//                }
//
//            }
//        }, year, month, day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
//        dialog.show();
//    }

    class RequsetBean {

        private String person_id;// 人员ID
        public String member_id;// 会员ID
        public String member_no;// 会员编号
        public String member_name;// 会员姓名
        public String customer_type;
        public String title;// 类型
        public String content;// 工作内容
        public String warn_time;// 提醒时间
        public String auth;
        public String role_type;
        public String work_type_id;


    }

    /**
     * 添加待辦
     *
     * @param data
     * @throws JSONException
     */
    public void add_upcoming_matter(final String data) throws JSONException {


        JSONObject jsonObject = new JSONObject(data);

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.ADD_UPCOMING_MATTER), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequsetSimpleBean requestOrderBean = gson.fromJson(jsonObject.toString(), RequsetSimpleBean.class);
                if (requestOrderBean.isSuccess()) {
                    ToastUtils.showToastShort("待办事项添加成功");
                    EventBus.getDefault().post(new IntEvent(0, 151));
                    finish();
                } else {
                    ToastUtils.showToastShort("待办事项添加失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.networkResponse.statusCode + "");
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);

    }

    private List<RequsetUpcomingMaterParamBean.Data> mParamInfoList = new ArrayList<>();

    private void findParams(final String customer_type) {
        RequsetBean requsetBean = new RequsetBean();
        requsetBean.customer_type = customer_type;
        requsetBean.role_type = SPUtils.getString("role_type", "1");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(requsetBean).toString());
            LogUtil.i(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_UPCOMING_MATTER_PARAM), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());

                RequsetUpcomingMaterParamBean paramInfoBean = gson.fromJson(jsonObject.toString(), RequsetUpcomingMaterParamBean.class);
                if (paramInfoBean.getSuccess()) {
                    mParamInfoList = paramInfoBean.getData();

                    listPopup.showPopupWindow();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                //  ToastUtils.showToastShort(volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }


    class ListPopup extends BasePopupWindow {


        Context context;

        public ListPopup(Context context) {
            super(context);
            ListView popup_list = (ListView) findViewById(R.id.popup_list);
            MyPopupListAdapter myPopupListAdapter = new MyPopupListAdapter(context);
            popup_list.setAdapter(myPopupListAdapter);
            this.context = context;
        }

        @Override
        protected Animation initShowAnimation() {
            return null;
        }

        @Override
        public View getClickToDismissView() {
            return getPopupWindowView();
        }

        @Override
        public View onCreatePopupView() {

            //  popupView=View.inflate(context,R.layout.popup_list_consultant,null);
            return createPopupById(R.layout.popup_list_potential);

        }

        @Override
        public View initAnimaView() {
            return null;
        }

        class MyPopupListAdapter extends BaseAdapter {
            private Context context;

            public MyPopupListAdapter(Context context) {
                this.context = context;
            }

            @Override
            public int getCount() {
                return mParamInfoList.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup viewGroup) {
                //     LogUtil.i("asdasdjalsdllasjdlk");
                ViewHolder vh = null;
                if (convertView == null) {
                    vh = new ViewHolder();
                    convertView = View.inflate(context, R.layout.listview_item_list_consultant, null);
                    vh.mTextView = (TextView) convertView.findViewById(R.id.item_tx);
                    convertView.setTag(vh);
                } else {
                    vh = (ViewHolder) convertView.getTag();
                }
                vh.mTextView.setText(mParamInfoList.get(position).getName());

                vh.mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        tv_type.setText(mParamInfoList.get(position).getName());
                        //    requsetBean.title = mParamInfoList.get(position).getName();
                        requsetBean.work_type_id = mParamInfoList.get(position).getId();
                        listPopup.dismiss();
                    }
                });

                return convertView;

            }


            class ViewHolder {
                public TextView mTextView;
            }
        }
    }

}

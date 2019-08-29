package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RootBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feng on 2017/9/28.
 */

public class RegisterInfoActivity extends BaseActivity {

    String strSex = "1", strBirthday = "1990-12-10", strHeight = "170", strWeight = "55";
    TextView text_birthday, text_height, text_name, text_male, text_female, text_weight,
            button, tv_start, over_time;
    PopupWindow mPopWindow;
    //ListView list_year,list_date,list_height;
    SimpleAdapter mSchedule;
    MyAdapter arrayAdapter;
    //View contentView;
    View inflate, inflate1;
    private final static int DATE_DIALOG = 0;
    private Calendar c = null;
    RequestQueue requestQueue;
    String id, strName, gender = "1", syear, smonth, sdate;//性别:1、男，2、女，3、未知，4、保密
    Data mData;
    Gson gson;
    AlertDialog.Builder alertdialog;
    LoopView loopview, lp_year, lp_month, lp_date;
    int year;
    String isleapyear;
    int daysByYearMonth;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                LogUtil.i(strName);
                FriendshipManagerPresenter.setMyNick(strName, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        LogUtil.i("昵称修改失败"+i);
                    }

                    @Override
                    public void onSuccess() {

                        LogUtil.i("昵称修改成功");
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        initHost();
        intiView();
        setClick();

    }

    private void initHost() {
        Time time = new Time();
        time.setToNow();
        year = time.year;

        gson = new Gson();
        requestQueue = Volley.newRequestQueue(RegisterInfoActivity.this);

        mData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        id = SPUtils.getString(Constants.MY_USERID, null);

    }

    private void showDate() {
        ViewGroup parent = (ViewGroup) inflate1.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        final ArrayList<String> yearList = new ArrayList<String>();
        final ArrayList<String> monthList = new ArrayList<String>();
        final ArrayList<String> dateList = new ArrayList<String>();
        int n = 1900;
        int len = year - n;
        for (int i = 0; i <= len; i++) {
            yearList.add((n + i) + "");
        }
        for (int j = 0; j < 12; j++) {
            monthList.add((1 + j) + "");
        }
        lp_year.setNotLoop();
        lp_date.setNotLoop();
        lp_month.setNotLoop();
        lp_year.setItems(yearList);
        lp_month.setItems(monthList);

        lp_year.setInitPosition(yearList.size() - 20);
        syear = yearList.get(yearList.size() - 20);
        lp_month.setInitPosition(0);
        smonth = monthList.get(0);
        sdate = "1";

        daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
        dateList.clear();
        for (int z = 1; z <= daysByYearMonth; z++) {
            dateList.add(z + "");
        }
        lp_date.setItems(dateList);

        //设置字体大小
        lp_year.setTextSize(16);
        lp_month.setTextSize(16);
        lp_date.setTextSize(16);

        lp_year.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                syear = yearList.get(index);
                daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                dateList.clear();
                for (int z = 1; z <= daysByYearMonth; z++) {
                    dateList.add(z + "");
                }
                lp_date.setItems(dateList);
            }
        });

        lp_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                smonth = monthList.get(index);
                daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                dateList.clear();
                for (int z = 1; z <= daysByYearMonth; z++) {
                    dateList.add(z + "");
                }
                lp_date.setItems(dateList);
            }
        });

        lp_date.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sdate = dateList.get(index);
            }
        });

        alertdialog.setTitle("选择出生年月");
        alertdialog.setView(inflate1);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text_birthday.setText(syear + "年" + smonth + "月" + sdate + "日");
                strBirthday = syear + "-" + smonth + "-" + sdate;
            }
        });
        alertdialog.show();

    }

    public void intiView() {
        inflate = LayoutInflater.from(RegisterInfoActivity.this).inflate(R.layout.timeselect, null);
        inflate1 = LayoutInflater.from(RegisterInfoActivity.this).inflate(R.layout.birthdayselect, null);
        tv_start = inflate.findViewById(R.id.tv_start);
        tv_start.setVisibility(View.GONE);
        loopview = inflate.findViewById(R.id.loopview);
        over_time = inflate.findViewById(R.id.over_time);
        over_time.setVisibility(View.GONE);

        lp_year = inflate1.findViewById(R.id.lp_year);
        lp_month = inflate1.findViewById(R.id.lp_month);
        lp_date = inflate1.findViewById(R.id.lp_date);

        alertdialog = new AlertDialog.Builder(RegisterInfoActivity.this);

        //contentView = LayoutInflater.from(RegisterInfoActivity.this).inflate(R.layout.selectorwindowsingle,null);
//        mPopWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        list_year = contentView.findViewById(R.id.list_year);
//        list_date = contentView.findViewById(R.id.list_date);
//        list_height = contentView.findViewById(R.id.list_height);
//        cancel_action = contentView.findViewById(R.id.cancel_action);
//        over = contentView.findViewById(R.id.over);
//        selecttitle = contentView.findViewById(R.id.selecttitle);
        button = findViewById(R.id.button);
        text_birthday = findViewById(R.id.text_birthday);
        text_height = findViewById(R.id.text_height);
        text_weight = findViewById(R.id.text_weight);

        text_male = findViewById(R.id.text_male);
        text_female = findViewById(R.id.text_female);
        button = findViewById(R.id.button);
        text_name = findViewById(R.id.text_name);
        if (mData.getPerson().getNick_name() != null && mData.getPerson().getNick_name().length() > 0) {
            text_name.setText(mData.getPerson().getNick_name());
            strName = mData.getPerson().getNick_name().toString();
        }
        if (TextUtils.equals(mData.getPerson().getGender(),"1")){
            text_male.setBackgroundResource(R.drawable.male_blue);
            text_female.setBackgroundResource(R.drawable.female_gray);
            strSex = "男";
            gender = "1";
        }
        if (TextUtils.equals(mData.getPerson().getGender(),"2")){
            text_male.setBackgroundResource(R.drawable.male_gray);
            text_female.setBackgroundResource(R.drawable.female_blue);
            strSex = "女";
            gender = "2";
        }
    }

    public void setClick() {
        text_birthday.setOnClickListener(onclick);
        //cancel_action.setOnClickListener(onclick);
        text_height.setOnClickListener(onclick);
        text_name.setOnClickListener(onclick);
        text_male.setOnClickListener(onclick);
        text_female.setOnClickListener(onclick);
        //over.setOnClickListener(onclick);
        text_weight.setOnClickListener(onclick);
        button.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int v = view.getId();
            switch (v) {
                case R.id.text_birthday: {
                    showDate();
//                    c = Calendar.getInstance();
//                    new DatePickerDialog(RegisterInfoActivity.this,new DatePickerDialog.OnDateSetListener() {
//                        public void onDateSet(DatePicker dp, int year,int month, int dayOfMonth) {
//                            //et.setText("您选择了：" + year + "年" + (month+1) + "月" + dayOfMonth + "日");
//                            text_birthday.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
//                            strBirthday = year+"-"+(month+1)+"-"+dayOfMonth;
//                        }
//                    }, c.get(Calendar.YEAR), // 传入年份
//                            c.get(Calendar.MONTH), // 传入月份
//                            c.get(Calendar.DAY_OF_MONTH) // 传入天数
//                    ).show();
                    //showSelectorWindow();
                }
                break;
                case R.id.text_height:
                    //showSelectorWindow(0);
                    //selecttitle.setText("选择身高");
                    showWH(0);
                    break;
                case R.id.text_weight:
                    //showSelectorWindow(1);
                    //selecttitle.setText("选择体重");
                    showWH(1);
                    break;
                case R.id.text_name:
                    //  text_name.setText("");
                    showName(text_name.getText().toString());
                    break;
                case R.id.cancel_action: {
                    dismissWindow();
                }
                break;
                case R.id.over:
                    dismissWindow();
                    break;
                case R.id.text_male:
                    text_male.setBackgroundResource(R.drawable.male_blue);
                    text_female.setBackgroundResource(R.drawable.female_gray);
                    strSex = "男";
                    gender = "1";
                    break;
                case R.id.text_female:
                    text_male.setBackgroundResource(R.drawable.male_gray);
                    text_female.setBackgroundResource(R.drawable.female_blue);
                    strSex = "女";
                    gender = "2";
                    break;
                case R.id.button:
                    if (strName == null || strName.equals("")) {
                        ToastUtils.showToastShort("请输入昵称");
                    } else {
                        commit();

                    }
                    break;
            }

        }
    };

    private void showWH(final int j) {
        int n;
        final ArrayList<String> arrayList = new ArrayList<String>();
        ViewGroup parent = (ViewGroup) inflate.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        if (j == 0) {
            for (int i = 30; i < 300; i++) {
                arrayList.add(i + "");
            }
        } else {
            for (int y = 10; y < 500; y++) {
                arrayList.add(y + "");
            }
        }
        loopview.setNotLoop();
        loopview.setItems(arrayList);
        //设置初始位置
        if (j == 0) {
            loopview.setInitPosition(arrayList.size() / 2);
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                if ("60".equals(arrayList.get(i))) {
                    loopview.setInitPosition(i);
                }
            }
        }

        loopview.setTextSize(18);
        loopview.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (j == 0) {
                    text_height.setText(arrayList.get(index) + " cm");
                } else {
                    text_weight.setText(arrayList.get(index) + " kg");
                }
            }
        });
        if (j == 0) {
            alertdialog.setTitle("选择身高");
        } else {
            alertdialog.setTitle("选择体重");
        }

        alertdialog.setView(inflate);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.show();
    }

    public void showName(String hint) {

        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(RegisterInfoActivity.this);
        View dialogView = LayoutInflater.from(RegisterInfoActivity.this)
                .inflate(R.layout.edit_name, null);
        //normalDialog.setTitle("编辑昵称");
        final TextView edit_name = dialogView.findViewById(R.id.edit_name);
        // edit_name.setText(hint);
        normalDialog.setView(dialogView);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        strName = edit_name.getText().toString();
                        text_name.setText(strName);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();

    }

//    public void showSelectorWindow(int x){
//        final int j = x;
//        mPopWindow.setContentView(contentView);
//        //显示PopupWindow
//        View rootview = LayoutInflater.from(RegisterInfoActivity.this).inflate(R.layout.activity_register_info, null);
//        String[] str  = new String[71];
//        Integer[] str1 = new Integer[165];
//        final ArrayList<String> arHeight = new ArrayList<String>();
//        int n;
//        if(j==0){
//            for(int i = 0;i<71;i++){
//                n = 150+i;
//                str[i] = n+"";
//            }
//            Arrays.sort(str);
//            for(int z = 0;z<str.length;z++){
//                arHeight.add(str[z]);
//            }
//        }else {
//            for(int y=0;y<165;y++){
//                n = 35+y;
//                str1[y] = n;
//            }
//            Arrays.sort(str1);
//            for(int z = 0;z<str1.length;z++){
//                arHeight.add(str1[z]+"");
//            }
//        }

//        arrayAdapter = new MyAdapter(arHeight,this);
//        list_height.setAdapter(arrayAdapter);
//
//        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//        mPopWindow.setAnimationStyle(R.style.selectorMenuAnim);
//
//        list_height.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if(j==0){
//                    text_height.setText(arHeight.get(i)+" cm");
//                    strHeight = arHeight.get(i);
//                }else{
//                    text_weight.setText(arHeight.get(i)+" kg");
//                    strWeight = arHeight.get(i);
//                }
//            }
//        });
//
//    }

    public void dismissWindow() {
        if (null != mPopWindow && mPopWindow.isShowing()) {
            mPopWindow.dismiss();
        }
    }

    public class MyAdapter extends BaseAdapter {

        ArrayList<String> arrayList;
        LayoutInflater inflater = null;

        public MyAdapter(ArrayList<String> list, Context context) {
            arrayList = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView item_text = null;
            if (view == null) {
                view = inflater.inflate(R.layout.selector_item, null);
            }
            item_text = view.findViewById(R.id.item_text);
            item_text.setText(arrayList.get(i));
            return view;
        }
    }

    public void commit() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SET_BASE_USERINFO_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RootBean rootBean = gson.fromJson(s, RootBean.class);
                if ("true".equals(rootBean.success)) {
                    ToastUtils.showToastShort("提交成功！");
                    mData.getPerson().setBirthday(strBirthday);
                    mData.getPerson().setNick_name(strName);
                    mData.getPerson().setHeight(strHeight);
                    mData.getPerson().setWeight(strWeight);
                    mData.getPerson().setGender(gender);
                    DataInfoCache.saveOneCache(mData, Constants.MY_INFO);
                    EventBus.getDefault().post(new StringEvent("", 1010));
                    handler.sendEmptyMessage(1);


                    Intent intent = new Intent(RegisterInfoActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    ToastUtils.showToastShort("提交失败！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("提交失败！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", id);
                hashMap.put("nickName", strName);
                hashMap.put("gender", gender);
                hashMap.put("height", strHeight);
                hashMap.put("weight", strWeight);
                hashMap.put("birthday", strBirthday);
                //LogUtil.e("zzf",hashMap.toString());
                return hashMap;
            }

        };
        requestQueue.add(stringRequest);

    }
}

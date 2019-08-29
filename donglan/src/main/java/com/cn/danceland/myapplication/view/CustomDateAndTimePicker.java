package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by feng on 2018/4/20.
 * 日期和时间选择
 */

public class CustomDateAndTimePicker extends AlertDialog {
    View inflate1, inflate;
    LoopView loopview, lp_year, lp_month, lp_date, lp_hour, lp_minute;
    AlertDialog.Builder alertdialog;
    String title;
    private OnClickEnter onClickEnter;
    int maxMonth, maxDate;
    private final TextView tv_hour;
    private final TextView tv_minute;
    private Time time;
    private final TextView tv_year;
    private final TextView tv_mounth;
    private final TextView tv_date;
    private String CurrentDate;
    ArrayList<String> yearList;
    ArrayList<String> monthList;
    ArrayList<String> dateList;

    public CustomDateAndTimePicker(Context context, String title) {
        super(context);
        this.title = title;
        inflate1 = LayoutInflater.from(context).inflate(R.layout.datepicker, null);
        lp_year = inflate1.findViewById(R.id.lp_year);
        tv_year = inflate1.findViewById(R.id.tv_year);
        lp_month = inflate1.findViewById(R.id.lp_month);
        tv_mounth = inflate1.findViewById(R.id.tv_mounth);
        lp_date = inflate1.findViewById(R.id.lp_date);
        tv_date = inflate1.findViewById(R.id.tv_date);
        lp_hour = inflate1.findViewById(R.id.lp_hour);
        lp_minute = inflate1.findViewById(R.id.lp_minute);
        tv_hour = inflate1.findViewById(R.id.tv_hour);
        tv_minute = inflate1.findViewById(R.id.tv_minute);
        alertdialog = new AlertDialog.Builder(context);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickEnter.onClick();
            }
        });
    }

    public CustomDateAndTimePicker(Context context, String title, String date) {
        super(context);
        this.CurrentDate = date;
        this.title = title;
        inflate1 = LayoutInflater.from(context).inflate(R.layout.datepicker, null);
        lp_year = inflate1.findViewById(R.id.lp_year);
        tv_year = inflate1.findViewById(R.id.tv_year);
        lp_month = inflate1.findViewById(R.id.lp_month);
        tv_mounth = inflate1.findViewById(R.id.tv_mounth);
        lp_date = inflate1.findViewById(R.id.lp_date);
        tv_date = inflate1.findViewById(R.id.tv_date);
        lp_hour = inflate1.findViewById(R.id.lp_hour);
        lp_minute = inflate1.findViewById(R.id.lp_minute);
        tv_hour = inflate1.findViewById(R.id.tv_hour);
        tv_minute = inflate1.findViewById(R.id.tv_minute);
        alertdialog = new AlertDialog.Builder(context);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickEnter.onClick();
            }
        });
    }


    public interface OnClickEnter {
        public void onClick();
    }

    public void setDialogOnClickListener(OnClickEnter onClickEnter) {

        this.onClickEnter = onClickEnter;
    }

    public void setGoneHourAndMinute() {
        lp_hour.setVisibility(View.GONE);
        lp_minute.setVisibility(View.GONE);
        tv_hour.setVisibility(View.GONE);
        tv_minute.setVisibility(View.GONE);
    }

    public void setGoneYearAndMounth() {
        lp_year.setVisibility(View.GONE);
        lp_month.setVisibility(View.GONE);
        lp_date.setVisibility(View.GONE);

        tv_year.setVisibility(View.GONE);
        tv_mounth.setVisibility(View.GONE);
        tv_date.setVisibility(View.GONE);
    }

    public void setMax(int month, int date) {
        maxMonth = month;
        maxDate = date;
    }


    String syear, smonth, sdate, shour, sminute, timeString, dateString;
    int daysByYearMonth;

    private void showDate() {
        time = new Time();
        time.setToNow();
        final int year = time.year + 10;
        ViewGroup parent = (ViewGroup) inflate1.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        yearList = new ArrayList<String>();
        monthList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        int n = 1900;
        int len = year - n;

        for (int i = 0; i <= len; i++) {
            yearList.add((n + i) + "年");
        }
        for (int j = 0; j < 12; j++) {
            if ((1 + j) < 10) {
                monthList.add("0" + (1 + j) + "月");
            } else {
                monthList.add((1 + j) + "月");
            }

        }
        lp_year.setNotLoop();
        lp_date.setNotLoop();
        lp_month.setNotLoop();
        lp_year.setItems(yearList);
        lp_month.setItems(monthList);

        lp_year.setInitPosition(yearList.size() - 11);
        syear = yearList.get(yearList.size() - 11).replace("年", "");
        lp_month.setInitPosition(0);
        smonth = monthList.get(0).replace("月", "");
        sdate = "1";

        daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
        dateList.clear();
        for (int z = 1; z <= daysByYearMonth; z++) {
            if (z < 10) {
                dateList.add("0" + z + "日");
            } else {
                dateList.add(z + "日");
            }

        }
        //   LogUtil.e(dateList.toString());
        lp_date.setItems(dateList);

        //设置字体大小
        lp_year.setTextSize(18);

        lp_month.setTextSize(18);
        lp_date.setTextSize(18);
//        lp_year.setCenterTextColor(Color.parseColor("#333333"));
//        lp_month.setCenterTextColor(Color.parseColor("#333333"));
//        lp_date.setCenterTextColor(Color.parseColor("#333333"));
        lp_year.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_month.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_date.setOuterTextColor(Color.parseColor("#6d819c"));
//        lp_year.setLineSpacingMultiplier(2f);
//        lp_month.setLineSpacingMultiplier(2f);
//        lp_date.setLineSpacingMultiplier(2f);
        lp_year.setItemsVisibleCount(5);
        lp_month.setItemsVisibleCount(5);
        lp_date.setItemsVisibleCount(5);
        lp_year.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                syear = yearList.get(index).replace("年", "");
                daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                dateList.clear();
                for (int z = 1; z <= daysByYearMonth; z++) {
                    if (z < 10) {
                        dateList.add("0" + z + "日");
                    } else {
                        dateList.add(z + "日");
                    }
                }
                lp_date.setItems(dateList);
            }
        });

        lp_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                smonth = monthList.get(index).replace("月", "");
                daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                dateList.clear();
                for (int z = 1; z <= daysByYearMonth; z++) {
                    if (z < 10) {
                        dateList.add("0" + z + "日");
                    } else {
                        dateList.add(z + "日");
                    }
                }
                lp_date.setItems(dateList);
            }
        });

        lp_date.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sdate = dateList.get(index).replace("日", "");
            }
        });


        final ArrayList<String> hourList = new ArrayList<String>();
        final ArrayList<String> minuteList = new ArrayList<String>();

        for (int x = 0; x < 24; x++) {
            if (x < 10) {
                hourList.add("0" + x + "时");
            } else {
                hourList.add(x + "时");
            }
        }
        for (int y = 0; y < 60; y++) {
            if (y < 10) {
                minuteList.add("0" + y + "分");
            } else {
                minuteList.add(y + "分");
            }

        }
//        LogUtil.i(hourList.toString());
//        LogUtil.i(minuteList.toString());
        lp_hour.setItems(hourList);
        lp_minute.setItems(minuteList);
        shour = time.hour + "";
        if (shour.length() == 1) {
            shour = "0" + time.hour;
        }
        for (int i = 0; i < hourList.size(); i++) {
            if (shour.equals(hourList.get(i).replace("时", ""))) {
                lp_hour.setInitPosition(i);
            }
        }
        sminute = time.minute + "";
        if (sminute.length() == 1) {
            sminute = "0" + time.minute;
        }
        for (int i = 0; i < minuteList.size(); i++) {
            if (sminute.equals(minuteList.get(i).replace("分", ""))) {
                lp_minute.setInitPosition(i);
            }
        }

        lp_hour.setTextSize(18);
        lp_hour.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_minute.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_minute.setTextSize(18);
        lp_hour.setItemsVisibleCount(7);
        lp_minute.setItemsVisibleCount(7);
        lp_year.setItemsVisibleCount(7);
        lp_month.setItemsVisibleCount(7);
        lp_date.setItemsVisibleCount(7);

        //sminute = minuteList.get(30);

        lp_hour.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                shour = hourList.get(index).replace("时", "");
            }
        });
        lp_minute.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sminute = minuteList.get(index).replace("分", "");
            }
        });

        if (CurrentDate!=null){
            setdate(CurrentDate);
        }


        alertdialog.setTitle(title);
        alertdialog.setView(inflate1);
        //alertdialog.setPositiveButton("确定", onClickListener);
        alertdialog.show();

    }

    /**
     * 设置小时 分钟的picker
     *
     * @param lookHour   小时
     * @param lookMinute 分钟
     */
    private void showDate(int lookHour, int lookMinute) {
        time = new Time();
        time.setToNow();
        time.hour = lookHour;
        time.minute = lookMinute;

        final int year = time.year;
        ViewGroup parent = (ViewGroup) inflate1.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        yearList = new ArrayList<String>();
        monthList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        int n = 1900;
        int len = year - n;
        for (int i = 0; i <= len; i++) {
            yearList.add((n + i) + "");
        }
        if (maxMonth == 0) {
            for (int j = 0; j < 12; j++) {
                monthList.add((1 + j) + "");
            }
        } else {
            for (int j = 0; j < (time.month + 1); j++) {
                monthList.add((1 + j) + "");
            }
        }

        lp_year.setNotLoop();
        lp_date.setNotLoop();
        lp_month.setNotLoop();
        lp_year.setItems(yearList);
        lp_month.setItems(monthList);

        syear = year + "";
        smonth = (time.month + 1) + "";
        sdate = time.monthDay + "";

        for (int i = 0; i < yearList.size(); i++) {
            if (syear.equals(yearList.get(i))) {
                lp_year.setInitPosition(i);
            }
        }

        for (int i = 0; i < monthList.size(); i++) {
            if (smonth.equals(monthList.get(i))) {
                lp_month.setInitPosition(i);
            }
        }


        if (maxDate == 0) {
            daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
            dateList.clear();
            for (int z = 1; z <= daysByYearMonth; z++) {
                dateList.add(z + "");
            }
        } else {
            for (int z = 1; z <= Integer.valueOf(sdate); z++) {
                dateList.add(z + "");
            }
        }

        lp_date.setItems(dateList);

        for (int i = 0; i < dateList.size(); i++) {
            if (sdate.equals(dateList.get(i))) {
                lp_date.setInitPosition(i);
            }
        }


        //设置字体大小
        lp_year.setTextSize(16);
        lp_month.setTextSize(16);
        lp_date.setTextSize(16);

        lp_year.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                monthList.clear();
                if (maxMonth == 0) {
                    for (int j = 0; j < 12; j++) {
                        monthList.add((1 + j) + "");
                    }
                } else {
                    if (year != Integer.valueOf(yearList.get(index))) {
                        for (int j = 0; j < 12; j++) {
                            monthList.add((1 + j) + "");
                        }
                    } else {
                        for (int j = 0; j < maxMonth; j++) {
                            monthList.add((1 + j) + "");
                        }
                    }
                }

                lp_month.setItems(monthList);
                syear = yearList.get(index);
                dateList.clear();
                if (maxDate == 0) {
                    daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                    for (int z = 1; z <= daysByYearMonth; z++) {
                        dateList.add(z + "");
                    }
                } else {
                    if ((time.month + 1) == Integer.valueOf(smonth) && time.year == Integer.valueOf(syear)) {
                        for (int z = 1; z <= maxDate; z++) {
                            dateList.add(z + "");
                        }
                    } else {
                        daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                        for (int z = 1; z <= daysByYearMonth; z++) {
                            dateList.add(z + "");
                        }
                    }
                }
                lp_date.setItems(dateList);
            }
        });

        lp_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                smonth = monthList.get(index);
                dateList.clear();
                if (maxDate == 0) {
                    daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                    for (int z = 1; z <= daysByYearMonth; z++) {
                        dateList.add(z + "");
                    }
                } else {
                    if ((time.month + 1) == Integer.valueOf(smonth) && time.year == Integer.valueOf(syear)) {
                        for (int z = 1; z <= maxDate; z++) {
                            dateList.add(z + "");
                        }
                    } else {
                        daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                        for (int z = 1; z <= daysByYearMonth; z++) {
                            dateList.add(z + "");
                        }
                    }
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

        final ArrayList<String> hourList = new ArrayList<String>();
        final ArrayList<String> minuteList = new ArrayList<String>();

        for (int x = 0; x < 24; x++) {
            if (x < 10) {
                hourList.add("0" + x + "时");
            } else {
                hourList.add(x + "" + "时");
            }
        }
        for (int y = 0; y < 60; y++) {
            if (y < 10) {
                minuteList.add("0" + y + "分");
            } else {
                minuteList.add(y + "" + "分");
            }

        }
        lp_hour.setItems(hourList);
        lp_minute.setItems(minuteList);
        shour = time.hour + "";
        if (shour.length() == 1) {
            shour = "0" + time.hour;
        }
        for (int i = 0; i < hourList.size(); i++) {
            if (shour.equals(hourList.get(i).replace("时", ""))) {
                lp_hour.setInitPosition(i);
            }
        }
        sminute = time.minute + "";
        if (sminute.length() == 1) {
            sminute = "0" + time.minute;
        }
        for (int i = 0; i < minuteList.size(); i++) {
            if (sminute.equals(minuteList.get(i).replace("分", ""))) {
                lp_minute.setInitPosition(i);
            }
        }

        lp_hour.setTextSize(18);
        lp_minute.setTextSize(18);
        lp_hour.setItemsVisibleCount(7);
        lp_minute.setItemsVisibleCount(7);
        lp_year.setItemsVisibleCount(7);
        lp_month.setItemsVisibleCount(7);
        lp_date.setItemsVisibleCount(7);

        //sminute = minuteList.get(30);

        lp_hour.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                shour = hourList.get(index).replace("时", "");
            }
        });
        lp_minute.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sminute = minuteList.get(index).replace("分", "");
            }
        });


        if (CurrentDate!=null){
            setdate(CurrentDate);
        }

        alertdialog.setTitle(title);
        alertdialog.setView(inflate1);
        //alertdialog.setPositiveButton("确定", onClickListener);
        alertdialog.show();

    }

    private void setdate(String date) {
        String[] dates = date.split("-");

        int yearpos = yearList.indexOf(dates[0] + "年");
        lp_year.setCurrentPosition(yearpos);
        syear = yearList.get(yearpos).replace("年", "");

        int monthpos = monthList.indexOf(dates[1] + "月");
        lp_month.setCurrentPosition(monthpos);
        smonth = monthList.get(monthpos).replace("月", "");
        int datepos = dateList.indexOf(dates[2] + "日");
        lp_date.setCurrentPosition(datepos);
        //    LogUtil.e(datepos+"");
        sdate = dateList.get(datepos).replace("日", "");
    }

    public String getYear() {

        return syear;
    }

    public String getMonth() {
        return smonth;

    }

    public String getDay() {

        return sdate;
    }

    public String getHour() {
        return shour;
    }

    public String getMinute() {
        return sminute;

    }


    public String getDateString() {

        dateString = syear + "年" + smonth + "月" + sdate + "日";
        return dateString;
    }

    public String getDateStringF() {
        if (Integer.valueOf(smonth) < 10 && Integer.valueOf(sdate) >= 10) {
            dateString = syear + "-0" + Integer.valueOf(smonth) + "-" + sdate;
        } else if (Integer.valueOf(sdate) < 10 && Integer.valueOf(smonth) >= 10) {
            dateString = syear + "-" + smonth + "-0" + Integer.valueOf(sdate);
        } else if (Integer.valueOf(sdate) < 10 && Integer.valueOf(smonth) < 10) {
            dateString = syear + "-0" + Integer.valueOf(smonth) + "-0" + Integer.valueOf(sdate);
        } else {
            dateString = syear + "-" + smonth + "-" + sdate;
        }
        return dateString;
    }


    public String getTimeString() {
        timeString = syear + "年" + smonth + "月" + sdate + "日" + shour + "时" + sminute + "分";
        return timeString;

    }

    public String getHorizongtal() {
        return timeString = syear + "-" + smonth + "-" + sdate;
    }

    public String getTime() {
        return shour + ":" + sminute;
    }

    public void showWindow() {
        showDate();
    }

    public void showWindow(int lookHour, int lookMinute) {
        showDate(lookHour, lookMinute);
    }
}

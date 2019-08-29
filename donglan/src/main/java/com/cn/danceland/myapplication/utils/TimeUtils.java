package com.cn.danceland.myapplication.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.necer.ncalendar.utils.MyLog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


/**
 * Created by shy on 2017/11/27 14:35
 * Email:644563767@qq.com
 * 处理时间和日期的工具类
 */

@SuppressLint("WrongConstant")
public class TimeUtils {
    private static SimpleDateFormat DATE_FORMAT_TILL_SECOND = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat DATE_FORMAT_TILL_DAY_CURRENT_YEAR = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat DATE_FORMAT_TILL_DAY_CH = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 日期字符串转换为Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date strToDate(String dateStr, String format) {
        Date date = null;

        if (!TextUtils.isEmpty(dateStr)) {
            DateFormat df = new SimpleDateFormat(format);
            try {
                date = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 日期转换为字符串
     *
     * @param timeStr
     * @param format
     * @return
     */
    public static String dateToString(String timeStr, String format) {
        // 判断是否是今年
        Date date = TimeUtils.strToDate(timeStr, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 如果是今年的话，才去“xx月xx日”日期格式
        if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            //   LogUtil.i(Calendar.getInstance().get(Calendar.YEAR)+"");
            return DATE_FORMAT_TILL_DAY_CURRENT_YEAR.format(date);
        }

        return DATE_FORMAT_TILL_DAY_CH.format(date);
    }

    //毫秒转日期
    public static String millToDate(long time3) {
        Date date2 = new Date();
        date2.setTime(time3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date2);
    }


    public static int getAgeByBirth(String birthday) throws ParseException {
        // 格式化传入的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse(birthday);
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date()); // 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(parse); // 传入的时间

            //如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 日期逻辑
     *
     * @param dateStr 日期字符串
     * @return
     */
    public static String timeLogic(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        long now = calendar.getTimeInMillis();
        Date date = strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        calendar.setTime(date);
        long past = calendar.getTimeInMillis();

        // 相差的秒数
        long time = (now - past) / 1000;

        StringBuffer sb = new StringBuffer();
        if (time < 60) { // 1小时内
            //  return sb.append(time + "秒前").toString();
            return sb.append("刚刚").toString();
        } else if (time > 60 && time < 3600) {
            return sb.append(time / 60 + "分钟前").toString();
        } else if (time >= 3600 && time < 3600 * 24) {
            return sb.append(time / 3600 + "小时前").toString();
        } else if (time >= 3600 * 24 && time < 3600 * 48) {
            return sb.append("昨天").toString();
        } else if (time >= 3600 * 48 && time < 3600 * 72) {
            return sb.append("前天").toString();
        } else if (time >= 3600 * 72) {
            return dateToString(dateStr, "yyyy-MM-dd HH:mm:ss");
        }
        return dateToString(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 计算剩余时间
     *
     * @param orderTime
     * @return
     */
    public static String leftTime(String orderTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        long now = calendar.getTimeInMillis();
        Date date = strToDate(orderTime, "yyyy-MM-dd HH:mm:ss");
        calendar.setTime(date);
        long end = calendar.getTimeInMillis() + 24 * 60 * 60 * 1000;

        // 相差的秒数
        long time = (end - now) / 1000;
        StringBuffer sb = new StringBuffer();
        if (end < now) {
            return "支付超时";
        } else {
            return sb.append(time / 3600 + "小时" + (time % 3600) / 60 + "分钟").toString();

        }
    }


    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 厘米转换成米
     */
    public static String convertMi(String limi) {
        DecimalFormat df2 = new DecimalFormat("###.0");
        return df2.format(Float.valueOf(limi) / 100d);
    }


    // 将字符串转为时间戳
    //string为字符串的日期格式，如："yyyy年MM月dd日HH时mm分ss秒"

    /**
     * 22      * 日期格式字符串转换成时间戳
     * 23      * @param date 字符串日期
     * 24      * @param format 如：yyyy-MM-dd HH:mm:ss
     * 25      * @return
     * 26
     */
    public static Long date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return sdf.parse(date_str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    //毫秒转字符串
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static String timeToStr(long ms, String format){
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter= new SimpleDateFormat(format,Locale.CHINESE);

        // 设置格式化器的时区为格林威治时区，否则格式化的结果不对，中国的时间比格林威治时间早8小时，比如0点会被格式化为8:00
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return formatter.format(ms);
    }

    //毫秒转凌晨整点
    public static long timeToTopHour(long time) {
        Date date = new Date();
        date.setTime(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String dateToWeek2(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * date转calendar
     */
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String dateToString(Date date) {
        // 获得SimpleDateFormat类
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        //打印当前时间
        return sf.format(date);
    }

    /**
     * 获取月初的毫秒时间戳
     */
    public static long getMonthFirstDay(Calendar calendar) {
        //Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH + 1, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取月末的毫秒时间戳
     */
    public static long getMonthLastDay(Calendar calendar) {
        //Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置本月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * start
     * 本周开始时间戳 - 以星期一为本周的第一天
     */
    public static String getWeekStartTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        return simpleDateFormat.format(cal.getTime()) + " 00:00:00";
    }

    /**
     * end
     * 本周结束时间戳 - 以星期一为本周的第一天
     */
    public static String getWeekEndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        return simpleDateFormat.format(cal.getTime()) + " 23:59:59";
    }

    /**
     * 根据分钟数获取时间字符串24小时制
     */
    public static String MinuteToTime(int minute) {
        String time = null;
        if (minute % 60 == 0) {
            time = minute / 60 + ":00";
        } else {
            if (minute % 60 < 10) {
                time = minute / 60 + ":0" + minute % 60;
            } else {
                time = minute / 60 + ":" + minute % 60;
            }
        }
        return time;
    }


    /**
     * 时间戳
     *
     * @param segmentation 分割  如：手环折线图 5分钟一累加
     * @param date
     * @return
     */
    public static List<Date> getSegmentationTime(int segmentation, Date date) {
        Date start = dayStartDate(date);//转换为天的起始date
        Date nextDayDate = nextDay(start);//下一天的date

        List<Date> result = new ArrayList<Date>();
        while (start.compareTo(nextDayDate) < 0) {
            result.add(start);
            //日期加15分钟
            start = addFiveMin(start, segmentation);
        }

        return result;
    }

    private static Date addFiveMin(Date start, int offset) {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.MINUTE, offset);
        return c.getTime();
    }

    private static Date nextDay(Date start) {
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private static Date dayStartDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 当天的起始时间
     *
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 当天的结束时间
     *
     * @return
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 59);
        return todayEnd.getTime();
    }

    /**
     * 时间戳  如：手环折线图
     * 上个月所有的日期
     *
     * @return 61天日期List 天  算本天
     */
    public static ArrayList<String> getLastMonthDayData() {
        long temp = getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), 8);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(temp));
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1); //向后推移一天
            Date date = calendar.getTime();
            results.add(calendar.getTime() + "");
        }
//        long temp = getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), 8);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date(temp));
//        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  //获得当前日期所在月份有多少天（或者说day的最大值)，用于后面的计算
//        calendar.set(Calendar.DAY_OF_MONTH, 1); //由于是获取当月日期信息，所以直接操作当月Calendar即可。将日期调为当月第一天
//        ArrayList<String> results = new ArrayList<>();
//        for (int i = 0; i < 60; i++) {
//            Date date = calendar.getTime();
//            results.add(format.format(date).toString());
//            calendar.add(Calendar.DAY_OF_MONTH, 1); //向后推移一天
//        }
        return results;
    }

    /**
     * 时间戳  如：手环折线图
     *
     * @param format SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * @return 本月日期List 天
     */
    public static ArrayList<String> getDayData(SimpleDateFormat format) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);  //获得当前日期所在月份有多少天（或者说day的最大值)，用于后面的计算
        c.set(Calendar.DAY_OF_MONTH, 1); //由于是获取当月日期信息，所以直接操作当月Calendar即可。将日期调为当月第一天
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < maxDay; i++) {
            Date date = c.getTime();
            results.add(format.format(date).toString());
            c.add(Calendar.DAY_OF_MONTH, 1); //向后推移一天
        }
        return results;
    }

    /**
     * 本周起始截止时间  如：手环折线图
     * <p>
     * format      SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     *
     * @param currentTime System.currentTimeMillis();//当前时间戳
     * @return 本月日期List 周
     */
    public static String getWeekData(long currentTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        String results = System.currentTimeMillis() + "";
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        //当前星期几
        int mWay = c.get(Calendar.DAY_OF_WEEK);

        //一天的时间戳天数___减去礼拜几,,获取礼拜一是几号
        long weekStart = currentTime - ((1000 * 60 * 60 * 24) * (mWay - 1));//礼拜天
        long weekStop = currentTime + ((1000 * 60 * 60 * 24) * (7 - mWay));//礼拜六
//        String startTime = format.format(weekStart).toString();
//        String stopTime = format.format(weekStop).toString();
        results = weekStart + "&" + weekStop;
        return results;
    }

    /**
     * 多周起始截止时间
     *
     * @param count 多少周
     *              format      SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * @return
     */
    public static ArrayList<String> getWeekListData(int count) {
        ArrayList<String> results = new ArrayList<>();
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int day = c.get(Calendar.DAY_OF_MONTH); // 需要更改的天数
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                day = c.get(Calendar.DAY_OF_MONTH) - 7;
            }
            c.set(Calendar.DAY_OF_MONTH, day);
            long currentTime = c.getTimeInMillis();
            String weekDataStr = getWeekData(currentTime);
            results.add(weekDataStr);
        }
        Collections.reverse(results);
        return results;
    }

    /**
     * 获取阶段日期 整点的小时、分钟、秒  格式如：yyyy-MM-dd + 00:00:00:00
     *
     * @param dateType 使用方法 char datetype = '7';
     * @author Yangtse
     */
    public static long getPeriodTopDate(SimpleDateFormat format, int dateType) {
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int hour; // 需要更改的小时
        int day; // 需要更改的天数
        switch (dateType) {
            case 0: // 1小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 1;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case 1: // 2小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 2;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case 2: // 3小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 3;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case 3: // 6小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 6;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case 4: // 12小时前
                hour = c.get(Calendar.HOUR_OF_DAY) - 12;
                c.set(Calendar.HOUR_OF_DAY, hour);
                break;
            case 5: // 一天前
                day = c.get(Calendar.DAY_OF_MONTH) - 1;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case 6: // 一星期前
                day = c.get(Calendar.DAY_OF_MONTH) - 7;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case 7: // 一个月前 System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
                day = c.get(Calendar.DAY_OF_MONTH) - 30;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
            case 8://60天前 手环心率用到   加上本天
                day = c.get(Calendar.DAY_OF_MONTH) - 60;
                c.set(Calendar.DAY_OF_MONTH, day);
                break;
        }
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder strForwardDate = new StringBuilder().append(mYear).append(
                (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(
                (mDay < 10) ? "0" + mDay : mDay);
        LogUtil.i("strDate------->" + strForwardDate + "-" + format.format(c.getTime()) + "-" + c.getTimeInMillis());
        return c.getTimeInMillis();
    }

    /**
     * 是否过了这个时间
     *
     * @param year
     * @param month
     * @param date
     * @param hour
     * @param minute
     * @return
     */
    public static boolean isAfterToday(int year, int month, int date, int hour, int minute) {

        Date date1 = new Date(year, month, date, hour, minute);
        Calendar c = Calendar.getInstance();
        Date now = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR), c.get(Calendar.MINUTE));

        if (date1.after(now)) {
            return true;//超过了今天
        } else {
            return false;//没有超过今天
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算两个日期相差多少天
     *
     * @param date1
     * @param date2
     * @return 天数
     */
    public static int differentDaysByMillisecond(long date1, long date2) {
        int days = (int) ((date2 - date1) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 根据Birthday算年龄
     * @param date
     * @return
     */
    public static int getAgeFromBirthTime(Date date) {
        // 得到当前时间的年、月、日
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH) + 1;
            int dayNow = cal.get(Calendar.DATE);
            //得到输入时间的年，月，日
            cal.setTime(date);
            int selectYear = cal.get(Calendar.YEAR);
            int selectMonth = cal.get(Calendar.MONTH) + 1;
            int selectDay = cal.get(Calendar.DATE);
            // 用当前年月日减去生日年月日
            int yearMinus = yearNow - selectYear;
            int monthMinus = monthNow - selectMonth;
            int dayMinus = dayNow - selectDay;
            int age = yearMinus;// 先大致赋值
            if (yearMinus <= 0) {
                age = 0;
            }
            if (monthMinus < 0) {
                age = age - 1;
            } else if (monthMinus == 0) {
                if (dayMinus < 0) {
                    age = age - 1;
                }
            }
            return age;
        }
        return 0;
    }

}

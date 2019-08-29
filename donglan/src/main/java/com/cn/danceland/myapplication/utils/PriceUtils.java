package com.cn.danceland.myapplication.utils;

import java.text.DecimalFormat;

/**
 * Created by shy on 2017/12/22 10:53
 * Email:644563767@qq.com
 * 价格格式化类，保留两位小数
 */


public class PriceUtils {
    /**
     * 格式化价格
     * 输入float返回string
     *
     * @param price
     * @return
     */
    public static String formatPrice2String(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "¥" + decimalFormat.format(price);
    }


    /**
     * 格式化价格
     * 输入float返回float
     *
     * @param price
     * @return
     */
    public static float formatPrice2float(float price) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String s = decimalFormat.format(price);
     //   LogUtil.i(s);
        float p =Float.parseFloat(s);
      //  LogUtil.i(p+"");
        return p;
    }


    /**
     * 格式化价格
     * 输入string返回string
     *
     * @param price
     * @return
     */
    public static String formatPrice2String(String price) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "¥" + decimalFormat.format(Float.parseFloat(price));

    }


    /**
     * 格式化价格
     * 输入string返回float
     *
     * @param price
     * @return
     */
    public static float formatPrice2float(String price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String s = decimalFormat.format(Float.parseFloat(price));
        float p =Float.parseFloat(s);
        return p;
    }


}

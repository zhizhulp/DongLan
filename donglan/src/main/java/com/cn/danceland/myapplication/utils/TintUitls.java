package com.cn.danceland.myapplication.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * Created by shy on 2018/12/28 16:51
 * Email:644563767@qq.com
 * 着色器工具类
 */


public class TintUitls {
    /**
     *
     * @param context
     * @param imgid 图片
     * @param imageView 目标显示
     * @param colorid 颜色
     */
    public static void changeColor(Context context,int imgid, ImageView imageView, int colorid) {
        Drawable drawable = ContextCompat.getDrawable(context,imgid);
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context,colorid));
        imageView.setImageDrawable(drawable);
    }
}

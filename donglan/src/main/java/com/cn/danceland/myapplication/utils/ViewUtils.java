package com.cn.danceland.myapplication.utils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
    /**
     * 把自身从父View中移除
     */
    public static void removeSelfFromParent(View view) {
        // 先找到父类，再通过父类移除孩子
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * FindViewById的泛型封装，减少强转代码
     */
    public static <T extends View> T findViewById(View layout, int id) {
        return (T) layout.findViewById(id);
    }

    /**
     * 设置margin
     *
     * @param v view
     * @param l left
     * @param t top
     * @param r right
     * @param b bottom
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
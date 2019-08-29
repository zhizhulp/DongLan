package com.cn.danceland.myapplication.shouhuan.utils;

import java.util.HashMap;

/**
 * 数据操作工具类
 */
public interface OnColumnClickListener {
    void onColumnClick(int position, HashMap<String, Float> locationMap);

    void onDrawFinished();
}
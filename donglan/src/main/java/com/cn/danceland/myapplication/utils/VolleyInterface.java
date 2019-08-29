package com.cn.danceland.myapplication.utils;

import android.content.Context;

import com.android.volley.Response;

/**
 * Created by shy on 2018/4/28 11:39
 * Email:644563767@qq.com
 */


public abstract class VolleyInterface {
    public Context mContext;
    public static Response.Listener<String> mListener;
    public static Response.ErrorListener mErrorListener;
    public VolleyInterface(Context mContext, Response.Listener<String> mListener, Response.ErrorListener mErrorListener) {
        this.mContext = mContext;
        this.mListener=mListener;
        this.mErrorListener=mErrorListener;
    }
    public abstract void onSuccess(String result);

}

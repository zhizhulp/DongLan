package com.cn.danceland.myapplication.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2018/4/28 11:29
 * Email:644563767@qq.com
 */


public class MyJsonObjectRequest extends JsonObjectRequest {
    public MyJsonObjectRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(0, url, (String) null, listener, errorListener);
    }

    public MyJsonObjectRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, (String) null, listener, errorListener);
    }

    public MyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest == null ? null : jsonRequest.toString(), listener, errorListener);
    }

    public MyJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(jsonRequest == null ? 0 : 1, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = new HashMap<String, String>();

        map.put("Authorization", SPUtils.getString(Constants.MY_TOKEN, null));
        // LogUtil.e( SPUtils.getString(Constants.MY_TOKEN, null));
        map.put("version", Constants.getVersion());
        map.put("platform", Constants.getPlatform());
        map.put("channel", AppUtils.getChannelCode());
        LogUtil.i(map.toString());
        return map;

    }
}

package com.cn.danceland.myapplication.utils;

import android.content.Intent;

import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.activity.HomeActivity;
import com.cn.danceland.myapplication.activity.LoginActivity;

/**
 * Created by feng on 2018/6/4.
 */

public class RequestLogOut {

    /***
     * 退出登录
     */
    public void logOut() {


        MyApplication.getCurrentActivity().startActivity(new Intent(MyApplication.getCurrentActivity(), LoginActivity.class));

        SPUtils.setBoolean(Constants.ISLOGINED, false);
        //退出主页面
        HomeActivity.instance.finish();
        MyApplication.getCurrentActivity().finish();
        ToastUtils.showToastShort("当前登录已过期，需要重新登录");

//
//        final Data mInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
//        String url = Constants.LOGOUT_URL;
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                LogUtil.i(s);
//
//                Gson gson = new Gson();
//                RequsetSimpleBean    requestInfoBean = gson.fromJson(s, RequsetSimpleBean.class);
//                if (requestInfoBean.getSuccess()) {
//                    MyApplication.getCurrentActivity().startActivity(new Intent(MyApplication.getCurrentActivity(), LoginActivity.class));
//
//                    SPUtils.setBoolean(Constants.ISLOGINED, false);
//                    //退出主页面
//                    HomeActivity.instance.finish();
//                    MyApplication.getCurrentActivity().finish();
//                    ToastUtils.showToastShort("当前登录已过期，需要重新登录");
//                } else {
//                    //失败
//                    ToastUtils.showToastShort("退出登录失败");
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                ToastUtils.showToastShort("请求失败，请查看网络连接");
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//
//                map.put("terminal", "1");
//                map.put("member_no", mInfo.getPerson().getMember_no());
//                map.put("person_id", SPUtils.getString(Constants.MY_USERID, null));
//                // map.put("romType", "0");
//                return map;
//            }
//        };
//
//        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
//        request.setTag("logOut");
//        // 设置超时时间
//        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        // 将请求加入全局队列中
//        MyApplication.getHttpQueues().add(request);
    }

}

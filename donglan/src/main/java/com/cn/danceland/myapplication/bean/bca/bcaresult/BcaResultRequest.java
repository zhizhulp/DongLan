package com.cn.danceland.myapplication.bean.bca.bcaresult;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @友情提示: 请清理掉用不到的代码包括这段注释
 **/

/**
 * @类说明:结果项--网络请求层
 * @author:高振中
 * @date:2018-03-29 11:54:48
 **/
public class BcaResultRequest {

	/**
	 * @方法说明:新增结果项
	 **/
	public void save(BcaResult bcaResult, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaResult/save", new Gson().toJson(bcaResult), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:修改结果项
	 **/
	public void update(BcaResult bcaResult, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaResult/update", new Gson().toJson(bcaResult), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键删除结果项
	 **/
	public void delete(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "bcaResult/delete?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}


	/**
	 * @方法说明:按条件查询结果项列表
	 **/
	public void queryList(BcaResultCond cond, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaResult/queryList", new Gson().toJson(cond), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按条件查询结果项分页列表
	 **/
	public void queryPage(BcaResultCond cond, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaResult/queryPage", new Gson().toJson(cond), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键查询结果项单个数据
	 **/
	public void findById(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "bcaResult/findById?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询结果项数据个数
	 **/
	public void queryCount(BcaResultCond cond, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaResult/queryCount", new Gson().toJson(cond), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}
}
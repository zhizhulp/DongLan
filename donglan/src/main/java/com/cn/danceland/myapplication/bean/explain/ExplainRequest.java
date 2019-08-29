package com.cn.danceland.myapplication.bean.explain;

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
 * @类说明:说明须知--网络请求层
 * @author:高振中
 * @date:2018-04-25 11:23:07
 **/
public class ExplainRequest {

	/**
	 * @方法说明:新增说明须知
	 **/
	public void save(Explain explain, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "explain/save", new Gson().toJson(explain), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:修改说明须知
	 **/
	public void update(Explain explain, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "explain/update", new Gson().toJson(explain), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键删除说明须知
	 **/
	public void delete(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "explain/delete?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}


	/**
	 * @方法说明:按条件查询说明须知列表
	 **/
	public void queryList(ExplainCond cond, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "explain/queryList", new Gson().toJson(cond), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按条件查询说明须知分页列表
	 **/
	public void queryPage(ExplainCond cond, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "explain/queryPage", new Gson().toJson(cond), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键查询说明须知单个数据
	 **/
	public void findById(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "explain/findById?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询说明须知数据个数
	 **/
	public void queryCount(ExplainCond cond, Listener<JSONObject> listener) {
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "explain/queryCount", new Gson().toJson(cond), listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}
}
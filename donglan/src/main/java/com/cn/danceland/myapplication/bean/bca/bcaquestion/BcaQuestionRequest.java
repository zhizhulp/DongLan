package com.cn.danceland.myapplication.bean.bca.bcaquestion;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @友情提示: 请清理掉用不到的代码包括这段注释
 **/

/**
 * @类说明:问题题干--网络请求层
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaQuestionRequest {

	/**
	 * @方法说明:新增问题题干
	 **/
	public void save(BcaQuestion bcaQuestion, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(bcaQuestion));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaQuestion/save", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:修改问题题干
	 **/
	public void update(BcaQuestion bcaQuestion, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(bcaQuestion));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaQuestion/update", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键删除问题题干
	 **/
	public void delete(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "bcaQuestion/delete?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}


	/**
	 * @方法说明:按条件查询问题题干列表
	 **/
	public void queryList(BcaQuestionCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
			LogUtil.i(json.toString());
		} catch (JSONException e) {
			// LogUtil.i(bcaQuestion.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaQuestion/queryList", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按条件查询问题题干分页列表
	 **/
	public void queryPage(BcaQuestionCond cond, Listener<JSONObject> listener) {

		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaQuestion/queryPage", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键查询问题题干单个数据
	 **/
	public void findById(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "bcaQuestion/findById?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询问题题干数据个数
	 **/
	public void queryCount(BcaQuestionCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(bcaQuestion.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaQuestion/queryCount", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}
}
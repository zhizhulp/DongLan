package com.cn.danceland.myapplication.bean.bca.bcaoption;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @友情提示: 请清理掉用不到的代码包括这段注释
 **/

/**
 * @类说明:答案选项--网络请求层
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaOptionRequest {

	/**
	 * @方法说明:新增答案选项
	 **/
	public void save(BcaOption bcaOption, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(bcaOption));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaOption/save", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:修改答案选项
	 **/
	public void update(BcaOption bcaOption, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(bcaOption));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaOption/update", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键删除答案选项
	 **/
	public void delete(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "bcaOption/delete?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}


	/**
	 * @方法说明:按条件查询答案选项列表
	 **/
	public void queryList(BcaOptionCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(bcaOption.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaOption/queryList", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询答案选项分页列表
	 **/
	public void queryPage(BcaOptionCond cond, Listener<JSONObject> listener) {

		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaOption/queryPage", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键查询答案选项单个数据
	 **/
	public void findById(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "bcaOption/findById?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询答案选项数据个数
	 **/
	public void queryCount(BcaOptionCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(bcaOption.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "bcaOption/queryCount", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}
}
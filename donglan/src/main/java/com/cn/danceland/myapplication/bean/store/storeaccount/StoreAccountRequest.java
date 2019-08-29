package com.cn.danceland.myapplication.bean.store.storeaccount;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
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
 * @类说明:储值帐户--网络请求层
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreAccountRequest {

	/**
	 * @方法说明:新增储值帐户
	 **/
	public void save(StoreAccount storeAccount, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(storeAccount));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeAccount/save", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:修改储值帐户
	 **/
	public void update(StoreAccount storeAccount, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(storeAccount));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeAccount/update", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键删除储值帐户
	 **/
	public void delete(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "storeAccount/delete?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}


	/**
	 * @方法说明:按条件查询储值帐户列表
	 **/
	public void queryList(StoreAccountCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(storeAccount.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeAccount/findAccount ", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按条件查询储值帐户分页列表
	 **/
	public void queryPage(StoreAccountCond cond, Listener<JSONObject> listener) {

		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeAccount/queryPage", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键查询储值帐户单个数据
	 **/
	public void findById(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "storeAccount/findById?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询储值帐户数据个数
	 **/
	public void queryCount(StoreAccountCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(storeAccount.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeAccount/queryCount", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}
}
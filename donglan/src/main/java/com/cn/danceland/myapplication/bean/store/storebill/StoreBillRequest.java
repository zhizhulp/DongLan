package com.cn.danceland.myapplication.bean.store.storebill;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @友情提示: 请清理掉用不到的代码包括这段注释
 **/

/**
 * @类说明:储值卡流水帐单--网络请求层
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreBillRequest {

	/**
	 * @方法说明:新增储值卡流水帐单
	 **/
	public void save(StoreBill storeBill, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(storeBill));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeBill/queryList_v2", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:修改储值卡流水帐单
	 **/
	public void update(StoreBill storeBill, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(storeBill));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeBill/update", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键删除储值卡流水帐单
	 **/
	public void delete(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "storeBill/delete?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}


	/**
	 * @方法说明:按条件查询储值卡流水帐单列表
	 **/
	public void queryList(StoreBillCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(storeBill.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeBill/queryList_v2", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按条件查询储值卡流水帐单分页列表
	 **/
	public void queryPage(StoreBillCond cond, Listener<JSONObject> listener) {

		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeBill/queryPage", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) ;
		MyApplication.getHttpQueues().add(request);
	}

	/**
	 * @方法说明:按主键查询储值卡流水帐单单个数据
	 **/
	public void findById(Long id, Listener<String> listener) {
		MyStringRequest request = new MyStringRequest(1, Constants.HOST + "storeBill/findById?id=" + id, listener, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		}) {

		};
		MyApplication.getHttpQueues().add(request);
	}

 

	/**
	 * @方法说明:按条件查询储值卡流水帐单数据个数
	 **/
	public void queryCount(StoreBillCond cond, Listener<JSONObject> listener) {
		JSONObject json = null;
		try {
			json = new JSONObject(new Gson().toJson(cond));
		} catch (JSONException e) {
			// LogUtil.i(storeBill.toString());
			e.printStackTrace();
		}
		MyJsonObjectRequest request = new MyJsonObjectRequest(1, Constants.HOST + "storeBill/queryCount", json, listener, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				ToastUtils.showToastShort("请检查手机网络！");
			}
		});
		MyApplication.getHttpQueues().add(request);
	}
}
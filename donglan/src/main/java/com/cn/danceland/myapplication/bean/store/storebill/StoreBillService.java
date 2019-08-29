package com.cn.danceland.myapplication.bean.store.storebill;

import java.util.List;
import java.text.SimpleDateFormat;

import com.android.volley.Response;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Page;
import com.cn.danceland.myapplication.utils.ToastUtils;

/**
 * @友情提示: 请清理掉用不到的代码包括这段注释
 **/

/**
 * @类说明:储值卡流水帐单--业务逻辑
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreBillService {
	private StoreBillRequest request = new StoreBillRequest();
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * @方法说明:新增储值卡流水帐单
	 **/
	public void save() {
		StoreBill storeBill = new StoreBill();
		// TODO 准备数据
		request.save(storeBill, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				DLResult<Integer> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Integer>>() {
				}.getType());
				if (result.isSuccess()) {
					// TODO 请求据成功后的代码
				} else {
					ToastUtils.showToastShort("保存数据失败,请检查手机网络！");
				}
			}
		});

	}


	/**
	 * @方法说明:修改储值卡流水帐单
	 **/
	public void update() {
		StoreBill storeBill = new StoreBill();
		// TODO 准备数据
		request.save(storeBill, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				DLResult<Integer> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Integer>>() {
				}.getType());
				if (result.isSuccess()) {
					// TODO 请求成功后的代码
				} else {
					ToastUtils.showToastShort("修改数据失败,请检查手机网络！");
				}
			}
		});

	}


	/**
	 * @方法说明:按主键删除储值卡流水帐单
	 **/
	public void delete() {
		Long id = null;
		// TODO 准备数据
		request.delete(id, new Response.Listener<String>() {
			public void onResponse(String res) {
				DLResult<Integer> result = gson.fromJson(res, new TypeToken<DLResult<Integer>>() {
				}.getType());
				if (result.isSuccess()) {
					// TODO 请求成功后的代码
				} else {
					ToastUtils.showToastShort("删除数据失败,请检查手机网络！");
				}
			}
		});
	}


	/**
	 * @方法说明:按条件查询储值卡流水帐单列表
	 **/
	public void queryList() {
		StoreBillCond cond = new StoreBillCond();
		// TODO 准备查询条件
		request.queryList(cond, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				DLResult<List<StoreBill>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<StoreBill>>>() {
				}.getType());
				if (result.isSuccess()) {
					List<StoreBill> list = result.getData();
					System.out.println(list);
					// TODO 请求成功后的代码
				} else {
					ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
				}
			}
		});
	}


	/**
	 * @方法说明:按条件查询储值卡流水帐单分页列表
	 **/
	public void queryPage() {
		StoreBillCond cond = new StoreBillCond();
		// TODO 准备查询条件
		request.queryPage(cond, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				// LogUtil.i(json.toString());
				DLResult<Page<StoreBill>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Page<StoreBill>>>() {
				}.getType());
				if (result.isSuccess()) {
					Page<StoreBill> page = result.getData();
					List<StoreBill> list = page.getContent();
					System.out.println(list);
					// TODO 查询成功后的代码
				} else {
					ToastUtils.showToastShort("查询列表失败,请检查手机网络！");
				}

			}
		});

	}


	/**
	 * @方法说明:按主键查询单个储值卡流水帐单
	 **/
	public void findById() {
		Long id = null;
		// TODO 准备数据
		request.findById(id, new Response.Listener<String>() {
			public void onResponse(String res) {
				DLResult<StoreBill> result = gson.fromJson(res, new TypeToken<DLResult<StoreBill>>() {
				}.getType());
				if (result.isSuccess()) {
					StoreBill storeBill = result.getData();
					System.out.println(storeBill);
					// TODO 查询成功后的代码
				} else {
					ToastUtils.showToastShort("请检查手机网络！");
				}
			}
		});

	}
 

	/**
	 * @方法说明:按条件查询储值卡流水帐单数据个数
	 **/
	public void queryCount() {
		StoreBillCond cond = new StoreBillCond();
		// TODO 准备查询条件
		request.queryCount(cond, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				DLResult<Long> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Long>>() {
				}.getType());
				if (result.isSuccess()) {
					Long count = result.getData();
					System.out.println(count);
					// TODO 请求成功后的代码
				} else {
					ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
				}
			}
		});
	}
}
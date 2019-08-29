package com.cn.danceland.myapplication.bean.store.storetype;

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
 * @类说明:储值卡类型--业务逻辑
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreTypeService {
	private StoreTypeRequest request = new StoreTypeRequest();
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * @方法说明:新增储值卡类型
	 **/
	public void save() {
		StoreType storeType = new StoreType();
		// TODO 准备数据
		request.save(storeType, new Response.Listener<JSONObject>() {
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
	 * @方法说明:修改储值卡类型
	 **/
	public void update() {
		StoreType storeType = new StoreType();
		// TODO 准备数据
		request.save(storeType, new Response.Listener<JSONObject>() {
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
	 * @方法说明:按主键删除储值卡类型
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
//	 * @方法说明:按条件查询储值卡类型列表
//	 **/
//	public void queryList() {
//		StoreTypeCond cond = new StoreTypeCond();
//		// TODO 准备查询条件
//		request.queryList(cond, new Response.Listener<JSONObject>() {
//			public void onResponse(JSONObject json) {
//				DLResult<List<StoreType>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<StoreType>>>() {
//				}.getType());
//				if (result.isSuccess()) {
//					List<StoreType> list = result.getData();
//					System.out.println(list);
//					// TODO 请求成功后的代码
//				} else {
//					ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
//				}
//			}
//		});
//	}


	/**
	 * @方法说明:按条件查询储值卡类型分页列表
	 **/
	public void queryPage() {
		StoreTypeCond cond = new StoreTypeCond();
		// TODO 准备查询条件
		request.queryPage(cond, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				// LogUtil.i(json.toString());
				DLResult<Page<StoreType>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Page<StoreType>>>() {
				}.getType());
				if (result.isSuccess()) {
					Page<StoreType> page = result.getData();
					List<StoreType> list = page.getContent();
					System.out.println(list);
					// TODO 查询成功后的代码
				} else {
					ToastUtils.showToastShort("查询列表失败,请检查手机网络！");
				}

			}
		});

	}


	/**
	 * @方法说明:按主键查询单个储值卡类型
	 **/
	public void findById() {
		Long id = null;
		// TODO 准备数据
		request.findById(id, new Response.Listener<String>() {
			public void onResponse(String res) {
				DLResult<StoreType> result = gson.fromJson(res, new TypeToken<DLResult<StoreType>>() {
				}.getType());
				if (result.isSuccess()) {
					StoreType storeType = result.getData();
					System.out.println(storeType);
					// TODO 查询成功后的代码
				} else {
					ToastUtils.showToastShort("请检查手机网络！");
				}
			}
		});

	}
 

	/**
	 * @方法说明:按条件查询储值卡类型数据个数
	 **/
	public void queryCount() {
		StoreTypeCond cond = new StoreTypeCond();
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
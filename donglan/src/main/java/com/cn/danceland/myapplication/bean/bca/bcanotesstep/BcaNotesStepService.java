package com.cn.danceland.myapplication.bean.bca.bcanotesstep;

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
 * @类说明:体测分析注意事项--业务逻辑
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaNotesStepService {
	private BcaNotesStepRequest request = new BcaNotesStepRequest();
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * @方法说明:新增体测分析注意事项
	 **/
	public void save() {
		BcaNotesStep bcaNotesStep = new BcaNotesStep();
		// TODO 准备数据
		request.save(bcaNotesStep, new Response.Listener<JSONObject>() {
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
	 * @方法说明:修改体测分析注意事项
	 **/
	public void update() {
		BcaNotesStep bcaNotesStep = new BcaNotesStep();
		// TODO 准备数据
		request.save(bcaNotesStep, new Response.Listener<JSONObject>() {
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
	 * @方法说明:按主键删除体测分析注意事项
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
	 * @方法说明:按条件查询体测分析注意事项列表
	 **/
	public void queryList() {
		BcaNotesStepCond cond = new BcaNotesStepCond();
		// TODO 准备查询条件
		request.queryList(cond, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				DLResult<List<BcaNotesStep>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<BcaNotesStep>>>() {
				}.getType());
				if (result.isSuccess()) {
					List<BcaNotesStep> list = result.getData();
					System.out.println(list);
					// TODO 请求成功后的代码
				} else {
					ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
				}
			}
		});
	}


	/**
	 * @方法说明:按条件查询体测分析注意事项分页列表
	 **/
	public void queryPage() {
		BcaNotesStepCond cond = new BcaNotesStepCond();
		// TODO 准备查询条件
		request.queryPage(cond, new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject json) {
				// LogUtil.i(json.toString());
				DLResult<Page<BcaNotesStep>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<Page<BcaNotesStep>>>() {
				}.getType());
				if (result.isSuccess()) {
					Page<BcaNotesStep> page = result.getData();
					List<BcaNotesStep> list = page.getContent();
					System.out.println(list);
					// TODO 查询成功后的代码
				} else {
					ToastUtils.showToastShort("查询列表失败,请检查手机网络！");
				}

			}
		});

	}


	/**
	 * @方法说明:按主键查询单个体测分析注意事项
	 **/
	public void findById() {
		Long id = null;
		// TODO 准备数据
		request.findById(id, new Response.Listener<String>() {
			public void onResponse(String res) {
				DLResult<BcaNotesStep> result = gson.fromJson(res, new TypeToken<DLResult<BcaNotesStep>>() {
				}.getType());
				if (result.isSuccess()) {
					BcaNotesStep bcaNotesStep = result.getData();
					System.out.println(bcaNotesStep);
					// TODO 查询成功后的代码
				} else {
					ToastUtils.showToastShort("请检查手机网络！");
				}
			}
		});

	}
 

	/**
	 * @方法说明:按条件查询体测分析注意事项数据个数
	 **/
	public void queryCount() {
		BcaNotesStepCond cond = new BcaNotesStepCond();
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
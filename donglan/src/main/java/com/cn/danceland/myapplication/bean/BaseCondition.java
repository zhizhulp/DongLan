package com.cn.danceland.myapplication.bean;

/**
 * @功能说明:拼加页面查询条件的基础类
 * @author gzz_gzz@163.com
 * @date 2018-02-15
 */
public class BaseCondition {
	private Integer size = 10;// 页大小(每页记录条)
	private Integer page = 0;// 当前页码

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}

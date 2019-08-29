package com.cn.danceland.myapplication.bean;

import java.util.ArrayList;

import java.util.List;

/**
 * @功能描述:分页数据
 * @author gzz_gzz@163.com
 * @date 2018-02-15
 */
public class Page<T> {

	private final List<T> content = new ArrayList<>();
	private int number;// 当前页
	private int size;// 页大小
	private long totalElements;// 记录数

	public long getPageCount() {
		return totalElements % size == 0 ? totalElements / size : totalElements / size + 1;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public List<T> getContent() {
		return content;
	}

}

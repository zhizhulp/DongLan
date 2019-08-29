package com.cn.danceland.myapplication.bean.bca.bcaquestion;

import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;

import java.util.List;

/**
 * @类说明:问题题干--实体类
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaQuestion {

	//原始属性
	private Long id;// 主键
	private String centent;// 内容
	private Short order_no;// 排序编号
	private Byte enabled;// 启用禁用
	private Byte type;// 问题类型
	private String test_content;// 测试内容JSON
	private Long branch_id;// 门店
	private List<BcaOption> options;
	private Long is_single;// 是否为单选 1 单选 0 非单选

	public Long getIs_single() {
		return is_single;
	}

	public void setIs_single(Long is_single) {
		this.is_single = is_single;
	}


	public List<BcaOption> getOptions() {
		return options;
	}

	public void setOptions(List<BcaOption> options) {
		this.options = options;
	}
	//新增属性

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCentent() {
		return centent;
	}
	public void setCentent(String centent) {
		this.centent = centent;
	}
	public Short getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Short order_no) {
		this.order_no = order_no;
	}
	public Byte getEnabled() {
		return enabled;
	}
	public void setEnabled(Byte enabled) {
		this.enabled = enabled;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getTest_content() {
		return test_content;
	}
	public void setTest_content(String test_content) {
		this.test_content = test_content;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	@Override
	public String toString() {
		return "BcaQuestion{" +
				"id=" + id +
				", centent='" + centent + '\'' +
				", order_no=" + order_no +
				", enabled=" + enabled +
				", type=" + type +
				", test_content='" + test_content + '\'' +
				", branch_id=" + branch_id +
				", options=" + options +
				'}';
	}
}
package com.cn.danceland.myapplication.bean.bca.bcaoption;

/**
 * @类说明:答案选项--实体类
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaOption {

	//原始属性
	private Long id;// 主键
	private Byte type;// 单选多选
	private Short order_no;// 排序编号
	private String title;// 选项内容
	private String result;// 结果文本
	private Long question_id;// 问题编号
	private String perfix;// 前缀
	private Long branch_id;// 门店
	private boolean isChecked;//是否选中
	private String content;//答案

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean checked) {
		isChecked = checked;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Short getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Short order_no) {
		this.order_no = order_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Long getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}
	public String getPerfix() {
		return perfix;
	}
	public void setPerfix(String perfix) {
		this.perfix = perfix;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
}
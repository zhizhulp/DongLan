package com.cn.danceland.myapplication.bean.explain;

/**
 * @类说明:说明须知--实体类
 * @author:
 * @date:2018-04-25 10:59:56
 **/
public class Explain {

	//原始属性
	private Long id;// 主键id
	private Byte type;// 类型
	private Long branch_id;// 门店id
	private String content;// 内容

	//新增属性

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
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
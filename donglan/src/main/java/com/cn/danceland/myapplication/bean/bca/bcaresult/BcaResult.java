package com.cn.danceland.myapplication.bean.bca.bcaresult;

import java.io.Serializable;

/**
 * @类说明:结果项--实体类
 * @author:高振中
 * @date:2018-03-29 11:54:48
 **/
public class BcaResult implements Serializable{

	//原始属性
	private Long id;// 主键
	private Long a_id;// 体测分析编号
	private Long question_id;// 问题编号
	private Long opt_id;// 选项编号
	private String content;// 结果内容
	private Long branch_id;// 门店

	//新增属性

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getA_id() {
		return a_id;
	}
	public void setA_id(Long a_id) {
		this.a_id = a_id;
	}
	public Long getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}
	public Long getOpt_id() {
		return opt_id;
	}
	public void setOpt_id(Long opt_id) {
		this.opt_id = opt_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BcaResult bcaResult = (BcaResult) o;

		if (id != null ? !id.equals(bcaResult.id) : bcaResult.id != null) return false;
		if (a_id != null ? !a_id.equals(bcaResult.a_id) : bcaResult.a_id != null) return false;
		if (question_id != null ? !question_id.equals(bcaResult.question_id) : bcaResult.question_id != null)
			return false;
		if (opt_id != null ? !opt_id.equals(bcaResult.opt_id) : bcaResult.opt_id != null)
			return false;
		return branch_id != null ? branch_id.equals(bcaResult.branch_id) : bcaResult.branch_id == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (a_id != null ? a_id.hashCode() : 0);
		result = 31 * result + (question_id != null ? question_id.hashCode() : 0);
		result = 31 * result + (opt_id != null ? opt_id.hashCode() : 0);
		result = 31 * result + (branch_id != null ? branch_id.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "BcaResult{" +
				"id=" + id +
				", a_id=" + a_id +
				", question_id=" + question_id +
				", opt_id=" + opt_id +
				", content='" + content + '\'' +
				", branch_id=" + branch_id +
				'}';
	}
}
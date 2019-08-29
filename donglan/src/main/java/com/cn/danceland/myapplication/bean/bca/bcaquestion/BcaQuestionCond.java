package com.cn.danceland.myapplication.bean.bca.bcaquestion;
import com.cn.danceland.myapplication.bean.BaseCondition;

/**
 * @类说明:问题题干--查询条件实体
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaQuestionCond extends BaseCondition  {

	//把不用条件清理掉
	private Long id;// 主键
	private String centent;// 内容
	private Short order_no;// 排序编号
	private Byte enabled;// 启用禁用
	private Byte type;// 问题类型
	private String test_content;// 测试内容JSON
	private Long branch_id;// 门店

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
}
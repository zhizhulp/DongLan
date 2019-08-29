package com.cn.danceland.myapplication.bean.bca.bcanotesstep;
import com.cn.danceland.myapplication.bean.BaseCondition;

/**
 * @类说明:体测分析注意事项--查询条件实体
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaNotesStepCond extends BaseCondition  {

	//把不用条件清理掉
	private Long id;// 主键
	private Short order_no;// 顺序编号
	private String title;// 步骤内容
	private Byte type;// 大标题还是小标题
	private Long parent_id;// 父节点
	private Long branch_id;// 门店

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
}
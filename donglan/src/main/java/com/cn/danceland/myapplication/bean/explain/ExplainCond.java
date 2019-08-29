package com.cn.danceland.myapplication.bean.explain;
import com.cn.danceland.myapplication.bean.BaseCondition;
import com.cn.danceland.myapplication.bean.BaseCondition;

/**
 * @类说明:说明须知--查询条件实体
 * @author:
 * @date:2018-04-25 10:59:56
 **/
public class ExplainCond extends BaseCondition {

	//把不用条件清理掉
	private Long id;// 主键id
	private Byte type;// 类型1 买卡须知 2 买私教须知 3 买储值须知 4 买卡说明 5 买私教说明
	private Long branch_id;// 门店id
	private String content;// 内容

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
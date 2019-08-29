package com.cn.danceland.myapplication.bean.store.storeaccount;
import com.cn.danceland.myapplication.bean.BaseCondition;

/**
 * @类说明:储值帐户--查询条件实体
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreAccountCond extends BaseCondition  {

	//把不用条件清理掉
	private Long id;// 主键
	private Long member_id;// 会员ID
	private Long branch_id;// 门店ID
	private Float remain;// 余额
	private Float giving;// 赠送金额
	private Byte enable;// 状态

	@Override
	public String toString() {
		return "StoreAccountCond{" +
				"id=" + id +
				", member_id=" + member_id +
				", branch_id=" + branch_id +
				", remain=" + remain +
				", giving=" + giving +
				", enable=" + enable +
				'}';
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public Float getRemain() {
		return remain;
	}
	public void setRemain(Float remain) {
		this.remain = remain;
	}
	public Float getGiving() {
		return giving;
	}
	public void setGiving(Float giving) {
		this.giving = giving;
	}
	public Byte getEnable() {
		return enable;
	}
	public void setEnable(Byte enable) {
		this.enable = enable;
	}
}
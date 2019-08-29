package com.cn.danceland.myapplication.bean.store.storebill;
import java.util.Date;
import com.cn.danceland.myapplication.bean.BaseCondition;

/**
 * @类说明:储值卡流水帐单--查询条件实体
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreBillCond extends BaseCondition  {

	//把不用条件清理掉
	private Long id;// 主键
	private Byte type;// 类型
	private Long store_type_id;// 储值卡类型ID
	private Float price;// 金额
	private Float giving;// 赠送金额
	private Date operate_time;// 操作时间
	private Long operate_id;// 操作人ID
	private Long goods_id;// 商品ID
	private String remark;// 备注
	private Long branch_id;// 门店ID
	private Long member_id;// 会员ID
	private Long account_id;// 帐号ID

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
	public Long getStore_type_id() {
		return store_type_id;
	}
	public void setStore_type_id(Long store_type_id) {
		this.store_type_id = store_type_id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getGiving() {
		return giving;
	}
	public void setGiving(Float giving) {
		this.giving = giving;
	}
	public Date getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}
	public Long getOperate_id() {
		return operate_id;
	}
	public void setOperate_id(Long operate_id) {
		this.operate_id = operate_id;
	}
	public Long getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
	public Long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}
}
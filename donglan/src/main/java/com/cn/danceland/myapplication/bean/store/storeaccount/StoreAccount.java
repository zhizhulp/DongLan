package com.cn.danceland.myapplication.bean.store.storeaccount;

/**
 * @类说明:储值帐户--实体类
 * @author:高振中
 * @date:2018-03-14 10:16:26
 **/
public class StoreAccount {

	//原始属性
	private Long id;// 主键
	private Long member_id;// 会员ID
	private Long branch_id;// 门店ID
	private float remain;// 余额
	private float giving;// 赠送金额
	private Byte enable;// 状态
	private String img_path;
	private String img_url;// 图片地址
	private String address_name;//地址

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getAddress_name() {
		return address_name;
	}

	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
//新增属性

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
	public float getRemain() {
		return remain;
	}
	public void setRemain(float remain) {
		this.remain = remain;
	}
	public float getGiving() {
		return giving;
	}
	public void setGiving(float giving) {
		this.giving = giving;
	}
	public Byte getEnable() {
		return enable;
	}
	public void setEnable(Byte enable) {
		this.enable = enable;
	}
}
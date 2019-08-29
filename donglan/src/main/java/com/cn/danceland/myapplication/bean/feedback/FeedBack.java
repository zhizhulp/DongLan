package com.cn.danceland.myapplication.bean.feedback;
import java.util.Date;

/**
 * @类说明:意见反馈--实体类
 * @author:高振中
 * @date:2018-03-26 10:24:30
 **/
public class FeedBack {

	//原始属性
	private Long id;// 主键
	private String content;// 反馈内容
	private Long branch_id;// 门店id
	private Long opinion_id;// 发意见的人
	private Integer type;// 反馈类型
	private Date opinion_date;// 反馈时间
	private String reply_content;// 回复内容
	private Date reply_date;// 回复时间
	private Long reply_id;// 回复人
	private Byte status;// 状态
	private String contact_way;// 联系方式

	//新增属性

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getOpinion_id() {
		return opinion_id;
	}
	public void setOpinion_id(Long opinion_id) {
		this.opinion_id = opinion_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getOpinion_date() {
		return opinion_date;
	}
	public void setOpinion_date(Date opinion_date) {
		this.opinion_date = opinion_date;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public Date getReply_date() {
		return reply_date;
	}
	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}
	public Long getReply_id() {
		return reply_id;
	}
	public void setReply_id(Long reply_id) {
		this.reply_id = reply_id;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getContact_way() {
		return contact_way;
	}
	public void setContact_way(String contact_way) {
		this.contact_way = contact_way;
	}
}
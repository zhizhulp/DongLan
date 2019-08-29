package com.cn.danceland.myapplication.bean.bca.bcaanalysis;
import java.util.Date;
import com.cn.danceland.myapplication.bean.BaseCondition;

/**
 * @类说明:体测分析--查询条件实体
 * @author:高振中
 * @date:2018-04-08 13:41:17
 **/
public class BcaAnalysisCond extends BaseCondition  {

	//把不用条件清理掉
	private Long id;// 主键
	private String member_no;// 会员编号
	private Long teach_id;// 体测分析教练
	private Date test_time;// 体测分析时间
	private Long branch_id;// 门店
	private String frontal_path;// 正面照
	private String side_path;// 侧面照
	private String behind_path;// 背后照
	private Long member_id;// 会员ID

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public Long getTeach_id() {
		return teach_id;
	}
	public void setTeach_id(Long teach_id) {
		this.teach_id = teach_id;
	}
	public Date getTest_time() {
		return test_time;
	}
	public void setTest_time(Date test_time) {
		this.test_time = test_time;
	}
	public Long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}
	public String getFrontal_path() {
		return frontal_path;
	}
	public void setFrontal_path(String frontal_path) {
		this.frontal_path = frontal_path;
	}
	public String getSide_path() {
		return side_path;
	}
	public void setSide_path(String side_path) {
		this.side_path = side_path;
	}
	public String getBehind_path() {
		return behind_path;
	}
	public void setBehind_path(String behind_path) {
		this.behind_path = behind_path;
	}
	public Long getMember_id() {
		return member_id;
	}
	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}
}
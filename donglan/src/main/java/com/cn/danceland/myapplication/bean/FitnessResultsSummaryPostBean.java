package com.cn.danceland.myapplication.bean;

/**
 * 体测结果 请求参数
 * Created by yxx on 2018-09-21.
 */

public class FitnessResultsSummaryPostBean {
    private Long member_id;// 会员ID

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    @Override
    public String toString() {
        return "FitnessResultsSummaryPostBean{" +
                "member_id=" + member_id +
                '}';
    }
}

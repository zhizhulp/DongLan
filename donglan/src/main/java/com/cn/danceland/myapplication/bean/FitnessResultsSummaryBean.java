package com.cn.danceland.myapplication.bean;

import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;

import java.util.List;

/**
 * Created by yxx on 2018-09-21.
 */

public class FitnessResultsSummaryBean {
    private boolean success;
    private String errorMsg;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private Long id;// 主键
        private String member_no;// 会员编号
        private Long teach_id;// 体测分析教练
        private String test_time;// 体测分析时间
        private Long branch_id;// 门店
        private String frontal_url;// 正面照
        private String side_url;// 侧面照
        private String behind_url;// 背后照
        private Long member_id;// 会员ID

        private String content;//综合评价
        private List<QuestionTypes> questionTypes;// 问题列表
        private String teach_name;
        private String branch_name;

        public String getTeach_name() {
            return teach_name;
        }

        public void setTeach_name(String teach_name) {
            this.teach_name = teach_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

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

        public String getTest_time() {
            return test_time;
        }

        public void setTest_time(String test_time) {
            this.test_time = test_time;
        }

        public Long getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(Long branch_id) {
            this.branch_id = branch_id;
        }

        public String getFrontal_url() {
            return frontal_url;
        }

        public void setFrontal_url(String frontal_url) {
            this.frontal_url = frontal_url;
        }

        public String getSide_url() {
            return side_url;
        }

        public void setSide_url(String side_url) {
            this.side_url = side_url;
        }

        public String getBehind_url() {
            return behind_url;
        }

        public void setBehind_url(String behind_url) {
            this.behind_url = behind_url;
        }

        public Long getMember_id() {
            return member_id;
        }

        public void setMember_id(Long member_id) {
            this.member_id = member_id;
        }


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<QuestionTypes> getQuestionTypes() {
            return questionTypes;
        }

        public void setQuestionTypes(List<QuestionTypes> questionTypes) {
            this.questionTypes = questionTypes;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", member_no='" + member_no + '\'' +
                    ", teach_id=" + teach_id +
                    ", test_time='" + test_time + '\'' +
                    ", branch_id=" + branch_id +
                    ", frontal_url='" + frontal_url + '\'' +
                    ", side_url='" + side_url + '\'' +
                    ", behind_url='" + behind_url + '\'' +
                    ", member_id=" + member_id +
                    ", content='" + content + '\'' +
                    ", questionTypes=" + questionTypes +
                    ", teach_name='" + teach_name + '\'' +
                    ", branch_name='" + branch_name + '\'' +
                    '}';
        }
    }

    public class QuestionTypes {
        private String typeName;
        private String typeValue;
        private List<Questions> questions;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeValue() {
            return typeValue;
        }

        public void setTypeValue(String typeValue) {
            this.typeValue = typeValue;
        }

        public List<Questions> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Questions> questions) {
            this.questions = questions;
        }

        @Override
        public String toString() {
            return "QuestionTypes{" +
                    "typeName='" + typeName + '\'' +
                    ", typeValue='" + typeValue + '\'' +
                    ", questions=" + questions +
                    '}';
        }
    }
    public static class Questions {

        //原始属性
        private Long id;// 主键
        private String centent;// 内容
        private Short order_no;// 排序编号
        private Byte enabled;// 启用禁用
        private Byte type;// 问题类型
        private Long branch_id;// 门店
        private List<BcaOption> options;


        public List<BcaOption> getOptions() {
            return options;
        }

        public void setOptions(List<BcaOption> options) {
            this.options = options;
        }
        //新增属性

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
        public Long getBranch_id() {
            return branch_id;
        }
        public void setBranch_id(Long branch_id) {
            this.branch_id = branch_id;
        }

        @Override
        public String toString() {
            return "BcaQuestion{" +
                    "id=" + id +
                    ", centent='" + centent + '\'' +
                    ", order_no=" + order_no +
                    ", enabled=" + enabled +
                    ", type=" + type +
                    ", branch_id=" + branch_id +
                    ", options=" + options +
                    '}';
        }
    }
}

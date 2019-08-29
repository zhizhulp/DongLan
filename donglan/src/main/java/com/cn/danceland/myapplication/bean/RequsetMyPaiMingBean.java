package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/3/27 14:50
 * Email:644563767@qq.com
 */


public class RequsetMyPaiMingBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

    @Override
    public String toString() {
        return "RequsetMyPaiMingBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    public static class Data {
        @Override
        public String toString() {
            return "Data{" +
                    "branchScore=" + branchScore +
                    ", branchRanking=" + branchRanking +
                    '}';
        }

        private int branchScore;//次数
        private int branchRanking;//排名
        public void setBranchScore(int branchScore) {
            this.branchScore = branchScore;
        }
        public int getBranchScore() {
            return branchScore;
        }

        public void setBranchRanking(int branchRanking) {
            this.branchRanking = branchRanking;
        }
        public int getBranchRanking() {
            return branchRanking;
        }

    }
}
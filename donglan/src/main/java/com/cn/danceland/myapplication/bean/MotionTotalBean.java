package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的 运动数据
 * Created by yxx on 2018-12-04.
 */

public class MotionTotalBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

    @Override
    public String toString() {
        return "MotionBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean getSuccess() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {
        private String aerobic_heart;// 有氧平均心率 ,
        private String anaerobic_heart;// 无氧平均心率 ,
        private String avg_heavy;// 无氧平均重量 ,
        private String avg_speed;// 有氧平均速度 ,
        private String distance;//有氧运动距离 ,
        private String group_count;//无氧运动组数 ,
        private String member_name;//会员名称 ,
        private String member_no;// 会员号 ,
        private String person_id;//人员id ,
        private String time;//有氧运动时间 ,
        private String times;//无氧运动次数

        @Override
        public String toString() {
            return "Data{" +
                    "aerobic_heart='" + aerobic_heart + '\'' +
                    ", anaerobic_heart='" + anaerobic_heart + '\'' +
                    ", avg_heavy='" + avg_heavy + '\'' +
                    ", avg_speed='" + avg_speed + '\'' +
                    ", distance='" + distance + '\'' +
                    ", group_count='" + group_count + '\'' +
                    ", member_name='" + member_name + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", person_id='" + person_id + '\'' +
                    ", time='" + time + '\'' +
                    ", times='" + times + '\'' +
                    '}';
        }

        public String getAerobic_heart() {
            return aerobic_heart;
        }

        public void setAerobic_heart(String aerobic_heart) {
            this.aerobic_heart = aerobic_heart;
        }

        public String getAnaerobic_heart() {
            return anaerobic_heart;
        }

        public void setAnaerobic_heart(String anaerobic_heart) {
            this.anaerobic_heart = anaerobic_heart;
        }

        public String getAvg_heavy() {
            return avg_heavy;
        }

        public void setAvg_heavy(String avg_heavy) {
            this.avg_heavy = avg_heavy;
        }

        public String getAvg_speed() {
            return avg_speed;
        }

        public void setAvg_speed(String avg_speed) {
            this.avg_speed = avg_speed;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getGroup_count() {
            return group_count;
        }

        public void setGroup_count(String group_count) {
            this.group_count = group_count;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_no() {
            return member_no;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}


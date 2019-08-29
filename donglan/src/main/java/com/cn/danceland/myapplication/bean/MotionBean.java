package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的 运动数据
 * Created by yxx on 2018-12-04.
 */

public class MotionBean implements Serializable {

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

        private List<Data.Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;

        @Override
        public String toString() {
            return "Data{" +
                    "content=" + content +
                    ", number=" + number +
                    ", size=" + size +
                    ", totalElements=" + totalElements +
                    ", numberOfElements=" + numberOfElements +
                    ", totalPages=" + totalPages +
                    ", last=" + last +
                    '}';
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public class Content implements Serializable {

            private String calorie;//卡路里（单位cal，即kcal*1000） ,
            private String cooldown;//平均休息时间（秒） ,
            private String distance;// 运动距离（单位米） ,
            private String heart;// 平均心率 ,
            private String heavy;// 平均重量（公斤） ,
            private String id;//id ,
            private String incline;// 当期阶段平均坡度 ,
            private String name;// 设备名称 ,
            private String person_id;// 人员id ,
            private String speed;// 当前阶段平均速度 ,
            private String start_time;// 用户点击开始运动的时间 ,
            private String sub_type;// 舒华设备小类型 ,
            private String time;// 运动时长(秒) ,
            private String times;// 总次数 ,
            private String type;// 舒华设备大类型 1:跑步机 2:磁控车 3:无氧设备
            private String watt;// 瓦特 ,

            @Override
            public String toString() {
                return "Content{" +
                        "calorie='" + calorie + '\'' +
                        ", cooldown='" + cooldown + '\'' +
                        ", distance='" + distance + '\'' +
                        ", heart='" + heart + '\'' +
                        ", heavy='" + heavy + '\'' +
                        ", id='" + id + '\'' +
                        ", incline='" + incline + '\'' +
                        ", name='" + name + '\'' +
                        ", person_id='" + person_id + '\'' +
                        ", speed='" + speed + '\'' +
                        ", start_time='" + start_time + '\'' +
                        ", sub_type='" + sub_type + '\'' +
                        ", time='" + time + '\'' +
                        ", times='" + times + '\'' +
                        ", type='" + type + '\'' +
                        ", watt='" + watt + '\'' +
                        '}';
            }

            public String getWatt() {
                return watt;
            }

            public void setWatt(String watt) {
                this.watt = watt;
            }

            public String getCalorie() {
                return calorie;
            }

            public void setCalorie(String calorie) {
                this.calorie = calorie;
            }

            public String getCooldown() {
                return cooldown;
            }

            public void setCooldown(String cooldown) {
                this.cooldown = cooldown;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getHeart() {
                return heart;
            }

            public void setHeart(String heart) {
                this.heart = heart;
            }

            public String getHeavy() {
                return heavy;
            }

            public void setHeavy(String heavy) {
                this.heavy = heavy;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIncline() {
                return incline;
            }

            public void setIncline(String incline) {
                this.incline = incline;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPerson_id() {
                return person_id;
            }

            public void setPerson_id(String person_id) {
                this.person_id = person_id;
            }

            public String getSpeed() {
                return speed;
            }

            public void setSpeed(String speed) {
                this.speed = speed;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getSub_type() {
                return sub_type;
            }

            public void setSub_type(String sub_type) {
                this.sub_type = sub_type;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}


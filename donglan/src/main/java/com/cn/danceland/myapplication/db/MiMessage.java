package com.cn.danceland.myapplication.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by feng on 2017/12/7.
 */
@Entity
public class MiMessage {

    private int id;
    private String content;
    private String personId;
    private String personName;
    private String dynId;
    private String selfPath;
    private String type;
    private String time;
    @Generated(hash = 750699147)
    public MiMessage(int id, String content, String personId, String personName,
            String dynId, String selfPath, String type, String time) {
        this.id = id;
        this.content = content;
        this.personId = personId;
        this.personName = personName;
        this.dynId = dynId;
        this.selfPath = selfPath;
        this.type = type;
        this.time = time;
    }
    @Generated(hash = 932895701)
    public MiMessage() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPersonId() {
        return this.personId;
    }
    public void setPersonId(String personId) {
        this.personId = personId;
    }
    public String getPersonName() {
        return this.personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public String getDynId() {
        return this.dynId;
    }
    public void setDynId(String dynId) {
        this.dynId = dynId;
    }
    public String getSelfPath() {
        return this.selfPath;
    }
    public void setSelfPath(String selfPath) {
        this.selfPath = selfPath;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}

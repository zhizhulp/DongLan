/**
  * Copyright 2017 bejson.com 
  */
package com.cn.danceland.myapplication.bean;

/**
 * Auto-generated: 2017-10-26 11:25:11
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RequestInfoBean {


    private boolean success;
    private String errorMsg;
    private Data data;


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

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    @Override
    public String toString() {
        return "RequestInfoBean{" +

                ", success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
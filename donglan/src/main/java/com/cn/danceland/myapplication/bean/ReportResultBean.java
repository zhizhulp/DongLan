package com.cn.danceland.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/4/26.
 */

public class ReportResultBean implements Parcelable{

    private boolean success;
    private String errorMsg;
    private String code;
    private List<Data> data;
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

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }



    public static class Data implements Parcelable{

        private String title;
        private String columnName;
        private String unit;
        private String today;
        private String endOfToDay;
        private String allOfMonth;
        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
        public String getColumnName() {
            return columnName;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
        public String getUnit() {
            return unit;
        }

        public void setToday(String today) {
            this.today = today;
        }
        public String getToday() {
            return today;
        }

        public void setEndOfToDay(String endOfToDay) {
            this.endOfToDay = endOfToDay;
        }
        public String getEndOfToDay() {
            return endOfToDay;
        }

        public void setAllOfMonth(String allOfMonth) {
            this.allOfMonth = allOfMonth;
        }
        public String getAllOfMonth() {
            return allOfMonth;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.columnName);
            dest.writeString(this.unit);
            dest.writeString(this.today);
            dest.writeString(this.endOfToDay);
            dest.writeString(this.allOfMonth);
        }

        public Data() {
        }

        protected Data(Parcel in) {
            this.title = in.readString();
            this.columnName = in.readString();
            this.unit = in.readString();
            this.today = in.readString();
            this.endOfToDay = in.readString();
            this.allOfMonth = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.errorMsg);
        dest.writeString(this.code);
        dest.writeList(this.data);
    }

    public ReportResultBean() {
    }

    protected ReportResultBean(Parcel in) {
        this.success = in.readByte() != 0;
        this.errorMsg = in.readString();
        this.code = in.readString();
        this.data = new ArrayList<Data>();
        in.readList(this.data, Data.class.getClassLoader());
    }

    public static final Creator<ReportResultBean> CREATOR = new Creator<ReportResultBean>() {
        @Override
        public ReportResultBean createFromParcel(Parcel source) {
            return new ReportResultBean(source);
        }

        @Override
        public ReportResultBean[] newArray(int size) {
            return new ReportResultBean[size];
        }
    };
}

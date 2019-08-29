package com.cn.danceland.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shy on 2018/12/18 14:28
 * Email:644563767@qq.com
 */

public class ImageBean implements Parcelable {

    private String FilePath;
    private String FileName;
    private Boolean IsSelect;
    private int viewLeft;
    private int viewTop;
    private int viewHeight;
    private int viewWidth;

    public int getViewLeft() {
        return viewLeft;
    }
    public void setViewLeft(int viewLeft) {
        this.viewLeft = viewLeft;
    }
    public int getViewTop() {
        return viewTop;
    }
    public void setViewTop(int viewTop) {
        this.viewTop = viewTop;
    }
    public int getViewHeight() {
        return viewHeight;
    }
    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }
    public int getViewWidth() {
        return viewWidth;
    }
    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }
    public ImageBean(int left,int top,int height,int width){
        viewLeft = left;
        viewTop = top;
        viewHeight = height;
        viewWidth = width;
    }
    public ImageBean(String p, String f){
        FilePath = p;
        FileName = f;
        IsSelect = false;
    }
    public String getFilePath() {
        return FilePath;
    }
    public void setFilePath(String filePath) {
        FilePath = filePath;
    }
    public String getFileName() {
        return FileName;
    }
    public void setFileName(String fileName) {
        FileName = fileName;
    }
    public Boolean getSelect() {
        return IsSelect;
    }
    public void setSelect(Boolean select) {
        IsSelect = select;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.FilePath);
        dest.writeString(this.FileName);
        dest.writeValue(this.IsSelect);
        dest.writeInt(this.viewLeft);
        dest.writeInt(this.viewTop);
        dest.writeInt(this.viewHeight);
        dest.writeInt(this.viewWidth);
    }
    protected ImageBean(Parcel in) {
        this.FilePath = in.readString();
        this.FileName = in.readString();
        this.IsSelect = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.viewLeft = in.readInt();
        this.viewTop = in.readInt();
        this.viewHeight = in.readInt();
        this.viewWidth = in.readInt();
    }
    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }
        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };
}


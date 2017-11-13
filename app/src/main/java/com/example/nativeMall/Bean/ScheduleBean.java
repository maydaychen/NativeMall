package com.example.nativeMall.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Du on 2016/8/25.
 */
public class ScheduleBean implements Parcelable {
    /**
     * headerId : 22
     * workTime : 2016-05-28
     * workAmPm : a
     * workPrice : 200
     * workComment :
     * workStatrDt : 2016-05-28 09:00:00
     * workEndDt : 2016-05-28 12:00:00
     * visitType : 普通门诊
     */

    private String headerId;
    private String workTime;
    private String workAmPm;
    private String workPrice;
    private String workComment;
    private String workStatrDt;
    private String workEndDt;
    private String visitType;

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkAmPm() {
        return workAmPm;
    }

    public void setWorkAmPm(String workAmPm) {
        this.workAmPm = workAmPm;
    }

    public String getWorkPrice() {
        return workPrice;
    }

    public void setWorkPrice(String workPrice) {
        this.workPrice = workPrice;
    }

    public String getWorkComment() {
        return workComment;
    }

    public void setWorkComment(String workComment) {
        this.workComment = workComment;
    }

    public String getWorkStatrDt() {
        return workStatrDt;
    }

    public void setWorkStatrDt(String workStatrDt) {
        this.workStatrDt = workStatrDt;
    }

    public String getWorkEndDt() {
        return workEndDt;
    }

    public void setWorkEndDt(String workEndDt) {
        this.workEndDt = workEndDt;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.headerId);
        dest.writeString(this.workTime);
        dest.writeString(this.workAmPm);
        dest.writeString(this.workPrice);
        dest.writeString(this.workComment);
        dest.writeString(this.workStatrDt);
        dest.writeString(this.workEndDt);
        dest.writeString(this.visitType);
    }

    public ScheduleBean() {
    }

    protected ScheduleBean(Parcel in) {
        this.headerId = in.readString();
        this.workTime = in.readString();
        this.workAmPm = in.readString();
        this.workPrice = in.readString();
        this.workComment = in.readString();
        this.workStatrDt = in.readString();
        this.workEndDt = in.readString();
        this.visitType = in.readString();
    }

    public static final Creator<ScheduleBean> CREATOR = new Creator<ScheduleBean>() {
        @Override
        public ScheduleBean createFromParcel(Parcel source) {
            return new ScheduleBean(source);
        }

        @Override
        public ScheduleBean[] newArray(int size) {
            return new ScheduleBean[size];
        }
    };
}


package com.example.nativeMall.Bean;

/**
 * Created by Du on 2016/8/25.
 */
public class TimeAndCostBean {

    private String signalSource = "";
    private String workTime = "";
    private String workAmPm = "";
    private String workPrice ="";
    private String workComment ="";
    private String workStatrDt ="";
    private String workEndDt = "";
    private String visitType ="";
    private String headerId ="";
    private String ifRestricted ="";

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public String getIfRestricted() {
        return ifRestricted;
    }

    public void setIfRestricted(String ifRestricted) {
        this.ifRestricted = ifRestricted;
    }

    public String getSignalSource() {
        return signalSource;
    }

    public void setSignalSource(String signalSource) {
        this.signalSource = signalSource;
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
}

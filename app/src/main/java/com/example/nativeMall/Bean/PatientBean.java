package com.example.nativeMall.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JTR on 2016/8/5.
 */
public class PatientBean implements Parcelable {
    /**
     * id : 86
     * patientAge : 23
     * patientId : 320212345678908523
     * patientName : 看看
     * patientAddress : 1518
     * isDefault : n
     * patientSex : 0
     * patientPhoneno : 18301721508
     */

    private String id;
    private String patientAge;
    private String patientId;
    private String patientName;
    private String patientAddress;
    private String isDefault;
    private String patientSex;
    private String patientPhoneno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientPhoneno() {
        return patientPhoneno;
    }

    public void setPatientPhoneno(String patientPhoneno) {
        this.patientPhoneno = patientPhoneno;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.patientAge);
        dest.writeString(this.patientId);
        dest.writeString(this.patientName);
        dest.writeString(this.patientAddress);
        dest.writeString(this.isDefault);
        dest.writeString(this.patientSex);
        dest.writeString(this.patientPhoneno);
    }

    public PatientBean() {
    }

    protected PatientBean(Parcel in) {
        this.id = in.readString();
        this.patientAge = in.readString();
        this.patientId = in.readString();
        this.patientName = in.readString();
        this.patientAddress = in.readString();
        this.isDefault = in.readString();
        this.patientSex = in.readString();
        this.patientPhoneno = in.readString();
    }

    public static final Creator<PatientBean> CREATOR = new Creator<PatientBean>() {
        @Override
        public PatientBean createFromParcel(Parcel source) {
            return new PatientBean(source);
        }

        @Override
        public PatientBean[] newArray(int size) {
            return new PatientBean[size];
        }
    };
}

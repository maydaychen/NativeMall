package com.example.nativeMall.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Du on 2016/8/25.
 */

/**  ！！  Map中的Key如下：
 * doctorCode: "doctor_liujingyuan",
 * doctorLevel: "专家",
 * doctorId: "76",
 * doctorField: "中医外科",
 * hospitalName: "国医馆",
 * hospitalId: "1",
 * firstDeptName: "中医科",
 * secondDeptName: "中医外科",
 * secondDeptId: "8",
 * doctorDesc: "中医外科",
 * doctorUrl: "/shgyg/userfiles/1/images/img/2016/06/4b66ade8jw1e5kdk45ffej207r07rt9a(1).jpg",
 * doctorName: "刘景元",
 * orderCount: 0,
 * firstDeptId: "1"
 */
public class MapBean implements Parcelable {


    private Map<String, Object> map = new HashMap<>();


    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(map);

    }

    public MapBean() {
    }

    protected MapBean(Parcel in) {

        this.map = in.readHashMap(HashMap.class.getClassLoader());
    }

    public static final Creator<MapBean> CREATOR = new Creator<MapBean>() {
        @Override
        public MapBean createFromParcel(Parcel source) {
            MapBean mapBean = new MapBean();
            mapBean.map = source.readHashMap(HashMap.class.getClassLoader());
            return mapBean;
        }

        @Override
        public MapBean[] newArray(int size) {
            return null;
        }
    };
}


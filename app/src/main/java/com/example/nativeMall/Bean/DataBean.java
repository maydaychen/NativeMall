package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by Du on 2016/8/25.
 */
public class DataBean {

    private String workTime;
    private List<TimeAndCostBean> data;

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public List<TimeAndCostBean> getData() {
        return data;
    }

    public void setData(List<TimeAndCostBean> data) {
        this.data = data;
    }
}

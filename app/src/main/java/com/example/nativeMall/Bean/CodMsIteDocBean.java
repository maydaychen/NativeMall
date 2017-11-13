package com.example.nativeMall.Bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Du on 2016/8/25.
 */
public class CodMsIteDocBean {

    private int CODE;
    private String MSG;
    private List<DateDataBean> ITEMS;
    private Map<String,Object> doctor;

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public List<DateDataBean> getITEMS() {
        return ITEMS;
    }

    public void setITEMS(List<DateDataBean> ITEMS) {
        this.ITEMS = ITEMS;
    }

    public Map<String, Object> getDoctor() {
        return doctor;
    }

    public void setDoctor(Map<String, Object> doctor) {
        this.doctor = doctor;
    }
}

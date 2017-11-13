package com.example.nativeMall.Bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Du on 2016/9/27.
 */
public class DataMsgSucesBean {


    private List<Map<String, Object>> data;
    private String msg;
    private String success;

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

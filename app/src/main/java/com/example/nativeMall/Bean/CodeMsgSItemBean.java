package com.example.nativeMall.Bean;

import java.util.Map;

/**
 * Created by Du on 2016/9/8.
 */
public class CodeMsgSItemBean {

    private int CODE;
    private String MSG;

    public int getCODE() {
        return CODE;
    }

    public String getMSG() {
        return MSG;
    }

    public Map<String, Object> getITEMS() {
        return ITEMS;
    }

    private Map<String, Object> ITEMS;
}

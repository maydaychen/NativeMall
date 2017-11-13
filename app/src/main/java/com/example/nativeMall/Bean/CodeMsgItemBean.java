package com.example.nativeMall.Bean;

import java.util.Map;

/**
 * Created by Du on 2016/9/14.
 */
public class CodeMsgItemBean {

    private int CODE;
    private String MSG;

    public int getCODE() {
        return CODE;
    }

    public String getMSG() {
        return MSG;
    }

    public Map<String, Object> getITEM() {
        return ITEM;
    }

    private Map<String, Object> ITEM;
}


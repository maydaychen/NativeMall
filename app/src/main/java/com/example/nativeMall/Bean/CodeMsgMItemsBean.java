package com.example.nativeMall.Bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Du on 2016/8/22.
 */
public class CodeMsgMItemsBean {

    private int CODE;
    private String MSG;
    private List<Map<String, Object>> ITEMS;

    public int getCODE() {
        return CODE;
    }

    public String getMSG() {
        return MSG;
    }

    public List<Map<String, Object>> getITEMS() {
        return ITEMS;
    }
}

package com.example.nativeMall.Bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Du on 2016/9/27.
 */
public class HeadStoresBean {

    private Map<String ,Object> head;
    private List<StoreBean> stores;

    public List<StoreBean> getStores() {
        return stores;
    }

    public void setStores(List<StoreBean> stores) {
        this.stores = stores;
    }

    public Map<String, Object> getHead() {
        return head;
    }

    public void setHead(Map<String, Object> head) {
        this.head = head;
    }
}

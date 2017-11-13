package com.example.nativeMall.Bean;

/**
 * 作者：Du on 2016/9/27 10:48
 * 邮箱：2091320109@qq.com
 */

import java.io.Serializable;

/**
 * "storeid": 94
 * "name": "国大药房"
 * "phone": ""
 * "mobile": "13800000000"
 * "logo": ""
 * "address": "福州路221号"
 * "distance": null
 */
public class StoreBean implements Serializable{

    private String storeid;
    private String name;
    private String phone;
    private String mobile;
    private String logo;
    private String address;
    private String distance;

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

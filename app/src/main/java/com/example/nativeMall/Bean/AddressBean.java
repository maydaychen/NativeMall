package com.example.nativeMall.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：JTR on 2016/9/23 15:54
 * 邮箱：2091320109@qq.com
 */
public class AddressBean {
    /**
     * data : [{"uid":50,"provincename":"北京市","default":"11","said":78,"regionid":375,"address":"阿鲁巴","consignee":"阿鲁","name":"东城区","provinceid":1,"cityname":"北京市","cityid":35,"mobile":"18301721508"},{"uid":50,"provincename":"北京市","default":"0","said":79,"regionid":375,"address":"力量航路","consignee":"搜索","name":"东城区","provinceid":1,"cityname":"北京市","cityid":35,"mobile":"13412312312"}]
     * msg : success
     * success : T
     */

    private String msg;
    private String success;
    /**
     * uid : 50
     * provincename : 北京市
     * default : 11
     * said : 78
     * regionid : 375
     * address : 阿鲁巴
     * consignee : 阿鲁
     * name : 东城区
     * provinceid : 1
     * cityname : 北京市
     * cityid : 35
     * mobile : 18301721508
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private int uid;
        private String provincename;
        @SerializedName("default")
        private String defaultX;
        private int said;
        private int regionid;
        private String address;
        private String consignee;
        private String name;
        private int provinceid;
        private String cityname;
        private int cityid;
        private String mobile;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getProvincename() {
            return provincename;
        }

        public void setProvincename(String provincename) {
            this.provincename = provincename;
        }

        public String getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(String defaultX) {
            this.defaultX = defaultX;
        }

        public int getSaid() {
            return said;
        }

        public void setSaid(int said) {
            this.said = said;
        }

        public int getRegionid() {
            return regionid;
        }

        public void setRegionid(int regionid) {
            this.regionid = regionid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(int provinceid) {
            this.provinceid = provinceid;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public int getCityid() {
            return cityid;
        }

        public void setCityid(int cityid) {
            this.cityid = cityid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}

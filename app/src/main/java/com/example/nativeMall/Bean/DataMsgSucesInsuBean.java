package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Du on 2016/9/28.
 */
public class DataMsgSucesInsuBean {

    private InsuranceBean data;
    private String msg;
    private String success;

    public InsuranceBean getData() {
        return data;
    }

    public void setData(InsuranceBean data) {
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

    public static class InsuranceBean implements Serializable{

        private String logo;
        private String compId;
        private String memo;
        private List<InsuranceItemBean> details;
        private String name;
        private String url;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCompId() {
            return compId;
        }

        public void setCompId(String compId) {
            this.compId = compId;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public List<InsuranceItemBean> getDetails() {
            return details;
        }

        public void setDetails(List<InsuranceItemBean> details) {
            this.details = details;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }


    }
    public static class InsuranceItemBean implements Serializable{

        private String price;
        private String bzservice;
        private String ment;
        private String salenum;
        private String cbage;
        private String pname;
        private String dmemo;
        private String typeId;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBzservice() {
            return bzservice;
        }

        public void setBzservice(String bzservice) {
            this.bzservice = bzservice;
        }

        public String getMent() {
            return ment;
        }

        public void setMent(String ment) {
            this.ment = ment;
        }

        public String getSalenum() {
            return salenum;
        }

        public void setSalenum(String salenum) {
            this.salenum = salenum;
        }

        public String getCbage() {
            return cbage;
        }

        public void setCbage(String cbage) {
            this.cbage = cbage;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getDmemo() {
            return dmemo;
        }

        public void setDmemo(String dmemo) {
            this.dmemo = dmemo;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }
    }
}

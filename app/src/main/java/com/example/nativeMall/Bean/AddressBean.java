package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：JTR on 2016/9/23 15:54
 * 邮箱：2091320109@qq.com
 */
public class AddressBean {
    /**
     * statusCode : 1
     * result : {"list":[{"id":"1","uniacid":"2","openid":"ucb1cc5ac6c42c0f1e5dfe4c17d6f687f","realname":"chenyi","mobile":"13665137658","province":"北京市","city":"北京市","area":"东城区","address":"1518","isdefault":"1","zipcode":"","deleted":"0"}]}
     */

    private int statusCode;
    private ResultBean result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 1
             * uniacid : 2
             * openid : ucb1cc5ac6c42c0f1e5dfe4c17d6f687f
             * realname : chenyi
             * mobile : 13665137658
             * province : 北京市
             * city : 北京市
             * area : 东城区
             * address : 1518
             * isdefault : 1
             * zipcode :
             * deleted : 0
             */

            private String id;
            private String uniacid;
            private String openid;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;
            private String isdefault;
            private String zipcode;
            private String deleted;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUniacid() {
                return uniacid;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(String isdefault) {
                this.isdefault = isdefault;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }
        }
    }
}

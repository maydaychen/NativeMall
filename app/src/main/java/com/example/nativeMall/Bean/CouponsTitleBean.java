package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2017/12/9.
 */

public class CouponsTitleBean {
    /**
     * statusCode : 1
     * result : [{"id":0,"uniacid":2,"name":"全部优惠券","displayorder":0,"status":1},{"id":"17","uniacid":"2","name":"抵用券","displayorder":"0","status":"1"},{"id":"18","uniacid":"2","name":"现金券","displayorder":"0","status":"1"},{"id":"19","uniacid":"2","name":"新用户券","displayorder":"0","status":"1"},{"id":"20","uniacid":"2","name":"老用户券","displayorder":"0","status":"1"},{"id":"21","uniacid":"2","name":"国庆活动券","displayorder":"0","status":"1"}]
     */

    private int statusCode;
    private List<ResultBean> result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 0
         * uniacid : 2
         * name : 全部优惠券
         * displayorder : 0
         * status : 1
         */

        private int id;
        private int uniacid;
        private String name;
        private int displayorder;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUniacid() {
            return uniacid;
        }

        public void setUniacid(int uniacid) {
            this.uniacid = uniacid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(int displayorder) {
            this.displayorder = displayorder;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

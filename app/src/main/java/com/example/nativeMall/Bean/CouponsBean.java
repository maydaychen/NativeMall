package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/12/9.
 */

public class CouponsBean implements Serializable{
    /**
     * statusCode : 1
     * result : [{"id":"880","couponid":"42","gettime":"1512789733","timelimit":"1","timedays":"5","timestart":"1506787200","timeend":"1513785599","thumb":"","couponname":"礼品券","enough":"25.00","backtype":"0","deduct":"5.00","discount":"9.00","backmoney":"","backcredit":"","backredpack":"","bgcolor":"","free":false,"past":false,"getstatus":3,"gettypestr":"领取","timestr":"2017-12-20","css":"deduct","backstr":"立减","backpre":true,"_backmoney":"5.00"}]
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

    public static class ResultBean implements Serializable{
        /**
         * id : 880
         * couponid : 42
         * gettime : 1512789733
         * timelimit : 1
         * timedays : 5
         * timestart : 1506787200
         * timeend : 1513785599
         * thumb :
         * couponname : 礼品券
         * enough : 25.00
         * backtype : 0
         * deduct : 5.00
         * discount : 9.00
         * backmoney :
         * backcredit :
         * backredpack :
         * bgcolor :
         * free : false
         * past : false
         * getstatus : 3
         * gettypestr : 领取
         * timestr : 2017-12-20
         * css : deduct
         * backstr : 立减
         * backpre : true
         * _backmoney : 5.00
         */

        private String id;
        private String couponid;
        private String gettime;
        private String timelimit;
        private String timedays;
        private String timestart;
        private String timeend;
        private String thumb;
        private String couponname;
        private String enough;
        private String backtype;
        private String deduct;
        private String discount;
        private String backmoney;
        private String backcredit;
        private String backredpack;
        private String bgcolor;
        private boolean free;
        private boolean past;
        private int getstatus;
        private String gettypestr;
        private String timestr;
        private String css;
        private String backstr;
        private boolean backpre;
        private String _backmoney;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCouponid() {
            return couponid;
        }

        public void setCouponid(String couponid) {
            this.couponid = couponid;
        }

        public String getGettime() {
            return gettime;
        }

        public void setGettime(String gettime) {
            this.gettime = gettime;
        }

        public String getTimelimit() {
            return timelimit;
        }

        public void setTimelimit(String timelimit) {
            this.timelimit = timelimit;
        }

        public String getTimedays() {
            return timedays;
        }

        public void setTimedays(String timedays) {
            this.timedays = timedays;
        }

        public String getTimestart() {
            return timestart;
        }

        public void setTimestart(String timestart) {
            this.timestart = timestart;
        }

        public String getTimeend() {
            return timeend;
        }

        public void setTimeend(String timeend) {
            this.timeend = timeend;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getCouponname() {
            return couponname;
        }

        public void setCouponname(String couponname) {
            this.couponname = couponname;
        }

        public String getEnough() {
            return enough;
        }

        public void setEnough(String enough) {
            this.enough = enough;
        }

        public String getBacktype() {
            return backtype;
        }

        public void setBacktype(String backtype) {
            this.backtype = backtype;
        }

        public String getDeduct() {
            return deduct;
        }

        public void setDeduct(String deduct) {
            this.deduct = deduct;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getBackmoney() {
            return backmoney;
        }

        public void setBackmoney(String backmoney) {
            this.backmoney = backmoney;
        }

        public String getBackcredit() {
            return backcredit;
        }

        public void setBackcredit(String backcredit) {
            this.backcredit = backcredit;
        }

        public String getBackredpack() {
            return backredpack;
        }

        public void setBackredpack(String backredpack) {
            this.backredpack = backredpack;
        }

        public String getBgcolor() {
            return bgcolor;
        }

        public void setBgcolor(String bgcolor) {
            this.bgcolor = bgcolor;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public boolean isPast() {
            return past;
        }

        public void setPast(boolean past) {
            this.past = past;
        }

        public int getGetstatus() {
            return getstatus;
        }

        public void setGetstatus(int getstatus) {
            this.getstatus = getstatus;
        }

        public String getGettypestr() {
            return gettypestr;
        }

        public void setGettypestr(String gettypestr) {
            this.gettypestr = gettypestr;
        }

        public String getTimestr() {
            return timestr;
        }

        public void setTimestr(String timestr) {
            this.timestr = timestr;
        }

        public String getCss() {
            return css;
        }

        public void setCss(String css) {
            this.css = css;
        }

        public String getBackstr() {
            return backstr;
        }

        public void setBackstr(String backstr) {
            this.backstr = backstr;
        }

        public boolean isBackpre() {
            return backpre;
        }

        public void setBackpre(boolean backpre) {
            this.backpre = backpre;
        }

        public String get_backmoney() {
            return _backmoney;
        }

        public void set_backmoney(String _backmoney) {
            this._backmoney = _backmoney;
        }
    }
}

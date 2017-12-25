package com.example.nativeMall.Bean;

/**
 * Created by user on 2017/12/12.
 */

public class CouponDetailBean {
    /**
     * statusCode : 1
     * result : {"id":"42","uniacid":"2","catid":"18","couponname":"礼品券","gettype":"1","getmax":"5","usetype":"0","returntype":"0","bgcolor":"","enough":"25.00","timelimit":"1","coupontype":"0","timedays":"5","timestart":"1506787200","timeend":"1513785599","discount":"9.00","deduct":"5.00","backtype":"0","backmoney":"","backcredit":"","backredpack":"","backwhen":"0","thumb":"","desc":"<p>此券为线下活动礼品兑换券，也可在商城当现金使用<\/p>","createtime":"1504831073","total":"10000","status":"0","money":"0.00","respdesc":"","respthumb":"","resptitle":"","respurl":"","credit":"0","usecredit2":"0","remark":"","descnoset":"1","pwdkey":"","pwdsuc":"","pwdfail":"","pwdurl":"","pwdask":"","pwdstatus":"0","pwdtimes":"0","pwdfull":"","pwdwords":"","pwdopen":"0","pwdown":"","pwdexit":"","pwdexitstr":"","displayorder":"0","supplier_uid":"0","getcashier":"0","cashiersids":"N;","cashiersnames":"N;","categoryids":"a:1:{i:0;s:0:\"\";}","categorynames":"a:1:{i:0;s:0:\"\";}","goodsnames":"a:1:{i:0;s:74:\"2017年新款Apple/苹果 iPad平板电脑9.7英寸 32G/128G air2升级版\";}","goodsids":"a:1:{i:0;s:1:\"3\";}","storeids":"N;","storenames":"N;","getstore":"0","getsupplier":"0","supplierids":"N;","suppliernames":"N;","lasttotal":9942,"free":false,"past":false,"getstatus":3,"gettypestr":"领取","timestr":"2017-12-20","css":"deduct","backstr":"立减","backpre":true,"_backmoney":"5.00","cangetmax":3,"canget":true}
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
        /**
         * id : 42
         * uniacid : 2
         * catid : 18
         * couponname : 礼品券
         * gettype : 1
         * getmax : 5
         * usetype : 0
         * returntype : 0
         * bgcolor :
         * enough : 25.00
         * timelimit : 1
         * coupontype : 0
         * timedays : 5
         * timestart : 1506787200
         * timeend : 1513785599
         * discount : 9.00
         * deduct : 5.00
         * backtype : 0
         * backmoney :
         * backcredit :
         * backredpack :
         * backwhen : 0
         * thumb :
         * desc : <p>此券为线下活动礼品兑换券，也可在商城当现金使用</p>
         * createtime : 1504831073
         * total : 10000
         * status : 0
         * money : 0.00
         * respdesc :
         * respthumb :
         * resptitle :
         * respurl :
         * credit : 0
         * usecredit2 : 0
         * remark :
         * descnoset : 1
         * pwdkey :
         * pwdsuc :
         * pwdfail :
         * pwdurl :
         * pwdask :
         * pwdstatus : 0
         * pwdtimes : 0
         * pwdfull :
         * pwdwords :
         * pwdopen : 0
         * pwdown :
         * pwdexit :
         * pwdexitstr :
         * displayorder : 0
         * supplier_uid : 0
         * getcashier : 0
         * cashiersids : N;
         * cashiersnames : N;
         * categoryids : a:1:{i:0;s:0:"";}
         * categorynames : a:1:{i:0;s:0:"";}
         * goodsnames : a:1:{i:0;s:74:"2017年新款Apple/苹果 iPad平板电脑9.7英寸 32G/128G air2升级版";}
         * goodsids : a:1:{i:0;s:1:"3";}
         * storeids : N;
         * storenames : N;
         * getstore : 0
         * getsupplier : 0
         * supplierids : N;
         * suppliernames : N;
         * lasttotal : 9942
         * free : false
         * past : false
         * getstatus : 3
         * gettypestr : 领取
         * timestr : 2017-12-20
         * css : deduct
         * backstr : 立减
         * backpre : true
         * _backmoney : 5.00
         * cangetmax : 3
         * canget : true
         */

        private String id;
        private String uniacid;
        private String catid;
        private String couponname;
        private String gettype;
        private String getmax;
        private String usetype;
        private String returntype;
        private String bgcolor;
        private String enough;
        private String timelimit;
        private String coupontype;
        private String timedays;
        private String timestart;
        private String timeend;
        private String discount;
        private String deduct;
        private String backtype;
        private String backmoney;
        private String backcredit;
        private String backredpack;
        private String backwhen;
        private String thumb;
        private String desc;
        private String createtime;
        private String total;
        private String status;
        private String money;
        private String respdesc;
        private String respthumb;
        private String resptitle;
        private String respurl;
        private String credit;
        private String usecredit2;
        private String remark;
        private String descnoset;
        private String pwdkey;
        private String pwdsuc;
        private String pwdfail;
        private String pwdurl;
        private String pwdask;
        private String pwdstatus;
        private String pwdtimes;
        private String pwdfull;
        private String pwdwords;
        private String pwdopen;
        private String pwdown;
        private String pwdexit;
        private String pwdexitstr;
        private String displayorder;
        private String supplier_uid;
        private String getcashier;
        private String cashiersids;
        private String cashiersnames;
        private String categoryids;
        private String categorynames;
        private String goodsnames;
        private String goodsids;
        private String storeids;
        private String storenames;
        private String getstore;
        private String getsupplier;
        private String supplierids;
        private String suppliernames;
        private int lasttotal;
        private boolean free;
        private boolean past;
        private int getstatus;
        private String gettypestr;
        private String timestr;
        private String css;
        private String backstr;
        private boolean backpre;
        private String _backmoney;
        private int cangetmax;
        private boolean canget;

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

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getCouponname() {
            return couponname;
        }

        public void setCouponname(String couponname) {
            this.couponname = couponname;
        }

        public String getGettype() {
            return gettype;
        }

        public void setGettype(String gettype) {
            this.gettype = gettype;
        }

        public String getGetmax() {
            return getmax;
        }

        public void setGetmax(String getmax) {
            this.getmax = getmax;
        }

        public String getUsetype() {
            return usetype;
        }

        public void setUsetype(String usetype) {
            this.usetype = usetype;
        }

        public String getReturntype() {
            return returntype;
        }

        public void setReturntype(String returntype) {
            this.returntype = returntype;
        }

        public String getBgcolor() {
            return bgcolor;
        }

        public void setBgcolor(String bgcolor) {
            this.bgcolor = bgcolor;
        }

        public String getEnough() {
            return enough;
        }

        public void setEnough(String enough) {
            this.enough = enough;
        }

        public String getTimelimit() {
            return timelimit;
        }

        public void setTimelimit(String timelimit) {
            this.timelimit = timelimit;
        }

        public String getCoupontype() {
            return coupontype;
        }

        public void setCoupontype(String coupontype) {
            this.coupontype = coupontype;
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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDeduct() {
            return deduct;
        }

        public void setDeduct(String deduct) {
            this.deduct = deduct;
        }

        public String getBacktype() {
            return backtype;
        }

        public void setBacktype(String backtype) {
            this.backtype = backtype;
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

        public String getBackwhen() {
            return backwhen;
        }

        public void setBackwhen(String backwhen) {
            this.backwhen = backwhen;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRespdesc() {
            return respdesc;
        }

        public void setRespdesc(String respdesc) {
            this.respdesc = respdesc;
        }

        public String getRespthumb() {
            return respthumb;
        }

        public void setRespthumb(String respthumb) {
            this.respthumb = respthumb;
        }

        public String getResptitle() {
            return resptitle;
        }

        public void setResptitle(String resptitle) {
            this.resptitle = resptitle;
        }

        public String getRespurl() {
            return respurl;
        }

        public void setRespurl(String respurl) {
            this.respurl = respurl;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getUsecredit2() {
            return usecredit2;
        }

        public void setUsecredit2(String usecredit2) {
            this.usecredit2 = usecredit2;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getDescnoset() {
            return descnoset;
        }

        public void setDescnoset(String descnoset) {
            this.descnoset = descnoset;
        }

        public String getPwdkey() {
            return pwdkey;
        }

        public void setPwdkey(String pwdkey) {
            this.pwdkey = pwdkey;
        }

        public String getPwdsuc() {
            return pwdsuc;
        }

        public void setPwdsuc(String pwdsuc) {
            this.pwdsuc = pwdsuc;
        }

        public String getPwdfail() {
            return pwdfail;
        }

        public void setPwdfail(String pwdfail) {
            this.pwdfail = pwdfail;
        }

        public String getPwdurl() {
            return pwdurl;
        }

        public void setPwdurl(String pwdurl) {
            this.pwdurl = pwdurl;
        }

        public String getPwdask() {
            return pwdask;
        }

        public void setPwdask(String pwdask) {
            this.pwdask = pwdask;
        }

        public String getPwdstatus() {
            return pwdstatus;
        }

        public void setPwdstatus(String pwdstatus) {
            this.pwdstatus = pwdstatus;
        }

        public String getPwdtimes() {
            return pwdtimes;
        }

        public void setPwdtimes(String pwdtimes) {
            this.pwdtimes = pwdtimes;
        }

        public String getPwdfull() {
            return pwdfull;
        }

        public void setPwdfull(String pwdfull) {
            this.pwdfull = pwdfull;
        }

        public String getPwdwords() {
            return pwdwords;
        }

        public void setPwdwords(String pwdwords) {
            this.pwdwords = pwdwords;
        }

        public String getPwdopen() {
            return pwdopen;
        }

        public void setPwdopen(String pwdopen) {
            this.pwdopen = pwdopen;
        }

        public String getPwdown() {
            return pwdown;
        }

        public void setPwdown(String pwdown) {
            this.pwdown = pwdown;
        }

        public String getPwdexit() {
            return pwdexit;
        }

        public void setPwdexit(String pwdexit) {
            this.pwdexit = pwdexit;
        }

        public String getPwdexitstr() {
            return pwdexitstr;
        }

        public void setPwdexitstr(String pwdexitstr) {
            this.pwdexitstr = pwdexitstr;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(String displayorder) {
            this.displayorder = displayorder;
        }

        public String getSupplier_uid() {
            return supplier_uid;
        }

        public void setSupplier_uid(String supplier_uid) {
            this.supplier_uid = supplier_uid;
        }

        public String getGetcashier() {
            return getcashier;
        }

        public void setGetcashier(String getcashier) {
            this.getcashier = getcashier;
        }

        public String getCashiersids() {
            return cashiersids;
        }

        public void setCashiersids(String cashiersids) {
            this.cashiersids = cashiersids;
        }

        public String getCashiersnames() {
            return cashiersnames;
        }

        public void setCashiersnames(String cashiersnames) {
            this.cashiersnames = cashiersnames;
        }

        public String getCategoryids() {
            return categoryids;
        }

        public void setCategoryids(String categoryids) {
            this.categoryids = categoryids;
        }

        public String getCategorynames() {
            return categorynames;
        }

        public void setCategorynames(String categorynames) {
            this.categorynames = categorynames;
        }

        public String getGoodsnames() {
            return goodsnames;
        }

        public void setGoodsnames(String goodsnames) {
            this.goodsnames = goodsnames;
        }

        public String getGoodsids() {
            return goodsids;
        }

        public void setGoodsids(String goodsids) {
            this.goodsids = goodsids;
        }

        public String getStoreids() {
            return storeids;
        }

        public void setStoreids(String storeids) {
            this.storeids = storeids;
        }

        public String getStorenames() {
            return storenames;
        }

        public void setStorenames(String storenames) {
            this.storenames = storenames;
        }

        public String getGetstore() {
            return getstore;
        }

        public void setGetstore(String getstore) {
            this.getstore = getstore;
        }

        public String getGetsupplier() {
            return getsupplier;
        }

        public void setGetsupplier(String getsupplier) {
            this.getsupplier = getsupplier;
        }

        public String getSupplierids() {
            return supplierids;
        }

        public void setSupplierids(String supplierids) {
            this.supplierids = supplierids;
        }

        public String getSuppliernames() {
            return suppliernames;
        }

        public void setSuppliernames(String suppliernames) {
            this.suppliernames = suppliernames;
        }

        public int getLasttotal() {
            return lasttotal;
        }

        public void setLasttotal(int lasttotal) {
            this.lasttotal = lasttotal;
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

        public int getCangetmax() {
            return cangetmax;
        }

        public void setCangetmax(int cangetmax) {
            this.cangetmax = cangetmax;
        }

        public boolean isCanget() {
            return canget;
        }

        public void setCanget(boolean canget) {
            this.canget = canget;
        }
    }
}

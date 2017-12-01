package com.example.nativeMall.Bean;

/**
 * Created by user on 2017/11/22.
 */

public class ConfirmBean {
    /**
     * statusCode : 1
     * result : {"openid":"u07102c8746988b66c6039542f8dc8392","agentid":"0","cash":0,"discountprice":0,"deductprice":0,"deductcredit":0,"deductcredit2":0,"deductenough":0,"status":0,"paytype":0,"transid":"","remark":"","addressid":69,"goodsprice":395472,"dispatchprice":0,"dispatchtype":0,"dispatchid":1,"carrier":"a:0:{}","createtime":1511351609,"isverify":0,"verifycode":0,"address":"a:12:{s:2:\"id\";s:2:\"69\";s:7:\"uniacid\";s:1:\"2\";s:6:\"openid\";s:33:\"u07102c8746988b66c6039542f8dc8392\";s:8:\"realname\";s:6:\"hcychc\";s:6:\"mobile\";s:11:\"13665137658\";s:8:\"province\";s:6:\"北京\";s:4:\"city\";s:9:\"北京市\";s:4:\"area\";s:9:\"西城区\";s:7:\"address\";s:9:\"gixigxgkx\";s:9:\"isdefault\";s:1:\"1\";s:7:\"zipcode\";s:0:\"\";s:7:\"deleted\";s:1:\"0\";}","price":395472,"uniacid":"2","ordersn":"SH20171122195329205776","id":"15487"}
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
         * openid : u07102c8746988b66c6039542f8dc8392
         * agentid : 0
         * cash : 0
         * discountprice : 0
         * deductprice : 0
         * deductcredit : 0
         * deductcredit2 : 0
         * deductenough : 0
         * status : 0
         * paytype : 0
         * transid :
         * remark :
         * addressid : 69
         * goodsprice : 395472
         * dispatchprice : 0
         * dispatchtype : 0
         * dispatchid : 1
         * carrier : a:0:{}
         * createtime : 1511351609
         * isverify : 0
         * verifycode : 0
         * address : a:12:{s:2:"id";s:2:"69";s:7:"uniacid";s:1:"2";s:6:"openid";s:33:"u07102c8746988b66c6039542f8dc8392";s:8:"realname";s:6:"hcychc";s:6:"mobile";s:11:"13665137658";s:8:"province";s:6:"北京";s:4:"city";s:9:"北京市";s:4:"area";s:9:"西城区";s:7:"address";s:9:"gixigxgkx";s:9:"isdefault";s:1:"1";s:7:"zipcode";s:0:"";s:7:"deleted";s:1:"0";}
         * price : 395472
         * uniacid : 2
         * ordersn : SH20171122195329205776
         * id : 15487
         */

        private String openid;
        private String agentid;
        private int cash;
        private int discountprice;
        private int deductprice;
        private int deductcredit;
        private int deductcredit2;
        private int deductenough;
        private int status;
        private int paytype;
        private String transid;
        private String remark;
        private int addressid;
        private int goodsprice;
        private int dispatchprice;
        private int dispatchtype;
        private int dispatchid;
        private String carrier;
        private int createtime;
        private int isverify;
        private int verifycode;
        private String address;
        private int price;
        private String uniacid;
        private String ordersn;
        private String id;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public int getDiscountprice() {
            return discountprice;
        }

        public void setDiscountprice(int discountprice) {
            this.discountprice = discountprice;
        }

        public int getDeductprice() {
            return deductprice;
        }

        public void setDeductprice(int deductprice) {
            this.deductprice = deductprice;
        }

        public int getDeductcredit() {
            return deductcredit;
        }

        public void setDeductcredit(int deductcredit) {
            this.deductcredit = deductcredit;
        }

        public int getDeductcredit2() {
            return deductcredit2;
        }

        public void setDeductcredit2(int deductcredit2) {
            this.deductcredit2 = deductcredit2;
        }

        public int getDeductenough() {
            return deductenough;
        }

        public void setDeductenough(int deductenough) {
            this.deductenough = deductenough;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public String getTransid() {
            return transid;
        }

        public void setTransid(String transid) {
            this.transid = transid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getAddressid() {
            return addressid;
        }

        public void setAddressid(int addressid) {
            this.addressid = addressid;
        }

        public int getGoodsprice() {
            return goodsprice;
        }

        public void setGoodsprice(int goodsprice) {
            this.goodsprice = goodsprice;
        }

        public int getDispatchprice() {
            return dispatchprice;
        }

        public void setDispatchprice(int dispatchprice) {
            this.dispatchprice = dispatchprice;
        }

        public int getDispatchtype() {
            return dispatchtype;
        }

        public void setDispatchtype(int dispatchtype) {
            this.dispatchtype = dispatchtype;
        }

        public int getDispatchid() {
            return dispatchid;
        }

        public void setDispatchid(int dispatchid) {
            this.dispatchid = dispatchid;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getIsverify() {
            return isverify;
        }

        public void setIsverify(int isverify) {
            this.isverify = isverify;
        }

        public int getVerifycode() {
            return verifycode;
        }

        public void setVerifycode(int verifycode) {
            this.verifycode = verifycode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getUniacid() {
            return uniacid;
        }

        public void setUniacid(String uniacid) {
            this.uniacid = uniacid;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

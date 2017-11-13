package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：JTR on 2016/10/14 13:42
 * 邮箱：2091320109@qq.com
 */
public class OrderDetailBean implements Serializable{
    /**
     * oid : 80
     * address : 联航路
     * addtime : 2016-10-14
     * consignee : 陈译
     * osn : MT14764207201489472
     * orderstate : 30
     * orderamount : 0.03
     * surplusmoney : 0.03
     * storeid : 94
     * storename : 国大药房
     * shipsn : null
     * shipconame : null
     * shipcoid : null
     * mobile : 18301721508
     * paymode : 1
     * details : [{"shopprice":"0.01","buycount":"1","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","pid":"203","showimg":"/userfiles/500aaa0b-e1c7-4de5-9917-1a34014113e1.jpg","realcount":null},{"shopprice":"0.01","buycount":"2","name":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","pid":"196","showimg":"/userfiles/41afa496-3c11-433c-9c1d-00ff270c38d1.jpg","realcount":null}]
     */

    private DataBean data;
    /**
     * data : {"oid":"80","address":"联航路","addtime":"2016-10-14","consignee":"陈译","osn":"MT14764207201489472","orderstate":"30","orderamount":"0.03","surplusmoney":"0.03","storeid":"94","storename":"国大药房","shipsn":null,"shipconame":null,"shipcoid":null,"mobile":"18301721508","paymode":"1","details":[{"shopprice":"0.01","buycount":"1","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","pid":"203","showimg":"/userfiles/500aaa0b-e1c7-4de5-9917-1a34014113e1.jpg","realcount":null},{"shopprice":"0.01","buycount":"2","name":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","pid":"196","showimg":"/userfiles/41afa496-3c11-433c-9c1d-00ff270c38d1.jpg","realcount":null}]}
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean implements Serializable{
        private String oid;
        private String address;
        private String addtime;
        private String consignee;
        private String osn;
        private String orderstate;
        private String orderamount;
        private String surplusmoney;
        private String storeid;
        private String storename;
        private Object shipsn;
        private Object shipconame;
        private Object shipcoid;
        private String mobile;
        private String paymode;
        private Long surplus;

        /**
         * shopprice : 0.01
         * buycount : 1
         * name : 京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品
         * pid : 203
         * showimg : /userfiles/500aaa0b-e1c7-4de5-9917-1a34014113e1.jpg
         * realcount : null
         */

        public void setSurplus(Long surplus) {
            this.surplus = surplus;
        }

        public Long getSurplus() {
            return surplus;
        }

        private List<DetailsBean> details;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getOsn() {
            return osn;
        }

        public void setOsn(String osn) {
            this.osn = osn;
        }

        public String getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(String orderstate) {
            this.orderstate = orderstate;
        }

        public String getOrderamount() {
            return orderamount;
        }

        public void setOrderamount(String orderamount) {
            this.orderamount = orderamount;
        }

        public String getSurplusmoney() {
            return surplusmoney;
        }

        public void setSurplusmoney(String surplusmoney) {
            this.surplusmoney = surplusmoney;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public Object getShipsn() {
            return shipsn;
        }

        public void setShipsn(Object shipsn) {
            this.shipsn = shipsn;
        }

        public Object getShipconame() {
            return shipconame;
        }

        public void setShipconame(Object shipconame) {
            this.shipconame = shipconame;
        }

        public Object getShipcoid() {
            return shipcoid;
        }

        public void setShipcoid(Object shipcoid) {
            this.shipcoid = shipcoid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPaymode() {
            return paymode;
        }

        public void setPaymode(String paymode) {
            this.paymode = paymode;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean implements Serializable{
            private String shopprice;
            private String buycount;
            private String name;
            private String pid;
            private String showimg;
            private Object realcount;

            public String getShopprice() {
                return shopprice;
            }

            public void setShopprice(String shopprice) {
                this.shopprice = shopprice;
            }

            public String getBuycount() {
                return buycount;
            }

            public void setBuycount(String buycount) {
                this.buycount = buycount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getShowimg() {
                return showimg;
            }

            public void setShowimg(String showimg) {
                this.showimg = showimg;
            }

            public Object getRealcount() {
                return realcount;
            }

            public void setRealcount(Object realcount) {
                this.realcount = realcount;
            }
        }
    }
}

package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/12/5 14:34
 * 邮箱：2091320109@qq.com
 */
public class ShoppingCarBean {
    /**
     * data : [{"proList":[{"recordid":"553","uid":"77","pid":"254","psn":"","storeid":"123","name":"小米  3G  黑色","showimg":"/userfiles/fa09e431-e505-43cf-8e9e-730a6aece87d.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"1","sname":"小米专卖"},{"recordid":"547","uid":"77","pid":"253","psn":"","storeid":"123","name":"小米  3G  白色","showimg":"/userfiles/637f0223-3e0f-46b0-8e64-44dd0a649232.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"1","sname":"小米专卖"},{"recordid":"541","uid":"77","pid":"254","psn":"","storeid":"123","name":"小米  3G  黑色","showimg":"/userfiles/fa09e431-e505-43cf-8e9e-730a6aece87d.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"2","sname":"小米专卖"}],"sName":"小米专卖"},{"proList":[{"recordid":"538","uid":"77","pid":"203","psn":"10000008","storeid":"94","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","showimg":"/userfiles/500aaa0b-e1c7-4de5-9917-1a34014113e1.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"8","sname":"国大药房"},{"recordid":"535","uid":"77","pid":"195","psn":"10000004","storeid":"94","name":"沐舒坦盐酸氨溴索口服溶液100ml止咳化痰用于痰液粘稠难咳出者","showimg":"/userfiles/22757592-daa6-4c8b-8b19-a3e1aada55bf.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"1","sname":"国大药房"},{"recordid":"500","uid":"77","pid":"196","psn":"10000005","storeid":"94","name":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","showimg":"/userfiles/41afa496-3c11-433c-9c1d-00ff270c38d1.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"2","sname":"国大药房"},{"recordid":"499","uid":"77","pid":"201","psn":"10000006","storeid":"94","name":"同仁堂 银翘解毒片 40片风热感冒发热头痛咽喉痛感冒药咳嗽","showimg":"/userfiles/c9b970d6-a2f6-4d41-b466-71932242f2a0.jpg","discountprice":"0.00","shopprice":"0.02","weight":"0","realcount":"0","buycount":"1","sname":"国大药房"}],"sName":"国大药房"}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
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

    public static class DataBean {
        /**
         * proList : [{"recordid":"553","uid":"77","pid":"254","psn":"","storeid":"123","name":"小米  3G  黑色","showimg":"/userfiles/fa09e431-e505-43cf-8e9e-730a6aece87d.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"1","sname":"小米专卖"},{"recordid":"547","uid":"77","pid":"253","psn":"","storeid":"123","name":"小米  3G  白色","showimg":"/userfiles/637f0223-3e0f-46b0-8e64-44dd0a649232.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"1","sname":"小米专卖"},{"recordid":"541","uid":"77","pid":"254","psn":"","storeid":"123","name":"小米  3G  黑色","showimg":"/userfiles/fa09e431-e505-43cf-8e9e-730a6aece87d.jpg","discountprice":"0.00","shopprice":"0.01","weight":"0","realcount":"0","buycount":"2","sname":"小米专卖"}]
         * sName : 小米专卖
         */

        private String sName;
        private List<ProListBean> proList;

        public String getSName() {
            return sName;
        }

        public void setSName(String sName) {
            this.sName = sName;
        }

        public List<ProListBean> getProList() {
            return proList;
        }

        public void setProList(List<ProListBean> proList) {
            this.proList = proList;
        }

        public static class ProListBean {
            /**
             * recordid : 553
             * uid : 77
             * pid : 254
             * psn :
             * storeid : 123
             * name : 小米  3G  黑色
             * showimg : /userfiles/fa09e431-e505-43cf-8e9e-730a6aece87d.jpg
             * discountprice : 0.00
             * shopprice : 0.01
             * weight : 0
             * realcount : 0
             * buycount : 1
             * sname : 小米专卖
             */

            private String recordid;
            private String uid;
            private String pid;
            private String psn;
            private String storeid;
            private String name;
            private String showimg;
            private String discountprice;
            private String shopprice;
            private String weight;
            private String realcount;
            private String buycount;
            private String sname;

            public String getRecordid() {
                return recordid;
            }

            public void setRecordid(String recordid) {
                this.recordid = recordid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getPsn() {
                return psn;
            }

            public void setPsn(String psn) {
                this.psn = psn;
            }

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

            public String getShowimg() {
                return showimg;
            }

            public void setShowimg(String showimg) {
                this.showimg = showimg;
            }

            public String getDiscountprice() {
                return discountprice;
            }

            public void setDiscountprice(String discountprice) {
                this.discountprice = discountprice;
            }

            public String getShopprice() {
                return shopprice;
            }

            public void setShopprice(String shopprice) {
                this.shopprice = shopprice;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getRealcount() {
                return realcount;
            }

            public void setRealcount(String realcount) {
                this.realcount = realcount;
            }

            public String getBuycount() {
                return buycount;
            }

            public void setBuycount(String buycount) {
                this.buycount = buycount;
            }

            public String getSname() {
                return sname;
            }

            public void setSname(String sname) {
                this.sname = sname;
            }
        }
    }
}

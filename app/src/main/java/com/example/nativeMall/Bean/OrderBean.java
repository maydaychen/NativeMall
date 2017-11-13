package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/18 17:11
 * 邮箱：2091320109@qq.com
 */
public class OrderBean {
    /**
     * data : [{"oid":"90","osn":"14741882213029450","uid":"50","storename":"国大药房","orderstate":"30","paysystemname":null,"paytime":null,"shipfee":"0.00","payfee":"498.00","details":[{"recordid":"87","oid":"90","uid":"50","sid":null,"pid":"193","psn":"10000002","name":"澳琳达袋鼠精胶囊","showimg":"/imgservice/userfiles/cabcc60ed2014ee4bf657cbc8dabbbc9.jpg","shopprice":"498.00","buycount":"1","addtime":"2016-09-18"}]}]
     * msg : success
     * success : T
     */

    private String msg;
    private String success;
    /**
     * oid : 90
     * osn : 14741882213029450
     * uid : 50
     * storename : 国大药房
     * orderstate : 30
     * paysystemname : null
     * paytime : null
     * shipfee : 0.00
     * payfee : 498.00
     * details : [{"recordid":"87","oid":"90","uid":"50","sid":null,"pid":"193","psn":"10000002","name":"澳琳达袋鼠精胶囊","showimg":"/imgservice/userfiles/cabcc60ed2014ee4bf657cbc8dabbbc9.jpg","shopprice":"498.00","buycount":"1","addtime":"2016-09-18"}]
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

    public static class DataBean {
        private String oid;
        private String osn;
        private String uid;
        private String storename;
        private String orderstate;
        private Object paysystemname;
        private Object paytime;
        private String shipfee;
        private String payfee;
        /**
         * recordid : 87
         * oid : 90
         * uid : 50
         * sid : null
         * pid : 193
         * psn : 10000002
         * name : 澳琳达袋鼠精胶囊
         * showimg : /imgservice/userfiles/cabcc60ed2014ee4bf657cbc8dabbbc9.jpg
         * shopprice : 498.00
         * buycount : 1
         * addtime : 2016-09-18
         */

        private List<DetailsBean> details;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getOsn() {
            return osn;
        }

        public void setOsn(String osn) {
            this.osn = osn;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStorename() {
            return storename;
        }

        public void setStorename(String storename) {
            this.storename = storename;
        }

        public String getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(String orderstate) {
            this.orderstate = orderstate;
        }

        public Object getPaysystemname() {
            return paysystemname;
        }

        public void setPaysystemname(Object paysystemname) {
            this.paysystemname = paysystemname;
        }

        public Object getPaytime() {
            return paytime;
        }

        public void setPaytime(Object paytime) {
            this.paytime = paytime;
        }

        public String getShipfee() {
            return shipfee;
        }

        public void setShipfee(String shipfee) {
            this.shipfee = shipfee;
        }

        public String getPayfee() {
            return payfee;
        }

        public void setPayfee(String payfee) {
            this.payfee = payfee;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            private String recordid;
            private String oid;
            private String uid;
            private Object sid;
            private String pid;
            private String psn;
            private String name;
            private String showimg;
            private String shopprice;
            private String buycount;
            private String addtime;

            public String getRecordid() {
                return recordid;
            }

            public void setRecordid(String recordid) {
                this.recordid = recordid;
            }

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public Object getSid() {
                return sid;
            }

            public void setSid(Object sid) {
                this.sid = sid;
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

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}

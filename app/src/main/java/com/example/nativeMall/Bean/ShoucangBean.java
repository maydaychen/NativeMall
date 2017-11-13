package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/29 15:22
 * 邮箱：2091320109@qq.com
 */
public class ShoucangBean {
    /**
     * data : [{"uid":57,"storeid":"94","shopprice":54,"name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","pid":203,"salecount":"0","sname":"国大药房","showimg":"/userfiles/500aaa0b-e1c7-4de5-9917-1a34014113e1.jpg","reviewcount":"0","recordid":26},{"uid":57,"storeid":"94","shopprice":23,"name":"同仁堂 银翘解毒片 40片风热感冒发热头痛咽喉痛感冒药咳嗽","pid":201,"salecount":"0","sname":"国大药房","showimg":"/userfiles/c9b970d6-a2f6-4d41-b466-71932242f2a0.jpg","reviewcount":"0","recordid":27}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * uid : 57
     * storeid : 94
     * shopprice : 54
     * name : 京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品
     * pid : 203
     * salecount : 0
     * sname : 国大药房
     * showimg : /userfiles/500aaa0b-e1c7-4de5-9917-1a34014113e1.jpg
     * reviewcount : 0
     * recordid : 26
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
        private int uid;
        private String storeid;
        private String shopprice;
        private String name;
        private int pid;
        private String salecount;
        private String sname;
        private String showimg;
        private String reviewcount;
        private int recordid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getStoreid() {
            return storeid;
        }

        public void setStoreid(String storeid) {
            this.storeid = storeid;
        }

        public String getShopprice() {
            return shopprice;
        }

        public void setShopprice(String shopprice) {
            this.shopprice = shopprice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getSalecount() {
            return salecount;
        }

        public void setSalecount(String salecount) {
            this.salecount = salecount;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getShowimg() {
            return showimg;
        }

        public void setShowimg(String showimg) {
            this.showimg = showimg;
        }

        public String getReviewcount() {
            return reviewcount;
        }

        public void setReviewcount(String reviewcount) {
            this.reviewcount = reviewcount;
        }

        public int getRecordid() {
            return recordid;
        }

        public void setRecordid(int recordid) {
            this.recordid = recordid;
        }
    }
}

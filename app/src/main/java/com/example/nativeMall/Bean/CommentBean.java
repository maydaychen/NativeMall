package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/10/28 15:32
 * 邮箱：2091320109@qq.com
 */
public class CommentBean {
    /**
     * data : [{"reviewid":15,"star":5,"quality":5,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"5"},{"reviewid":16,"star":3,"quality":3,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"3"},{"reviewid":17,"star":1,"quality":1,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"1"},{"reviewid":18,"star":1,"quality":1,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"1"},{"reviewid":19,"star":2,"quality":2,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"2"},{"reviewid":20,"star":4,"quality":4,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"4"},{"reviewid":24,"star":5,"quality":5,"uid":77,"storeid":94,"message":null,"sname":"国大药房","name":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","username":null,"reviewtime":"2016-10-28","speed":"5"}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * reviewid : 15
     * star : 5
     * quality : 5
     * uid : 77
     * storeid : 94
     * message : null
     * sname : 国大药房
     * name : 京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品
     * username : null
     * reviewtime : 2016-10-28
     * speed : 5
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
        private int reviewid;
        private int star;
        private int quality;
        private int uid;
        private int storeid;
        private Object message;
        private String sname;
        private String name;
        private Object username;
        private String reviewtime;
        private String speed;

        public int getReviewid() {
            return reviewid;
        }

        public void setReviewid(int reviewid) {
            this.reviewid = reviewid;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getStoreid() {
            return storeid;
        }

        public void setStoreid(int storeid) {
            this.storeid = storeid;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public String getReviewtime() {
            return reviewtime;
        }

        public void setReviewtime(String reviewtime) {
            this.reviewtime = reviewtime;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }
    }
}

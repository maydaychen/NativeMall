package com.example.nativeMall.Bean;

import java.io.Serializable;

/**
 * 作者：JTR on 2016/9/8 09:34
 * 邮箱：2091320109@qq.com
 */
public class UserBean implements Serializable{

    /**
     * uid :
     * username :
     * tagid : 0069000043
     * nickname :
     * email :
     * sscard :
     * name : 李阳阳
     * userid : 8a81c3394f168a60014fd4b82d7502dc
     * idcard : 412326199006032417
     * type : healthcloud
     * realvalid : 3
     * mobile : 15502107886
     */

    private DataBean data;
    /**
     * data : {"uid":"","username":"","tagid":"0069000043","nickname":"","email":"","sscard":"","name":"李阳阳","userid":"8a81c3394f168a60014fd4b82d7502dc","idcard":"412326199006032417","type":"healthcloud","realvalid":"3","mobile":"15502107886"}
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
        private String uid;
        private String username;
        private String tagid;
        private String nickname;
        private String email;
        private String sscard;
        private String name;
        private String userid;
        private String idcard;
        private String type;
        private String realvalid;
        private String mobile;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTagid() {
            return tagid;
        }

        public void setTagid(String tagid) {
            this.tagid = tagid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSscard() {
            return sscard;
        }

        public void setSscard(String sscard) {
            this.sscard = sscard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRealvalid() {
            return realvalid;
        }

        public void setRealvalid(String realvalid) {
            this.realvalid = realvalid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}

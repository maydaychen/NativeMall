package com.example.nativeMall.Bean;

import java.io.Serializable;

/**
 * 作者：JTR on 2016/10/11 14:34
 * 邮箱：2091320109@qq.com
 */
public class MallStoreBean implements Serializable{

    /**
     * data : {"serve":"2.33","desc":"2.00","ship":"3.33","info":{"storeid":"94","banner":"/userfiles/7fcac03b-c144-4685-b397-da75ebb8002c.jpg","logo":"/userfiles/392a8bcf-1423-42c4-b8bf-4fe18adb1da8.jpg","mobile":"13800000000","name":"国大药房","phone":"","qq":"","description":""}}
     * msg : ok
     * success : T
     */

    private DataBean data;
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
        /**
         * serve : 2.33
         * desc : 2.00
         * ship : 3.33
         * info : {"storeid":"94","banner":"/userfiles/7fcac03b-c144-4685-b397-da75ebb8002c.jpg","logo":"/userfiles/392a8bcf-1423-42c4-b8bf-4fe18adb1da8.jpg","mobile":"13800000000","name":"国大药房","phone":"","qq":"","description":""}
         */

        private String serve;
        private String desc;
        private String ship;
        private InfoBean info;

        public String getServe() {
            return serve;
        }

        public void setServe(String serve) {
            this.serve = serve;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getShip() {
            return ship;
        }

        public void setShip(String ship) {
            this.ship = ship;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public static class InfoBean implements Serializable{
            /**
             * storeid : 94
             * banner : /userfiles/7fcac03b-c144-4685-b397-da75ebb8002c.jpg
             * logo : /userfiles/392a8bcf-1423-42c4-b8bf-4fe18adb1da8.jpg
             * mobile : 13800000000
             * name : 国大药房
             * phone :
             * qq :
             * description :
             */

            private String storeid;
            private String banner;
            private String logo;
            private String mobile;
            private String name;
            private String phone;
            private String qq;
            private String description;

            public String getStoreid() {
                return storeid;
            }

            public void setStoreid(String storeid) {
                this.storeid = storeid;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}

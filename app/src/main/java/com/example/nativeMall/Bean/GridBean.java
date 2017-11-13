package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/9 10:52
 * 邮箱：2091320109@qq.com
 */
public class GridBean {
    /**
     * data : [{"cateid":"59","catename":"糖尿病","imgpath":"/imgservice/userfiles/676fac2d7ed3402097fd79726d14d7a2.png","sort":"3"},{"cateid":"58","catename":"高血压","imgpath":"/imgservice/userfiles/95c83e8d0d80469887f0c0d1ac7427d5.png","sort":"4"},{"cateid":"55","catename":"肠胃用药","imgpath":"/imgservice/userfiles/69222f20943c41ef852d0f7dab2a5ea5.png","sort":"1"},{"cateid":"56","catename":"妇科疾病","imgpath":"/imgservice/userfiles/5c29f97f6c184334a89adbb727939642.png","sort":"2"},{"cateid":"57","catename":"心脑血管","imgpath":"/imgservice/userfiles/8dafda8748934b149ad2c3b5a07182fc.png","sort":"5"}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * cateid : 59
     * catename : 糖尿病
     * imgpath : /imgservice/userfiles/676fac2d7ed3402097fd79726d14d7a2.png
     * sort : 3
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
        private String cateid;
        private String catename;
        private String imgpath;
        private String sort;

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}

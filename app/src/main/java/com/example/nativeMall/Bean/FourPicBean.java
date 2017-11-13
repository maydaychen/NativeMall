package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/11/2 13:50
 * 邮箱：2091320109@qq.com
 */
public class FourPicBean {
    /**
     * data : [{"npid":"22","name":"鐢熸椿鍒嗙被鎺ㄥ箍","state":"0","imgpath":"/userfiles/a4f5e49d-1af1-41ef-80a1-aa0414e1234c.jpg","sort":"2"},{"npid":"33","name":"鐢熸椿鍒嗙被鎺ㄥ箍","state":"0","imgpath":"/userfiles/af5a83e8-5249-4df6-8de6-c76f7ec5172c.jpg","sort":"0"},{"npid":"34","name":"鐢熸椿鍒嗙被鎺ㄥ箍","state":"0","imgpath":"/userfiles/3648fb3a-faf9-4dac-be47-4e1f4300e063.jpg","sort":"0"},{"npid":"35","name":"鐢熸椿鍒嗙被鎺ㄥ箍","state":"0","imgpath":"/userfiles/3c6dfe9d-bd66-46da-a553-002617a2d6e7.jpg","sort":"4"}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * npid : 22
     * name : 鐢熸椿鍒嗙被鎺ㄥ箍
     * state : 0
     * imgpath : /userfiles/a4f5e49d-1af1-41ef-80a1-aa0414e1234c.jpg
     * sort : 2
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
        private String npid;
        private String name;
        private String state;
        private String imgpath;
        private String sort;

        public String getNpid() {
            return npid;
        }

        public void setNpid(String npid) {
            this.npid = npid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

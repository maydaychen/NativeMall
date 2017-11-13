package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/26 14:32
 * 邮箱：2091320109@qq.com
 */
public class MallCategoryBean {
    /**
     * data : [{"projects":[{"shopprice":"8.00","pname":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","pid":"196","skugid":"0","showimg":"/imgservice/userfiles/fb7b6eec4698488e95f41718aff281ce.jpg"}],"npid":"37","sort":"","imgpath":"/imgservice/userfiles/09876c38a30245b78c7bbe91ec3303f3.png","name":"首页活动推广图"},{"projects":[{"shopprice":"54.00","pname":"京都念慈菴蜜炼川贝枇杷膏150ml感冒咳嗽化痰止咳利咽止咳药品","pid":"203","skugid":"0","showimg":"/imgservice/userfiles/47425b3187ea4ed79758bc863fb61c33.jpg"}],"npid":"36","sort":"","imgpath":"/imgservice/userfiles/808396fd6f1045b0af2f02fd8319d3f6.png","name":"首页活动推广图"}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * projects : [{"shopprice":"8.00","pname":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","pid":"196","skugid":"0","showimg":"/imgservice/userfiles/fb7b6eec4698488e95f41718aff281ce.jpg"}]
     * npid : 37
     * sort :
     * imgpath : /imgservice/userfiles/09876c38a30245b78c7bbe91ec3303f3.png
     * name : 首页活动推广图
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
        private String sort;
        private String imgpath;
        private String name;
        /**
         * shopprice : 8.00
         * pname : 护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛
         * pid : 196
         * skugid : 0
         * showimg : /imgservice/userfiles/fb7b6eec4698488e95f41718aff281ce.jpg
         */

        private List<ProjectsBean> projects;

        public String getNpid() {
            return npid;
        }

        public void setNpid(String npid) {
            this.npid = npid;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ProjectsBean> getProjects() {
            return projects;
        }

        public void setProjects(List<ProjectsBean> projects) {
            this.projects = projects;
        }

        public static class ProjectsBean {
            private String shopprice;
            private String pname;
            private String pid;
            private String skugid;
            private String showimg;

            public String getShopprice() {
                return shopprice;
            }

            public void setShopprice(String shopprice) {
                this.shopprice = shopprice;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSkugid() {
                return skugid;
            }

            public void setSkugid(String skugid) {
                this.skugid = skugid;
            }

            public String getShowimg() {
                return showimg;
            }

            public void setShowimg(String showimg) {
                this.showimg = showimg;
            }
        }
    }
}

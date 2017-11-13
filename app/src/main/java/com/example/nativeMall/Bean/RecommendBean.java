package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/9 09:16
 * 邮箱：2091320109@qq.com
 */
public class RecommendBean {
    /**
     * data : [{"projects":[{"shopprice":"14.00","pname":"999感冒清热颗粒12g*10袋疏风散寒解表清热用于风寒感冒头痛发热","pid":"192","skugid":"0","showimg":"/imgservice/userfiles/78b065753eca4c5181882d12ae7df960.jpg"},{"shopprice":"8.00","pname":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","pid":"196","skugid":"0","showimg":""}],"npid":"20","sort":"","imgpath":"/imgservice/userfiles/3c9c33c800ee47b58b99f552f6598a2e.jpg","name":"首页商品推荐"}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * projects : [{"shopprice":"14.00","pname":"999感冒清热颗粒12g*10袋疏风散寒解表清热用于风寒感冒头痛发热","pid":"192","skugid":"0","showimg":"/imgservice/userfiles/78b065753eca4c5181882d12ae7df960.jpg"},{"shopprice":"8.00","pname":"护彤小儿氨酚黄那敏颗粒12袋感冒退烧药流鼻涕鼻塞咳嗽药发热头痛","pid":"196","skugid":"0","showimg":""}]
     * npid : 20
     * sort :
     * imgpath : /imgservice/userfiles/3c9c33c800ee47b58b99f552f6598a2e.jpg
     * name : 首页商品推荐
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
         * shopprice : 14.00
         * pname : 999感冒清热颗粒12g*10袋疏风散寒解表清热用于风寒感冒头痛发热
         * pid : 192
         * skugid : 0
         * showimg : /imgservice/userfiles/78b065753eca4c5181882d12ae7df960.jpg
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

package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/26 14:47
 * 邮箱：2091320109@qq.com
 */
public class FamousBean {
    /**
     * data : [{"pmid":48,"pid":193,"storeid":94,"name":"名店抢购","time":0,"product":{"ppid":193,"pcateid":52,"pstoreid":94,"storestid":104,"skugid":0,"pname":"澳琳达袋鼠精胶囊","shopprice":498,"showimg":"/imgservice/userfiles/51eb28b682bd45eabcd807d1c2e4b1f6.jpg"}}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * pmid : 48
     * pid : 193
     * storeid : 94
     * name : 名店抢购
     * time : 0
     * product : {"ppid":193,"pcateid":52,"pstoreid":94,"storestid":104,"skugid":0,"pname":"澳琳达袋鼠精胶囊","shopprice":498,"showimg":"/imgservice/userfiles/51eb28b682bd45eabcd807d1c2e4b1f6.jpg"}
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
        private int pmid;
        private int pid;
        private int storeid;
        private String name;
        private int time;
        /**
         * ppid : 193
         * pcateid : 52
         * pstoreid : 94
         * storestid : 104
         * skugid : 0
         * pname : 澳琳达袋鼠精胶囊
         * shopprice : 498
         * showimg : /imgservice/userfiles/51eb28b682bd45eabcd807d1c2e4b1f6.jpg
         */

        private ProductBean product;

        public int getPmid() {
            return pmid;
        }

        public void setPmid(int pmid) {
            this.pmid = pmid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getStoreid() {
            return storeid;
        }

        public void setStoreid(int storeid) {
            this.storeid = storeid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public static class ProductBean {
            private int ppid;
            private int pcateid;
            private int pstoreid;
            private int storestid;
            private int skugid;
            private String pname;
            private String shopprice;
            private String showimg;

            public int getPpid() {
                return ppid;
            }

            public void setPpid(int ppid) {
                this.ppid = ppid;
            }

            public int getPcateid() {
                return pcateid;
            }

            public void setPcateid(int pcateid) {
                this.pcateid = pcateid;
            }

            public int getPstoreid() {
                return pstoreid;
            }

            public void setPstoreid(int pstoreid) {
                this.pstoreid = pstoreid;
            }

            public int getStorestid() {
                return storestid;
            }

            public void setStorestid(int storestid) {
                this.storestid = storestid;
            }

            public int getSkugid() {
                return skugid;
            }

            public void setSkugid(int skugid) {
                this.skugid = skugid;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public String getShopprice() {
                return shopprice;
            }

            public void setShopprice(String shopprice) {
                this.shopprice = shopprice;
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

package com.example.nativeMall.Bean;

/**
 * 作者：JTR on 2016/9/13 10:51
 * 邮箱：2091320109@qq.com
 */
public class DailyBean {
    /**
     * pmid : 56
     * pid : 195
     * storeid : 94
     * name : 国大每日一抢
     * time : 86400000
     * product : {"ppid":195,"pcateid":51,"pstoreid":94,"storestid":104,"skugid":0,"pname":"沐舒坦盐酸氨溴索口服溶液100ml止咳化痰用于痰液粘稠难咳出者","shopprice":21,"showimg":"/imgservice/userfiles/2a81d1b4fa2a4918867dd0c315cb8207.jpg"}
     */

    private DataBean data;
    /**
     * data : {"pmid":56,"pid":195,"storeid":94,"name":"国大每日一抢","time":86400000,"product":{"ppid":195,"pcateid":51,"pstoreid":94,"storestid":104,"skugid":0,"pname":"沐舒坦盐酸氨溴索口服溶液100ml止咳化痰用于痰液粘稠难咳出者","shopprice":21,"showimg":"/imgservice/userfiles/2a81d1b4fa2a4918867dd0c315cb8207.jpg"}}
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

    public static class DataBean {
        private int pmid;
        private int pid;
        private int storeid;
        private String name;
        private int time;
        /**
         * ppid : 195
         * pcateid : 51
         * pstoreid : 94
         * storestid : 104
         * skugid : 0
         * pname : 沐舒坦盐酸氨溴索口服溶液100ml止咳化痰用于痰液粘稠难咳出者
         * shopprice : 21
         * showimg : /imgservice/userfiles/2a81d1b4fa2a4918867dd0c315cb8207.jpg
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

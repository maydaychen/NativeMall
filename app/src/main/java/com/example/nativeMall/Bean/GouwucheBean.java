package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/10/25 10:02
 * 邮箱：2091320109@qq.com
 */
public class GouwucheBean {
    private DataBean data;
    /**
     * data : {"attrs":[{"attval":[{"inputvalue":null,"attrvalueid":2286,"attrvalue":"2G"},{"inputvalue":null,"attrvalueid":2285,"attrvalue":"3G"},{"inputvalue":null,"attrvalueid":2284,"attrvalue":"4G"}],"attname":"联通"},{"attval":[{"inputvalue":null,"attrvalueid":2287,"attrvalue":"白色"},{"inputvalue":null,"attrvalueid":2288,"attrvalue":"黑色"}],"attname":"颜色"}],"products":[{"pid":"253","pinfo":[{"attrvalueid":2285,"attrvalue":"3G","number":49,"showing":null},{"attrvalueid":2287,"attrvalue":"白色","number":49,"showing":null}]},{"pid":"254","pinfo":[{"attrvalueid":2285,"attrvalue":"3G","number":49,"showing":null},{"attrvalueid":2288,"attrvalue":"黑色","number":49,"showing":null}]},{"pid":"255","pinfo":[{"attrvalueid":2286,"attrvalue":"2G","number":0,"showing":null},{"attrvalueid":2287,"attrvalue":"白色","number":0,"showing":null}]},{"pid":"256","pinfo":[{"attrvalueid":2286,"attrvalue":"2G","number":0,"showing":null},{"attrvalueid":2288,"attrvalue":"黑色","number":0,"showing":null}]},{"pid":"257","pinfo":[{"attrvalueid":2284,"attrvalue":"4G","number":0,"showing":null},{"attrvalueid":2287,"attrvalue":"白色","number":0,"showing":null}]},{"pid":"258","pinfo":[{"attrvalueid":2284,"attrvalue":"4G","number":0,"showing":null},{"attrvalueid":2288,"attrvalue":"黑色","number":0,"showing":null}]}]}
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
        /**
         * attval : [{"inputvalue":null,"attrvalueid":2286,"attrvalue":"2G"},{"inputvalue":null,"attrvalueid":2285,"attrvalue":"3G"},{"inputvalue":null,"attrvalueid":2284,"attrvalue":"4G"}]
         * attname : 联通
         */

        private List<AttrsBean> attrs;
        /**
         * pid : 253
         * pinfo : [{"attrvalueid":2285,"attrvalue":"3G","number":49,"showing":null},{"attrvalueid":2287,"attrvalue":"白色","number":49,"showing":null}]
         */

        private List<ProductsBean> products;

        public List<AttrsBean> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<AttrsBean> attrs) {
            this.attrs = attrs;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class AttrsBean {
            private String attname;
            /**
             * inputvalue : null
             * attrvalueid : 2286
             * attrvalue : 2G
             */

            private List<AttvalBean> attval;

            public String getAttname() {
                return attname;
            }

            public void setAttname(String attname) {
                this.attname = attname;
            }

            public List<AttvalBean> getAttval() {
                return attval;
            }

            public void setAttval(List<AttvalBean> attval) {
                this.attval = attval;
            }

            public static class AttvalBean {
                private Object inputvalue;
                private int attrvalueid;
                private String attrvalue;

                public Object getInputvalue() {
                    return inputvalue;
                }

                public void setInputvalue(Object inputvalue) {
                    this.inputvalue = inputvalue;
                }

                public int getAttrvalueid() {
                    return attrvalueid;
                }

                public void setAttrvalueid(int attrvalueid) {
                    this.attrvalueid = attrvalueid;
                }

                public String getAttrvalue() {
                    return attrvalue;
                }

                public void setAttrvalue(String attrvalue) {
                    this.attrvalue = attrvalue;
                }
            }
        }

        public static class ProductsBean {
            private String pid;
            /**
             * attrvalueid : 2285
             * attrvalue : 3G
             * number : 49
             * showing : null
             */

            private List<PinfoBean> pinfo;

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public List<PinfoBean> getPinfo() {
                return pinfo;
            }

            public void setPinfo(List<PinfoBean> pinfo) {
                this.pinfo = pinfo;
            }

            public static class PinfoBean {
                private int attrvalueid;
                private String attrvalue;
                private int number;
                private Object showing;

                public int getAttrvalueid() {
                    return attrvalueid;
                }

                public void setAttrvalueid(int attrvalueid) {
                    this.attrvalueid = attrvalueid;
                }

                public String getAttrvalue() {
                    return attrvalue;
                }

                public void setAttrvalue(String attrvalue) {
                    this.attrvalue = attrvalue;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public Object getShowing() {
                    return showing;
                }

                public void setShowing(Object showing) {
                    this.showing = showing;
                }
            }
        }
    }
}

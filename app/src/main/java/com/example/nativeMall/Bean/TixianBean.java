package com.example.nativeMall.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2017/12/7.
 */

public class TixianBean {
    /**
     * statusCode : 1
     * result : {"total":{"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"},"ok":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"lock":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"apply":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"check":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"pay":{"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"},"invalid":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"o_status_0":{"cg_money_sum":"9446.00","order_count":"3","c_money_sum":"27.16"},"o_status_1":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"o_status_2":{"cg_money_sum":0,"order_count":"0","c_money_sum":0},"o_status_3":{"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"},"default":{"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"},"manage":{"cg_money_sum":0,"order_count":"0","c_money_sum":0}}
     */

    private int statusCode;
    private ResultBean result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * total : {"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"}
         * ok : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * lock : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * apply : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * check : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * pay : {"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"}
         * invalid : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * o_status_0 : {"cg_money_sum":"9446.00","order_count":"3","c_money_sum":"27.16"}
         * o_status_1 : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * o_status_2 : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         * o_status_3 : {"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"}
         * default : {"cg_money_sum":"8223.60","order_count":"36","c_money_sum":"147.22"}
         * manage : {"cg_money_sum":0,"order_count":"0","c_money_sum":0}
         */

        private TotalBean total;
        private OkBean ok;
        private LockBean lock;
        private ApplyBean apply;
        private CheckBean check;
        private PayBean pay;
        private InvalidBean invalid;
        private OStatus0Bean o_status_0;
        private OStatus1Bean o_status_1;
        private OStatus2Bean o_status_2;
        private OStatus3Bean o_status_3;
        @SerializedName("default")
        private DefaultBean defaultX;
        private ManageBean manage;

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public OkBean getOk() {
            return ok;
        }

        public void setOk(OkBean ok) {
            this.ok = ok;
        }

        public LockBean getLock() {
            return lock;
        }

        public void setLock(LockBean lock) {
            this.lock = lock;
        }

        public ApplyBean getApply() {
            return apply;
        }

        public void setApply(ApplyBean apply) {
            this.apply = apply;
        }

        public CheckBean getCheck() {
            return check;
        }

        public void setCheck(CheckBean check) {
            this.check = check;
        }

        public PayBean getPay() {
            return pay;
        }

        public void setPay(PayBean pay) {
            this.pay = pay;
        }

        public InvalidBean getInvalid() {
            return invalid;
        }

        public void setInvalid(InvalidBean invalid) {
            this.invalid = invalid;
        }

        public OStatus0Bean getO_status_0() {
            return o_status_0;
        }

        public void setO_status_0(OStatus0Bean o_status_0) {
            this.o_status_0 = o_status_0;
        }

        public OStatus1Bean getO_status_1() {
            return o_status_1;
        }

        public void setO_status_1(OStatus1Bean o_status_1) {
            this.o_status_1 = o_status_1;
        }

        public OStatus2Bean getO_status_2() {
            return o_status_2;
        }

        public void setO_status_2(OStatus2Bean o_status_2) {
            this.o_status_2 = o_status_2;
        }

        public OStatus3Bean getO_status_3() {
            return o_status_3;
        }

        public void setO_status_3(OStatus3Bean o_status_3) {
            this.o_status_3 = o_status_3;
        }

        public DefaultBean getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(DefaultBean defaultX) {
            this.defaultX = defaultX;
        }

        public ManageBean getManage() {
            return manage;
        }

        public void setManage(ManageBean manage) {
            this.manage = manage;
        }

        public static class TotalBean {
            /**
             * cg_money_sum : 8223.60
             * order_count : 36
             * c_money_sum : 147.22
             */

            private String cg_money_sum;
            private String order_count;
            private String c_money_sum;

            public String getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(String cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public String getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(String c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class OkBean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class LockBean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class ApplyBean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class CheckBean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class PayBean {
            /**
             * cg_money_sum : 8223.60
             * order_count : 36
             * c_money_sum : 147.22
             */

            private String cg_money_sum;
            private String order_count;
            private String c_money_sum;

            public String getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(String cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public String getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(String c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class InvalidBean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class OStatus0Bean {
            /**
             * cg_money_sum : 9446.00
             * order_count : 3
             * c_money_sum : 27.16
             */

            private String cg_money_sum;
            private String order_count;
            private String c_money_sum;

            public String getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(String cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public String getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(String c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class OStatus1Bean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class OStatus2Bean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class OStatus3Bean {
            /**
             * cg_money_sum : 8223.60
             * order_count : 36
             * c_money_sum : 147.22
             */

            private String cg_money_sum;
            private String order_count;
            private String c_money_sum;

            public String getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(String cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public String getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(String c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class DefaultBean {
            /**
             * cg_money_sum : 8223.60
             * order_count : 36
             * c_money_sum : 147.22
             */

            private String cg_money_sum;
            private String order_count;
            private String c_money_sum;

            public String getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(String cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public String getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(String c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }

        public static class ManageBean {
            /**
             * cg_money_sum : 0
             * order_count : 0
             * c_money_sum : 0
             */

            private int cg_money_sum;
            private String order_count;
            private int c_money_sum;

            public int getCg_money_sum() {
                return cg_money_sum;
            }

            public void setCg_money_sum(int cg_money_sum) {
                this.cg_money_sum = cg_money_sum;
            }

            public String getOrder_count() {
                return order_count;
            }

            public void setOrder_count(String order_count) {
                this.order_count = order_count;
            }

            public int getC_money_sum() {
                return c_money_sum;
            }

            public void setC_money_sum(int c_money_sum) {
                this.c_money_sum = c_money_sum;
            }
        }
    }
}

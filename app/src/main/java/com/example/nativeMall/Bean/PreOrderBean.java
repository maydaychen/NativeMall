package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：JTR on 2016/9/14 09:54
 * 邮箱：2091320109@qq.com
 */
public class PreOrderBean implements Serializable {

    /**
     * data : {"allPrice":5997,"address":{"said":189,"regionid":1188,"provinceid":10,"cityid":116,"isdefault":"11","alias":"","consignee":"啦啦啦啦啦啦","mobile":"15145960288","phone":"","address":"还是我","provincename":"江苏省","cityname":"盐城市","rename":"滨海县"},"allFair":0,"cutPrice":300,"products":[{"product":{"pid":"239","storeid":"116","storecid":"184","storestid":"112","state":"0","isbest":"0","isnew":"0","name":"华为麦芒4  3G  黑色  ","shopprice":"1999.00","marketprice":"2399.00","displayorder":"0","weight":"0","showimg":"/userfiles/c00fefde-a470-46ac-bf6d-17457a9f6424.jpg","sname":"华为专卖","buycount":"1","fare":"0.00"},"attrs":[],"buycount":"1","fare":"0.00","carid":"604","totalMoney":"1999.0"},{"product":{"pid":"238","storeid":"116","storecid":"184","storestid":"112","state":"0","isbest":"0","isnew":"0","name":"华为麦芒4  3G  白色","shopprice":"1999.00","marketprice":"2399.00","displayorder":"0","weight":"0","showimg":null,"sname":"华为专卖","buycount":"2","fare":"0.00"},"attrs":[{"attrid":"54","attname":"联通","attrvalueid":"2285","attrvalue":"3G"},{"attrid":"64","attname":"颜色","attrvalueid":"2287","attrvalue":"白色"}],"buycount":"2","fare":"0.00","carid":"603","totalMoney":"3998.0"}]}
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

    public static class DataBean implements Serializable {
        /**
         * allPrice : 5997
         * address : {"said":189,"regionid":1188,"provinceid":10,"cityid":116,"isdefault":"11","alias":"","consignee":"啦啦啦啦啦啦","mobile":"15145960288","phone":"","address":"还是我","provincename":"江苏省","cityname":"盐城市","rename":"滨海县"}
         * allFair : 0
         * cutPrice : 300
         * products : [{"product":{"pid":"239","storeid":"116","storecid":"184","storestid":"112","state":"0","isbest":"0","isnew":"0","name":"华为麦芒4  3G  黑色  ","shopprice":"1999.00","marketprice":"2399.00","displayorder":"0","weight":"0","showimg":"/userfiles/c00fefde-a470-46ac-bf6d-17457a9f6424.jpg","sname":"华为专卖","buycount":"1","fare":"0.00"},"attrs":[],"buycount":"1","fare":"0.00","carid":"604","totalMoney":"1999.0"},{"product":{"pid":"238","storeid":"116","storecid":"184","storestid":"112","state":"0","isbest":"0","isnew":"0","name":"华为麦芒4  3G  白色","shopprice":"1999.00","marketprice":"2399.00","displayorder":"0","weight":"0","showimg":null,"sname":"华为专卖","buycount":"2","fare":"0.00"},"attrs":[{"attrid":"54","attname":"联通","attrvalueid":"2285","attrvalue":"3G"},{"attrid":"64","attname":"颜色","attrvalueid":"2287","attrvalue":"白色"}],"buycount":"2","fare":"0.00","carid":"603","totalMoney":"3998.0"}]
         */

        private double allPrice;
        private AddressBean address;
        private double allFair;
        private double cutPrice;
        private List<ProductsBean> products;

        public double getAllPrice() {
            return allPrice;
        }

        public void setAllPrice(int allPrice) {
            this.allPrice = allPrice;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public double getAllFair() {
            return allFair;
        }

        public void setAllFair(int allFair) {
            this.allFair = allFair;
        }

        public double getCutPrice() {
            return cutPrice;
        }

        public void setCutPrice(int cutPrice) {
            this.cutPrice = cutPrice;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class AddressBean implements Serializable {
            /**
             * said : 189
             * regionid : 1188
             * provinceid : 10
             * cityid : 116
             * isdefault : 11
             * alias :
             * consignee : 啦啦啦啦啦啦
             * mobile : 15145960288
             * phone :
             * address : 还是我
             * provincename : 江苏省
             * cityname : 盐城市
             * rename : 滨海县
             */

            private int said;
            private int regionid;
            private int provinceid;
            private int cityid;
            private String isdefault;
            private String alias;
            private String consignee;
            private String mobile;
            private String phone;
            private String address;
            private String provincename;
            private String cityname;
            private String rename;

            public int getSaid() {
                return said;
            }

            public void setSaid(int said) {
                this.said = said;
            }

            public int getRegionid() {
                return regionid;
            }

            public void setRegionid(int regionid) {
                this.regionid = regionid;
            }

            public int getProvinceid() {
                return provinceid;
            }

            public void setProvinceid(int provinceid) {
                this.provinceid = provinceid;
            }

            public int getCityid() {
                return cityid;
            }

            public void setCityid(int cityid) {
                this.cityid = cityid;
            }

            public String getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(String isdefault) {
                this.isdefault = isdefault;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProvincename() {
                return provincename;
            }

            public void setProvincename(String provincename) {
                this.provincename = provincename;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getRename() {
                return rename;
            }

            public void setRename(String rename) {
                this.rename = rename;
            }
        }

        public static class ProductsBean implements Serializable {
            /**
             * product : {"pid":"239","storeid":"116","storecid":"184","storestid":"112","state":"0","isbest":"0","isnew":"0","name":"华为麦芒4  3G  黑色  ","shopprice":"1999.00","marketprice":"2399.00","displayorder":"0","weight":"0","showimg":"/userfiles/c00fefde-a470-46ac-bf6d-17457a9f6424.jpg","sname":"华为专卖","buycount":"1","fare":"0.00"}
             * attrs : []
             * buycount : 1
             * fare : 0.00
             * carid : 604
             * totalMoney : 1999.0
             */

            private ProductBean product;
            private String buycount;
            private String fare;
            private String carid;
            private String totalMoney;
            private List<?> attrs;

            public ProductBean getProduct() {
                return product;
            }

            public void setProduct(ProductBean product) {
                this.product = product;
            }

            public String getBuycount() {
                return buycount;
            }

            public void setBuycount(String buycount) {
                this.buycount = buycount;
            }

            public String getFare() {
                return fare;
            }

            public void setFare(String fare) {
                this.fare = fare;
            }

            public String getCarid() {
                return carid;
            }

            public void setCarid(String carid) {
                this.carid = carid;
            }

            public String getTotalMoney() {
                return totalMoney;
            }

            public void setTotalMoney(String totalMoney) {
                this.totalMoney = totalMoney;
            }

            public List<?> getAttrs() {
                return attrs;
            }

            public void setAttrs(List<?> attrs) {
                this.attrs = attrs;
            }

            public static class ProductBean implements Serializable {
                /**
                 * pid : 239
                 * storeid : 116
                 * storecid : 184
                 * storestid : 112
                 * state : 0
                 * isbest : 0
                 * isnew : 0
                 * name : 华为麦芒4  3G  黑色
                 * shopprice : 1999.00
                 * marketprice : 2399.00
                 * displayorder : 0
                 * weight : 0
                 * showimg : /userfiles/c00fefde-a470-46ac-bf6d-17457a9f6424.jpg
                 * sname : 华为专卖
                 * buycount : 1
                 * fare : 0.00
                 */

                private String pid;
                private String storeid;
                private String storecid;
                private String storestid;
                private String state;
                private String isbest;
                private String isnew;
                private String name;
                private String shopprice;
                private String marketprice;
                private String displayorder;
                private String weight;
                private String showimg;
                private String sname;
                private String buycount;
                private String fare;

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public String getStoreid() {
                    return storeid;
                }

                public void setStoreid(String storeid) {
                    this.storeid = storeid;
                }

                public String getStorecid() {
                    return storecid;
                }

                public void setStorecid(String storecid) {
                    this.storecid = storecid;
                }

                public String getStorestid() {
                    return storestid;
                }

                public void setStorestid(String storestid) {
                    this.storestid = storestid;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getIsbest() {
                    return isbest;
                }

                public void setIsbest(String isbest) {
                    this.isbest = isbest;
                }

                public String getIsnew() {
                    return isnew;
                }

                public void setIsnew(String isnew) {
                    this.isnew = isnew;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getShopprice() {
                    return shopprice;
                }

                public void setShopprice(String shopprice) {
                    this.shopprice = shopprice;
                }

                public String getMarketprice() {
                    return marketprice;
                }

                public void setMarketprice(String marketprice) {
                    this.marketprice = marketprice;
                }

                public String getDisplayorder() {
                    return displayorder;
                }

                public void setDisplayorder(String displayorder) {
                    this.displayorder = displayorder;
                }

                public String getWeight() {
                    return weight;
                }

                public void setWeight(String weight) {
                    this.weight = weight;
                }

                public String getShowimg() {
                    return showimg;
                }

                public void setShowimg(String showimg) {
                    this.showimg = showimg;
                }

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }

                public String getBuycount() {
                    return buycount;
                }

                public void setBuycount(String buycount) {
                    this.buycount = buycount;
                }

                public String getFare() {
                    return fare;
                }

                public void setFare(String fare) {
                    this.fare = fare;
                }
            }
        }
    }
}

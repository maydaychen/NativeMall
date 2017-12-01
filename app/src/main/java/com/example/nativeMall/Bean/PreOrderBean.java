package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：JTR on 2016/9/14 09:54
 * 邮箱：2091320109@qq.com
 */
public class PreOrderBean implements Serializable {
    /**
     * statusCode : 1
     * result : {"orderGoods":[{"goodsid":"146","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货","weight":"0.00","issendfree":"1","isnodiscount":"0","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg","marketprice":"8988.00","storeids":"","isverify":"1","deduct":"0.00","total":1,"optionid":847,"optiontitle":"灰色+64G"}],"addressLists":[{"id":"3","uniacid":"2","openid":"oNSO9wHBr-KbfKt0sNybs2wByWHY","realname":"汤正刚","mobile":"18915787525","province":"江苏省","city":"苏州市","area":"张家港市","address":"清水湾29-604","isdefault":"1","zipcode":"","deleted":"0"}],"defaultAddress":{"id":"3","uniacid":"2","openid":"oNSO9wHBr-KbfKt0sNybs2wByWHY","realname":"汤正刚","mobile":"18915787525","province":"江苏省","city":"苏州市","area":"张家港市","address":"清水湾29-604","isdefault":"1","zipcode":"","deleted":"0"},"dispatches":[{"id":"1","uniacid":"2","dispatchname":"包邮","dispatchtype":"0","displayorder":"0","firstprice":"0.00","secondprice":"0.00","firstweight":"1000","secondweight":"1000","express":"","areas":"a:0:{}","carriers":"a:0:{}","enabled":"1","price":0}],"memberDiscount":{"total":1,"goodsprice":8988,"realprice":8988,"deductprice":0,"discountprice":0,"couponcount":0,"deductmoney":0,"deductcredit":0},"shopSet":{"name":"江苏岗隆数码科技有限公司","img":"","logo":"images/2/2017/07/yTtEitwZziqoupMw3BoMQuQhhuPuqu.png","signimg":"","qq":"","address":"江苏省宜兴市经济开发区文庄路8号创意软件大厦A座3楼","phone":"400-187-6188","description":"江苏岗隆数码科技有限公司，成立于2011年11月14日，2016年9月进行业务重组，注册资本5000万元，公司围绕移动通讯终端产品、数码电子消费产品等领域，形成了分销、电商零售、移动转售、供应链金融业务四大业务板块。岗隆数码现已覆盖长三角、珠三角、京津冀、成渝商圈等多省（市）的近300多家批发商及线上各大B2C运营平台、各大银行商城。岗隆数码致力于向广大消费者提供手机数码产品销售、配件以及售后等综合专业化的服务。公司几大运营商形成了共创共赢的战略合作关系。同时与苹果、华为、三星、小米、联想、酷派、乐视、魅族、中兴、OPPO、vivo等上游资源及代理商上构建了以分销、零售为核心业务的格局。","catlevel":"2","catshow":0,"catadvimg":"","catadvurl":"","levelname":"尊享会员","levelurl":""}}
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
         * orderGoods : [{"goodsid":"146","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货","weight":"0.00","issendfree":"1","isnodiscount":"0","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg","marketprice":"8988.00","storeids":"","isverify":"1","deduct":"0.00","total":1,"optionid":847,"optiontitle":"灰色+64G"}]
         * addressLists : [{"id":"3","uniacid":"2","openid":"oNSO9wHBr-KbfKt0sNybs2wByWHY","realname":"汤正刚","mobile":"18915787525","province":"江苏省","city":"苏州市","area":"张家港市","address":"清水湾29-604","isdefault":"1","zipcode":"","deleted":"0"}]
         * defaultAddress : {"id":"3","uniacid":"2","openid":"oNSO9wHBr-KbfKt0sNybs2wByWHY","realname":"汤正刚","mobile":"18915787525","province":"江苏省","city":"苏州市","area":"张家港市","address":"清水湾29-604","isdefault":"1","zipcode":"","deleted":"0"}
         * dispatches : [{"id":"1","uniacid":"2","dispatchname":"包邮","dispatchtype":"0","displayorder":"0","firstprice":"0.00","secondprice":"0.00","firstweight":"1000","secondweight":"1000","express":"","areas":"a:0:{}","carriers":"a:0:{}","enabled":"1","price":0}]
         * memberDiscount : {"total":1,"goodsprice":8988,"realprice":8988,"deductprice":0,"discountprice":0,"couponcount":0,"deductmoney":0,"deductcredit":0}
         * shopSet : {"name":"江苏岗隆数码科技有限公司","img":"","logo":"images/2/2017/07/yTtEitwZziqoupMw3BoMQuQhhuPuqu.png","signimg":"","qq":"","address":"江苏省宜兴市经济开发区文庄路8号创意软件大厦A座3楼","phone":"400-187-6188","description":"江苏岗隆数码科技有限公司，成立于2011年11月14日，2016年9月进行业务重组，注册资本5000万元，公司围绕移动通讯终端产品、数码电子消费产品等领域，形成了分销、电商零售、移动转售、供应链金融业务四大业务板块。岗隆数码现已覆盖长三角、珠三角、京津冀、成渝商圈等多省（市）的近300多家批发商及线上各大B2C运营平台、各大银行商城。岗隆数码致力于向广大消费者提供手机数码产品销售、配件以及售后等综合专业化的服务。公司几大运营商形成了共创共赢的战略合作关系。同时与苹果、华为、三星、小米、联想、酷派、乐视、魅族、中兴、OPPO、vivo等上游资源及代理商上构建了以分销、零售为核心业务的格局。","catlevel":"2","catshow":0,"catadvimg":"","catadvurl":"","levelname":"尊享会员","levelurl":""}
         */

        private DefaultAddressBean defaultAddress;
        private MemberDiscountBean memberDiscount;
        private ShopSetBean shopSet;
        private List<OrderGoodsBean> orderGoods;
        private List<AddressListsBean> addressLists;
        private List<DispatchesBean> dispatches;

        public DefaultAddressBean getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(DefaultAddressBean defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public MemberDiscountBean getMemberDiscount() {
            return memberDiscount;
        }

        public void setMemberDiscount(MemberDiscountBean memberDiscount) {
            this.memberDiscount = memberDiscount;
        }

        public ShopSetBean getShopSet() {
            return shopSet;
        }

        public void setShopSet(ShopSetBean shopSet) {
            this.shopSet = shopSet;
        }

        public List<OrderGoodsBean> getOrderGoods() {
            return orderGoods;
        }

        public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
            this.orderGoods = orderGoods;
        }

        public List<AddressListsBean> getAddressLists() {
            return addressLists;
        }

        public void setAddressLists(List<AddressListsBean> addressLists) {
            this.addressLists = addressLists;
        }

        public List<DispatchesBean> getDispatches() {
            return dispatches;
        }

        public void setDispatches(List<DispatchesBean> dispatches) {
            this.dispatches = dispatches;
        }

        public static class DefaultAddressBean {
            /**
             * id : 3
             * uniacid : 2
             * openid : oNSO9wHBr-KbfKt0sNybs2wByWHY
             * realname : 汤正刚
             * mobile : 18915787525
             * province : 江苏省
             * city : 苏州市
             * area : 张家港市
             * address : 清水湾29-604
             * isdefault : 1
             * zipcode :
             * deleted : 0
             */

            private String id;
            private String uniacid;
            private String openid;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;
            private String isdefault;
            private String zipcode;
            private String deleted;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUniacid() {
                return uniacid;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(String isdefault) {
                this.isdefault = isdefault;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }
        }

        public static class MemberDiscountBean {
            /**
             * total : 1
             * goodsprice : 8988
             * realprice : 8988
             * deductprice : 0
             * discountprice : 0
             * couponcount : 0
             * deductmoney : 0
             * deductcredit : 0
             */

            private int total;
            private int goodsprice;
            private int realprice;
            private int deductprice;
            private int discountprice;
            private int couponcount;
            private int deductmoney;
            private int deductcredit;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getGoodsprice() {
                return goodsprice;
            }

            public void setGoodsprice(int goodsprice) {
                this.goodsprice = goodsprice;
            }

            public int getRealprice() {
                return realprice;
            }

            public void setRealprice(int realprice) {
                this.realprice = realprice;
            }

            public int getDeductprice() {
                return deductprice;
            }

            public void setDeductprice(int deductprice) {
                this.deductprice = deductprice;
            }

            public int getDiscountprice() {
                return discountprice;
            }

            public void setDiscountprice(int discountprice) {
                this.discountprice = discountprice;
            }

            public int getCouponcount() {
                return couponcount;
            }

            public void setCouponcount(int couponcount) {
                this.couponcount = couponcount;
            }

            public int getDeductmoney() {
                return deductmoney;
            }

            public void setDeductmoney(int deductmoney) {
                this.deductmoney = deductmoney;
            }

            public int getDeductcredit() {
                return deductcredit;
            }

            public void setDeductcredit(int deductcredit) {
                this.deductcredit = deductcredit;
            }
        }

        public static class ShopSetBean {
            /**
             * name : 江苏岗隆数码科技有限公司
             * img :
             * logo : images/2/2017/07/yTtEitwZziqoupMw3BoMQuQhhuPuqu.png
             * signimg :
             * qq :
             * address : 江苏省宜兴市经济开发区文庄路8号创意软件大厦A座3楼
             * phone : 400-187-6188
             * description : 江苏岗隆数码科技有限公司，成立于2011年11月14日，2016年9月进行业务重组，注册资本5000万元，公司围绕移动通讯终端产品、数码电子消费产品等领域，形成了分销、电商零售、移动转售、供应链金融业务四大业务板块。岗隆数码现已覆盖长三角、珠三角、京津冀、成渝商圈等多省（市）的近300多家批发商及线上各大B2C运营平台、各大银行商城。岗隆数码致力于向广大消费者提供手机数码产品销售、配件以及售后等综合专业化的服务。公司几大运营商形成了共创共赢的战略合作关系。同时与苹果、华为、三星、小米、联想、酷派、乐视、魅族、中兴、OPPO、vivo等上游资源及代理商上构建了以分销、零售为核心业务的格局。
             * catlevel : 2
             * catshow : 0
             * catadvimg :
             * catadvurl :
             * levelname : 尊享会员
             * levelurl :
             */

            private String name;
            private String img;
            private String logo;
            private String signimg;
            private String qq;
            private String address;
            private String phone;
            private String description;
            private String catlevel;
            private int catshow;
            private String catadvimg;
            private String catadvurl;
            private String levelname;
            private String levelurl;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getSignimg() {
                return signimg;
            }

            public void setSignimg(String signimg) {
                this.signimg = signimg;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getCatlevel() {
                return catlevel;
            }

            public void setCatlevel(String catlevel) {
                this.catlevel = catlevel;
            }

            public int getCatshow() {
                return catshow;
            }

            public void setCatshow(int catshow) {
                this.catshow = catshow;
            }

            public String getCatadvimg() {
                return catadvimg;
            }

            public void setCatadvimg(String catadvimg) {
                this.catadvimg = catadvimg;
            }

            public String getCatadvurl() {
                return catadvurl;
            }

            public void setCatadvurl(String catadvurl) {
                this.catadvurl = catadvurl;
            }

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getLevelurl() {
                return levelurl;
            }

            public void setLevelurl(String levelurl) {
                this.levelurl = levelurl;
            }
        }

        public static class OrderGoodsBean {
            /**
             * goodsid : 146
             * title : Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货
             * weight : 0.00
             * issendfree : 1
             * isnodiscount : 0
             * thumb : https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg
             * marketprice : 8988.00
             * storeids :
             * isverify : 1
             * deduct : 0.00
             * total : 1
             * optionid : 847
             * optiontitle : 灰色+64G
             */

            private String goodsid;
            private String title;
            private String weight;
            private String issendfree;
            private String isnodiscount;
            private String thumb;
            private String marketprice;
            private String storeids;
            private String isverify;
            private String deduct;
            private int total;
            private int optionid;
            private String optiontitle;

            public String getGoodsid() {
                return goodsid;
            }

            public void setGoodsid(String goodsid) {
                this.goodsid = goodsid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getIssendfree() {
                return issendfree;
            }

            public void setIssendfree(String issendfree) {
                this.issendfree = issendfree;
            }

            public String getIsnodiscount() {
                return isnodiscount;
            }

            public void setIsnodiscount(String isnodiscount) {
                this.isnodiscount = isnodiscount;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getMarketprice() {
                return marketprice;
            }

            public void setMarketprice(String marketprice) {
                this.marketprice = marketprice;
            }

            public String getStoreids() {
                return storeids;
            }

            public void setStoreids(String storeids) {
                this.storeids = storeids;
            }

            public String getIsverify() {
                return isverify;
            }

            public void setIsverify(String isverify) {
                this.isverify = isverify;
            }

            public String getDeduct() {
                return deduct;
            }

            public void setDeduct(String deduct) {
                this.deduct = deduct;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getOptionid() {
                return optionid;
            }

            public void setOptionid(int optionid) {
                this.optionid = optionid;
            }

            public String getOptiontitle() {
                return optiontitle;
            }

            public void setOptiontitle(String optiontitle) {
                this.optiontitle = optiontitle;
            }
        }

        public static class AddressListsBean {
            /**
             * id : 3
             * uniacid : 2
             * openid : oNSO9wHBr-KbfKt0sNybs2wByWHY
             * realname : 汤正刚
             * mobile : 18915787525
             * province : 江苏省
             * city : 苏州市
             * area : 张家港市
             * address : 清水湾29-604
             * isdefault : 1
             * zipcode :
             * deleted : 0
             */

            private String id;
            private String uniacid;
            private String openid;
            private String realname;
            private String mobile;
            private String province;
            private String city;
            private String area;
            private String address;
            private String isdefault;
            private String zipcode;
            private String deleted;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUniacid() {
                return uniacid;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIsdefault() {
                return isdefault;
            }

            public void setIsdefault(String isdefault) {
                this.isdefault = isdefault;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }
        }

        public static class DispatchesBean {
            /**
             * id : 1
             * uniacid : 2
             * dispatchname : 包邮
             * dispatchtype : 0
             * displayorder : 0
             * firstprice : 0.00
             * secondprice : 0.00
             * firstweight : 1000
             * secondweight : 1000
             * express :
             * areas : a:0:{}
             * carriers : a:0:{}
             * enabled : 1
             * price : 0
             */

            private String id;
            private String uniacid;
            private String dispatchname;
            private String dispatchtype;
            private String displayorder;
            private String firstprice;
            private String secondprice;
            private String firstweight;
            private String secondweight;
            private String express;
            private String areas;
            private String carriers;
            private String enabled;
            private int price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUniacid() {
                return uniacid;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getDispatchname() {
                return dispatchname;
            }

            public void setDispatchname(String dispatchname) {
                this.dispatchname = dispatchname;
            }

            public String getDispatchtype() {
                return dispatchtype;
            }

            public void setDispatchtype(String dispatchtype) {
                this.dispatchtype = dispatchtype;
            }

            public String getDisplayorder() {
                return displayorder;
            }

            public void setDisplayorder(String displayorder) {
                this.displayorder = displayorder;
            }

            public String getFirstprice() {
                return firstprice;
            }

            public void setFirstprice(String firstprice) {
                this.firstprice = firstprice;
            }

            public String getSecondprice() {
                return secondprice;
            }

            public void setSecondprice(String secondprice) {
                this.secondprice = secondprice;
            }

            public String getFirstweight() {
                return firstweight;
            }

            public void setFirstweight(String firstweight) {
                this.firstweight = firstweight;
            }

            public String getSecondweight() {
                return secondweight;
            }

            public void setSecondweight(String secondweight) {
                this.secondweight = secondweight;
            }

            public String getExpress() {
                return express;
            }

            public void setExpress(String express) {
                this.express = express;
            }

            public String getAreas() {
                return areas;
            }

            public void setAreas(String areas) {
                this.areas = areas;
            }

            public String getCarriers() {
                return carriers;
            }

            public void setCarriers(String carriers) {
                this.carriers = carriers;
            }

            public String getEnabled() {
                return enabled;
            }

            public void setEnabled(String enabled) {
                this.enabled = enabled;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}

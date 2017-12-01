package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2017/11/16.
 */

public class AttrbuteBean {
    /**
     * statusCode : 1
     * result : [{"id":"146","uniacid":"2","pcate":"5","ccate":"8","type":"1","status":"1","displayorder":"6","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg","unit":"件","description":"","content":"<p><img src=\"http://ganglong.wshoto.com/attachment/images/2/2017/09/mJS8LPHZP8UHfjaBLQujqslfQfuRdA.jpg\" alt=\"images/2/2017/09/mJS8LPHZP8UHfjaBLQujqslfQfuRdA.jpg\" style=\"white-space: normal;\" width=\"100%\"/><img src=\"https://ganglong.wshoto.com/attachment/images/2/2017/10/SMsGG5azkfIHzlonFsm1OU6f3A9ZMM.jpg\" alt=\"Apple-iPhone-X.jpg\" width=\"100%\"/><img src=\"https://ganglong.wshoto.com/attachment/images/2/2017/10/DTLbFLcZfbb8OC84Ht5B8CBhk0a8fZ.jpg\" alt=\"images/2/2017/10/DTLbFLcZfbb8OC84Ht5B8CBhk0a8fZ.jpg\" width=\"100%\"/><img src=\"https://ganglong.wshoto.com/attachment/images/2/2017/10/yJO823oOV72OZ7S0s9sxogzhSaAH30.jpg\" alt=\"images/2/2017/10/yJO823oOV72OZ7S0s9sxogzhSaAH30.jpg\" width=\"100%\"/><img src=\"https://ganglong.wshoto.com/attachment/images/2/2017/10/fd2avr6UPqus736Aa3S27726NARVeD.jpg\" alt=\"images/2/2017/10/fd2avr6UPqus736Aa3S27726NARVeD.jpg\" width=\"100%\"/><\/p>","goodssn":"","productsn":"","productprice":"0.00","marketprice":"8728.00","costprice":"0.00","originalprice":"0.00","total":"44","totalcnf":"0","sales":"36","salesreal":"0","spec":"","createtime":"1508750521","weight":"0.00","credit":"0","maxbuy":"1","usermaxbuy":"1","hasoption":"1","dispatch":"0","thumb_url":"a:7:{i:0;s:51:\"images/2/2017/10/Z5NhlOOHOlh91Y1OO9oGGHzLhaFes5.jpg\";i:1;s:51:\"images/2/2017/10/kqN31K2N1l2p39pk116KDEpHy1YPpN.jpg\";i:2;s:51:\"images/2/2017/10/utV67C5Bc5F1zdt4v6F8v588411K6B.jpg\";i:3;s:51:\"images/2/2017/10/EFuZtVCdVStc4uDYUTczV4dc92dCtz.jpg\";i:4;s:51:\"images/2/2017/10/AJ0Z4E5o1zxONMP6Fj46FnMp6x1p66.jpg\";i:5;s:51:\"images/2/2017/10/F31IzgIIWRZiE6TEbIiN0Ign1VWNTg.jpg\";i:6;s:51:\"images/2/2017/10/B4wOA80w7r1Oj7XuJO7iIo1JrU1CII.jpg\";}","isnew":"1","ishot":"1","isdiscount":"1","isrecommand":"1","issendfree":"1","istime":"0","iscomment":"0","timestart":"1508750400","timeend":"1508750400","viewcount":"418","deleted":"0","hascommission":"0","commission1_rate":"0.00","commission1_pay":"0.00","commission2_rate":"0.00","commission2_pay":"0.00","commission3_rate":"0.00","commission3_pay":"0.00","score":"0.00","taobaoid":"","taotaoid":"","taobaourl":"","updatetime":"0","share_title":"","share_icon":"","cash":"1","commission_thumb":"","isnodiscount":"0","showlevels":"","buylevels":"","showgroups":"","buygroups":"","isverify":"1","storeids":"","noticeopenid":"","tcate":"0","noticetype":"0","needfollow":"0","followtip":"","followurl":"","deduct":"0.00"}]
     */

    private int statusCode;
    private List<ResultBean> result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 146
         * uniacid : 2
         * pcate : 5
         * ccate : 8
         * type : 1
         * status : 1
         * displayorder : 6
         * title : Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货
         * thumb : https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg
         * unit : 件
         * description :
         * content : <p><img src="http://ganglong.wshoto.com/attachment/images/2/2017/09/mJS8LPHZP8UHfjaBLQujqslfQfuRdA.jpg" alt="images/2/2017/09/mJS8LPHZP8UHfjaBLQujqslfQfuRdA.jpg" style="white-space: normal;" width="100%"/><img src="https://ganglong.wshoto.com/attachment/images/2/2017/10/SMsGG5azkfIHzlonFsm1OU6f3A9ZMM.jpg" alt="Apple-iPhone-X.jpg" width="100%"/><img src="https://ganglong.wshoto.com/attachment/images/2/2017/10/DTLbFLcZfbb8OC84Ht5B8CBhk0a8fZ.jpg" alt="images/2/2017/10/DTLbFLcZfbb8OC84Ht5B8CBhk0a8fZ.jpg" width="100%"/><img src="https://ganglong.wshoto.com/attachment/images/2/2017/10/yJO823oOV72OZ7S0s9sxogzhSaAH30.jpg" alt="images/2/2017/10/yJO823oOV72OZ7S0s9sxogzhSaAH30.jpg" width="100%"/><img src="https://ganglong.wshoto.com/attachment/images/2/2017/10/fd2avr6UPqus736Aa3S27726NARVeD.jpg" alt="images/2/2017/10/fd2avr6UPqus736Aa3S27726NARVeD.jpg" width="100%"/></p>
         * goodssn :
         * productsn :
         * productprice : 0.00
         * marketprice : 8728.00
         * costprice : 0.00
         * originalprice : 0.00
         * total : 44
         * totalcnf : 0
         * sales : 36
         * salesreal : 0
         * spec :
         * createtime : 1508750521
         * weight : 0.00
         * credit : 0
         * maxbuy : 1
         * usermaxbuy : 1
         * hasoption : 1
         * dispatch : 0
         * thumb_url : a:7:{i:0;s:51:"images/2/2017/10/Z5NhlOOHOlh91Y1OO9oGGHzLhaFes5.jpg";i:1;s:51:"images/2/2017/10/kqN31K2N1l2p39pk116KDEpHy1YPpN.jpg";i:2;s:51:"images/2/2017/10/utV67C5Bc5F1zdt4v6F8v588411K6B.jpg";i:3;s:51:"images/2/2017/10/EFuZtVCdVStc4uDYUTczV4dc92dCtz.jpg";i:4;s:51:"images/2/2017/10/AJ0Z4E5o1zxONMP6Fj46FnMp6x1p66.jpg";i:5;s:51:"images/2/2017/10/F31IzgIIWRZiE6TEbIiN0Ign1VWNTg.jpg";i:6;s:51:"images/2/2017/10/B4wOA80w7r1Oj7XuJO7iIo1JrU1CII.jpg";}
         * isnew : 1
         * ishot : 1
         * isdiscount : 1
         * isrecommand : 1
         * issendfree : 1
         * istime : 0
         * iscomment : 0
         * timestart : 1508750400
         * timeend : 1508750400
         * viewcount : 418
         * deleted : 0
         * hascommission : 0
         * commission1_rate : 0.00
         * commission1_pay : 0.00
         * commission2_rate : 0.00
         * commission2_pay : 0.00
         * commission3_rate : 0.00
         * commission3_pay : 0.00
         * score : 0.00
         * taobaoid :
         * taotaoid :
         * taobaourl :
         * updatetime : 0
         * share_title :
         * share_icon :
         * cash : 1
         * commission_thumb :
         * isnodiscount : 0
         * showlevels :
         * buylevels :
         * showgroups :
         * buygroups :
         * isverify : 1
         * storeids :
         * noticeopenid :
         * tcate : 0
         * noticetype : 0
         * needfollow : 0
         * followtip :
         * followurl :
         * deduct : 0.00
         */

        private String id;
        private String uniacid;
        private String pcate;
        private String ccate;
        private String type;
        private String status;
        private String displayorder;
        private String title;
        private String thumb;
        private String unit;
        private String description;
        private String content;
        private String goodssn;
        private String productsn;
        private String productprice;
        private String marketprice;
        private String costprice;
        private String originalprice;
        private String total;
        private String totalcnf;
        private String sales;
        private String salesreal;
        private String spec;
        private String createtime;
        private String weight;
        private String credit;
        private String maxbuy;
        private String usermaxbuy;
        private String hasoption;
        private String dispatch;
        private String thumb_url;
        private String isnew;
        private String ishot;
        private String isdiscount;
        private String isrecommand;
        private String issendfree;
        private String istime;
        private String iscomment;
        private String timestart;
        private String timeend;
        private String viewcount;
        private String deleted;
        private String hascommission;
        private String commission1_rate;
        private String commission1_pay;
        private String commission2_rate;
        private String commission2_pay;
        private String commission3_rate;
        private String commission3_pay;
        private String score;
        private String taobaoid;
        private String taotaoid;
        private String taobaourl;
        private String updatetime;
        private String share_title;
        private String share_icon;
        private String cash;
        private String commission_thumb;
        private String isnodiscount;
        private String showlevels;
        private String buylevels;
        private String showgroups;
        private String buygroups;
        private String isverify;
        private String storeids;
        private String noticeopenid;
        private String tcate;
        private String noticetype;
        private String needfollow;
        private String followtip;
        private String followurl;
        private String deduct;

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

        public String getPcate() {
            return pcate;
        }

        public void setPcate(String pcate) {
            this.pcate = pcate;
        }

        public String getCcate() {
            return ccate;
        }

        public void setCcate(String ccate) {
            this.ccate = ccate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(String displayorder) {
            this.displayorder = displayorder;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGoodssn() {
            return goodssn;
        }

        public void setGoodssn(String goodssn) {
            this.goodssn = goodssn;
        }

        public String getProductsn() {
            return productsn;
        }

        public void setProductsn(String productsn) {
            this.productsn = productsn;
        }

        public String getProductprice() {
            return productprice;
        }

        public void setProductprice(String productprice) {
            this.productprice = productprice;
        }

        public String getMarketprice() {
            return marketprice;
        }

        public void setMarketprice(String marketprice) {
            this.marketprice = marketprice;
        }

        public String getCostprice() {
            return costprice;
        }

        public void setCostprice(String costprice) {
            this.costprice = costprice;
        }

        public String getOriginalprice() {
            return originalprice;
        }

        public void setOriginalprice(String originalprice) {
            this.originalprice = originalprice;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTotalcnf() {
            return totalcnf;
        }

        public void setTotalcnf(String totalcnf) {
            this.totalcnf = totalcnf;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public String getSalesreal() {
            return salesreal;
        }

        public void setSalesreal(String salesreal) {
            this.salesreal = salesreal;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getMaxbuy() {
            return maxbuy;
        }

        public void setMaxbuy(String maxbuy) {
            this.maxbuy = maxbuy;
        }

        public String getUsermaxbuy() {
            return usermaxbuy;
        }

        public void setUsermaxbuy(String usermaxbuy) {
            this.usermaxbuy = usermaxbuy;
        }

        public String getHasoption() {
            return hasoption;
        }

        public void setHasoption(String hasoption) {
            this.hasoption = hasoption;
        }

        public String getDispatch() {
            return dispatch;
        }

        public void setDispatch(String dispatch) {
            this.dispatch = dispatch;
        }

        public String getThumb_url() {
            return thumb_url;
        }

        public void setThumb_url(String thumb_url) {
            this.thumb_url = thumb_url;
        }

        public String getIsnew() {
            return isnew;
        }

        public void setIsnew(String isnew) {
            this.isnew = isnew;
        }

        public String getIshot() {
            return ishot;
        }

        public void setIshot(String ishot) {
            this.ishot = ishot;
        }

        public String getIsdiscount() {
            return isdiscount;
        }

        public void setIsdiscount(String isdiscount) {
            this.isdiscount = isdiscount;
        }

        public String getIsrecommand() {
            return isrecommand;
        }

        public void setIsrecommand(String isrecommand) {
            this.isrecommand = isrecommand;
        }

        public String getIssendfree() {
            return issendfree;
        }

        public void setIssendfree(String issendfree) {
            this.issendfree = issendfree;
        }

        public String getIstime() {
            return istime;
        }

        public void setIstime(String istime) {
            this.istime = istime;
        }

        public String getIscomment() {
            return iscomment;
        }

        public void setIscomment(String iscomment) {
            this.iscomment = iscomment;
        }

        public String getTimestart() {
            return timestart;
        }

        public void setTimestart(String timestart) {
            this.timestart = timestart;
        }

        public String getTimeend() {
            return timeend;
        }

        public void setTimeend(String timeend) {
            this.timeend = timeend;
        }

        public String getViewcount() {
            return viewcount;
        }

        public void setViewcount(String viewcount) {
            this.viewcount = viewcount;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getHascommission() {
            return hascommission;
        }

        public void setHascommission(String hascommission) {
            this.hascommission = hascommission;
        }

        public String getCommission1_rate() {
            return commission1_rate;
        }

        public void setCommission1_rate(String commission1_rate) {
            this.commission1_rate = commission1_rate;
        }

        public String getCommission1_pay() {
            return commission1_pay;
        }

        public void setCommission1_pay(String commission1_pay) {
            this.commission1_pay = commission1_pay;
        }

        public String getCommission2_rate() {
            return commission2_rate;
        }

        public void setCommission2_rate(String commission2_rate) {
            this.commission2_rate = commission2_rate;
        }

        public String getCommission2_pay() {
            return commission2_pay;
        }

        public void setCommission2_pay(String commission2_pay) {
            this.commission2_pay = commission2_pay;
        }

        public String getCommission3_rate() {
            return commission3_rate;
        }

        public void setCommission3_rate(String commission3_rate) {
            this.commission3_rate = commission3_rate;
        }

        public String getCommission3_pay() {
            return commission3_pay;
        }

        public void setCommission3_pay(String commission3_pay) {
            this.commission3_pay = commission3_pay;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTaobaoid() {
            return taobaoid;
        }

        public void setTaobaoid(String taobaoid) {
            this.taobaoid = taobaoid;
        }

        public String getTaotaoid() {
            return taotaoid;
        }

        public void setTaotaoid(String taotaoid) {
            this.taotaoid = taotaoid;
        }

        public String getTaobaourl() {
            return taobaourl;
        }

        public void setTaobaourl(String taobaourl) {
            this.taobaourl = taobaourl;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getShare_title() {
            return share_title;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public String getShare_icon() {
            return share_icon;
        }

        public void setShare_icon(String share_icon) {
            this.share_icon = share_icon;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getCommission_thumb() {
            return commission_thumb;
        }

        public void setCommission_thumb(String commission_thumb) {
            this.commission_thumb = commission_thumb;
        }

        public String getIsnodiscount() {
            return isnodiscount;
        }

        public void setIsnodiscount(String isnodiscount) {
            this.isnodiscount = isnodiscount;
        }

        public String getShowlevels() {
            return showlevels;
        }

        public void setShowlevels(String showlevels) {
            this.showlevels = showlevels;
        }

        public String getBuylevels() {
            return buylevels;
        }

        public void setBuylevels(String buylevels) {
            this.buylevels = buylevels;
        }

        public String getShowgroups() {
            return showgroups;
        }

        public void setShowgroups(String showgroups) {
            this.showgroups = showgroups;
        }

        public String getBuygroups() {
            return buygroups;
        }

        public void setBuygroups(String buygroups) {
            this.buygroups = buygroups;
        }

        public String getIsverify() {
            return isverify;
        }

        public void setIsverify(String isverify) {
            this.isverify = isverify;
        }

        public String getStoreids() {
            return storeids;
        }

        public void setStoreids(String storeids) {
            this.storeids = storeids;
        }

        public String getNoticeopenid() {
            return noticeopenid;
        }

        public void setNoticeopenid(String noticeopenid) {
            this.noticeopenid = noticeopenid;
        }

        public String getTcate() {
            return tcate;
        }

        public void setTcate(String tcate) {
            this.tcate = tcate;
        }

        public String getNoticetype() {
            return noticetype;
        }

        public void setNoticetype(String noticetype) {
            this.noticetype = noticetype;
        }

        public String getNeedfollow() {
            return needfollow;
        }

        public void setNeedfollow(String needfollow) {
            this.needfollow = needfollow;
        }

        public String getFollowtip() {
            return followtip;
        }

        public void setFollowtip(String followtip) {
            this.followtip = followtip;
        }

        public String getFollowurl() {
            return followurl;
        }

        public void setFollowurl(String followurl) {
            this.followurl = followurl;
        }

        public String getDeduct() {
            return deduct;
        }

        public void setDeduct(String deduct) {
            this.deduct = deduct;
        }
    }
}

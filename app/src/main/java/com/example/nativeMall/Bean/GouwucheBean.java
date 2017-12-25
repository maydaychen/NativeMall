package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/10/25 10:02
 * 邮箱：2091320109@qq.com
 */
public class GouwucheBean {
    /**
     * statusCode : 1
     * result : {"list":[{"id":"136","total":"1","goodsid":"128","optionid":"670","createtime":"2017-11-25 11:34:45","stock":"153","maxbuy":"0","title":"苹果 Apple iPhone 8 plus 热销中","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/09/tjDyjqD4Z3J5JYF4Q7jjj74DYMXy6J.jpg","optionstock":"153","optiontitle":"64G公开版+银色","specs":"433_431","marketprice":"6028.00","productprice":"0.00"},{"id":"135","total":"1","goodsid":"146","optionid":"848","createtime":"2017-11-25 11:34:37","stock":"14","maxbuy":"1","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg","optionstock":"14","optiontitle":"灰色+256G","specs":"525_528","marketprice":"10618.00","productprice":"0.00"}],"total":2,"totalprice":"16,646.00"}
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
         * list : [{"id":"136","total":"1","goodsid":"128","optionid":"670","createtime":"2017-11-25 11:34:45","stock":"153","maxbuy":"0","title":"苹果 Apple iPhone 8 plus 热销中","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/09/tjDyjqD4Z3J5JYF4Q7jjj74DYMXy6J.jpg","optionstock":"153","optiontitle":"64G公开版+银色","specs":"433_431","marketprice":"6028.00","productprice":"0.00"},{"id":"135","total":"1","goodsid":"146","optionid":"848","createtime":"2017-11-25 11:34:37","stock":"14","maxbuy":"1","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg","optionstock":"14","optiontitle":"灰色+256G","specs":"525_528","marketprice":"10618.00","productprice":"0.00"}]
         * total : 2
         * totalprice : 16,646.00
         */

        private int total;
        private String totalprice;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 136
             * total : 1
             * goodsid : 128
             * optionid : 670
             * createtime : 2017-11-25 11:34:45
             * stock : 153
             * maxbuy : 0
             * title : 苹果 Apple iPhone 8 plus 热销中
             * thumb : https://ganglong.wshoto.com/attachment/images/2/2017/09/tjDyjqD4Z3J5JYF4Q7jjj74DYMXy6J.jpg
             * optionstock : 153
             * optiontitle : 64G公开版+银色
             * specs : 433_431
             * marketprice : 6028.00
             * productprice : 0.00
             */

            private String id;
            private String total;
            private String goodsid;
            private String optionid;
            private String createtime;
            private String stock;
            private String maxbuy;
            private String title;
            private String thumb;
            private String optionstock;
            private String optiontitle;
            private String specs;
            private String marketprice;
            private String productprice;

            public boolean isChoosed() {
                return choosed;
            }

            public void setChoosed(boolean choosed) {
                this.choosed = choosed;
            }

            private boolean choosed;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getGoodsid() {
                return goodsid;
            }

            public void setGoodsid(String goodsid) {
                this.goodsid = goodsid;
            }

            public String getOptionid() {
                return optionid;
            }

            public void setOptionid(String optionid) {
                this.optionid = optionid;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getMaxbuy() {
                return maxbuy;
            }

            public void setMaxbuy(String maxbuy) {
                this.maxbuy = maxbuy;
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

            public String getOptionstock() {
                return optionstock;
            }

            public void setOptionstock(String optionstock) {
                this.optionstock = optionstock;
            }

            public String getOptiontitle() {
                return optiontitle;
            }

            public void setOptiontitle(String optiontitle) {
                this.optiontitle = optiontitle;
            }

            public String getSpecs() {
                return specs;
            }

            public void setSpecs(String specs) {
                this.specs = specs;
            }

            public String getMarketprice() {
                return marketprice;
            }

            public void setMarketprice(String marketprice) {
                this.marketprice = marketprice;
            }

            public String getProductprice() {
                return productprice;
            }

            public void setProductprice(String productprice) {
                this.productprice = productprice;
            }
        }
    }
}

package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/29 15:22
 * 邮箱：2091320109@qq.com
 */
public class ShoucangBean {
    /**
     * statusCode : 1
     * result : [{"favoritetime":"2017-11-24 10:40:41","id":"146","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货","productprice":"0.00","marketprice":"8728.00"}]
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
         * favoritetime : 2017-11-24 10:40:41
         * id : 146
         * thumb : https://ganglong.wshoto.com/attachment/images/2/2017/10/Fagf8a88PwDuGA8Lg50LfG5gTGB11l.jpg
         * title : Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货
         * productprice : 0.00
         * marketprice : 8728.00
         */

        private String favoritetime;
        private String id;
        private String thumb;
        private String title;
        private String productprice;
        private String marketprice;

        public String getFavoritetime() {
            return favoritetime;
        }

        public void setFavoritetime(String favoritetime) {
            this.favoritetime = favoritetime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
    }
}

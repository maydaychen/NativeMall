package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2017/11/16.
 */

public class BannerBean {
    /**
     * statusCode : 1
     * result : [{"id":"2","uniacid":"2","advname":"打折狂欢","link":"","thumb":"http://ws7.wshoto.com/attachment/images/2/2017/01/OcQ0J4u70T4p8pOeUQpj6Q1Q0c6OT4.jpg","displayorder":"0","enabled":"1"},{"id":"5","uniacid":"2","advname":"过年打折","link":"","thumb":"http://ws7.wshoto.com/attachment/images/2/2017/01/r6fXH8vGlLsxVSbx6wVg5cX6XZc9zG.jpg","displayorder":"0","enabled":"1"}]
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
         * id : 2
         * uniacid : 2
         * advname : 打折狂欢
         * link :
         * thumb : http://ws7.wshoto.com/attachment/images/2/2017/01/OcQ0J4u70T4p8pOeUQpj6Q1Q0c6OT4.jpg
         * displayorder : 0
         * enabled : 1
         */

        private String id;
        private String uniacid;
        private String advname;
        private String link;
        private String thumb;
        private String displayorder;
        private String enabled;

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

        public String getAdvname() {
            return advname;
        }

        public void setAdvname(String advname) {
            this.advname = advname;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getDisplayorder() {
            return displayorder;
        }

        public void setDisplayorder(String displayorder) {
            this.displayorder = displayorder;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }
    }
}

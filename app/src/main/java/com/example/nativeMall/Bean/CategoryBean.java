package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2017/11/16.
 */

public class CategoryBean {
    /**
     * statusCode : 1
     * result : [{"id":"159","uniacid":"2","name":"优惠插件","thumb":false,"parentid":"0","isrecommand":"0","description":"","displayorder":"0","enabled":"1","ishome":"0","advimg":"http://ws7.wshoto.com/attachment/images/2/2016/12/RWVMVRY3VrCnXx6kiGKvBxV3vDK89V.png","advurl":"","level":"1","supplier_uid":null}]
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
         * id : 159
         * uniacid : 2
         * name : 优惠插件
         * thumb : false
         * parentid : 0
         * isrecommand : 0
         * description :
         * displayorder : 0
         * enabled : 1
         * ishome : 0
         * advimg : http://ws7.wshoto.com/attachment/images/2/2016/12/RWVMVRY3VrCnXx6kiGKvBxV3vDK89V.png
         * advurl :
         * level : 1
         * supplier_uid : null
         */

        private String id;
        private String uniacid;
        private String name;
        private boolean thumb;
        private String parentid;
        private String isrecommand;
        private String description;
        private String displayorder;
        private String enabled;
        private String ishome;
        private String advimg;
        private String advurl;
        private String level;
        private Object supplier_uid;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isThumb() {
            return thumb;
        }

        public void setThumb(boolean thumb) {
            this.thumb = thumb;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getIsrecommand() {
            return isrecommand;
        }

        public void setIsrecommand(String isrecommand) {
            this.isrecommand = isrecommand;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getIshome() {
            return ishome;
        }

        public void setIshome(String ishome) {
            this.ishome = ishome;
        }

        public String getAdvimg() {
            return advimg;
        }

        public void setAdvimg(String advimg) {
            this.advimg = advimg;
        }

        public String getAdvurl() {
            return advurl;
        }

        public void setAdvurl(String advurl) {
            this.advurl = advurl;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public Object getSupplier_uid() {
            return supplier_uid;
        }

        public void setSupplier_uid(Object supplier_uid) {
            this.supplier_uid = supplier_uid;
        }
    }
}

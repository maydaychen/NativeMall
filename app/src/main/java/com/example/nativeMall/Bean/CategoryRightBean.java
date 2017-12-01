package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2017/11/20.
 */

public class CategoryRightBean {
    /**
     * statusCode : 1
     * result : [{"id":"75","uniacid":"2","name":"限时秒杀","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/08/M55VG5Xx6iV65Tg5ZT5xN5GN5XXx5l.jpg","parentid":"74","isrecommand":"1","description":"","displayorder":"4","enabled":"1","ishome":"1","advimg":false,"advurl":"","level":"2","supplier_uid":null},{"id":"78","uniacid":"2","name":"游戏领奖","thumb":"https://ganglong.wshoto.com/attachment/images/2/2017/08/qSjj42nDOZdx4yCjycXyHo8XojGYJZ.jpg","parentid":"74","isrecommand":"0","description":"","displayorder":"1","enabled":"1","ishome":"0","advimg":false,"advurl":"","level":"2","supplier_uid":null}]
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
         * id : 75
         * uniacid : 2
         * name : 限时秒杀
         * thumb : https://ganglong.wshoto.com/attachment/images/2/2017/08/M55VG5Xx6iV65Tg5ZT5xN5GN5XXx5l.jpg
         * parentid : 74
         * isrecommand : 1
         * description :
         * displayorder : 4
         * enabled : 1
         * ishome : 1
         * advimg : false
         * advurl :
         * level : 2
         * supplier_uid : null
         */

        private String id;
        private String uniacid;
        private String name;
        private String thumb;
        private String parentid;
        private String isrecommand;
        private String description;
        private String displayorder;
        private String enabled;
        private String ishome;
        private boolean advimg;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
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

        public boolean isAdvimg() {
            return advimg;
        }

        public void setAdvimg(boolean advimg) {
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

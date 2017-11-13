package com.example.nativeMall.Bean;

import java.util.List;

/**
 * 作者：JTR on 2016/9/6 17:24
 * 邮箱：2091320109@qq.com
 */
public class FenleiBean {

    /**
     * data : [{"cateid":"12","name":"手机、数码、影音","parentid":"0","layer":"1","path":"12","icon":null,"children":null},{"cateid":"16","name":"一级分类1","parentid":"0","layer":"1","path":"16","icon":null,"children":null},{"cateid":"19","name":"一级分类2","parentid":"0","layer":"1","path":"19","icon":null,"children":null},{"cateid":"31","name":"大家电","parentid":"0","layer":"1","path":"31","icon":null,"children":null},{"cateid":"34","name":"图片分类1","parentid":"0","layer":"1","path":"34","icon":null,"children":null}]
     * msg : ok
     * success : T
     */

    private String msg;
    private String success;
    /**
     * cateid : 12
     * name : 手机、数码、影音
     * parentid : 0
     * layer : 1
     * path : 12
     * icon : null
     * children : null
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String cateid;
        private String name;
        private String parentid;
        private String layer;
        private String imgpath;
        private Object icon;
        private Object children;

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public String getPath() {
            return imgpath;
        }

        public void setPath(String path) {
            this.imgpath = path;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }
    }
}

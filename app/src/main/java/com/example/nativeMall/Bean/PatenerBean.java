package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2018/1/2.
 */

public class PatenerBean {
    /**
     * statusCode : 1
     * result : {"lists":[{"id":"2498","agentid":"204","openid":"oNSO9wP-5ij6IlLmPQZerNg3cXVA","nickname":"3333","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKvficsmFoxicrJPMtiae7MD7BHcO0bpvat4HJgPBY5I4K2uAnKfKicejyiabM0oYicGbuNbwkwYKslKQnw/132","createtime":"2017-12-31 16:00:15","isagent":"1","status":"1","agentlevel":"0","agenttime":"1514708399","oid":"15555","ostatus":"1","refundid":"0"},{"id":"1798","agentid":"204","openid":"oNSO9wC896PJs9_Y9RKBFzchiJRA","nickname":"","avatar":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDwBjo8a82HicFOWmadBzz6KzZGPnsYpZ03tw8V3jo2srVBJkn74mias5F08qZiaO2lfWIX0R1qTbfkg/132","createtime":"2017-09-22 19:59:48","isagent":"1","status":"1","agentlevel":"0","agenttime":"1507192153","oid":"15437","ostatus":"3","refundid":"0"},{"id":"1653","agentid":"273","openid":"oNSO9wLjmHCgOGvphJXRakRJxWfg","nickname":"天幕温水","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKSticiaA2aRMqnvOYNbeDic1gJRWvgXyv2fgDicVicPjd4qibWgGiaarg4TUHIJTgoe0KUfNuROEqnHO1hQ/132","createtime":"2017-09-18 18:46:30","isagent":"1","status":"0","agentlevel":"0","agenttime":"0","oid":"15429","ostatus":"3","refundid":"0"},{"id":"311","agentid":"204","openid":"oNSO9wKE-CFb1Dbahzm3zMmQoRiw","nickname":"Only one","avatar":"http://wx.qlogo.cn/mmopen/464KdXHKDZHm1ZeFIGiboibUpnmIB37mmufND0ELnSnuiaZbkThoph0jOiaGRibBjC4vrViaeuqoNG0nUOYEOAAyk3iaXGUtwLuGLwH/132","createtime":"2017-08-11 13:51:50","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503619466","oid":"15377","ostatus":"3","refundid":"0"},{"id":"293","agentid":"204","openid":"oNSO9wLyzXptz7t1OKWVecgY_H6c","nickname":"汤伟","avatar":"http://wx.qlogo.cn/mmopen/464KdXHKDZGmttF0kL9V4gqlG5Qq0H95DJwtEkbpqY7EeXISMN628GmNGFKKibnqeK7CP0n8r5ztianlib2nsvs3eXibBXibMAIdk/132","createtime":"2017-08-11 12:26:00","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503395881","oid":"15393","ostatus":"3","refundid":"0"},{"id":"237","agentid":"204","openid":"oNSO9wBIlB_qe9-1sHAkI7MQ6HE8","nickname":"仅此而已","avatar":"http://wx.qlogo.cn/mmopen/zlkJ7qgGqEwibtzicicl4Grr3rfoSAtCBO3lLn4gA83TciazHB5LKOttIGf4xOFQMH3JfvZYE7Hrk7MH8gH4y6DzEllhh1GFhqTic/0","createtime":"2017-08-11 09:46:11","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503974992","oid":"15405","ostatus":"3","refundid":"0"},{"id":"236","agentid":"204","openid":"oNSO9wEZNIXMX6TiCNLuhM96XQo0","nickname":"A会飞的鱼","avatar":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLBTpP8gw4O7pZCrapTqI0sbANwIRq17vz0xNSvtWVf6lsuYeXFnGcMMZ3tzKurdP2cNLfjXHnHTbg/132","createtime":"2017-08-11 09:45:26","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503398195","oid":"15385","ostatus":"3","refundid":"0"},{"id":"233","agentid":"204","openid":"oNSO9wLx6Hp_NpVHWNGCryK0yHpM","nickname":"香烟*520","avatar":"http://wx.qlogo.cn/mmopen/NVScz79licL81hfJAKvrHyKL1urlxlpwNKAibMIv4RiabLgeyicZbOvtpYXFq0Dr8ccLD0w5f50bleGVSPV1aPk0LUsSSEOMp9Ek/132","createtime":"2017-08-11 09:39:57","isagent":"1","status":"1","agentlevel":"0","agenttime":"1507107046","oid":"15391","ostatus":"3","refundid":"0"},{"id":"222","agentid":"204","openid":"oNSO9wLo1clrJyWKlXc1d1WbwGiQ","nickname":"李天威","avatar":"http://wx.qlogo.cn/mmopen/NVScz79licL95iccDoxuQMYXYD6w4qhZOlADF371mibzJUuiax7u8SoKqkCbw9FI8PvNqhcFicMjWicEmviaRlnFadulM7TLo18xWkL/132","createtime":"2017-08-11 09:22:57","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503399052","oid":"15350","ostatus":"3","refundid":"0"},{"id":"206","agentid":"204","openid":"oNSO9wCBWgc-Q_qSpNj9t95uGnLQ","nickname":"紫罗莎","avatar":"http://wx.qlogo.cn/mmopen/LXqicVqwJiatoTlcM221n2rdB5o7p3d7pbuGOL6icEvbRCJnSkfv3cfM2KGdr8237Uj6kgWpY2cPUZ2wVAfcZMvlQ/132","createtime":"2017-08-11 09:13:18","isagent":"1","status":"1","agentlevel":"0","agenttime":"1507107029","oid":"15384","ostatus":"3","refundid":"0"}],"next":true}
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
         * lists : [{"id":"2498","agentid":"204","openid":"oNSO9wP-5ij6IlLmPQZerNg3cXVA","nickname":"3333","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKvficsmFoxicrJPMtiae7MD7BHcO0bpvat4HJgPBY5I4K2uAnKfKicejyiabM0oYicGbuNbwkwYKslKQnw/132","createtime":"2017-12-31 16:00:15","isagent":"1","status":"1","agentlevel":"0","agenttime":"1514708399","oid":"15555","ostatus":"1","refundid":"0"},{"id":"1798","agentid":"204","openid":"oNSO9wC896PJs9_Y9RKBFzchiJRA","nickname":"","avatar":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLDwBjo8a82HicFOWmadBzz6KzZGPnsYpZ03tw8V3jo2srVBJkn74mias5F08qZiaO2lfWIX0R1qTbfkg/132","createtime":"2017-09-22 19:59:48","isagent":"1","status":"1","agentlevel":"0","agenttime":"1507192153","oid":"15437","ostatus":"3","refundid":"0"},{"id":"1653","agentid":"273","openid":"oNSO9wLjmHCgOGvphJXRakRJxWfg","nickname":"天幕温水","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKSticiaA2aRMqnvOYNbeDic1gJRWvgXyv2fgDicVicPjd4qibWgGiaarg4TUHIJTgoe0KUfNuROEqnHO1hQ/132","createtime":"2017-09-18 18:46:30","isagent":"1","status":"0","agentlevel":"0","agenttime":"0","oid":"15429","ostatus":"3","refundid":"0"},{"id":"311","agentid":"204","openid":"oNSO9wKE-CFb1Dbahzm3zMmQoRiw","nickname":"Only one","avatar":"http://wx.qlogo.cn/mmopen/464KdXHKDZHm1ZeFIGiboibUpnmIB37mmufND0ELnSnuiaZbkThoph0jOiaGRibBjC4vrViaeuqoNG0nUOYEOAAyk3iaXGUtwLuGLwH/132","createtime":"2017-08-11 13:51:50","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503619466","oid":"15377","ostatus":"3","refundid":"0"},{"id":"293","agentid":"204","openid":"oNSO9wLyzXptz7t1OKWVecgY_H6c","nickname":"汤伟","avatar":"http://wx.qlogo.cn/mmopen/464KdXHKDZGmttF0kL9V4gqlG5Qq0H95DJwtEkbpqY7EeXISMN628GmNGFKKibnqeK7CP0n8r5ztianlib2nsvs3eXibBXibMAIdk/132","createtime":"2017-08-11 12:26:00","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503395881","oid":"15393","ostatus":"3","refundid":"0"},{"id":"237","agentid":"204","openid":"oNSO9wBIlB_qe9-1sHAkI7MQ6HE8","nickname":"仅此而已","avatar":"http://wx.qlogo.cn/mmopen/zlkJ7qgGqEwibtzicicl4Grr3rfoSAtCBO3lLn4gA83TciazHB5LKOttIGf4xOFQMH3JfvZYE7Hrk7MH8gH4y6DzEllhh1GFhqTic/0","createtime":"2017-08-11 09:46:11","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503974992","oid":"15405","ostatus":"3","refundid":"0"},{"id":"236","agentid":"204","openid":"oNSO9wEZNIXMX6TiCNLuhM96XQo0","nickname":"A会飞的鱼","avatar":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLBTpP8gw4O7pZCrapTqI0sbANwIRq17vz0xNSvtWVf6lsuYeXFnGcMMZ3tzKurdP2cNLfjXHnHTbg/132","createtime":"2017-08-11 09:45:26","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503398195","oid":"15385","ostatus":"3","refundid":"0"},{"id":"233","agentid":"204","openid":"oNSO9wLx6Hp_NpVHWNGCryK0yHpM","nickname":"香烟*520","avatar":"http://wx.qlogo.cn/mmopen/NVScz79licL81hfJAKvrHyKL1urlxlpwNKAibMIv4RiabLgeyicZbOvtpYXFq0Dr8ccLD0w5f50bleGVSPV1aPk0LUsSSEOMp9Ek/132","createtime":"2017-08-11 09:39:57","isagent":"1","status":"1","agentlevel":"0","agenttime":"1507107046","oid":"15391","ostatus":"3","refundid":"0"},{"id":"222","agentid":"204","openid":"oNSO9wLo1clrJyWKlXc1d1WbwGiQ","nickname":"李天威","avatar":"http://wx.qlogo.cn/mmopen/NVScz79licL95iccDoxuQMYXYD6w4qhZOlADF371mibzJUuiax7u8SoKqkCbw9FI8PvNqhcFicMjWicEmviaRlnFadulM7TLo18xWkL/132","createtime":"2017-08-11 09:22:57","isagent":"1","status":"1","agentlevel":"0","agenttime":"1503399052","oid":"15350","ostatus":"3","refundid":"0"},{"id":"206","agentid":"204","openid":"oNSO9wCBWgc-Q_qSpNj9t95uGnLQ","nickname":"紫罗莎","avatar":"http://wx.qlogo.cn/mmopen/LXqicVqwJiatoTlcM221n2rdB5o7p3d7pbuGOL6icEvbRCJnSkfv3cfM2KGdr8237Uj6kgWpY2cPUZ2wVAfcZMvlQ/132","createtime":"2017-08-11 09:13:18","isagent":"1","status":"1","agentlevel":"0","agenttime":"1507107029","oid":"15384","ostatus":"3","refundid":"0"}]
         * next : true
         */

        private boolean next;
        private List<ListsBean> lists;

        public boolean isNext() {
            return next;
        }

        public void setNext(boolean next) {
            this.next = next;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * id : 2498
             * agentid : 204
             * openid : oNSO9wP-5ij6IlLmPQZerNg3cXVA
             * nickname : 3333
             * avatar : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKvficsmFoxicrJPMtiae7MD7BHcO0bpvat4HJgPBY5I4K2uAnKfKicejyiabM0oYicGbuNbwkwYKslKQnw/132
             * createtime : 2017-12-31 16:00:15
             * isagent : 1
             * status : 1
             * agentlevel : 0
             * agenttime : 1514708399
             * oid : 15555
             * ostatus : 1
             * refundid : 0
             */

            private String id;
            private String agentid;
            private String openid;
            private String nickname;
            private String avatar;
            private String createtime;
            private String isagent;
            private String status;
            private String agentlevel;
            private String agenttime;
            private String oid;
            private String ostatus;
            private String refundid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAgentid() {
                return agentid;
            }

            public void setAgentid(String agentid) {
                this.agentid = agentid;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getIsagent() {
                return isagent;
            }

            public void setIsagent(String isagent) {
                this.isagent = isagent;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAgentlevel() {
                return agentlevel;
            }

            public void setAgentlevel(String agentlevel) {
                this.agentlevel = agentlevel;
            }

            public String getAgenttime() {
                return agenttime;
            }

            public void setAgenttime(String agenttime) {
                this.agenttime = agenttime;
            }

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getOstatus() {
                return ostatus;
            }

            public void setOstatus(String ostatus) {
                this.ostatus = ostatus;
            }

            public String getRefundid() {
                return refundid;
            }

            public void setRefundid(String refundid) {
                this.refundid = refundid;
            }
        }
    }
}

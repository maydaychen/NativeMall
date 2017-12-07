package com.example.nativeMall.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/11/23.
 */

public class MemberBean implements Serializable{
    /**
     * statusCode : 1
     * result : {"id":"204","openid":"oNSO9wHBr-KbfKt0sNybs2wByWHY","level":"0","agentid":"0","weixin":"大王","realname":"汤正刚","mobile":"18915787525","createtime":"1502412809","agenttime":"1502413146","status":"1","isagent":"1","agentlevel":"0","nickname":"山不转水转","credit1":"29.00","credit2":"0.00","inviter":"0","gender":"1","birthyear":"","birthmonth":"","birthday":"","avatar":"http://wx.qlogo.cn/mmopen/NVScz79licL81hfJAKvrHyPUZAMQICIBNDTWnILibDXYH2uJnlBZ3rvtibzmNo0opLr9bprKl1qgQ6KEczZg0dyGQHXJWvNUPRQ/132","province":"江苏","city":"苏州","area":"","childtime":"0","parent_name":"江苏岗隆数码科技有限公司","leveldetail":{"level":0,"levelname":"尊享会员","discount":0},"agentleveldetail":{"levelname":"顶级分销商","commission1":"1.6","commission2":"0.4","commission3":0,"commission_fugou":[],"priority":0}}
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

    public static class ResultBean implements Serializable{
        /**
         * id : 204
         * openid : oNSO9wHBr-KbfKt0sNybs2wByWHY
         * level : 0
         * agentid : 0
         * weixin : 大王
         * realname : 汤正刚
         * mobile : 18915787525
         * createtime : 1502412809
         * agenttime : 1502413146
         * status : 1
         * isagent : 1
         * agentlevel : 0
         * nickname : 山不转水转
         * credit1 : 29.00
         * credit2 : 0.00
         * inviter : 0
         * gender : 1
         * birthyear :
         * birthmonth :
         * birthday :
         * avatar : http://wx.qlogo.cn/mmopen/NVScz79licL81hfJAKvrHyPUZAMQICIBNDTWnILibDXYH2uJnlBZ3rvtibzmNo0opLr9bprKl1qgQ6KEczZg0dyGQHXJWvNUPRQ/132
         * province : 江苏
         * city : 苏州
         * area :
         * childtime : 0
         * parent_name : 江苏岗隆数码科技有限公司
         * leveldetail : {"level":0,"levelname":"尊享会员","discount":0}
         * agentleveldetail : {"levelname":"顶级分销商","commission1":"1.6","commission2":"0.4","commission3":0,"commission_fugou":[],"priority":0}
         */

        private String id;
        private String openid;
        private String level;
        private String agentid;
        private String weixin;
        private String realname;
        private String mobile;
        private String createtime;
        private String agenttime;
        private String status;
        private String isagent;
        private String agentlevel;
        private String nickname;
        private String credit1;
        private String credit2;
        private String inviter;
        private String gender;
        private String birthyear;
        private String birthmonth;
        private String birthday;
        private String avatar;
        private String province;
        private String city;
        private String area;
        private String childtime;
        private String parent_name;
        private LeveldetailBean leveldetail;
        private AgentleveldetailBean agentleveldetail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getAgenttime() {
            return agenttime;
        }

        public void setAgenttime(String agenttime) {
            this.agenttime = agenttime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsagent() {
            return isagent;
        }

        public void setIsagent(String isagent) {
            this.isagent = isagent;
        }

        public String getAgentlevel() {
            return agentlevel;
        }

        public void setAgentlevel(String agentlevel) {
            this.agentlevel = agentlevel;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCredit1() {
            return credit1;
        }

        public void setCredit1(String credit1) {
            this.credit1 = credit1;
        }

        public String getCredit2() {
            return credit2;
        }

        public void setCredit2(String credit2) {
            this.credit2 = credit2;
        }

        public String getInviter() {
            return inviter;
        }

        public void setInviter(String inviter) {
            this.inviter = inviter;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthyear() {
            return birthyear;
        }

        public void setBirthyear(String birthyear) {
            this.birthyear = birthyear;
        }

        public String getBirthmonth() {
            return birthmonth;
        }

        public void setBirthmonth(String birthmonth) {
            this.birthmonth = birthmonth;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getChildtime() {
            return childtime;
        }

        public void setChildtime(String childtime) {
            this.childtime = childtime;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public LeveldetailBean getLeveldetail() {
            return leveldetail;
        }

        public void setLeveldetail(LeveldetailBean leveldetail) {
            this.leveldetail = leveldetail;
        }

        public AgentleveldetailBean getAgentleveldetail() {
            return agentleveldetail;
        }

        public void setAgentleveldetail(AgentleveldetailBean agentleveldetail) {
            this.agentleveldetail = agentleveldetail;
        }

        public static class LeveldetailBean implements Serializable{
            /**
             * level : 0
             * levelname : 尊享会员
             * discount : 0
             */

            private int level;
            private String levelname;
            private int discount;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }
        }

        public static class AgentleveldetailBean implements Serializable{
            /**
             * levelname : 顶级分销商
             * commission1 : 1.6
             * commission2 : 0.4
             * commission3 : 0
             * commission_fugou : []
             * priority : 0
             */

            private String levelname;
            private String commission1;
            private String commission2;
            private int commission3;
            private int priority;
            private List<?> commission_fugou;

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getCommission1() {
                return commission1;
            }

            public void setCommission1(String commission1) {
                this.commission1 = commission1;
            }

            public String getCommission2() {
                return commission2;
            }

            public void setCommission2(String commission2) {
                this.commission2 = commission2;
            }

            public int getCommission3() {
                return commission3;
            }

            public void setCommission3(int commission3) {
                this.commission3 = commission3;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public List<?> getCommission_fugou() {
                return commission_fugou;
            }

            public void setCommission_fugou(List<?> commission_fugou) {
                this.commission_fugou = commission_fugou;
            }
        }
    }
}

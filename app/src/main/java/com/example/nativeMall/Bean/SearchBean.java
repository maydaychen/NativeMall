package com.example.nativeMall.Bean;

import java.util.List;

/**
 * Created by user on 2017/11/27.
 */

public class SearchBean {
    /**
     * statusCode : 1
     * result : [{"id":"134","title":"Apple 苹果 MLA02CH/A 苹果原装Apple Magic Mouse 2 （苹果无线鼠标第二代）"},{"id":"121","title":"iPad mini4 7.9英寸国行128G限时秒杀价2999元"},{"id":"107","title":"官方正品Human Race运动鞋Boost减震轻便透气网面休闲鞋男女跑鞋"},{"id":"93","title":"渲美 TOUCHBeauty TB-1653女士脱毛器 腋下腋毛刮毛器 电动剃毛器 家用"},{"id":"92","title":"渲美 TOUCHBeauty 美容仪 TB-1581声波洁面美容仪 软毛电动洁面仪洗脸刷毛孔清洁器去油"},{"id":"91","title":"渲美 TOUCHBeauty 洁面仪 TB-1487声波超柔洁面仪 男女士电动净油去黑头仪器毛孔清洁器"},{"id":"90","title":"渲美 TOUCHBeauty 美容仪 TB-1682微电流美容仪 瘦脸仪滚轮紧致小V脸部美容按摩器"},{"id":"88","title":"breo 倍轻松 便携 头皮按摩器SCALP mini系列按摩爪 头部按摩仪"},{"id":"87","title":"breo 倍轻松 头部按摩器 iDream3 头部眼部按摩仪 保健器材 头部按摩机"},{"id":"86","title":"breo 倍轻松 智能头部按摩器 iDream5 头部眼部按摩仪 按摩器材 头部按摩机"},{"id":"85","title":"渲美 TOUCHBeauty 美容仪 TB-1483全身水洗洁面仪毛孔清洁器 电动洗脸刷洗脸仪美容仪"},{"id":"84","title":"渲美 TOUCHBeauty 美容仪 TB-1583眼部按摩仪器美眼仪去眼袋细纹黑眼圈美眼笔眼霜离子美容仪"},{"id":"83","title":"渲美 TOUCHBeauty 美容仪 TB-1666超声波离子导入导出仪 家用美容仪震动美容器 毛孔清洁器"},{"id":"82","title":"渲美 TOUCHBeauty 美容仪 TB-1185纳米补水美容仪 冷喷蒸脸器手持喷雾器"},{"id":"79","title":"D8 全金属智能车充 车载充电器适用苹果5s/6/6s plus手机ipad汽车插头正品 金色"},{"id":"78","title":"D8 全金属 iphone7数据线适用苹果5s/6/6s plus手机ipad充电器线认证正品土豪金"},{"id":"76","title":"D8 iphone5s数据线适用苹果6/6S充电线 认证ipad2认证数据线"},{"id":"74","title":"D8 iphone7数据线5/5s/6/6s plus手机ipad充电器线苹果认证"},{"id":"34","title":"苹果 Apple iPhone7 4.7英寸 全网通4G手机"},{"id":"33","title":"苹果 Apple iPhone7 Plus 5.5英寸 全网通4G手机"},{"id":"32","title":"苹果 Apple iPhone 6s 4.7英寸 苹果6s 全网通4G手机"},{"id":"31","title":"苹果 Apple iPhone 6 4.7英寸 4G手机全网通智能手机 金色 32G标配"},{"id":"30","title":"苹果 Apple iPhone 6s Plus 5.5英寸 全网通4G手机"},{"id":"23","title":"Huawei/华为 PLE-703L青春版M2通话4G手机WIFI平板电脑"},{"id":"22","title":"2017新款 Apple/苹果 13英寸：MacBook Pro 3.1GHz 处理器 512GB"},{"id":"21","title":"Apple/苹果 MacBook Pro MJLQ2CH/A笔记本电脑/15英寸/256GB"},{"id":"20","title":"2017新款 Apple/苹果 13英寸：MacBook Pro 128GB"},{"id":"19","title":"2017款 Apple/苹果 15.4英寸：MacBook Pro 2.9GHz 处理器 512GB"},{"id":"18","title":"国行Apple/苹果 MacBook Air MMGF2CH/A 13.3英寸 轻薄笔记本电脑"},{"id":"17","title":"2017款 Apple/苹果 13英寸：MacBook Pro 3.1GHz 处理器 256GB"},{"id":"16","title":"2016款Apple/苹果 12 英寸 MacBook 256GB轻薄学习办公笔记本"},{"id":"15","title":"17款Apple/苹果 13英寸：MacBook Pro 256GB 轻薄 商务笔记本电脑"},{"id":"14","title":"Apple/苹果 13 英寸 1.8GHz 处理器 256GB 存储容量 MacBook Air"},{"id":"13","title":"17款Apple/苹果 12 英寸 1.2GHz 处理器 256GB 存储容量 MacBook"},{"id":"12","title":"2016款Apple/苹果 12 英寸 MacBook 512GB轻薄学习办公笔记本"},{"id":"11","title":"2017款 Apple/苹果 15英寸：MacBook Pro 2.8GHz 处理器 256GB"},{"id":"10","title":"Apple/苹果 13 英寸 1.8GHz 处理器 MacBook Air 128GB"},{"id":"9","title":"17款Apple/苹果 12 英寸 1.3GHz 处理器 512GB 存储容量 MacBook"},{"id":"8","title":"国行 Apple/苹果 MacBook Air MMGG2CH/A 13.3英寸轻薄笔记本电脑"},{"id":"7","title":"国行Apple/苹果 MacBook Pro MF839CH/A 13.3英寸 商务笔记本电脑"},{"id":"6","title":"2017新款Apple/苹果 iPad Pro 10.5英寸 64G轻薄平板电脑wifi"},{"id":"5","title":"国行Apple/苹果 ipad pro wifi版 9.7英寸 32G/128G轻薄平板电脑"},{"id":"4","title":"Apple/苹果 iPad mini 4 7.9英寸 128G 平板电脑wifi"},{"id":"3","title":"2017年新款Apple/苹果 iPad平板电脑9.7英寸 32G/128G air2升级版"},{"id":"127","title":"苹果 Apple iPhone 8 热销中"},{"id":"128","title":"苹果 Apple iPhone 8 plus 热销中"},{"id":"146","title":"Apple 苹果 iPhone X 限量预售 发售当天按订单顺序发货"}]
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
         * id : 134
         * title : Apple 苹果 MLA02CH/A 苹果原装Apple Magic Mouse 2 （苹果无线鼠标第二代）
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

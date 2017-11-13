package com.example.nativeMall.entity;


public class ProductInfo extends BaseInfo {
    private String imageUrl;
    private String desc;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    private String recordId;
    private String pid;
    private double price;
    private int count;
    private int position;// 绝对位置，只在ListView构造的购物车中，在删除时有效

    public ProductInfo() {
        super();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public ProductInfo(String id, String name, String imageUrl, String desc, double price, int count, String recordId,String pid) {

        super.Id = id;
        super.name = name;
        this.imageUrl = imageUrl;
        this.desc = desc;
        this.price = price;
        this.count = count;
        this.recordId = recordId;
        this.pid = pid;


    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}

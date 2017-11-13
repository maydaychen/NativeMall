package com.example.nativeMall;

import com.example.nativeMall.Bean.CommentBean;
import com.example.nativeMall.Bean.DetailBean;
import com.example.nativeMall.Bean.GouwucheBean;
import com.example.nativeMall.Bean.MallStoreBean;
import com.example.nativeMall.Bean.PreOrderBean;
import com.example.nativeMall.Bean.ShoppingCarBean;
import com.example.nativeMall.Bean.UselessBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 作者：JTR on 2016/11/24 14:15
 * 邮箱：2091320109@qq.com
 */
public interface GoodsDetailService {
    @POST("order/shoppingCart")
    rx.Observable<HttpResult<List<ShoppingCarBean.DataBean>>> getString(@Body RequestBody route);

    //购物车
    @POST("order/buyproductpre")
    rx.Observable<HttpResult<GouwucheBean.DataBean>> gouwuche(@Body RequestBody route);

    //药店详情
    @POST("address/storedetail")
    rx.Observable<HttpResult<MallStoreBean.DataBean>> checkStore(@Body RequestBody route);

    //所有评价
    @POST("index/queryEvaluate")
    rx.Observable<HttpResult<List<CommentBean.DataBean>>> findComment(@Body RequestBody route);

    //收藏，取消收藏
    @POST("order/collect")
    rx.Observable<UselessBean> shoucang(@Body RequestBody route);

    //添加到购物车
    @POST("order/shoppingCart")
    rx.Observable<UselessBean> addToGouwuche(@Body RequestBody route);

    //提交预订单
    @POST("order/submitPreOrder")
    rx.Observable<PreOrderBean> buyNow(@Body RequestBody route);

    //基本信息查询
    @POST("order/productinfo")
    rx.Observable<DetailBean> checkDetail(@Body RequestBody route);

}
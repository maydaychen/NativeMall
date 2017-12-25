package com.example.nativeMall.http;

import org.json.JSONObject;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * 作者：JTR on 2016/11/24 14:15
 * 邮箱：2091320109@qq.com
 */
public interface BlueService {
//    @GET("themes")
//    rx.Observable<ThemeBean> getString();
//
//    @GET("news/latest")
//    rx.Observable<StoryBean> getLatest();
//
//    @GET("news/{id}")
//    rx.Observable<ContentBean> getContent(@Path("id") String id);
//
//    @GET("story-extra/{id}")
//    rx.Observable<CommentBean> getComment(@Path("id") String id);


    @GET("/login/ApiLogin")
    rx.Observable<JSONObject> index_info(@Query("apiname") String apiname, @Query("apipass") String apipass);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/login/sendCode")
    rx.Observable<JSONObject> send_code(@Field("access_token") String access_token, @Field("mobile") String mobile,
                                        @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/login")
    rx.Observable<JSONObject> login(@Field("access_token") String access_token, @Query("device_tokens") String device_tokens, @Field("kapkey") String kapkey,
                                    @Field("mobile") String mobile, @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @DELETE("/login/sessionkey")
    rx.Observable<JSONObject> dalete_token(@Query("access_token") String access_token, @Query("sessionkey") String sessionkey,
                                           @Query("sign") String sign, @Query("timestamp") int timestamp);


    @Headers({"Content-Type:application/x-www-form-urlencoded", "addons: ewei_shop"})
    @FormUrlEncoded
    @POST("/uploads")
    rx.Observable<JSONObject> getAva(@Field("access_token") String access_token, @Field("avatar") String avatar,
                                     @Field("sessionkey") String sessionkey, @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/slides")
    rx.Observable<JSONObject> get_banner(@Query("access_token") String access_token,
                                         @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/categories")
    rx.Observable<JSONObject> getCategory(@Query("access_token") String access_token,
                                          @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/products/categories")
    rx.Observable<JSONObject> getCategoryGoods(@Query("access_token") String access_token, @Query("sessionkey") String session_key, @Query("ccate") String ccate,
                                               @Query("pcate") String pcate, @Query("psize") int psize, @Query("page") int page, @Query("sorttype") String sorttype,
                                               @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/categories")
    rx.Observable<JSONObject> getRightCategory(@Query("access_token") String access_token, @Query("pid") String pid,
                                               @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/products/attributes")
    rx.Observable<JSONObject> getAttributes(@Query("access_token") String access_token,
                                            @Query("sorttype") String sorttype, @Query("attributes") String attributes, @Query("page") int page,
                                            @Query("psize") int psize, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/products")
    rx.Observable<JSONObject> good_detail(@Query("access_token") String access_token, @Query("sessionkey") String session_key, @Query("id") String id,
                                          @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/orders/confirm")
    rx.Observable<JSONObject> buy_now(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey, @Query("goodsid") String goodsid,
                                      @Query("optionid") String optionid, @Query("cartids") String cartids, @Query("total") String total,
                                      @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/orders/confirm")
    rx.Observable<JSONObject> confirm_order(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                            @Field("goods") String goods, @Field("addressid") String addressid, @Field("dispatchid") String dispatchid,
                                            @Field("remark") String remark, @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/orders/payment")
    rx.Observable<JSONObject> pay_now(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                      @Field("ordersn") String ordersn, @Field("type") String type, @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/addresses")
    rx.Observable<JSONObject> addressList(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey,
                                          @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @DELETE("/addresses")
    rx.Observable<JSONObject> deleteAddress(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey,
                                            @Query("addressid") String addressid, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/addresses")
    rx.Observable<JSONObject> addAddress(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                         @Field("realname") String realname, @Field("mobile") String mobile,
                                         @Field("province") String province, @Field("city") String city,
                                         @Field("area") String area, @Field("address") String address,
                                         @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @PUT("/addresses")
    rx.Observable<JSONObject> editAddress(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                          @Field("realname") String realname, @Field("mobile") String mobile,
                                          @Field("province") String province, @Field("city") String city,
                                          @Field("area") String area, @Field("address") String address,
                                          @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/members/memberInfo")
    rx.Observable<JSONObject> member_info(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey,
                                          @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/products/favorite")
    rx.Observable<JSONObject> shoucang_list(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey, @Query("fields") String fields,
                                            @Query("page") int page, @Query("psize") int psize, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/products/favorite")
    rx.Observable<JSONObject> add_shoucang(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                           @Field("goodsid") String goodsid, @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @DELETE("/products/favorite")
    rx.Observable<JSONObject> delete_shoucang(@Query("access_token") String access_token, @Query("sessionkey") String sessionkey,
                                              @Query("goodsid") String goodsid, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/products/history")
    rx.Observable<JSONObject> history_list(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey, @Query("fields") String fields,
                                           @Query("page") int page, @Query("psize") int psize, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/carts")
    rx.Observable<JSONObject> cartsList(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey,
                                        @Query("sign") String sign, @Query("timestamp") int timestamp);


    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @POST("/carts")
    rx.Observable<JSONObject> addCart(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                      @Field("goodsid") String goodsid, @Field("total") int total, @Query("optionid") String optionid,
                                      @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @FormUrlEncoded
    @PUT("/carts")
    rx.Observable<JSONObject> changeCart(@Field("access_token") String access_token, @Field("sessionkey") String sessionkey,
                                         @Field("cartid") String cartid, @Field("type") String type,
                                         @Field("sign") String sign, @Field("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @DELETE("/carts")
    rx.Observable<JSONObject> deleteCart(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey,
                                         @Query("cartid") String cartid, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/carts/cartNums")
    rx.Observable<JSONObject> cartNum(@Query("access_token") String accessToken, @Query("sessionkey") String sessionKey,
                                      @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/products/search")
    rx.Observable<JSONObject> searchGoods(@Query("access_token") String accessToken, @Query("keywords") String keywords, @Query("sessionkey") String sessionKey,
                                          @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/qrimgs")
    rx.Observable<JSONObject> getCode(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/commissions/recordStatistics")
    rx.Observable<JSONObject> tixianList(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey, @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/coupons/my")
    rx.Observable<JSONObject> couponList(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey,
                                         @Query("page") String page, @Query("status") String status, @Query("psize") String psize,
                                         @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/coupons")
    rx.Observable<JSONObject> usefulCouponList(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey,
                                               @Query("page") String page, @Query("catid") String catid, @Query("psize") String psize,
                                               @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/coupons/category")
    rx.Observable<JSONObject> couponTitle(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey,
                                          @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/coupons/detail")
    rx.Observable<JSONObject> couponDetail(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey, @Query("couponid") String couponid,
                                           @Query("sign") String sign, @Query("timestamp") int timestamp);

    @Headers("addons: ewei_shop")
    @GET("/orders")
    rx.Observable<JSONObject> orderList(@Query("access_token") String accessToken, @Query("sessionkey") String sessionkey,
                                        @Query("page") String page, @Query("status") String status, @Query("psize") String psize,
                                        @Query("sign") String sign, @Query("timestamp") int timestamp);
}
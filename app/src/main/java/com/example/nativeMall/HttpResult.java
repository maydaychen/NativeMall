package com.example.nativeMall;

/**
 * 作者：JTR on 2016/11/25 10:31
 * 邮箱：2091320109@qq.com
 */
public class HttpResult<T> {
    private String msg;
    private String success;
    private T data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {

        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package com.mobile.centaur.network;

/**
 * created by zzz
 *
 * @class describe
 */
public class BaseResponse<T> {

    public static final String STATUS_SUCCESS = "0";

    private String message; // message 可用来返回接口的说明
    private String code; // 返回的code
    private String need_update;
    private String cur_time;
    private String result; // 返回的result
    private T data; // 具体的数据结果

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T page) {
        this.data = page;
    }
}


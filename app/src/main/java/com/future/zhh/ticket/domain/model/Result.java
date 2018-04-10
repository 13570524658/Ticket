package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/21.
 */

public class Result<T>  {
    private boolean success;
    private String msg;
    private T data;
    private String backCode = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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

    public String getBackCode() {
        return backCode;
    }

    public void setBackCode(String backCode) {
        this.backCode = backCode;
    }

}

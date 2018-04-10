package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogInfo {
    private String customerName;//客户名称
    private String customerID;//用户卡编号
    private String address;//客户地址
    private String checkTime;//检查时间
    private String isPass;//0=合格 1=不合格

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }
}

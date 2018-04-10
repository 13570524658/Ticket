package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceByCustomerListInfo {
    private String orderID;//订单编号
    private String address;//客户地址
    private String orderTime;//下单时间
    private String  transportType ;//配送方式
    private String phone;//手机
    private int state;//状态 0=已完成 1=发货中

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

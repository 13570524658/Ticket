package com.future.zhh.ticket.domain.model;

import com.future.zhh.ticket.presentation.view.activities.ScanGasInfoActivity;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceLogListInfo {
    private String SinceLogListTotal;// 订单自提记录数量
    private String orderID;//订单编号
    private String customerName;//客户名称
    private String address;//客户地址
    private String endTime;//完成时间
    private String state;//状态 0=已完成 1=发货中

    public String getSinceLogListTotal() {
        return SinceLogListTotal;
    }

    public void setSinceLogListTotal(String sinceLogListTotal) {
        SinceLogListTotal = sinceLogListTotal;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceTaskMsgInfo {
    private String orderID;//订单编号
    private String from;//订单来源
    private String tranSportType;//配送方式
    private String customerName;//客户名称
    private String customerID;//客户编号
    private String address;//客户地址
    private String customerCardNo;//用户卡编号
    private String station;//所属气站
    private String orderTime;//下单时间
    private String endTime;//完成时间
    private String phone;//客户手机
    private int state;//状态 0=已完成 1=发货中
    private String payGasTotal;//已交付气瓶数量
    private String RecyclingGasTotal;//已交付气瓶数量

    private List<PayGasListInfo> payGasInfoList;
    private List<RecyclingGasListInfo> recyclingGasInfoList;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTranSportType() {
        return tranSportType;
    }

    public void setTranSportType(String tranSportType) {
        this.tranSportType = tranSportType;
    }

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

    public String getCustomerCardNo() {
        return customerCardNo;
    }

    public void setCustomerCardNo(String customerCardNo) {
        this.customerCardNo = customerCardNo;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPayGasTotal() {
        return payGasTotal;
    }

    public void setPayGasTotal(String payGasTotal) {
        this.payGasTotal = payGasTotal;
    }

    public String getRecyclingGasTotal() {
        return RecyclingGasTotal;
    }

    public void setRecyclingGasTotal(String recyclingGasTotal) {
        RecyclingGasTotal = recyclingGasTotal;
    }

    public List<PayGasListInfo> getPayGasInfoList() {
        return payGasInfoList;
    }

    public void setPayGasInfoList(List<PayGasListInfo> payGasInfoList) {
        this.payGasInfoList = payGasInfoList;
    }

    public List<RecyclingGasListInfo> getRecyclingGasInfoList() {
        return recyclingGasInfoList;
    }

    public void setRecyclingGasInfoList(List<RecyclingGasListInfo> recyclingGasInfoList) {
        this.recyclingGasInfoList = recyclingGasInfoList;
    }
}

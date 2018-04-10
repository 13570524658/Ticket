package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceByOrderInfo {
    private String state;//订单状态 0=已完成1=已发货
    private String orderID;//订单编号
    private String from;//订单来源
    private String type;//0=自提 1=配送
   private String enterpriseID;//企业ID
  private   String   enterpriseName;//企业名称


    private String customerName;//客户名称
    private String customerID;//客户编号
    private String  address;//地址
    private String phone;//手机
    private String  customerCardNo;//用户卡编号
    private String station;//所属气站
    private String orderTime;//下单时间
    private String endTime;//完成时间
    private String  payTotal;//交付气瓶总数
    private String RecyclingTotal;//回收气瓶总数

   private List<SincePayGasInfo> payGasInfoList;//交付气瓶
   private List<RecyclingGasInfo> recyclingGasInfoList;//回收气瓶


    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(String payTotal) {
        this.payTotal = payTotal;
    }

    public String getRecyclingTotal() {
        return RecyclingTotal;
    }

    public void setRecyclingTotal(String recyclingTotal) {
        RecyclingTotal = recyclingTotal;
    }

    public List<SincePayGasInfo> getPayGasInfoList() {
        return payGasInfoList;
    }

    public void setPayGasInfoList(List<SincePayGasInfo> payGasInfoList) {
        this.payGasInfoList = payGasInfoList;
    }

    public List<RecyclingGasInfo> getRecyclingGasInfoList() {
        return recyclingGasInfoList;
    }

    public void setRecyclingGasInfoList(List<RecyclingGasInfo> recyclingGasInfoList) {
        this.recyclingGasInfoList = recyclingGasInfoList;
    }
}

package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportTaskDetailInfo {
    private String transpotID;//配送单ID
    private String transportTaskID;//配送任务ID
    private String  orderID;//订单ID
    private String customerID;//客户ID
    private String customerCardID;//用户卡编号
    private String customerName;//客户名称
    private String  transportStatus;//配送状态 0=已完成,1=已发货
    private String transportType;//配送方式
    private String gasStation;//气站
    private String address1;//地址1
    private String address2;//地址2
    private   String  phone1;//手机1
    private String  phone2;//手机2
    private String transportStartTime;//配送单创建时间
    private String  transportEndTime;//配送单完成时间

    private int payTotal;//交付气瓶总数
    private int reclaimTotal;//回收气瓶总数

    private List<PayGasInfo> payGasInfoList;//交付气瓶
    private List<ReclaimGasInfo> reclaimGasInfoList;//回收气瓶
    public int getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(int payTotal) {
        this.payTotal = payTotal;
    }


    public int getReclaimTotal() {
        return reclaimTotal;
    }

    public void setReclaimTotal(int reclaimTotal) {
        this.reclaimTotal = reclaimTotal;
    }

    public List<PayGasInfo> getPayGasInfoList() {
        return payGasInfoList;
    }

    public String getCustomerCardID() {
        return customerCardID;
    }

    public void setCustomerCardID(String customerCardID) {
        this.customerCardID = customerCardID;
    }

    public void setPayGasInfoList(List<PayGasInfo> payGasInfoList) {
        this.payGasInfoList = payGasInfoList;
    }

    public List<ReclaimGasInfo> getReclaimGasInfoList() {
        return reclaimGasInfoList;
    }

    public void setReclaimGasInfoList(List<ReclaimGasInfo> reclaimGasInfoList) {
        this.reclaimGasInfoList = reclaimGasInfoList;
    }

    public String getTranspotID() {
        return transpotID;
    }

    public void setTranspotID(String transpotID) {
        this.transpotID = transpotID;
    }

    public String getTransportTaskID() {
        return transportTaskID;
    }

    public void setTransportTaskID(String transportTaskID) {
        this.transportTaskID = transportTaskID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getTransportStartTime() {
        return transportStartTime;
    }

    public void setTransportStartTime(String transportStartTime) {
        this.transportStartTime = transportStartTime;
    }

    public String getTransportEndTime() {
        return transportEndTime;
    }

    public void setTransportEndTime(String transportEndTime) {
        this.transportEndTime = transportEndTime;
    }
}

package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class OrderTaskInfo {
    private  String   transportID;//配送单ID
    private  String  transportType;//配送方式
    private  String   orderID;//订单ID
    private   String   customerID;//客户ID
    private   String   customerCardNo;//客户卡号
    private   String   enterpriseID;////企业ID
    private   String   shopID;//门店ID
    private   String   stationID;//气站ID
    private   String   customerName;//客户名称
    private   String   enterpriseName;//企业名称
    private   String   shopName;//门店名称
    private   String   stationName;//气站名称
    private  String   customerAddress;//客户地址
    private  String     transportStatus;//配送状态0=已完成，1=已发货，2=待回单
    private  String    orderStatus;//0=正常 1=没有分配给当前App登录人，无法交付回收
    private String    phone;//客户电话
    private   String    transportTime;//配送单创建时间
    private List<OrderTaskListInfo> orderTaskListInfoList;



    public String getTransportID() {
        return transportID;
    }

    public void setTransportID(String transportID) {
        this.transportID = transportID;
    }

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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerCardNo() {
        return customerCardNo;
    }

    public void setCustomerCardNo(String customerCardNo) {
        this.customerCardNo = customerCardNo;
    }

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }


    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransportTime() {
        return transportTime;
    }

    public void setTransportTime(String transportTime) {
        this.transportTime = transportTime;
    }

    public List<OrderTaskListInfo> getOrderTaskListInfoList() {
        return orderTaskListInfoList;
    }

    public void setOrderTaskListInfoList(List<OrderTaskListInfo> orderTaskListInfoList) {
        this.orderTaskListInfoList = orderTaskListInfoList;
    }
}

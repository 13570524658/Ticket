package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/28.
 */

public class OrderInfo {
    private String enterpriseID;//所属企业ID
    private String enterpriseName;//所属企业名称
    private String orderID;//订单编号
    private String orderFrom;//订单来源
    private String orderStatus;//订单状态
    private String tranSportType;//配送方式
    private String customerName;//客户名称
    private String customerID;//客户编号
    private String phone1;//电话1
    private String phone2;//电话2
    private String address1;//地址1
    private String address2;//地址2
    private String orderTime;//下单时间
    private String orderQrCodeImage;//订单二维码


    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

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

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderQrCodeImage() {
        return orderQrCodeImage;
    }

    public void setOrderQrCodeImage(String orderQrCodeImage) {
        this.orderQrCodeImage = orderQrCodeImage;
    }
}

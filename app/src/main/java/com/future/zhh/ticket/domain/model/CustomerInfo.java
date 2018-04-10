package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CustomerInfo {
    private String customerID;//客户ID
    private String customerName;//客户名称
    private String customerCardNo;//用户卡编号
    private String customerLevel;//用户等级
    private String enterpriseName;//所属供气企业
    private String enterpriseID;//所属企业ID
    private String gasStaion;//所属气站
    private String area;//所属区域
    private String customerClass1;//客户分类一
    private String customerClass2;//客户分类二
    private String address1;//客户地址1
    private String address2;//客户地址2
    private String phone1;//手机1
    private String phone2;//手机2
    private String userQrCodeImage;//用户二维码图


    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
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

    public String getCustomerCardNo() {
        return customerCardNo;
    }

    public void setCustomerCardNo(String customerCardNo) {
        this.customerCardNo = customerCardNo;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getGasStaion() {
        return gasStaion;
    }

    public void setGasStaion(String gasStaion) {
        this.gasStaion = gasStaion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustomerClass1() {
        return customerClass1;
    }

    public void setCustomerClass1(String customerClass1) {
        this.customerClass1 = customerClass1;
    }

    public String getCustomerClass2() {
        return customerClass2;
    }

    public void setCustomerClass2(String customerClass2) {
        this.customerClass2 = customerClass2;
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

    public String getUserQrCodeImage() {
        return userQrCodeImage;
    }

    public void setUserQrCodeImage(String userQrCodeImage) {
        this.userQrCodeImage = userQrCodeImage;
    }
}

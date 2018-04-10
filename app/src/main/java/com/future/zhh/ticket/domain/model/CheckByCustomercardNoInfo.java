package com.future.zhh.ticket.domain.model;

import com.future.zhh.ticket.presentation.view.activities.ScanInfoActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class CheckByCustomercardNoInfo {
    private String enterpriseID;//所属企业ID
    private String enterpriseName;//所属企业名称
    private String customerName;//客户名称
    private String customerID;//客户编号
    private String phone;//手机
    private String customerCardNo;//用户卡编号
    private String station;
    private List<AddressListInfo> addressInfoList;//地址

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

    public List<AddressListInfo> getAddressInfoList() {
        return addressInfoList;
    }

    public void setAddressInfoList(List<AddressListInfo> addressInfoList) {
        this.addressInfoList = addressInfoList;
    }
}

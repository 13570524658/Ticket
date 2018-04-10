package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceByCustomerInfo {
    private String enterpriseID;//企业ID
    private   String   enterpriseName;//企业名称
    private String customerName;//客户名称
    private String customerID;//客户编号
    private String customerCardNo;//用户卡ID
    private String station;//气站
    private String type;//0=自提 1=配送
    private int total;//订单自提记录数量
    private List<SinceByCustomerListInfo> sinceByCustomerInfoList;

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

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SinceByCustomerListInfo> getSinceByCustomerInfoList() {
        return sinceByCustomerInfoList;
    }

    public void setSinceByCustomerInfoList(List<SinceByCustomerListInfo> sinceByCustomerInfoList) {
        this.sinceByCustomerInfoList = sinceByCustomerInfoList;
    }
}

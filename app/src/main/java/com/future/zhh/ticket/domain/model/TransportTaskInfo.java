package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportTaskInfo {
    private String  status;//订单状态 0=已完成 1=已发货
    private String transportID;//配送单ID
    private String transportTaskID;//配送任务ID
    private String customerName;//客户名称
    private String address;//客户地址
    private String transportTotal;//配送任务总数
    private String transportTime;//配送单创建时间

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransportID() {
        return transportID;
    }

    public void setTransportID(String transportID) {
        this.transportID = transportID;
    }

    public String getTransportTaskID() {
        return transportTaskID;
    }

    public void setTransportTaskID(String transportTaskID) {
        this.transportTaskID = transportTaskID;
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

    public String getTransportTotal() {
        return transportTotal;
    }

    public void setTransportTotal(String transportTotal) {
        this.transportTotal = transportTotal;
    }

    public String getTransportTime() {
        return transportTime;
    }

    public void setTransportTime(String transportTime) {
        this.transportTime = transportTime;
    }
}

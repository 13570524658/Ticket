package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/29.
 */

public class CustomerCardNoTaskListInfo {
  private   String   transportTaskID;//配送任务编号
    private  int transportTotal;//配送任务数量
    private   String     transportStatus;//配送状态0=已完成，1=已发货，2=待回单
    private   String    phone;//客户手机
    private    String    transportTime;//配送单创建时间
    private   String   customerAddress;//客户地址

    private   String    deliveryMsg;//交付情况
    private    String    reclaimMsg;//回收情况

    public String getTransportTaskID() {
        return transportTaskID;
    }

    public void setTransportTaskID(String transportTaskID) {
        this.transportTaskID = transportTaskID;
    }

    public int getTransportTotal() {
        return transportTotal;
    }

    public void setTransportTotal(int transportTotal) {
        this.transportTotal = transportTotal;
    }

    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDeliveryMsg() {
        return deliveryMsg;
    }

    public void setDeliveryMsg(String deliveryMsg) {
        this.deliveryMsg = deliveryMsg;
    }

    public String getReclaimMsg() {
        return reclaimMsg;
    }

    public void setReclaimMsg(String reclaimMsg) {
        this.reclaimMsg = reclaimMsg;
    }
}

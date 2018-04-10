package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/28.
 */

public class WorkerInfo {
    private   String    enterpriseID;//所属企业ID
    private    String    enterpriseName;//所属企业名称
    private String workerName;//从业人员名称
    private String workerID;//从业人员编号
    private String sex;//性别
    private String phone;//手机
    private String isAdmin;//0=企业,1=店长,2=送气工,3=充装工,4=收银员,5=仓管员,6=司机，7=门卫
    private String detName;//所属部门
    private String gasStation;//气站
    private String workerQrcodeImage;//从业人员二维码图


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

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDetName() {
        return detName;
    }

    public void setDetName(String detName) {
        this.detName = detName;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getWorkerQrcodeImage() {
        return workerQrcodeImage;
    }

    public void setWorkerQrcodeImage(String workerQrcodeImage) {
        this.workerQrcodeImage = workerQrcodeImage;
    }
}

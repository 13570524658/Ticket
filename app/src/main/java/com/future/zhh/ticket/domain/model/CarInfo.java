package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CarInfo {
    private String enterpriseID;//所属企业ID
    private String enterpriseName;//所属企业名称
    private String detName;//所属部门名称
    private String carModule;//车辆型号
    private String carResponsiblePerson;//车辆负责人
    private String phone;//手机
    private String productFactory;//生产厂家
    private String productTime;//生产日期
    private String userTime;//使用年限
    private String maintenanceTime;//检修周期
    private String nowMaintenanceTime;//最近检修时间
    private String gasStation;//所服务的气站
    private String carImage1;//车辆照片1
    private String carImage2;//车辆照片2
    private String carImage3;//车辆照片3
    private String carNo;//车牌号
    private String carID;//车辆编号
    private String carUse;//车辆用途

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


    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarUse() {
        return carUse;
    }

    public void setCarUse(String carUse) {
        this.carUse = carUse;
    }

    public String getDetName() {
        return detName;
    }

    public void setDetName(String detName) {
        this.detName = detName;
    }

    public String getCarModule() {
        return carModule;
    }

    public void setCarModule(String carModule) {
        this.carModule = carModule;
    }

    public String getCarResponsiblePerson() {
        return carResponsiblePerson;
    }

    public void setCarResponsiblePerson(String carResponsiblePerson) {
        this.carResponsiblePerson = carResponsiblePerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductFactory() {
        return productFactory;
    }

    public void setProductFactory(String productFactory) {
        this.productFactory = productFactory;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(String maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

    public String getNowMaintenanceTime() {
        return nowMaintenanceTime;
    }

    public void setNowMaintenanceTime(String nowMaintenanceTime) {
        this.nowMaintenanceTime = nowMaintenanceTime;
    }

    public String getGasStation() {
        return gasStation;
    }

    public void setGasStation(String gasStation) {
        this.gasStation = gasStation;
    }

    public String getCarImage1() {
        return carImage1;
    }

    public void setCarImage1(String carImage1) {
        this.carImage1 = carImage1;
    }

    public String getCarImage2() {
        return carImage2;
    }

    public void setCarImage2(String carImage2) {
        this.carImage2 = carImage2;
    }

    public String getCarImage3() {
        return carImage3;
    }

    public void setCarImage3(String carImage3) {
        this.carImage3 = carImage3;
    }
}

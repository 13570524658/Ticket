package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/21.
 */

public class UserInfo {
    private String userQrCode;//用户二维码
    private String id;//用户ID
    private String name;//用户名称
    private String userID;//账号
    private String password;//密码
    private String enterpriseID;//企业ID
    private String gasStationID;//气站ID
    private String shopID;//门店ID
    private String deptID;//部门ID
    private String enterpriseName;//企业名称
    private String gasStationName;//气站名称
    private String shopName;//门店名称
    private String deptName;//部门名称
    private String mobilePhone;//手机号
    private String isAdmin;//0=企业,1=店长,2=送气工,3=充装工,4=收银员,5=仓管员,6=司机
    private String  useStatus;//使用状态  0=正常 1=禁用 2=停用





    public String getUserQrCode() {
        return userQrCode;
    }

    public void setUserQrCode(String userQrCode) {
        this.userQrCode = userQrCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getGasStationID() {
        return gasStationID;
    }

    public void setGasStationID(String gasStationID) {
        this.gasStationID = gasStationID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

}

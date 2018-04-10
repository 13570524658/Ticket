package com.future.zhh.ticket.domain.model;



import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogDetailInfo {
    private String isAirVents;//室内有无通风口0=是 1=否
    private String isSafetyWarning;//有无安全警示标志0=是 1=否
    private String isAlarm;//有无燃气报警器0=是 1=否
    private String isTrueAlarm;//是否正确安装燃气报警器0=是 1=否
    private String isFireEquipment;//有无消防器材0=是 1=否
    private int outTimeGasTotal;//过期的气瓶数量
    private int scrapGasTotal;//报废的气瓶数量
    private String gasLocation;//气瓶放置位置
    private String spacing;//气瓶离炤间距0=1m，1=1m-2m，3=2m以上
    private String isNearStove;//是否离炤具过近0=是 1=否
    private String isAirLeakageValue;//气瓶阀门是否漏气0=是 1=否
    private String isProctectionDeviceStove;//灶具是否带熄火保护装置
    private String isToolUseYearStove;//灶具是否在使用年限内0=是 1=否
    private String isSmokeRoad;//烟道是否完好0=是 1=否
    private String hotWaterToolModule;//燃气热水器类型
    private String isHotwaterToolUseYear;//燃气热水器是否在使用年限内期0=是 1=否
    private String isVoltageRegulatorAirLeakage;//调压器是否漏气0=是 1=否
    private String isConnectingPipeAping;//连接管是否老化0=是 1=否
    private String checkImageOne;//检查图片
    private String checkImageTwo;//检查图片
    private String checkImageThree;//检查图片
    private String otherRemark;//其他情况
    private String isPass;//检查结论 0=合格 1=不合格

    private String checkPersion;//检查人
    private String checkTime;//检查时间
    private String customerName;//客户名称
    private String customerID;//客户编号
    private String address;//客户地址
    private String phone;//客户电话
    private String customerCardNo;//用户卡编号
    private String station;//所属气站
    private String gasTotal;//检查时录入气瓶数量
    private List<CheckGasLabelAndType> checkGasLabelAndTypeList;//检查时录入的气瓶信息

    public String getCheckPersion() {
        return checkPersion;
    }

    public void setCheckPersion(String checkPersion) {
        this.checkPersion = checkPersion;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getIsAirVents() {
        return isAirVents;
    }

    public void setIsAirVents(String isAirVents) {
        this.isAirVents = isAirVents;
    }

    public String getIsSafetyWarning() {
        return isSafetyWarning;
    }

    public void setIsSafetyWarning(String isSafetyWarning) {
        this.isSafetyWarning = isSafetyWarning;
    }

    public String getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(String isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getIsTrueAlarm() {
        return isTrueAlarm;
    }

    public void setIsTrueAlarm(String isTrueAlarm) {
        this.isTrueAlarm = isTrueAlarm;
    }

    public String getIsFireEquipment() {
        return isFireEquipment;
    }

    public void setIsFireEquipment(String isFireEquipment) {
        this.isFireEquipment = isFireEquipment;
    }

    public int getOutTimeGasTotal() {
        return outTimeGasTotal;
    }

    public void setOutTimeGasTotal(int outTimeGasTotal) {
        this.outTimeGasTotal = outTimeGasTotal;
    }

    public int getScrapGasTotal() {
        return scrapGasTotal;
    }

    public void setScrapGasTotal(int scrapGasTotal) {
        this.scrapGasTotal = scrapGasTotal;
    }

    public String getGasLocation() {
        return gasLocation;
    }

    public void setGasLocation(String gasLocation) {
        this.gasLocation = gasLocation;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public String getIsNearStove() {
        return isNearStove;
    }

    public void setIsNearStove(String isNearStove) {
        this.isNearStove = isNearStove;
    }

    public String getIsAirLeakageValue() {
        return isAirLeakageValue;
    }

    public void setIsAirLeakageValue(String isAirLeakageValue) {
        this.isAirLeakageValue = isAirLeakageValue;
    }

    public String getIsProctectionDeviceStove() {
        return isProctectionDeviceStove;
    }

    public void setIsProctectionDeviceStove(String isProctectionDeviceStove) {
        this.isProctectionDeviceStove = isProctectionDeviceStove;
    }

    public String getIsToolUseYearStove() {
        return isToolUseYearStove;
    }

    public void setIsToolUseYearStove(String isToolUseYearStove) {
        this.isToolUseYearStove = isToolUseYearStove;
    }

    public String getIsSmokeRoad() {
        return isSmokeRoad;
    }

    public void setIsSmokeRoad(String isSmokeRoad) {
        this.isSmokeRoad = isSmokeRoad;
    }

    public String getHotWaterToolModule() {
        return hotWaterToolModule;
    }

    public void setHotWaterToolModule(String hotWaterToolModule) {
        this.hotWaterToolModule = hotWaterToolModule;
    }

    public String getIsHotwaterToolUseYear() {
        return isHotwaterToolUseYear;
    }

    public void setIsHotwaterToolUseYear(String isHotwaterToolUseYear) {
        this.isHotwaterToolUseYear = isHotwaterToolUseYear;
    }

    public String getIsVoltageRegulatorAirLeakage() {
        return isVoltageRegulatorAirLeakage;
    }

    public void setIsVoltageRegulatorAirLeakage(String isVoltageRegulatorAirLeakage) {
        this.isVoltageRegulatorAirLeakage = isVoltageRegulatorAirLeakage;
    }

    public String getIsConnectingPipeAping() {
        return isConnectingPipeAping;
    }

    public void setIsConnectingPipeAping(String isConnectingPipeAping) {
        this.isConnectingPipeAping = isConnectingPipeAping;
    }

    public String getCheckImageOne() {
        return checkImageOne;
    }

    public void setCheckImageOne(String checkImageOne) {
        this.checkImageOne = checkImageOne;
    }

    public String getCheckImageTwo() {
        return checkImageTwo;
    }

    public void setCheckImageTwo(String checkImageTwo) {
        this.checkImageTwo = checkImageTwo;
    }

    public String getCheckImageThree() {
        return checkImageThree;
    }

    public void setCheckImageThree(String checkImageThree) {
        this.checkImageThree = checkImageThree;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getGasTotal() {
        return gasTotal;
    }

    public void setGasTotal(String gasTotal) {
        this.gasTotal = gasTotal;
    }

    public List<CheckGasLabelAndType> getCheckGasLabelAndTypeList() {
        return checkGasLabelAndTypeList;
    }

    public void setCheckGasLabelAndTypeList(List<CheckGasLabelAndType> checkGasLabelAndTypeList) {
        this.checkGasLabelAndTypeList = checkGasLabelAndTypeList;
    }
}

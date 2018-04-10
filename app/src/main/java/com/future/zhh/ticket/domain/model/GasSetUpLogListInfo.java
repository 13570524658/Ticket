package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasSetUpLogListInfo {
    private String enterpriseID;//企业ID
    private String gasLabe;//气瓶标签
    private String gasSteelcode;//气瓶钢码
    private String productFactoryID;//出厂编号
    private String setUpTime;//建档时间
    private String id;//规格ID

    public String getEnterpriseID() {
        return enterpriseID;
    }

    public void setEnterpriseID(String enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getGasLabe() {
        return gasLabe;
    }

    public void setGasLabe(String gasLabe) {
        this.gasLabe = gasLabe;
    }

    public String getGasSteelcode() {
        return gasSteelcode;
    }

    public void setGasSteelcode(String gasSteelcode) {
        this.gasSteelcode = gasSteelcode;
    }

    public String getProductFactoryID() {
        return productFactoryID;
    }

    public void setProductFactoryID(String productFactoryID) {
        this.productFactoryID = productFactoryID;
    }

    public String getSetUpTime() {
        return setUpTime;
    }

    public void setSetUpTime(String setUpTime) {
        this.setUpTime = setUpTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

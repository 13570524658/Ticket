package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/11/30.
 */

public class GasmoduleList {
    private String moduleType;//气瓶规格
    private String id;//规格ID
    private String moduleCode;
    private String moduleName;
    private double fillingCapacity;
    private double minVolume;
    private double maxVolume;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public double getFillingCapacity() {
        return fillingCapacity;
    }

    public void setFillingCapacity(double fillingCapacity) {
        this.fillingCapacity = fillingCapacity;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(double minVolume) {
        this.minVolume = minVolume;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }
}

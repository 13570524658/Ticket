package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SincePayGasInfo {
    private String label;//交付气瓶标签
    private String gasType;//交付气瓶规格

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }
}

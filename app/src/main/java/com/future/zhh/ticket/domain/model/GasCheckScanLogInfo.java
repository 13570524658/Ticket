package com.future.zhh.ticket.domain.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class GasCheckScanLogInfo implements Serializable {
    private String label;//气瓶标签
    private String type;//气瓶规格

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

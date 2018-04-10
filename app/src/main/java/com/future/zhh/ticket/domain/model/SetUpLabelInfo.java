package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/12/29.
 */

public class SetUpLabelInfo {
    private String label;//气瓶标签
    private String useStatus;//使用状态0=正常 1=作废 2=已建档 3=未导入

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportTaskListInfo {
    private int total;//配送任务数量
    List<TransportTaskInfo> transportTaskInfoList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TransportTaskInfo> getTransportTaskInfoList() {
        return transportTaskInfoList;
    }

    public void setTransportTaskInfoList(List<TransportTaskInfo> transportTaskInfoList) {
        this.transportTaskInfoList = transportTaskInfoList;
    }
}

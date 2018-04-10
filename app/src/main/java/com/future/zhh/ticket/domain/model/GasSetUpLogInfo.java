package com.future.zhh.ticket.domain.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasSetUpLogInfo {
    private List<GasSetUpLogListInfo >  gasSetUpLogListInfoList;

    public List<GasSetUpLogListInfo> getGasSetUpLogListInfoList() {
        return gasSetUpLogListInfoList;
    }

    public void setGasSetUpLogListInfoList(List<GasSetUpLogListInfo> gasSetUpLogListInfoList) {
        this.gasSetUpLogListInfoList = gasSetUpLogListInfoList;
    }
}

package com.future.zhh.ticket.domain.model;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasMsgChangeInfo {
  private     int gasStatu;// 0=未建档，1=已建档，2=未导入，3已作废

    public int getGasStatu() {
        return gasStatu;
    }

    public void setGasStatu(int gasStatu) {
        this.gasStatu = gasStatu;
    }
}

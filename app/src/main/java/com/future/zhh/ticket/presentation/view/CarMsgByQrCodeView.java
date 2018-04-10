package com.future.zhh.ticket.presentation.view;


import com.future.zhh.ticket.domain.model.CarInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface CarMsgByQrCodeView extends LoadingView {
    void retCarMsgByQrCodeView(boolean isSuccess, CarInfo result, String msg);
}

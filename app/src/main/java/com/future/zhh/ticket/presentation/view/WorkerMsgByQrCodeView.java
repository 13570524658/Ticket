package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.WorkerInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface WorkerMsgByQrCodeView extends LoadingView {
    void retWorkerMsgByQrCodeView(boolean isSuccess, WorkerInfo result, String msg);
}

package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.GasInfo;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface GasMsgByQrCodeView extends LoadingView  {
    void retGasMsgByQrCodeView(boolean isSuccess, GasInfo result, String msg);
}

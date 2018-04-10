package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.UserInfo;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface LoginByQrCodeView extends LoadingView {
    void retLoginByQrCodeView(boolean isSuccess, UserInfo result, String msg);
}

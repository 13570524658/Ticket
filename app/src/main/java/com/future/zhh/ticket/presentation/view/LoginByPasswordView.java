package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.UserInfo;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface LoginByPasswordView extends LoadingView{
    void retLoginByPasswordView(boolean isSuccess, UserInfo result, String msg);
}
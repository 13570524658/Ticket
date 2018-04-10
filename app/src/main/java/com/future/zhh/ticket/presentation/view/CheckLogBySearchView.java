package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.domain.model.ListResult;

/**
 * Created by Administrator on 2017/12/12.
 */

public interface CheckLogBySearchView extends LoadingView {
    void retCheckLogBySearchView(boolean isSuccess, ListResult<CheckLogInfo> result, String msg);
}

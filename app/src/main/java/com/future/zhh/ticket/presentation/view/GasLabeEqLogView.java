package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.GasSetUpLogInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 */

public interface GasLabeEqLogView extends LoadingView {
    void retGasLabeEqLogView(boolean isSuccess, List<GasSetUpLogListInfo> result, String msg);
}

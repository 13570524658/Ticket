package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.GasmoduleInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public interface GasModuleView extends LoadingView {
    void retGasModuleView(boolean isSuccess, List<GasmoduleList> result, String msg);
}

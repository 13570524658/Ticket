package com.future.zhh.ticket.presentation.view;


import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.domain.model.ListResult;



import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface GasSetUpLogView extends LoadingView {
    void retGasSetUpLogView(boolean isSuccess, List<GasSetUpLogListInfo> listResult, String msg);
}

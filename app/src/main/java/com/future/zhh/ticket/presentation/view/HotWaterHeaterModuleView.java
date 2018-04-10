package com.future.zhh.ticket.presentation.view;

import com.future.zhh.ticket.domain.model.HotWaterHeaterModuleListInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/12/9.
 */

public interface HotWaterHeaterModuleView extends LoadingView {
    void retHotWaterHeaterModuleView(boolean isSuccess, List<HotWaterHeaterModuleListInfo> result, String msg);
}

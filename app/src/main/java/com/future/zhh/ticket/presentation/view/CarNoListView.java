package com.future.zhh.ticket.presentation.view;

;import com.future.zhh.ticket.domain.model.CarNoListInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface CarNoListView extends LoadingView {
    void retCarNoListView(boolean isSuccess, List<CarNoListInfo> result, String msg);
}

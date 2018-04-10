package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.CheckLogMsgActivity;
import com.future.zhh.ticket.presentation.view.fragments.CheckLogMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.CheckLogMsgFragmentTwo;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/13.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class,SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface CheckLogMsgActivityComponent  {
    void inject(CheckLogMsgActivity checkLogMsgActivity);
    void inject(CheckLogMsgFragmentOne checkLogMsgFragmentOne);
    void inject(CheckLogMsgFragmentTwo checkLogMsgFragmentTwo);
}

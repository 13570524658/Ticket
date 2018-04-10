package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.CheckMsgActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/9.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class, SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface CheckMsgActivityComponents  extends ActivityComponent{
    void inject(CheckMsgActivity checkMsgActivity);
}

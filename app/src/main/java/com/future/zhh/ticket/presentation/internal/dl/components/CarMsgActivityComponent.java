package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.CarMsgActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/11/23.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class, SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface CarMsgActivityComponent extends ActivityComponent {
    void inject(CarMsgActivity carMsgActivity);
}

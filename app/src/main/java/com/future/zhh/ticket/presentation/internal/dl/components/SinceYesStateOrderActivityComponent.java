package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.SinceNotStateOrderActivity;
import com.future.zhh.ticket.presentation.view.activities.SinceYesStateOrderActivity;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTwo;
import com.future.zhh.ticket.presentation.view.fragments.SinceYesStateOrderFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.SinceYesStateOrderFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.SinceYesStateOrderFragmentTwo;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/4.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class, SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface SinceYesStateOrderActivityComponent  extends ActivityComponent{
    void  inject(SinceYesStateOrderActivity sinceYesStateOrderActivity);
    void inject(SinceYesStateOrderFragmentOne sinceYesStateOrderFragmentOne);
    void inject(SinceYesStateOrderFragmentTwo sinceYesStateOrderFragmentTwo);
    void inject(SinceYesStateOrderFragmentTree sinceYesStateOrderFragmentTree);
}

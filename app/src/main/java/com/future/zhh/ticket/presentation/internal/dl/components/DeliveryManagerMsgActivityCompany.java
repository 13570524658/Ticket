package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerMsgActivity;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerFragment;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTwo;

import dagger.Component;

/**
 * Created by Administrator on 2017/11/25.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class,SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface DeliveryManagerMsgActivityCompany {
    void inject(DeliveryManagerMsgActivity deliveryManagerMsgActivity);
    void inject(DeliveryManagerMsgFragmentOne deliveryManagerMsgFragmentOne);
    void inject(DeliveryManagerMsgFragmentTwo deliveryManagerMsgFragmentTwo);
    void inject(DeliveryManagerMsgFragmentTree deliveryManagerMsgFragmentTree);
}

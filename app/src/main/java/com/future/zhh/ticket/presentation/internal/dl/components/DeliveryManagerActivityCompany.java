package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerActivity;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/11/24.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class,SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface DeliveryManagerActivityCompany {
    void inject(DeliveryManagerActivity deliveryManagerActivity);
    void inject(DeliveryManagerFragment deliveryManagerFragment);
}

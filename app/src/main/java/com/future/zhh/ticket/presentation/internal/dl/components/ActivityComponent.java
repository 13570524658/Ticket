package com.future.zhh.ticket.presentation.internal.dl.components;

import android.app.Activity;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;

import dagger.Component;

/**
 * Created by Administrator on 2017/11/19.
 */

@PerActivity
@Component(modules = ActivityModule.class,dependencies = ApplicationComponent.class)
public interface ActivityComponent {
    Activity activity();
}

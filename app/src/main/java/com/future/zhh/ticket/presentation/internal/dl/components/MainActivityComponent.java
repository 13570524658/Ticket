package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.MainActivity;
import com.future.zhh.ticket.presentation.view.fragments.HomeFragment;
import com.future.zhh.ticket.presentation.view.fragments.HomeFragmentBK;

import dagger.Component;

/**
 * Created by Administrator on 2017/11/21.
 */

@PerActivity
@Component(modules = {ActivityModule.class,CommonModule.class, SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface MainActivityComponent{
    void inject(MainActivity mainActivity);
    void inject(HomeFragmentBK homeFragmentBK);
    void inject(HomeFragment homeFragment);
}

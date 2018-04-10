package com.future.zhh.ticket.presentation.internal.dl.components;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.CommonModule;
import com.future.zhh.ticket.presentation.internal.dl.modules.SupervisionModule;
import com.future.zhh.ticket.presentation.view.activities.SinceLogMsgActivity;
import com.future.zhh.ticket.presentation.view.fragments.SinceLogMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.SinceLogMsgFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.SinceLogMsgFragmentTwo;

import dagger.Component;

/**
 * Created by Administrator on 2017/12/6.
 */
@PerActivity
@Component(modules = {ActivityModule.class, CommonModule.class,SupervisionModule.class},dependencies = ApplicationComponent.class)
public interface SinceLogMsgActivityComponent  extends ApplicationComponent {
    void inject(SinceLogMsgActivity sinceLogMsgActivity);
    void inject(SinceLogMsgFragmentOne sinceLogMsgFragmentOne);
    void inject(SinceLogMsgFragmentTwo sinceLogMsgFragmentTwo);
    void inject(SinceLogMsgFragmentTree sinceLogMsgFragmentTree);
}

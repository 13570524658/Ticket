package com.future.zhh.ticket.presentation.internal.dl.components;

import android.content.Context;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.data.repository.SupervisionRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.presentation.internal.dl.modules.ApplicationModule;
import com.future.zhh.ticket.presentation.view.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/11/19.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //暴露给子层
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    CommonRepository commonRepository();
    SupervisionRepository supervisionRepository();
}

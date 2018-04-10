package com.future.zhh.ticket.presentation.internal.dl.modules;

import android.content.Context;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.data.repository.SupervisionRepository;
import com.future.zhh.ticket.data.repository.impl.CommonRepositoryImpl;
import com.future.zhh.ticket.data.repository.impl.SupervisionRepositoryImpl;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.executor.impl.JobExecutor;
import com.future.zhh.ticket.domain.executor.impl.UIThread;
import com.future.zhh.ticket.presentation.AppApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/11/19.
 */

@Module
public class ApplicationModule {
    private final AppApplication application;

    public ApplicationModule(AppApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplication(){
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideExcutor(JobExecutor jobExecutor){
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread){
        return uiThread;
    }

    @Provides
    @Singleton
    CommonRepository provideCommonRepository(CommonRepositoryImpl commonRepository){
        return commonRepository;
    }

    @Provides
    @Singleton
    SupervisionRepository provideSupervisionRepository(SupervisionRepositoryImpl supervisionRepository){
        return supervisionRepository;}


}

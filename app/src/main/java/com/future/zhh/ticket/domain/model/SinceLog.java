package com.future.zhh.ticket.domain.model;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceLog extends Case {
    private CommonRepository repository;
    private String id;

    @Inject
    public SinceLog(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                       CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }
    public void setData(String id){
        this.id=id;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.sinceLog(id);
    }
}

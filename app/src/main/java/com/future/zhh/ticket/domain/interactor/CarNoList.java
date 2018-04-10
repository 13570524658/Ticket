package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CarNoList extends Case {
    private CommonRepository repository;
    private String id;
    @Inject
    public CarNoList(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                     CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    public void setData(String id){
        this.id = id;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.carNoList(id);
    }
}

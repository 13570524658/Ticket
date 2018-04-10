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

public class ElectCarNo extends Case{
    private CommonRepository repository;
    private String id;
    private String carNo;
    @Inject
    public ElectCarNo(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                         CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    public void setData(String id, String carNo){
        this.id = id;
        this.carNo = carNo;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.electCarNo(id,carNo);
    }
}

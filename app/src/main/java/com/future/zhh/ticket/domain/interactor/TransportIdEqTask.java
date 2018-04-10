package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/11/29.
 */

public class TransportIdEqTask extends Case{
    private CommonRepository repository;
    private String transportID;
    private String customerName;
    private int state;

    @Inject
    public TransportIdEqTask(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                                CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }
    public void setData(String transportID,String customerName,int state){
        this.transportID = transportID;
        this.customerName=customerName;
        this.state=state;

    }


    @Override
    protected Observable buildCaseObservable() {
        return repository.transportIdEqTask(transportID,customerName,state);
    }
}

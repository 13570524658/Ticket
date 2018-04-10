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

public class TransportOfPay extends Case {
    private CommonRepository repository;
    private String transportTaskID;
    private String id;
    private String gasData;
    private int count;
    @Inject
    public TransportOfPay(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                             CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }
    public void setData(String transportTaskID,String id,String gasData,int count){
        this.transportTaskID=transportTaskID;
        this.id = id;
        this.gasData=gasData;
        this.count=count;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.transportOfPay(transportTaskID,id,gasData,count);
    }
}

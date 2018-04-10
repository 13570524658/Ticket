package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceOfPay extends Case {
    private CommonRepository repository;
    private String gasMsg;
    private String orderID;
    private String id;

    @Inject
    public SinceOfPay(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                         CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }

    public void setData(String gasMsg,String orderID,String id){
        this.gasMsg = gasMsg;
        this.orderID=orderID;
        this.id=id;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.sinceOfPay(gasMsg,orderID,id);
    }
}

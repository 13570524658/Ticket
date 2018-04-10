package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasSetUpLog extends Case {
    private CommonRepository repository;

    private String enterpriseID;
    private int pageSize;
    private int pageIndex;

    @Inject
    public GasSetUpLog(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                          CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }

    public void setData( String enterpriseID,int pageSize,int pageIndex ){

        this.enterpriseID = enterpriseID;
        this.pageSize=pageSize;
        this.pageIndex=pageIndex;
    }


    @Override
    protected Observable buildCaseObservable() {
        return repository.gasSetUpLog(enterpriseID,pageSize,pageIndex);
    }
}

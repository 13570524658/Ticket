package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogBySearch extends Case {
    private CommonRepository repository;
    private String userName;
    private String userNameID;
    @Inject
    public CheckLogBySearch(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                               CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }
    public void setData(String userName,String userNameID){
        this.userName=userName;
        this.userNameID=userNameID;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.checkLogBySearch(userName,userNameID);
    }
}

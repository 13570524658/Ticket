package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/11/21.
 */

public class LoginByPassword extends Case {
    private CommonRepository repository;
    private String userID;
    private String password;
    @Inject
    public LoginByPassword(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                           CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    public void setData(String userID, String password){
        this.userID = userID;
        this.password = password;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.loginByPassword(userID,password);
    }
}

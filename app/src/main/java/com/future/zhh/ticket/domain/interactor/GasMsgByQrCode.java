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

public class GasMsgByQrCode extends Case {
    private CommonRepository repository;
    private String gasQrCode;
    @Inject
    public GasMsgByQrCode(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                             CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    public void setData(String gasQrCode){
        this.gasQrCode = gasQrCode;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.gasMsgByQrCode(gasQrCode);
    }
}

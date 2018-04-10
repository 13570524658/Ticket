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

public class TransportTaskList extends Case {
    private CommonRepository repository;
    private  String id;
    private String enterpriseID;
    private int pageIndex;
    private int  pageSize;
    private int state;
    private String stationID;
    private String shopID;
    private String depID;

    @Inject
    public TransportTaskList(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                                CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }
    public void setData(String id,String enterpriseID,int pageIndex,int pageSize,int state,
                        String stationID,String shopID,String depID){
        this.id = id;
        this.enterpriseID=enterpriseID;
        this.pageIndex=pageIndex;
        this.pageSize=pageSize;
        this.state=state;
        this.stationID=stationID;
        this.shopID=shopID;
        this.depID=depID;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.transportTaskList(id,enterpriseID,pageIndex,pageSize,state,stationID,shopID,depID);
    }
}

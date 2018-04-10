package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.TransportTaskList;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;
import com.future.zhh.ticket.presentation.view.TransportTaskListView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportTaskListViewPresenter implements Presenter {
    Case transportTaskList;
    TransportTaskListView transportTaskListView;
    Context mContext;

    @Inject
    public TransportTaskListViewPresenter(Context mContext, @Named("transportTaskList") Case transportTaskList){
        this.mContext = mContext;
        this.transportTaskList = transportTaskList;
    }

    public void setView(TransportTaskListView transportTaskListView){
        this.transportTaskListView = transportTaskListView;
    }

    public void  transportTaskList(String id,String enterpriseID,int pageIndex,
                                   int pageSize,int state,String stationID,String shopID,String depID){
        ((TransportTaskList)transportTaskList).setData(id,enterpriseID,pageIndex,pageSize,state,stationID,shopID,depID);
        transportTaskList.execute(new TransportTaskListSubscriber(mContext));
        transportTaskListView.showLoading();
    }

    private final class TransportTaskListSubscriber extends DefaultSubscriber<Result<ListResult<TransportTaskInfo>>> {

        protected TransportTaskListSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportTaskListView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<TransportTaskInfo>>result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportTaskListView.retTransportTaskListView(true,result.getData(),result.getMsg());
            }else {
                transportTaskListView.hideLoading();
                transportTaskListView.retTransportTaskListView(false,result.getData(),result.getMsg());
            }
        }
    }
    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}

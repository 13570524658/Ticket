package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.TransportTaskDetail;
import com.future.zhh.ticket.domain.interactor.TransportTaskList;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.TransportTaskDetailInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;
import com.future.zhh.ticket.presentation.view.TransportTaskDetailView;


import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportTaskDetailPresenter implements Presenter {
    Case     transportTaskDetail;
    TransportTaskDetailView transportTaskDetailView;
    Context mContext;

    @Inject
    public TransportTaskDetailPresenter(Context mContext, @Named("transportTaskDetail") Case transportTaskDetail){
        this.mContext = mContext;
        this.transportTaskDetail = transportTaskDetail;
    }

    public void setView(TransportTaskDetailView transportTaskDetailView){
        this.transportTaskDetailView = transportTaskDetailView;
    }

    public void  transportTaskDetail(String transportID,String transportTaskID){
        ((TransportTaskDetail)transportTaskDetail).setData(transportID,transportTaskID);
        transportTaskDetail.execute(new TransportTaskDetailSubscriber(mContext));
        transportTaskDetailView.showLoading();
    }

    private final class TransportTaskDetailSubscriber extends DefaultSubscriber<Result<TransportTaskDetailInfo>> {

        protected TransportTaskDetailSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportTaskDetailView.hideLoading();
        }

        @Override
        public void onNext(Result<TransportTaskDetailInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportTaskDetailView.retTransportTaskDetailView(true,result.getData(),result.getMsg());
            }else {
                transportTaskDetailView.hideLoading();
                transportTaskDetailView.retTransportTaskDetailView(false,result.getData(),result.getMsg());
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

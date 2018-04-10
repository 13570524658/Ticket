package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.TransportIdEqTask;
import com.future.zhh.ticket.domain.interactor.TransportOfPay;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;
import com.future.zhh.ticket.presentation.view.TransportIdEqTaskView;
import com.future.zhh.ticket.presentation.view.TransportOfPayView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/29.
 */

public class TransportIdEqTaskPresenter implements Presenter{
    Case transportIdEqTask;
    TransportIdEqTaskView transportIdEqTaskView;
    Context mContext;

    @Inject
    public TransportIdEqTaskPresenter(Context mContext, @Named("transportIdEqTask") Case transportIdEqTask){
        this.mContext = mContext;
        this.transportIdEqTask = transportIdEqTask;
    }

    public void setView(TransportIdEqTaskView transportIdEqTaskView){
        this.transportIdEqTaskView = transportIdEqTaskView;
    }

    public void  transportIdEqTask(String transportID,String customerName ,int state){
        ((TransportIdEqTask)transportIdEqTask).setData(transportID,customerName,state);
        transportIdEqTask.execute(new TransportIdEqTaskSubscriber(mContext));
        transportIdEqTaskView.showLoading();
    }

    private final class TransportIdEqTaskSubscriber extends DefaultSubscriber<Result<ListResult<TransportTaskInfo>>> {

        protected TransportIdEqTaskSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportIdEqTaskView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<TransportTaskInfo>> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportIdEqTaskView.retTransportIdEqTaskView(true,result.getData(),result.getMsg());
            }else {
                transportIdEqTaskView.hideLoading();
                transportIdEqTaskView.retTransportIdEqTaskView(false,result.getData(),result.getMsg());
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

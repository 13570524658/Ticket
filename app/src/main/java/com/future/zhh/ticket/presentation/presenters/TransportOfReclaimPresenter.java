package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.TransportOfReclaim;
import com.future.zhh.ticket.domain.interactor.TransportTaskDetail;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.TransportTaskDetailInfo;
import com.future.zhh.ticket.presentation.view.TransportOfReclaimView;
import com.future.zhh.ticket.presentation.view.TransportTaskDetailView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportOfReclaimPresenter implements Presenter {
    Case transportOfReclaim;
    TransportOfReclaimView transportOfReclaimView;
    Context mContext;

    @Inject
    public TransportOfReclaimPresenter(Context mContext, @Named("transportOfReclaim") Case transportOfReclaim){
        this.mContext = mContext;
        this.transportOfReclaim = transportOfReclaim;
    }

    public void setView(TransportOfReclaimView transportOfReclaimView){
        this.transportOfReclaimView = transportOfReclaimView;
    }

    public void  transportOfReclaim(String transportID,String id,String gasData ,int count){
        ((TransportOfReclaim)transportOfReclaim).setData(transportID,id,gasData,count);
        transportOfReclaim.execute(new TransportOfReclaimSubscriber(mContext));
        transportOfReclaimView.showLoading();
    }

    private final class TransportOfReclaimSubscriber extends DefaultSubscriber<Result> {

        protected TransportOfReclaimSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportOfReclaimView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportOfReclaimView.retTransportOfReclaimView(true,result,result.getMsg());
            }else {
                transportOfReclaimView.hideLoading();
                transportOfReclaimView.retTransportOfReclaimView(false,result,result.getMsg());
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

package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.TransportOfPay;
import com.future.zhh.ticket.domain.interactor.TransportOfReclaim;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.TransportOfPayView;
import com.future.zhh.ticket.presentation.view.TransportOfReclaimView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class TransportOfPayPresenter implements Presenter {
    Case transportOfPay;
    TransportOfPayView transportOfPayView;
    Context mContext;

    @Inject
    public TransportOfPayPresenter(Context mContext, @Named("transportOfPay") Case transportOfPay){
        this.mContext = mContext;
        this.transportOfPay = transportOfPay;
    }

    public void setView(TransportOfPayView transportOfPayView){
        this.transportOfPayView = transportOfPayView;
    }

    public void  transportOfPay(String transportTaskID,String id,String gasData ,int count){
        ((TransportOfPay)transportOfPay).setData(transportTaskID,id,gasData,count);
        transportOfPay.execute(new TransportOfPaySubscriber(mContext));
        transportOfPayView.showLoading();
    }

    private final class TransportOfPaySubscriber extends DefaultSubscriber<Result> {

        protected TransportOfPaySubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportOfPayView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportOfPayView.retTransportOfPayView(true,result,result.getMsg());
            }else {
                transportOfPayView.hideLoading();
                transportOfPayView.retTransportOfPayView(false,result,result.getMsg());
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

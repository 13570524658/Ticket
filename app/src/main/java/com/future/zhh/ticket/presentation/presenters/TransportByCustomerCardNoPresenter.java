package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.TransportByCustomerCardNo;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.TransportByCustomerCardNoView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/29.
 */

public class TransportByCustomerCardNoPresenter implements Presenter {
    Case transportByCustomerCardNo;
    TransportByCustomerCardNoView transportByCustomerCardNoView;
    Context mContext;

    @Inject
    public TransportByCustomerCardNoPresenter(Context mContext, @Named("transportByCustomerCardNo") Case transportByCustomerCardNo){
        this.mContext = mContext;
        this.transportByCustomerCardNo = transportByCustomerCardNo;
    }

    public void setView(TransportByCustomerCardNoView transportByCustomerCardNoView){
        this.transportByCustomerCardNoView = transportByCustomerCardNoView;
    }

    public void  transportByCustomerCardNo(String customerCardNo){
        ((TransportByCustomerCardNo)transportByCustomerCardNo).setData(customerCardNo);
        transportByCustomerCardNo.execute(new TransportByCustomerCardNoSubscriber(mContext));
        transportByCustomerCardNoView.showLoading();
    }

    private final class TransportByCustomerCardNoSubscriber extends DefaultSubscriber<Result<CustomerCardNoTaskInfo>> {

        protected TransportByCustomerCardNoSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportByCustomerCardNoView.hideLoading();
        }

        @Override
        public void onNext(Result<CustomerCardNoTaskInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportByCustomerCardNoView.retTransportByCustomerCardNoView(true,result.getData(),result.getMsg());
            }else {
                transportByCustomerCardNoView.hideLoading();
                transportByCustomerCardNoView.retTransportByCustomerCardNoView(false,result.getData(),result.getMsg());
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

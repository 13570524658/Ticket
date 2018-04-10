package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.SinceByCustomer;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SinceByCustomerInfo;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.SinceByCustomerView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceByCustomerPresenter implements Presenter {
    Case sinceByCustomer;
    SinceByCustomerView sinceByCustomerView;
    Context mContext;

    @Inject
    public SinceByCustomerPresenter(Context mContext, @Named("sinceByCustomer") Case sinceByCustomer){
        this.mContext = mContext;
        this.sinceByCustomer = sinceByCustomer;
    }

    public void setView(SinceByCustomerView sinceByCustomerView){
        this.sinceByCustomerView = sinceByCustomerView;
    }

    public void  sinceByCustomer(String carQrCode){
        ((SinceByCustomer)sinceByCustomer).setData(carQrCode);
        sinceByCustomer.execute(new SinceByCustomerSubscriber(mContext));
        sinceByCustomerView.showLoading();
    }

    private final class SinceByCustomerSubscriber extends DefaultSubscriber<Result<SinceByCustomerInfo>> {

        protected SinceByCustomerSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            sinceByCustomerView.hideLoading();
        }

        @Override
        public void onNext(Result<SinceByCustomerInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceByCustomerView.retSinceByCustomerView(true,result.getData(),result.getMsg());
            }else {
                sinceByCustomerView.hideLoading();
                sinceByCustomerView.retSinceByCustomerView(false,result.getData(),result.getMsg());
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

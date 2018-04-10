package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.SinceByCustomer;
import com.future.zhh.ticket.domain.interactor.SinceByOder;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SinceByCustomerInfo;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.presentation.view.SinceByCustomerView;
import com.future.zhh.ticket.presentation.view.SinceByOderView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceByOderPresenter implements Presenter {
    Case sinceByOder;
    SinceByOderView sinceByOderView;
    Context mContext;

    @Inject
    public SinceByOderPresenter(Context mContext, @Named("sinceByOder") Case sinceByOder){
        this.mContext = mContext;
        this.sinceByOder = sinceByOder;
    }

    public void setView(SinceByOderView sinceByOderView){
        this.sinceByOderView = sinceByOderView;
    }

    public void  sinceByOder(String carQrCode){
        ((SinceByOder)sinceByOder).setData(carQrCode);
        sinceByOder.execute(new SinceByOderSubscriber(mContext));
        sinceByOderView.showLoading();
    }

    private final class SinceByOderSubscriber extends DefaultSubscriber<Result<SinceByOrderInfo>> {

        protected SinceByOderSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            sinceByOderView.hideLoading();
        }

        @Override
        public void onNext(Result<SinceByOrderInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceByOderView.retSinceByOderView(true,result.getData(),result.getMsg());
            }else {
                sinceByOderView.hideLoading();
                sinceByOderView.retSinceByOderView(false,result.getData(),result.getMsg());
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

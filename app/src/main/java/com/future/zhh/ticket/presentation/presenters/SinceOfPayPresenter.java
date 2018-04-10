package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.SinceOfPay;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.SinceOfPayView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceOfPayPresenter implements Presenter {
    Case sinceOfPay;
    SinceOfPayView sinceOfPayView;
    Context mContext;

    @Inject
    public SinceOfPayPresenter(Context mContext, @Named("sinceOfPay") Case sinceOfPay){
        this.mContext = mContext;
        this.sinceOfPay = sinceOfPay;
    }

    public void setView(SinceOfPayView sinceOfPayView){
        this.sinceOfPayView = sinceOfPayView;
    }

    public void  sinceOfPay(String gasMsg,String odrerID,String id){
        ((SinceOfPay)sinceOfPay).setData(gasMsg,odrerID,id);
        sinceOfPay.execute(new SinceOfReclaimSubscriber(mContext));
        sinceOfPayView.showLoading();
    }

    private final class SinceOfReclaimSubscriber extends DefaultSubscriber<Result> {

        protected SinceOfReclaimSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            sinceOfPayView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceOfPayView.retSinceOfPayView(true,result,result.getMsg());
            }else {
                sinceOfPayView.hideLoading();
                sinceOfPayView.retSinceOfPayView(false,result,result.getMsg());
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

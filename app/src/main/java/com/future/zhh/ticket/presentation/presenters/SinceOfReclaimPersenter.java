package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.SinceOfReclaim;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.SinceOfReclaimView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceOfReclaimPersenter implements Presenter {
    Case  sinceOfReclaim;
    SinceOfReclaimView sinceOfReclaimView;
    Context mContext;

    @Inject
    public SinceOfReclaimPersenter(Context mContext, @Named("sinceOfReclaim") Case sinceOfReclaim){
        this.mContext = mContext;
        this.sinceOfReclaim = sinceOfReclaim;
    }

    public void setView(SinceOfReclaimView sinceOfReclaimView){
        this.sinceOfReclaimView = sinceOfReclaimView;
    }

    public void  sinceOfReclaim(String gasMsg,String odrerID,String id){
        ((SinceOfReclaim)sinceOfReclaim).setData(gasMsg,odrerID,id);
        sinceOfReclaim.execute(new SinceOfReclaimSubscriber(mContext));
        sinceOfReclaimView.showLoading();
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
            sinceOfReclaimView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceOfReclaimView.retSinceOfReclaimView(true,result,result.getMsg());
            }else {
                sinceOfReclaimView.hideLoading();
                sinceOfReclaimView.retSinceOfReclaimView(false,result,result.getMsg());
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

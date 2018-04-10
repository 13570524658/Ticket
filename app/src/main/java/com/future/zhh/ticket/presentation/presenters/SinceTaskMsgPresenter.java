package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.SinceTaskMsg;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.presentation.view.SinceTaskMsgView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceTaskMsgPresenter implements Presenter {
    Case  sinceTaskMsg;
    SinceTaskMsgView sinceTaskMsgView;
    Context mContext;

    @Inject
    public SinceTaskMsgPresenter(Context mContext, @Named("sinceTaskMsg") Case sinceTaskMsg){
        this.mContext = mContext;
        this.sinceTaskMsg = sinceTaskMsg;
    }

    public void setView(SinceTaskMsgView sinceTaskMsgView){
        this.sinceTaskMsgView = sinceTaskMsgView;
    }

    public void  sinceTaskMsg(String orderID){
        ((SinceTaskMsg)sinceTaskMsg).setData(orderID);
        sinceTaskMsg.execute(new TransportByCustomerCardNoSubscriber(mContext));
        sinceTaskMsgView.showLoading();
    }

    private final class TransportByCustomerCardNoSubscriber extends DefaultSubscriber<Result<SinceTaskMsgInfo>> {

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
            sinceTaskMsgView.hideLoading();
        }

        @Override
        public void onNext(Result<SinceTaskMsgInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceTaskMsgView.retSinceTaskMsgView(true,result.getData(),result.getMsg());
            }else {
                sinceTaskMsgView.hideLoading();
                sinceTaskMsgView.retSinceTaskMsgView(false,result.getData(),result.getMsg());
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

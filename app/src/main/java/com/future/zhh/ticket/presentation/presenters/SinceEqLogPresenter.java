package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.SinceEqLog;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SinceLogInfo;
import com.future.zhh.ticket.domain.model.SinceLogListInfo;
import com.future.zhh.ticket.presentation.view.SinceEqLogView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceEqLogPresenter implements Presenter {
    Case sinceEqLog;
    SinceEqLogView sinceEqLogView;
    Context mContext;

    @Inject
    public SinceEqLogPresenter(Context mContext, @Named("sinceEqLog") Case sinceEqLog){
        this.mContext = mContext;
        this.sinceEqLog = sinceEqLog;
    }

    public void setView(SinceEqLogView sinceEqLogView){
        this.sinceEqLogView = sinceEqLogView;
    }

    public void  sinceEqLog(String orderID,String customerName){
        ((SinceEqLog)sinceEqLog).setData(orderID,customerName);
        sinceEqLog.execute(new SinceEqLogSubscriber(mContext));
        sinceEqLogView.showLoading();
    }

    private final class SinceEqLogSubscriber extends DefaultSubscriber<Result<ListResult<SinceLogListInfo>>> {

        protected SinceEqLogSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            sinceEqLogView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<SinceLogListInfo>> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceEqLogView.retSinceEqLogView(true,result.getData(),result.getMsg());
            }else {
                sinceEqLogView.hideLoading();
                sinceEqLogView.retSinceEqLogView(false,result.getData(),result.getMsg());
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

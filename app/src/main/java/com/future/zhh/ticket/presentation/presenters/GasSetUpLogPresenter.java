package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.GasSetUpLog;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.GasSetUpLogView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasSetUpLogPresenter implements Presenter{
    Case gasSetUpLog;
    GasSetUpLogView gasSetUpLogView;
    Context mContext;

    @Inject
    public GasSetUpLogPresenter(Context mContext, @Named("gasSetUpLog") Case gasSetUpLog){
        this.mContext = mContext;
        this.gasSetUpLog = gasSetUpLog;
    }

    public void setView(GasSetUpLogView gasSetUpLogView){
        this.gasSetUpLogView = gasSetUpLogView;
    }

    public void  gasSetUpLog(String enterpriseID,int pageSize,int pageIndex){
        ((GasSetUpLog)gasSetUpLog).setData(enterpriseID,pageSize,pageIndex);
        gasSetUpLog.execute(new GasSetUpLogInfoSubscriber(mContext));
        gasSetUpLogView.showLoading();
    }

    private final class GasSetUpLogInfoSubscriber extends DefaultSubscriber<Result<ListResult<GasSetUpLogListInfo>>> {

        protected GasSetUpLogInfoSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasSetUpLogView.hideLoading();
        }

        @Override
        public void onNext(Result <ListResult<GasSetUpLogListInfo>> result) {
            super.onNext(result);
            if (result!=null){
                gasSetUpLogView.retGasSetUpLogView(true,result.getData().getRows(),result.getMsg());

            }else {
                gasSetUpLogView.hideLoading();
                gasSetUpLogView.retGasSetUpLogView(false,null,result.getMsg());

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

package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.GasLabeEqLog;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.GasSetUpLogInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.GasLabeEqLogView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/2.
 */

public class GasLabeEqLogPresenter implements Presenter {
    Case gasLabeEqLog;
    GasLabeEqLogView gasLabeEqLogView;
    Context mContext;

    @Inject
    public GasLabeEqLogPresenter(Context mContext, @Named("gasLabeEqLog") Case gasLabeEqLog){
        this.mContext = mContext;
        this.gasLabeEqLog = gasLabeEqLog;
    }

    public void setView(GasLabeEqLogView gasLabeEqLogView){
        this.gasLabeEqLogView = gasLabeEqLogView;
    }

    public void  gasLabeEqLog(String gasLabe ,String steelCode){
        ((GasLabeEqLog)gasLabeEqLog).setData(gasLabe,steelCode);
        gasLabeEqLog.execute(new ElectCarNoSubscriber(mContext));
        gasLabeEqLogView.showLoading();
    }

    private final class ElectCarNoSubscriber extends DefaultSubscriber<Result<ListResult<GasSetUpLogListInfo>>> {

        protected ElectCarNoSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasLabeEqLogView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<GasSetUpLogListInfo>> result) {
            super.onNext(result);
            if (result!=null){
                gasLabeEqLogView.retGasLabeEqLogView(true,result.getData().getRows(),result.getMsg());
            }else {
                gasLabeEqLogView.hideLoading();
                gasLabeEqLogView.retGasLabeEqLogView(false,result.getData().getRows(),result.getMsg());
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

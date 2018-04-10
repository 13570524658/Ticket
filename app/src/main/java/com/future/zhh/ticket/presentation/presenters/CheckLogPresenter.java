package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CheckLog;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CheckLogView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogPresenter implements Presenter {
    Case checkLog;
    CheckLogView checkLogView;
    Context mContext;

    @Inject
    public CheckLogPresenter(Context mContext, @Named("checkLog") Case checkLog){
        this.mContext = mContext;
        this.checkLog = checkLog;
    }

    public void setView(CheckLogView checkLogView){
        this.checkLogView = checkLogView;
    }

    public void  checkLog(String enterpriseID){
        ((CheckLog)checkLog).setData(enterpriseID);
        checkLog.execute(new CheckLogSubscriber(mContext));
        checkLogView.showLoading();
    }

    private final class CheckLogSubscriber extends DefaultSubscriber<Result<ListResult<CheckLogInfo>>> {

        protected CheckLogSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            checkLogView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<CheckLogInfo>> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                checkLogView.retCheckLogView(true,result.getData(),result.getMsg());
            }else {
                checkLogView.hideLoading();
                checkLogView.retCheckLogView(false,result.getData(),result.getMsg());
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

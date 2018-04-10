package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SinceLog;
import com.future.zhh.ticket.domain.model.SinceLogInfo;
import com.future.zhh.ticket.domain.model.SinceLogListInfo;
import com.future.zhh.ticket.presentation.view.SinceLogView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceLogPersenter implements Presenter {
    Case sinceLog;
    SinceLogView sinceLogView;
    Context mContext;

    @Inject
    public SinceLogPersenter(Context mContext, @Named("sinceLog") Case sinceLog){
        this.mContext = mContext;
        this.sinceLog = sinceLog;
    }

    public void setView(SinceLogView sinceLogView){
        this.sinceLogView = sinceLogView;
    }

    public void  sinceLog(String id){
        ((SinceLog)sinceLog).setData(id);
        sinceLog.execute(new SinceLogSubscriber(mContext));
        sinceLogView.showLoading();
    }

    private final class SinceLogSubscriber extends DefaultSubscriber<Result<ListResult<SinceLogListInfo>>> {

        protected SinceLogSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            sinceLogView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<SinceLogListInfo>> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                sinceLogView.retSinceLogView(true,result.getData(),result.getMsg());
            }else {
                sinceLogView.hideLoading();
                sinceLogView.retSinceLogView(false,result.getData(),result.getMsg());
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

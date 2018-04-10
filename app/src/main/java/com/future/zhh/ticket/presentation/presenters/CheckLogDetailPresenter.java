package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CheckLogDetail;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CheckLogDetailInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CheckLogDetailView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogDetailPresenter implements Presenter {
    Case checkLogDetail;
    CheckLogDetailView checkLogDetailView;
    Context mContext;

    @Inject
    public CheckLogDetailPresenter(Context mContext, @Named("checkLogDetail") Case checkLogDetail){
        this.mContext = mContext;
        this.checkLogDetail = checkLogDetail;
    }

    public void setView(CheckLogDetailView checkLogDetailView){
        this.checkLogDetailView = checkLogDetailView;
    }

    public void  checkLogDetail(String customerName,String userNameID){
        ((CheckLogDetail)checkLogDetail).setData(customerName,userNameID);
        checkLogDetail.execute(new CheckLogDetailSubscriber(mContext));
        checkLogDetailView.showLoading();
    }

    private final class CheckLogDetailSubscriber extends DefaultSubscriber<Result<CheckLogDetailInfo>> {

        protected CheckLogDetailSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            checkLogDetailView.hideLoading();
        }

        @Override
        public void onNext(Result<CheckLogDetailInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                checkLogDetailView.retCheckLogDetailView(true,result.getData(),result.getMsg());
            }else {
                checkLogDetailView.hideLoading();
                checkLogDetailView.retCheckLogDetailView(false,result.getData(),result.getMsg());
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

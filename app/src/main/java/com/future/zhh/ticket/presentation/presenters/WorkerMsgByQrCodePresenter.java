package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.WorkerMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.WorkerInfo;
import com.future.zhh.ticket.presentation.view.WorkerMsgByQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class WorkerMsgByQrCodePresenter implements Presenter {
    Case workerMsgByQrCode;
    WorkerMsgByQrCodeView workerMsgByQrCodeView;
    Context mContext;

    @Inject
    public WorkerMsgByQrCodePresenter(Context mContext, @Named("workerMsgByQrCode") Case workerMsgByQrCode){
        this.mContext = mContext;
        this.workerMsgByQrCode = workerMsgByQrCode;
    }

    public void setView(WorkerMsgByQrCodeView workerMsgByQrCodeView){
        this.workerMsgByQrCodeView = workerMsgByQrCodeView;
    }

    public void  workerMsgByQrCode(String workerQrCode){
        ((WorkerMsgByQrCode)workerMsgByQrCode).setData(workerQrCode);
        workerMsgByQrCode.execute(new WorkerMsgByQrCodeSubscriber(mContext));
        workerMsgByQrCodeView.showLoading();
    }

    private final class WorkerMsgByQrCodeSubscriber extends DefaultSubscriber<Result<WorkerInfo>> {

        protected WorkerMsgByQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            workerMsgByQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<WorkerInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                workerMsgByQrCodeView.retWorkerMsgByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                workerMsgByQrCodeView.hideLoading();
                workerMsgByQrCodeView.retWorkerMsgByQrCodeView(false,result.getData(),result.getMsg());
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

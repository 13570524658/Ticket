package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.OrderMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.SetUpScan;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SetUpLabelInfo;
import com.future.zhh.ticket.presentation.view.OrderMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.SetUpScanView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/29.
 */

public class SetUpScanPersenter implements Presenter {
    Case setUpScan;
    SetUpScanView setUpScanView;
    Context mContext;

    @Inject
    public SetUpScanPersenter(Context mContext, @Named("setUpScan") Case setUpScan){
        this.mContext = mContext;
        this.setUpScan = setUpScan;
    }

    public void setView(SetUpScanView setUpScanView){
        this.setUpScanView = setUpScanView;
    }

    public void  orderMsgByQrCode(String gasLabe){
        ((SetUpScan)setUpScan).setData(gasLabe);
        setUpScan.execute(new SetUpScanSubscriber(mContext));
        setUpScanView.showLoading();
    }

    private final class SetUpScanSubscriber extends DefaultSubscriber<Result<SetUpLabelInfo>> {

        protected SetUpScanSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            setUpScanView.hideLoading();
        }

        @Override
        public void onNext(Result<SetUpLabelInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                setUpScanView.retSetUpScanView(true,result.getData(),result.getMsg());
            }else {
                setUpScanView.hideLoading();
                setUpScanView.retSetUpScanView(false,result.getData(),result.getMsg());
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

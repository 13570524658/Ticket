package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.TransportByOrderQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.OrderTaskInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.TransportByOrderQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/29.
 */

public class TransportByOrderQrCodePresenter implements Presenter {
    Case transportByOrderQrCode;
    TransportByOrderQrCodeView transportByOrderQrCodeView;
    Context mContext;

    @Inject
    public TransportByOrderQrCodePresenter(Context mContext, @Named("transportByOrderQrCode") Case transportByOrderQrCode){
        this.mContext = mContext;
        this.transportByOrderQrCode = transportByOrderQrCode;
    }

    public void setView(TransportByOrderQrCodeView transportByOrderQrCodeView){
        this.transportByOrderQrCodeView = transportByOrderQrCodeView;
    }

    public void  transportByOrderQrCode(String orderQRcode){
        ((TransportByOrderQrCode)transportByOrderQrCode).setData(orderQRcode);
        transportByOrderQrCode.execute(new TransportByOrderQrCodeSubscriber(mContext));
        transportByOrderQrCodeView.showLoading();
    }

    private final class TransportByOrderQrCodeSubscriber extends DefaultSubscriber<Result<OrderTaskInfo>> {

        protected TransportByOrderQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            transportByOrderQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<OrderTaskInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                transportByOrderQrCodeView.retOrderMsgByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                transportByOrderQrCodeView.hideLoading();
                transportByOrderQrCodeView.retOrderMsgByQrCodeView(false,result.getData(),result.getMsg());
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

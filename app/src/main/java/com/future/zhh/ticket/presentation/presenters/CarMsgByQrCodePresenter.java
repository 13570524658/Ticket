package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.WorkerMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.WorkerInfo;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.WorkerMsgByQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CarMsgByQrCodePresenter implements Presenter{
    Case carMsgByQrCode;
    CarMsgByQrCodeView carMsgByQrCodeView;
    Context mContext;

    @Inject
    public CarMsgByQrCodePresenter(Context mContext, @Named("carMsgByQrCode") Case carMsgByQrCode){
        this.mContext = mContext;
        this.carMsgByQrCode = carMsgByQrCode;
    }

    public void setView(CarMsgByQrCodeView carMsgByQrCodeView){
        this.carMsgByQrCodeView = carMsgByQrCodeView;
    }

    public void  carMsgByQrCode(String carQrCode){
        ((CarMsgByQrCode)carMsgByQrCode).setData(carQrCode);
        carMsgByQrCode.execute(new CarMsgByQrCodeSubscriber(mContext));
        carMsgByQrCodeView.showLoading();
    }

    private final class CarMsgByQrCodeSubscriber extends DefaultSubscriber<Result<CarInfo>> {

        protected CarMsgByQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            carMsgByQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<CarInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                carMsgByQrCodeView.retCarMsgByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                carMsgByQrCodeView.hideLoading();
                carMsgByQrCodeView.retCarMsgByQrCodeView(false,result.getData(),result.getMsg());
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

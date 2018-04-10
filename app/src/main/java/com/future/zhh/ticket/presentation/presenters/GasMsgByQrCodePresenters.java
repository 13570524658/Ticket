package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.GasMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class GasMsgByQrCodePresenters implements Presenter {
    Case gasMsgByQrCode;
    GasMsgByQrCodeView gasMsgByQrCodeView;
    Context mContext;

    @Inject
    public GasMsgByQrCodePresenters(Context mContext, @Named("gasMsgByQrCode") Case gasMsgByQrCode){
        this.mContext = mContext;
        this.gasMsgByQrCode = gasMsgByQrCode;
    }

    public void setView(GasMsgByQrCodeView gasMsgByQrCodeView){
        this.gasMsgByQrCodeView = gasMsgByQrCodeView;
    }

    public void  gasMsgByQrCode(String gasQrCode ){
        ((GasMsgByQrCode)gasMsgByQrCode).setData(gasQrCode);
        gasMsgByQrCode.execute(new GasMsgByQrCodeSubscriber(mContext));
        gasMsgByQrCodeView.showLoading();
    }

    private final class GasMsgByQrCodeSubscriber extends DefaultSubscriber<Result<GasInfo>> {

        protected GasMsgByQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasMsgByQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<GasInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                gasMsgByQrCodeView.retGasMsgByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                gasMsgByQrCodeView.hideLoading();
                gasMsgByQrCodeView.retGasMsgByQrCodeView(false,result.getData(),result.getMsg());
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

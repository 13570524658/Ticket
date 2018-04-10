package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.GasLabeReplace;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.GasLabeReplaceInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.GasLabeReplaceView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/2.
 */

public class GasLabeReplacePersent implements Presenter {
    Case gasLabeReplace;
    GasLabeReplaceView gasLabeReplaceView;
    Context mContext;

    @Inject
    public GasLabeReplacePersent(Context mContext, @Named("gasLabeReplace") Case gasLabeReplace){
        this.mContext = mContext;
        this.gasLabeReplace = gasLabeReplace;
    }

    public void setView(GasLabeReplaceView gasLabeReplaceView){
        this.gasLabeReplaceView = gasLabeReplaceView;
    }

    public void  gasLabeReplace(String gasLabe){
        ((GasLabeReplace)gasLabeReplace).setData(gasLabe);
        gasLabeReplace.execute(new GasLabeReplaceSubscriber(mContext));
        gasLabeReplaceView.showLoading();
    }

    private final class GasLabeReplaceSubscriber extends DefaultSubscriber<Result<GasLabeReplaceInfo>> {

        protected GasLabeReplaceSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasLabeReplaceView.hideLoading();
        }

        @Override
        public void onNext(Result<GasLabeReplaceInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                gasLabeReplaceView.retGasLabeReplaceView(true,result.getData(),result.getMsg());
            }else {
                gasLabeReplaceView.hideLoading();
                gasLabeReplaceView.retGasLabeReplaceView(false,result.getData(),result.getMsg());
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

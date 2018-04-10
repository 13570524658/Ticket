package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.GasMsgChange;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.GasMsgChangeInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.GasMsgChangeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/1.
 */

public class GasMsgChangePresent implements Presenter {
    Case gasMsgChange;
    GasMsgChangeView gasMsgChangeView;
    Context mContext;

    @Inject
    public GasMsgChangePresent(Context mContext, @Named("gasMsgChange") Case gasMsgChange){
        this.mContext = mContext;
        this.gasMsgChange = gasMsgChange;
    }

    public void setView(GasMsgChangeView gasMsgChangeView){
        this.gasMsgChangeView = gasMsgChangeView;
    }

    public void  gasMsgChange(String id,String gasLabe,String productFactoryCompany,String productFactoryID,String gasweight,
                              String gasModule,String gasSteelcode,String productData,String  lastTestCompany,
                              String lastTestData,String nextTestData,String image1,String image2,String image3){
        ((GasMsgChange)gasMsgChange).setData(id,gasLabe,productFactoryCompany,productFactoryID,gasweight,
                gasModule, gasSteelcode,productData, lastTestCompany, lastTestData,nextTestData,image1,image2,image3);
        gasMsgChange.execute(new GasMsgChangeSubscriber(mContext));
        gasMsgChangeView.showLoading();
    }

    private final class GasMsgChangeSubscriber extends DefaultSubscriber<Result> {

        protected GasMsgChangeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasMsgChangeView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                gasMsgChangeView.retGasMsgChangeView(true,result,result.getMsg());
            }else {
                gasMsgChangeView.hideLoading();
                gasMsgChangeView.retGasMsgChangeView(false,result,result.getMsg());
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

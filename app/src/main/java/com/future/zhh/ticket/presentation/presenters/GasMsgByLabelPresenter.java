package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CustomerMsgByCustomerID;
import com.future.zhh.ticket.domain.interactor.GasMsgByLabel;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CustomerMsgByCustomerIDView;
import com.future.zhh.ticket.presentation.view.GasMsgByLabelView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class GasMsgByLabelPresenter implements Presenter {
    Case gasMsgByLabel;
    GasMsgByLabelView gasMsgByLabelView;
    Context mContext;

    @Inject
    public GasMsgByLabelPresenter(Context mContext, @Named("gasMsgByLabel") Case gasMsgByLabel){
        this.mContext = mContext;
        this.gasMsgByLabel = gasMsgByLabel;
    }

    public void setView(GasMsgByLabelView gasMsgByLabelView){
        this.gasMsgByLabelView = gasMsgByLabelView;
    }

    public void  customerMsgByCustomerIDView(String customerID){
        ((GasMsgByLabel)gasMsgByLabel).setData(customerID);
        gasMsgByLabel.execute(new GasMsgByLabelSubscriber(mContext));
        gasMsgByLabelView.showLoading();
    }

    private final class GasMsgByLabelSubscriber extends DefaultSubscriber<Result<GasInfo>> {

        protected GasMsgByLabelSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasMsgByLabelView.hideLoading();
        }

        @Override
        public void onNext(Result<GasInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                gasMsgByLabelView.retGasMsgByLabelView(true,result.getData(),result.getMsg());
            }else {
                gasMsgByLabelView.hideLoading();
                gasMsgByLabelView.retGasMsgByLabelView(false,result.getData(),result.getMsg());
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

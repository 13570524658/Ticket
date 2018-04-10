package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CustomerMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.GasMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CustomerMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CustomerMsgByQrCodePresenter implements Presenter {
    Case  customerMsgByQrCode;
    CustomerMsgByQrCodeView customerMsgByQrCodeView;
    Context mContext;

    @Inject
    public CustomerMsgByQrCodePresenter(Context mContext, @Named("customerMsgByQrCode") Case customerMsgByQrCode){
        this.mContext = mContext;
        this.customerMsgByQrCode = customerMsgByQrCode;
    }

    public void setView(CustomerMsgByQrCodeView customerMsgByQrCodeView){
        this.customerMsgByQrCodeView = customerMsgByQrCodeView;
    }

    public void  customerMsgByQrCode(String customerQrCode ){
        ((CustomerMsgByQrCode)customerMsgByQrCode).setData(customerQrCode);
        customerMsgByQrCode.execute(new CustomerMsgByQrCodeSubscriber(mContext));
        customerMsgByQrCodeView.showLoading();
    }

    private final class CustomerMsgByQrCodeSubscriber extends DefaultSubscriber<Result<CustomerInfo>> {

        protected CustomerMsgByQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            customerMsgByQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<CustomerInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                customerMsgByQrCodeView.retCustomerMsgByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                customerMsgByQrCodeView.hideLoading();
                customerMsgByQrCodeView.retCustomerMsgByQrCodeView(false,result.getData(),result.getMsg());
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

package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CustomerMsgByCustomerID;
import com.future.zhh.ticket.domain.interactor.OrderMsgByOrderID;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CustomerMsgByCustomerIDView;
import com.future.zhh.ticket.presentation.view.OrderMsgByOrderIDView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CustomerMsgByCustomerIDPresenter implements Presenter {
    Case     customerMsgByCustomerID;
    CustomerMsgByCustomerIDView customerMsgByCustomerIDView;
    Context mContext;

    @Inject
    public CustomerMsgByCustomerIDPresenter(Context mContext, @Named("customerMsgByCustomerID") Case customerMsgByCustomerID){
        this.mContext = mContext;
        this.customerMsgByCustomerID = customerMsgByCustomerID;
    }

    public void setView(CustomerMsgByCustomerIDView customerMsgByCustomerIDView){
        this.customerMsgByCustomerIDView = customerMsgByCustomerIDView;
    }

    public void  customerMsgByCustomerIDView(String customerID){
        ((CustomerMsgByCustomerID)customerMsgByCustomerID).setData(customerID);
        customerMsgByCustomerID.execute(new CustomerMsgByCustomerIDSubscriber(mContext));
        customerMsgByCustomerIDView.showLoading();
    }

    private final class CustomerMsgByCustomerIDSubscriber extends DefaultSubscriber<Result<CustomerInfo>> {

        protected CustomerMsgByCustomerIDSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            customerMsgByCustomerIDView.hideLoading();
        }

        @Override
        public void onNext(Result<CustomerInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                customerMsgByCustomerIDView.retCustomerMsgByCustomerIDView(true,result.getData(),result.getMsg());
            }else {
                customerMsgByCustomerIDView.hideLoading();
                customerMsgByCustomerIDView.retCustomerMsgByCustomerIDView(false,result.getData(),result.getMsg());
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

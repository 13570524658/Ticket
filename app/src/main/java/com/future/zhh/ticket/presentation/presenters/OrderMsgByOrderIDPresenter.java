package com.future.zhh.ticket.presentation.presenters;

/**
 * Created by Administrator on 2017/11/28.
 */

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.OrderMsgByOrderID;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.OrderMsgByOrderIDView;

import javax.inject.Inject;
import javax.inject.Named;

public class OrderMsgByOrderIDPresenter implements Presenter {
    Case orderMsgByOrderID;
    OrderMsgByOrderIDView orderMsgByOrderIDView;
    Context mContext;

    @Inject
    public OrderMsgByOrderIDPresenter(Context mContext, @Named("orderMsgByOrderID") Case orderMsgByOrderID){
        this.mContext = mContext;
        this.orderMsgByOrderID = orderMsgByOrderID;
    }

    public void setView(OrderMsgByOrderIDView orderMsgByOrderIDView){
        this.orderMsgByOrderIDView = orderMsgByOrderIDView;
    }

    public void  orderMsgByOrderID(String orderID){
        ((OrderMsgByOrderID)orderMsgByOrderID).setData(orderID);
        orderMsgByOrderID.execute(new OrderMsgByOrderIDSubscriber(mContext));
        orderMsgByOrderIDView.showLoading();
    }

    private final class OrderMsgByOrderIDSubscriber extends DefaultSubscriber<Result<OrderInfo>> {

        protected OrderMsgByOrderIDSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            orderMsgByOrderIDView.hideLoading();
        }

        @Override
        public void onNext(Result<OrderInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                orderMsgByOrderIDView.retOrderMsgByOrderIDView(true,result.getData(),result.getMsg());
            }else {
                orderMsgByOrderIDView.hideLoading();
                orderMsgByOrderIDView.retOrderMsgByOrderIDView(false,result.getData(),result.getMsg());
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

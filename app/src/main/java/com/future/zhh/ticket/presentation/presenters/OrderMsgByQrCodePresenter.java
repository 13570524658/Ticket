package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.OrderMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.OrderMsgByQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class OrderMsgByQrCodePresenter implements Presenter {
    Case orderMsgByQrCode;
    OrderMsgByQrCodeView orderMsgByQrCodeView;
    Context mContext;

    @Inject
    public OrderMsgByQrCodePresenter(Context mContext, @Named("orderMsgByQrCode") Case orderMsgByQrCode){
        this.mContext = mContext;
        this.orderMsgByQrCode = orderMsgByQrCode;
    }

    public void setView(OrderMsgByQrCodeView orderMsgByQrCodeView){
        this.orderMsgByQrCodeView = orderMsgByQrCodeView;
    }

    public void  orderMsgByQrCode(String orderQrCode){
        ((OrderMsgByQrCode)orderMsgByQrCode).setData(orderQrCode);
        orderMsgByQrCode.execute(new OrderMsgByQrCodeSubscriber(mContext));
        orderMsgByQrCodeView.showLoading();
    }

    private final class OrderMsgByQrCodeSubscriber extends DefaultSubscriber<Result<OrderInfo>> {

        protected OrderMsgByQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            orderMsgByQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<OrderInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                orderMsgByQrCodeView.retOrderMsgByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                orderMsgByQrCodeView.hideLoading();
                orderMsgByQrCodeView.retOrderMsgByQrCodeView(false,result.getData(),result.getMsg());
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

package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CheckByOrder;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CheckByOrderInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CheckByOrderView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/8.
 */

public class CheckByOrderPresenter implements Presenter {
    Case  checkByOrder;
    CheckByOrderView checkByOrderView;
    Context mContext;

    @Inject
    public CheckByOrderPresenter(Context mContext, @Named("checkByOrder") Case checkByOrder){
        this.mContext = mContext;
        this.checkByOrder = checkByOrder;
    }

    public void setView(CheckByOrderView checkByOrderView){
        this.checkByOrderView = checkByOrderView;
    }

    public void  checkByOrder(String orderID){
        ((CheckByOrder)checkByOrder).setData(orderID);
        checkByOrder.execute(new CarNoListSubscriber(mContext));
        checkByOrderView.showLoading();
    }

    private final class CarNoListSubscriber extends DefaultSubscriber<Result<CheckByOrderInfo>> {

        protected CarNoListSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            checkByOrderView.hideLoading();
        }

        @Override
        public void onNext(Result<CheckByOrderInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                checkByOrderView.retCheckByOrderView(true,result.getData(),result.getMsg());
            }else {
                checkByOrderView.hideLoading();
                checkByOrderView.retCheckByOrderView(false,result.getData(),result.getMsg());
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

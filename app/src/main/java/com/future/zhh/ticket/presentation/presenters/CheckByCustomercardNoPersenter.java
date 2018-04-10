package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CheckByCustomercardNo;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CheckByCustomercardNoInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CheckByCustomercardNoView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/7.
 */

public class CheckByCustomercardNoPersenter implements Presenter {
    Case checkByCustomercardNo;
    CheckByCustomercardNoView checkByCustomercardNoView;
    Context mContext;

    @Inject
    public CheckByCustomercardNoPersenter(Context mContext, @Named("checkByCustomercardNo") Case checkByCustomercardNo){
        this.mContext = mContext;
        this.checkByCustomercardNo = checkByCustomercardNo;
    }

    public void setView(CheckByCustomercardNoView checkByCustomercardNoView){
        this.checkByCustomercardNoView = checkByCustomercardNoView;
    }

    public void  checkByCustomercardNo(String customerCardNo){
        ((CheckByCustomercardNo)checkByCustomercardNo).setData(customerCardNo);
        checkByCustomercardNo.execute(new CarNoListSubscriber(mContext));
        checkByCustomercardNoView.showLoading();
    }

    private final class CarNoListSubscriber extends DefaultSubscriber<Result<CheckByCustomercardNoInfo>> {

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
            checkByCustomercardNoView.hideLoading();
        }

        @Override
        public void onNext(Result<CheckByCustomercardNoInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                checkByCustomercardNoView.retCheckByCustomercardNoView(true,result.getData(),result.getMsg());
            }else {
                checkByCustomercardNoView.hideLoading();
                checkByCustomercardNoView.retCheckByCustomercardNoView(false,result.getData(),result.getMsg());
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

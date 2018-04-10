package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.ElectCarNo;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.ElectCarNoView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class ElectCarNoPresenters implements Presenter {
    Case electCarNo;
    ElectCarNoView electCarNoView;
    Context mContext;

    @Inject
    public ElectCarNoPresenters(Context mContext, @Named("electCarNo") Case electCarNo){
        this.mContext = mContext;
        this.electCarNo = electCarNo;
    }

    public void setView(ElectCarNoView electCarNoView){
        this.electCarNoView = electCarNoView;
    }

    public void  electCarNo(String id ,String carNo){
        ((ElectCarNo)electCarNo).setData(id,carNo);
        electCarNo.execute(new ElectCarNoSubscriber(mContext));
        electCarNoView.showLoading();
    }

    private final class ElectCarNoSubscriber extends DefaultSubscriber<Result> {

        protected ElectCarNoSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            electCarNoView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                electCarNoView.retElectCarNoView(true,result,result.getMsg());
            }else {
                electCarNoView.hideLoading();
                electCarNoView.retElectCarNoView(false,result,result.getMsg());
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

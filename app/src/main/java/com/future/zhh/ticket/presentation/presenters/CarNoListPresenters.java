package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CarNoList;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CarNoListInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CarNoListView;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CarNoListPresenters  implements Presenter {
    Case carNoList;
    CarNoListView carNoListView;
    Context mContext;

    @Inject
    public CarNoListPresenters(Context mContext, @Named("carNoList") Case carNoList){
        this.mContext = mContext;
        this.carNoList = carNoList;
    }

    public void setView(CarNoListView carNoListView){
        this.carNoListView = carNoListView;
    }

    public void  carNoList(String id){
        ((CarNoList)carNoList).setData(id);
        carNoList.execute(new CarNoListSubscriber(mContext));
        carNoListView.showLoading();
    }

    private final class CarNoListSubscriber extends DefaultSubscriber<Result<List<CarNoListInfo>>> {

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
            carNoListView.hideLoading();
        }

        @Override
        public void onNext(Result<List<CarNoListInfo>> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                carNoListView.retCarNoListView(true,result.getData(),result.getMsg());
            }else {
                carNoListView.hideLoading();
                carNoListView.retCarNoListView(false,result.getData(),result.getMsg());
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

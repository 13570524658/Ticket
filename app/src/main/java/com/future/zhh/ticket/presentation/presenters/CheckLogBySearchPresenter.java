package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CheckLogBySearch;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CheckLogBySearchView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogBySearchPresenter implements Presenter {
    Case checkLogBySearch;
    CheckLogBySearchView checkLogBySearchView;
    Context mContext;

    @Inject
    public CheckLogBySearchPresenter(Context mContext, @Named("checkLogBySearch") Case checkLogBySearch){
        this.mContext = mContext;
        this.checkLogBySearch = checkLogBySearch;
    }

    public void setView(CheckLogBySearchView checkLogBySearchView){
        this.checkLogBySearchView = checkLogBySearchView;
    }

    public void  checkByOrder(String userName,String userNameID){
        ((CheckLogBySearch)checkLogBySearch).setData(userName,userNameID);
        checkLogBySearch.execute(new CheckLogBySearchSubscriber(mContext));
        checkLogBySearchView.showLoading();
    }

    private final class CheckLogBySearchSubscriber extends DefaultSubscriber<Result<ListResult<CheckLogInfo>>> {

        protected CheckLogBySearchSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            checkLogBySearchView.hideLoading();
        }

        @Override
        public void onNext(Result<ListResult<CheckLogInfo>> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                checkLogBySearchView.retCheckLogBySearchView(true,result.getData(),result.getMsg());
            }else {
                checkLogBySearchView.hideLoading();
                checkLogBySearchView.retCheckLogBySearchView(false,result.getData(),result.getMsg());
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

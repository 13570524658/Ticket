package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.GasModule;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.GasmoduleInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.GasModuleView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/30.
 */

public class GasModulePresenter implements Presenter {
    Case gasModule;
    GasModuleView gasModuleView;
    Context mContext;

    @Inject
    public GasModulePresenter(Context mContext, @Named("gasModule") Case gasModule){
        this.mContext = mContext;
        this.gasModule = gasModule;
    }

    public void setView(GasModuleView gasModuleView){
        this.gasModuleView = gasModuleView;
    }

    public void  gasModule(String enterpriseID){
        ((GasModule)gasModule).setData(enterpriseID);
        gasModule.execute(new GasModuleSubscriber(mContext));
        gasModuleView.showLoading();
    }

    private final class  GasModuleSubscriber extends DefaultSubscriber<Result<List<GasmoduleList>>> {

        protected GasModuleSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            gasModuleView.hideLoading();
        }

        @Override
        public void onNext(Result<List<GasmoduleList>>result) {
            super.onNext(result);
            if (result.isSuccess()){
                gasModuleView.retGasModuleView(true,result.getData(),result.getMsg());
            }else {
                gasModuleView.hideLoading();
                gasModuleView.retGasModuleView(false,result.getData(),result.getMsg());
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

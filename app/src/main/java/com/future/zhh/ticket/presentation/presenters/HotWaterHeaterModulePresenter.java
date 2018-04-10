package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.HotWaterHeaterModule;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.HotWaterHeaterModuleListInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.HotWaterHeaterModuleView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/9.
 */

public class HotWaterHeaterModulePresenter implements Presenter{
    Case hotWaterHeaterModule;
    HotWaterHeaterModuleView hotWaterHeaterModuleView;
    Context mContext;

    @Inject
    public HotWaterHeaterModulePresenter(Context mContext, @Named("hotWaterHeaterModule") Case hotWaterHeaterModule){
        this.mContext = mContext;
        this.hotWaterHeaterModule = hotWaterHeaterModule;
    }

    public void setView(HotWaterHeaterModuleView hotWaterHeaterModuleView){
        this.hotWaterHeaterModuleView = hotWaterHeaterModuleView;
    }

    public void  hotWaterHeaterModule(String enterpriseID ){
        ((HotWaterHeaterModule)hotWaterHeaterModule).setData(enterpriseID);
        hotWaterHeaterModule.execute(new HotWaterHeaterModuleSubscriber(mContext));
        hotWaterHeaterModuleView.showLoading();
    }

    private final class HotWaterHeaterModuleSubscriber extends DefaultSubscriber<Result<List<HotWaterHeaterModuleListInfo>>> {

        protected HotWaterHeaterModuleSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            hotWaterHeaterModuleView.hideLoading();
        }

        @Override
        public void onNext(Result <List<HotWaterHeaterModuleListInfo>> result) {
            super.onNext(result);
            if (result!=null){
                hotWaterHeaterModuleView.retHotWaterHeaterModuleView(true,result.getData(),result.getMsg());

            }else {
                hotWaterHeaterModuleView.hideLoading();
                hotWaterHeaterModuleView.retHotWaterHeaterModuleView(false,result.getData(),result.getMsg());

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

package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.CheckSubmit;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.view.CheckSubmitView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/12/11.
 */

public class CheckSubmitPresenter implements Presenter{
    Case checkSubmit;
    CheckSubmitView checkSubmitView;
    Context mContext;

    @Inject
    public CheckSubmitPresenter(Context mContext, @Named("checkSubmit") Case checkSubmit){
        this.mContext = mContext;
        this.checkSubmit = checkSubmit;
    }

    public void setView(CheckSubmitView checkSubmitView){
        this.checkSubmitView = checkSubmitView;
    }

    public void  checkSubmit(String isAirVents,String isSafetyWarning,String isAlarm,String isTrueAlarm,
                             String isFireEquipment,int outTimeGasTotal,int  scrappingGasTotal,
                             String gasLocation,String spacing,String isNearStove,String  isAirLeakageValve,
                             String isProtectionDeviceStove,String isToolUseYearStove,String isSmokeRoad,
                             String hotWaterToolModule,String isHotWaterToolUseYear,String isVoltageRegulatorAirLeakage,
                             String isConnectingPipeAging,String checkImageOne,String checkImageTwo,
                             String checkImageThree,String oherRemark,String isPass,
                             String address,String customerName){
        ((CheckSubmit)checkSubmit).setData(isAirVents,isSafetyWarning,isAlarm,isTrueAlarm,
                isFireEquipment,outTimeGasTotal,scrappingGasTotal,gasLocation,spacing,isNearStove,
                isAirLeakageValve,isProtectionDeviceStove,isToolUseYearStove,isSmokeRoad,
                hotWaterToolModule,isHotWaterToolUseYear,isVoltageRegulatorAirLeakage,
                isConnectingPipeAging,checkImageOne,checkImageTwo,checkImageThree,oherRemark,isPass,
                address,customerName);
        checkSubmit.execute(new CheckSubmitSubscriber(mContext));
        checkSubmitView.showLoading();
    }

    private final class CheckSubmitSubscriber extends DefaultSubscriber<Result> {

        protected CheckSubmitSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            checkSubmitView.hideLoading();
        }

        @Override
        public void onNext(Result result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                checkSubmitView.retCheckSubmitView(true,result,result.getMsg());
            }else {
                checkSubmitView.hideLoading();
                checkSubmitView.retCheckSubmitView(false,result,result.getMsg());
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

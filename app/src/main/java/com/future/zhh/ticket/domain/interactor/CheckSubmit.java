package com.future.zhh.ticket.domain.interactor;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.base.Case;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/12/11.
 */

public class CheckSubmit extends Case {
    private CommonRepository repository;
    private String isAirVents;
    private String isSafetyWarning;
    private String isAlarm;
    private String isTrueAlarm;
    private String isFireEquipment;
    private int outTimeGasTotal;
    private int scrappingGasTotal;
    private String gasLocation;
    private String spacing;
    private String isNearStove;
    private String isAirLeakageValve;
    private String isProtectionDeviceStove;
    private String isToolUseYearStove;
    private String isSmokeRoad;
    private String hotWaterToolModule;
    private String isHotWaterToolUseYear;
    private String isVoltageRegulatorAirLeakage;
    private String isConnectingPipeAging;
    private String checkImageOne;
    private String checkImageTwo;
    private String checkImageThree;
    private String oherRemark;
    private String isPass;
    private String address;
    private String customerName;

    @Inject
    public CheckSubmit(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                          CommonRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository=repository;
    }
    public  void setData(String isAirVents,String isSafetyWarning,String isAlarm,
                         String isTrueAlarm,String isFireEquipment,int outTimeGasTotal,
                         int scrappingGasTotal,String gasLocation,String spacing,
                         String isNearStove,String isAirLeakageValve,String isProtectionDeviceStove,
                         String isToolUseYearStove,String isSmokeRoad,String hotWaterToolModule,
                         String isHotWaterToolUseYear,String isVoltageRegulatorAirLeakage,String isConnectingPipeAging,
                         String checkImageOne,String checkImageTwo,String checkImageThree,
                         String oherRemark,String isPass,String address,
                         String customerName){
                this.isAirVents=isAirVents;
                this.isSafetyWarning=isSafetyWarning;
                this.isAlarm=isAlarm;
                this.isTrueAlarm=isTrueAlarm;
                this.isFireEquipment=isFireEquipment;
                this.outTimeGasTotal=outTimeGasTotal;
                this.scrappingGasTotal=scrappingGasTotal;
                this.gasLocation=gasLocation;
                this.spacing=spacing;
                this.isNearStove=isNearStove;
                this.isAirLeakageValve=isAirLeakageValve;
                this.isProtectionDeviceStove=isProtectionDeviceStove;
                this.isToolUseYearStove=isToolUseYearStove;
                this.isSmokeRoad=isSmokeRoad;
                this.hotWaterToolModule=hotWaterToolModule;
                this.isHotWaterToolUseYear=isHotWaterToolUseYear;
                this.isVoltageRegulatorAirLeakage=isVoltageRegulatorAirLeakage;
                this.isConnectingPipeAging=isConnectingPipeAging;
                this.checkImageOne=checkImageOne;
                this.checkImageTwo=checkImageTwo;
                this.checkImageThree=checkImageThree;
                this.oherRemark=oherRemark;
                this.isPass=isPass;
                this.address=address;
                this.customerName=customerName;
    }

    @Override
    protected Observable buildCaseObservable() {
        return repository.checkSubmit(isAirVents,isSafetyWarning,isAlarm,isTrueAlarm,isFireEquipment,outTimeGasTotal,
                scrappingGasTotal,gasLocation,spacing,isNearStove,isAirLeakageValve,isProtectionDeviceStove,
                isToolUseYearStove,isSmokeRoad,hotWaterToolModule,isHotWaterToolUseYear,isVoltageRegulatorAirLeakage,
                isConnectingPipeAging,checkImageOne,checkImageTwo,checkImageThree,oherRemark,isPass,address,customerName);
    }
}

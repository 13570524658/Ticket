package com.future.zhh.ticket.data.repository.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.future.zhh.ticket.data.net.RetrofitManager;
import com.future.zhh.ticket.data.net.service.CommonService;
import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.CarNoListInfo;
import com.future.zhh.ticket.domain.model.CheckByCustomercardNoInfo;
import com.future.zhh.ticket.domain.model.CheckByOrderInfo;
import com.future.zhh.ticket.domain.model.CheckLogDetailInfo;
import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskInfo;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.GasLabeReplaceInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;
import com.future.zhh.ticket.domain.model.HotWaterHeaterModuleListInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.OrderTaskInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.SetUpLabelInfo;
import com.future.zhh.ticket.domain.model.SinceByCustomerInfo;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.domain.model.SinceLogInfo;
import com.future.zhh.ticket.domain.model.SinceLogListInfo;
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.domain.model.TransportTaskDetailInfo;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;
import com.future.zhh.ticket.domain.model.UserInfo;
import com.future.zhh.ticket.domain.model.WorkerInfo;
import com.future.zhh.ticket.presentation.utils.JsonSerializerUtil;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Administrator on 2017/11/19.
 */

public class CommonRepositoryImpl implements CommonRepository {
    public final static String TAG = CommonRepositoryImpl.class.getSimpleName();
    private Context mContext;
    private JsonSerializerUtil jsonSerializerUtil;

    @Inject
    public CommonRepositoryImpl(Context context,JsonSerializerUtil jsonSerializerUtil) {
        this.mContext = context;
        this.jsonSerializerUtil = jsonSerializerUtil;
    }

    @SuppressLint("LongLogTag")
    @Override
    public Observable<Result<UserInfo>> loginByPassword(String userID, String password) {

        Observable observable =  RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .loginByPassword(userID,password);
        Log.e("loginByPassword--------------",new Gson().toJson(observable));
        return  observable;
    }

    @Override
    public Observable<Result<UserInfo>> loginByQrCode(String userQrCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .loginByQrCode(userQrCode);
    }
    @Override
    public Observable<Result<List<CarNoListInfo>>> carNoList(String id) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .carNoList(id);
    }

    @Override
    public Observable<Result> electCarNo(String id,String carNo) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .electCarNo(id,carNo);
    }

    @Override
    public Observable<Result<GasInfo>> gasMsgByQrCode(String gasQrCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasMsgByQrCode(gasQrCode);
    }

    @Override
    public Observable<Result<CustomerInfo>> customerMsgByQrCode(String customerQrCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .customerMsgByQrCode(customerQrCode);
    }

    @Override
    public Observable<Result<WorkerInfo>> workerMsgByQrCode(String workerQrCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .workerMsgByQrCode(workerQrCode);
    }
    @Override
    public Observable<Result<CarInfo>> carMsgByQrCode(String carQrCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .carMsgByQrCode(carQrCode);
    }
    @Override
    public Observable<Result<OrderInfo>> orderMsgByQrCode(String orderQrCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .orderMsgByQrCode(orderQrCode);
    }

    @Override
    public Observable<Result<ListResult<TransportTaskInfo>>> transportTaskList(String id, String enterpriseID, int pageIndex,
                                                                               int pageSize, int state, String stationID, String shopID,
                                                                               String depID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportTaskList(id,enterpriseID,pageIndex,pageSize,state,stationID,shopID,depID);
    }

    @Override
    public Observable<Result<TransportTaskDetailInfo>> transportTaskDetail(String transportID, String transportTaskID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportTaskDetail(transportID,transportTaskID);
    }

    @Override
    public Observable<Result<OrderInfo>> orderMsgByOrderID(String orderID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .orderMsgByOrderID(orderID);
    }

    @Override
    public Observable<Result<CustomerInfo>> customerMsgByCustomerID(String customerID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .customerMsgByCustomerID(customerID);
    }
    @Override
    public Observable<Result<GasInfo>> gasMsgByLabel(String label) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasMsgByLabel(label);
    }




    @Override
    public Observable<Result> transportOfPay(String transportTaskID,String id,String gasData ,int count) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportOfPay(transportTaskID,id,gasData,count);
    }


    @Override
    public Observable<Result> transportOfReclaim(String transportTaskID,String id,String gasData,int count) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportOfReclaim(transportTaskID,id,gasData,count);
    }

    @Override
    public Observable<Result<ListResult<TransportTaskInfo>>> transportIdEqTask(String transportID, String customerName, int state ) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportIdEqTask(transportID,customerName,state);
    }

    @Override
    public Observable<Result<CustomerCardNoTaskInfo>> transportByCustomerCardNo(String customerCardNo ) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportByCustomerCardNo(customerCardNo);
    }

    @Override
    public Observable<Result<OrderTaskInfo>> transportByOrderQrCode(String orderQRcode ) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .transportByOrderQrCode(orderQRcode);
    }

    @Override
    public Observable<Result<SetUpLabelInfo>> setUpScan(String gasLabe) {



        return     RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .setUpScan(gasLabe);
    }

    @Override
    public Observable<Result<List<GasmoduleList>>> gasModule(String enterpriseID) {



        return         RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasModule(enterpriseID);
    }
    @Override
    public Observable<Result> gasMsgChange(String id, String gasLabe, String productFactoryCompany, String productFactoryID, String gasweight,
                                                             String gasModule, String gasSteelcode, String productData,
                                                             String lastTestCompany, String lastTestData, String nextTestData,
                                                             String image1, String image2, String image3) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasMsgChange(id,gasLabe,productFactoryCompany,productFactoryID,gasweight,gasModule,gasSteelcode,productData,lastTestCompany,
                        lastTestData, nextTestData,image1,image2,image3  );
    }

    @Override
    public   Observable<Result<ListResult <GasSetUpLogListInfo>>> gasSetUpLog(String enterpriseID,
                                                                                     int pageSize, int pageIndex) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasSetUpLog(enterpriseID,pageSize,pageIndex);
    }


    @Override
    public Observable<Result<ListResult<GasSetUpLogListInfo>>> gasLabeEqLog(String gasLabe,String steelCode) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasLabeEqLog(gasLabe,steelCode);
    }
    @Override
    public Observable<Result<GasLabeReplaceInfo>> gasLabeReplace(String gasLabe) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .gasLabeReplace(gasLabe);
    }

    @Override
    public Observable<Result<SinceByOrderInfo>> sinceByOder(String orderID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceByOder(orderID);
    }

    @Override
    public Observable<Result<SinceByCustomerInfo>> sinceByCustomer(String customerCardNo) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceByCustomer(customerCardNo);
    }

    @Override
    public Observable<Result> sinceOfPay(String gasMsg, String orderID, String id) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceOfPay(gasMsg,orderID,id);
    }

    @Override
    public Observable<Result> sinceOfReclaim(String gasMsg, String orderID, String id) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceOfReclaim(gasMsg,orderID,id);
    }

    @Override
    public Observable<Result<ListResult<SinceLogListInfo>>> sinceLog(String id) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceLog(id);
    }

    @Override
    public Observable<Result<ListResult<SinceLogListInfo>>> sinceEqLog(String orderID,String customerName) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceEqLog(orderID,customerName);
    }

    @Override
    public Observable<Result<SinceTaskMsgInfo>> sinceTaskMsg(String orderID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .sinceTaskMsg(orderID);
    }

    @Override
    public Observable<Result<CheckByCustomercardNoInfo>> checkByCustomercardNo(String customerCardNo) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .checkByCustomercardNo(customerCardNo);
    }

    @Override
    public Observable<Result<CheckByOrderInfo>> checkByOrder(String orderID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .checkByOrder(orderID);
    }
    @Override
    public Observable<Result<List<HotWaterHeaterModuleListInfo>>> hotWaterHeaterModule(String enterpriseID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .hotWaterHeaterModule(enterpriseID);
    }

    @Override
    public Observable<Result> checkSubmit(String isAirVents, String isSafetyWarning, String isAlarm,
                                          String isTrueAlarm, String isFireEquipment, int outTimeGasTotal,
                                          int scrappingGasTotal, String gasLocation, String spacing,
                                          String isNearStove, String isAirLeakageValve, String isProtectionDeviceStove,
                                          String isToolUseYearStove, String isSmokeRoad, String hotWaterToolModule,
                                          String isHotWaterToolUseYear, String isVoltageRegulatorAirLeakage, String isConnectingPipeAging,
                                          String CheckImageOne, String CheckImageTwo, String CheckImageThree,
                                          String oherRemark, String isPass, String address,
                                          String customerName) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .checkSubmit(isAirVents,isSafetyWarning,isAlarm,isTrueAlarm,isFireEquipment,outTimeGasTotal,
                        scrappingGasTotal,gasLocation,spacing,isNearStove,isAirLeakageValve,isProtectionDeviceStove,
                        isToolUseYearStove,isSmokeRoad,hotWaterToolModule,isHotWaterToolUseYear,isVoltageRegulatorAirLeakage,isConnectingPipeAging,
                        CheckImageOne,CheckImageTwo,CheckImageThree,oherRemark,isPass,address,customerName );
    }

    @Override
    public Observable<Result<ListResult<CheckLogInfo>>> checkLog(String enterpriseID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .checkLog(enterpriseID);
    }


    @Override
    public Observable<Result<ListResult<CheckLogInfo>>> checkLogBySearch(String userName,
                                                                         String userNameID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .checkLogBySearch(userName,userNameID);
    }

    @Override
    public Observable<Result<CheckLogDetailInfo>> checkLogDetail(String userName,
                                                                 String userNameID) {
        return RetrofitManager.builder(mContext)
                .getRetrofit().create(CommonService.class)
                .checkLogDetail(userName,userNameID);
    }
}

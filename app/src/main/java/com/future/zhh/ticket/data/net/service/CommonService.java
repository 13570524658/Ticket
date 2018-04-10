package com.future.zhh.ticket.data.net.service;

import com.future.zhh.ticket.data.net.intercepter.CacheInterceptor;
import com.future.zhh.ticket.domain.model.CarNoListInfo;
import com.future.zhh.ticket.domain.model.CheckByCustomercardNoInfo;
import com.future.zhh.ticket.domain.model.CheckByOrderInfo;
import com.future.zhh.ticket.domain.model.CheckLogDetailInfo;
import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskInfo;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.GasLabeReplaceInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;
import com.future.zhh.ticket.domain.model.HotWaterHeaterModuleListInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.OrderTaskInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.GasSetUpLogInfo;
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
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;

import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface CommonService {
    //登录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.LOGIN_BY_PASSWORD)
    Observable<Result<UserInfo>> loginByPassword(@Field("userID") String userID, @Field("password") String password);

    //二维码登录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.LOGIN_BY_QR_CODE)
    Observable<Result<UserInfo>> loginByQrCode(@Field("userQrCode") String userQrCode);

    //车辆列表
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CAR_NP_LIST)
    Observable<Result<List<CarNoListInfo>>> carNoList(@Field("id") String id);

    //锁定选中车辆
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.ELECTCARNO)
    Observable<Result> electCarNo(@Field("id") String id,@Field("carNo")String carNo);

    //气瓶二维码查询信息
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAS_MSG_BY_QR_CODE)
    Observable<Result<GasInfo>> gasMsgByQrCode(@Field("gasQrCode") String gasQrCode);
    //客户二维码查询信息
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CUSTOMER_MSG_BY_QR_CODE)
    Observable<Result<CustomerInfo>> customerMsgByQrCode(@Field("customerQrCode") String customerQrCode);

    //从业人员二维码查询信息
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.WORKER_MSG_BY_QR_CODE)
    Observable<Result<WorkerInfo>> workerMsgByQrCode(@Field("workerQrCode") String workerQrCode);
    //车辆二维码查询信息
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CAR_MSG_BY_QR_CODE)
    Observable<Result<CarInfo>> carMsgByQrCode(@Field("carQrCode") String carQrCode);

    //订单二维码查询信息
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.ORDER_MSG_BY_QR_CODE)
    Observable<Result<OrderInfo>> orderMsgByQrCode(@Field("orderQrCode") String orderQrCode);

    //配送任务列表
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_TAKE_LIST)
    Observable<Result<ListResult<TransportTaskInfo>>> transportTaskList(@Field("id") String id, @Field("enterpriseID")String enterpriseID,
                                                                        @Field("pageIndex")int pageIndex, @Field("pageSize")int pageSize,
                                                                        @Field("state")int state, @Field("stationID")String stationID,
                                                                        @Field("shopID")String shopID, @Field("depID")String depID);

    //订配送任务明细
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_TAKE_DETAIL)
    Observable<Result<TransportTaskDetailInfo>> transportTaskDetail(@Field("transportID") String transportID,@Field("transportTaskID")String transportTaskID);

    //配送任务订单id查询详细
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.ORDER_MSG_BY_ORDER_ID)
    Observable<Result<OrderInfo>> orderMsgByOrderID(@Field("orderID") String orderID);

    //配送任务客户id查询详细
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CUSTOMER_MSG_BY_CYSTONER_ID)
    Observable<Result<CustomerInfo>> customerMsgByCustomerID(@Field("customerID") String customerID);
    //配送任务气瓶标签查询详细
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAS_MSG_BY_LABEL)
    Observable<Result<GasInfo>> gasMsgByLabel(@Field("label") String label);

    //配送任务交付
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_OF_PAY)
    Observable<Result> transportOfPay(@Field("transportTaskID") String transportTaskID, @Field("id")String id, @Field("gasData ")String gasData
    ,@Field("count")int count);

    //配送任务回收
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_OF_RECLAIM)
    Observable<Result> transportOfReclaim(@Field("transportTaskID") String transportTaskID, @Field("id")String id, @Field("gasData ")String gasData
            ,@Field("count")int count);


    //在线搜索配送任务
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_ID_EQ_TASK)
    Observable<Result<ListResult<TransportTaskInfo>>> transportIdEqTask(@Field("transportID") String transportID, @Field("customerName")String customerName
            , @Field("state")int state);


    //配送任务扫用户卡
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_BY_CUSTOMER_CARD_NO)
    Observable<Result<CustomerCardNoTaskInfo>> transportByCustomerCardNo(@Field("customerCardNo") String customerCardNo);


    //配送任务扫订单号
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.TRANSPORT_BY_ORDER_QR_CODE)
    Observable<Result<OrderTaskInfo>> transportByOrderQrCode(@Field("orderQRcode") String orderQRcode);

    //气瓶建档扫气瓶
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SET_UP_SCAN)
    Observable<Result <SetUpLabelInfo>> setUpScan(@Field("gasLabe") String gasLabe);
    //气瓶建档气瓶型号
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAD_MODULE)
    Observable<Result <List<GasmoduleList>> > gasModule(@Field("enterpriseID") String enterpriseID);

    //气瓶信息建档
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAS_MSG_CHANG)
    Observable<Result> gasMsgChange(@Field("id")String id, @Field("gasLabe") String gasLabe, @Field("productFactoryCompany")String productFactoryCompany,
                                                      @Field("productFactoryID")String productFactoryID, @Field("gasweight")String gasweight,
                                                      @Field("gasModule")String gasModule, @Field("gasSteelcode")String gasSteelcode,
                                                      @Field("productData")String productData, @Field("lastTestCompany")String lastTestCompany,
                                                      @Field("lastTestData")String lastTestData, @Field("nextTestData")String nextTestData,
                                                      @Field("image1")String image1, @Field("image2")String image2, @Field("image3")String image3);

    //气瓶建档记录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAS_SET_UP_LOG)
    Observable<Result<ListResult<GasSetUpLogListInfo>>> gasSetUpLog(@Field("enterpriseID")String enterpriseID,
                                                                           @Field("pageSize")int pageSize,
                                                                           @Field("pageIndex")int pageIndex);

    //在线搜索建档记录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAS_LABEL_EQ_LOG)
    Observable<Result<ListResult<GasSetUpLogListInfo>>> gasLabeEqLog(@Field("gasLabe") String gasLabe,
                                                    @Field("steelCode")String steelCode);

    //建档更换气瓶标签
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.GAS_LABEL_REPLACE)
    Observable<Result<GasLabeReplaceInfo>> gasLabeReplace(@Field("gasLabe") String gasLabe);

    //自提扫订单号
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_BY_ORDER)
    Observable<Result<SinceByOrderInfo>> sinceByOder(@Field("orderID") String orderID);

    //自提扫用户卡
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_BY_CUSTOMER)
    Observable<Result<SinceByCustomerInfo>> sinceByCustomer(@Field("customerCardNo") String customerCardNo);

    //自提交付
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_OF_PAY)
    Observable<Result> sinceOfPay(@Field("gasMsg") String gasMsg,@Field("orderID")String oderID,
                                  @Field("id")String id);

    //自提回收
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_OF_RECLAIM)
    Observable<Result> sinceOfReclaim(@Field("gasMsg") String gasMsg,@Field("orderID")String oderID,
                                      @Field("id")String id);


    //自提历史记录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_LOG)
    Observable<Result<ListResult<SinceLogListInfo>>> sinceLog(@Field("id") String id);

    //自提在线搜索
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_EQ_LOG)
    Observable<Result<ListResult<SinceLogListInfo>>> sinceEqLog(@Field("orderID") String orderID,@Field("customerName")String customerName);

    //自提任务明细
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.SINCE_TASK_MSG)
    Observable<Result<SinceTaskMsgInfo>> sinceTaskMsg(@Field("orderID") String orderID);


    //入户安全检查扫用户卡
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CHECK_BY_CUSTOMER_CARD_NO)
    Observable<Result<CheckByCustomercardNoInfo>> checkByCustomercardNo(@Field("customerCardNo") String customerCardNo);

    //入户安全检查扫订单
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CHECK_BY_ORDER)
    Observable<Result<CheckByOrderInfo>> checkByOrder(@Field("orderID") String orderID);

    //热水器类型
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.HOT_WATER_HEATER_MODULE)
    Observable<Result<List<HotWaterHeaterModuleListInfo>>> hotWaterHeaterModule(@Field("enterpriseID") String enterpriseID);

    //入户安全检查提交
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CHECK_SUBMIT)
    Observable<Result> checkSubmit(@Field("isAirVents") String isAirVents,@Field("isSafetyWarning")String isSafetyWarning,@Field("isAlarm")String isAlarm,
                                   @Field("isTrueAlarm")String isTrueAlarm,@Field("isFireEquipment")String isFireEquipment,@Field("outTimeGasTotal")int outTimeGasTotal,
                                   @Field("scrappingGasTotal")int scrappingGasTotal,@Field("gasLocation")String gasLocation,@Field("spacing")String spacing,
                                   @Field("isNearStove")String isNearStove,@Field("isAirLeakageValve")String isAirLeakageValve,@Field("isProtectionDeviceStove")String isProtectionDeviceStove,
                                   @Field("isToolUseYearStove")String isToolUseYearStove,@Field("isSmokeRoad")String isSmokeRoad,@Field("hotWaterToolModule")String hotWaterToolModule,
                                   @Field("isHotWaterToolUseYear")String isHotWaterToolUseYear,@Field("isVoltageRegulatorAirLeakage")String isVoltageRegulatorAirLeakage,@Field("isConnectingPipeAging")String isConnectingPipeAging,
                                   @Field("checkImageOne")String checkImageOne,@Field("checkImageTwo")String checkImageTwo,@Field("checkImageThree")String checkImageThree,
                                   @Field("oherRemark")String oherRemark,@Field("isPass")String isPass,@Field("address")String address,
                                   @Field("customerName")String customerName);

    //入户检查历史记录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CHECK_LOG)
    Observable<Result<ListResult<CheckLogInfo>>> checkLog(@Field("enterpriseID") String enterpriseID);

    //入户检查在线搜索历史记录
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CHECK_LOG_BY_SEARCH)
    Observable<Result<ListResult<CheckLogInfo>>> checkLogBySearch(@Field("userName") String userName,
                                                                  @Field("userNameID")String userNameID);


    //入户检查历史记录详细
    @Headers(CacheInterceptor.CACHE_CONTROL_NO_CACHE)
    @FormUrlEncoded
    @POST(ApplicationDatas.CHECK_LOG_BY_SEARCH_DETAIL)
    Observable<Result<CheckLogDetailInfo >> checkLogDetail(@Field("userName") String userName,
                                                                      @Field("userNameID")String userNameID);
}

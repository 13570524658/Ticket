package com.future.zhh.ticket.data.repository;

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

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/11/19.
 */

public interface CommonRepository {

    Observable<Result<UserInfo>> loginByPassword(String userID, String password);

    Observable<Result<UserInfo>> loginByQrCode(String userQrCode);

    Observable<Result<List<CarNoListInfo>>> carNoList(String id);

    Observable<Result> electCarNo(String id, String carNo);

    Observable<Result<GasInfo>> gasMsgByQrCode(String gasQrCode);

    Observable<Result<CustomerInfo>> customerMsgByQrCode(String customerQrCode);

    Observable<Result<WorkerInfo>> workerMsgByQrCode(String workerQrCode);

    Observable<Result<CarInfo>> carMsgByQrCode(String carQrCode);

    Observable<Result<OrderInfo>> orderMsgByQrCode(String orderQrCode);


    Observable<Result<ListResult<TransportTaskInfo>>> transportTaskList(String id, String enterpriseID, int pageIndex,
                                                                        int pageSize, int state, String stationID, String shopID,
                                                                        String depID);

    Observable<Result<TransportTaskDetailInfo>> transportTaskDetail(String transportID, String transportTaskID);

    Observable<Result<OrderInfo>> orderMsgByOrderID(String orderID);

    Observable<Result<CustomerInfo>> customerMsgByCustomerID(String customerID);

    Observable<Result<GasInfo>> gasMsgByLabel(String label);

    Observable<Result> transportOfPay(String transportTaskID, String id, String gasData, int count);

    Observable<Result> transportOfReclaim(String transportID, String id, String gasData, int count);

    Observable<Result<ListResult<TransportTaskInfo>>> transportIdEqTask(String transportID, String customerName, int status);

    Observable<Result<CustomerCardNoTaskInfo>> transportByCustomerCardNo(String customerCardNo);

    Observable<Result<OrderTaskInfo>> transportByOrderQrCode(String orderQRcode);

    Observable<Result<SetUpLabelInfo>> setUpScan(String gasLabe);

    Observable<Result<List<GasmoduleList>>> gasModule(String enterpriseID);

    Observable<Result> gasMsgChange(String id, String gasLabe, String productFactoryCompany, String productFactoryID, String gasweight,
                                                      String gasModule, String gasSteelcode, String productData,
                                                      String nextTestCompany, String lastTestCompany, String lastTestData,
                                                      String image1, String image2, String image3);

    Observable<Result <ListResult <GasSetUpLogListInfo>>>  gasSetUpLog(String enterpriseID,
                                                                             int pageSize, int pageIndex);

    Observable<Result<ListResult<GasSetUpLogListInfo>>> gasLabeEqLog(String gasLabe, String steelCode);

    Observable<Result<GasLabeReplaceInfo>> gasLabeReplace(String gasLabe);

    Observable<Result<SinceByOrderInfo>> sinceByOder(String orderID);

    Observable<Result<SinceByCustomerInfo>> sinceByCustomer(String customerCardNo);

    Observable<Result> sinceOfPay(String gasMsg, String orderID, String id);

    Observable<Result> sinceOfReclaim(String gasMsg, String orderID, String id);

    Observable<Result<ListResult<SinceLogListInfo>>> sinceLog(String id);


    Observable<Result<ListResult<SinceLogListInfo>>> sinceEqLog(String orderID, String customerName);

    Observable<Result<SinceTaskMsgInfo>> sinceTaskMsg(String orderID);

    Observable<Result<CheckByCustomercardNoInfo>> checkByCustomercardNo(String customerCardNo);

    Observable<Result<CheckByOrderInfo>> checkByOrder(String orderID);

    Observable<Result<List<HotWaterHeaterModuleListInfo>>> hotWaterHeaterModule(String orderID);

    Observable<Result> checkSubmit(String isAirVents, String isSafetyWarning, String isAlarm,
                                   String isTrueAlarm, String isFireEquipment, int outTimeGasTotal,
                                   int scrappingGasTotal, String gasLocation, String spacing,
                                   String isNearStove, String isAirLeakageValve, String isProtectionDeviceStove,
                                   String isToolUseYearStove, String isSmokeRoad, String hotWaterToolModule,
                                   String isHotWaterToolUseYear, String isVoltageRegulatorAirLeakage, String isConnectingPipeAging,
                                   String checkImageOne, String checkImageTwo, String checkImageThree,
                                   String oherRemark, String isPass, String address,
                                   String customerName);

    Observable<Result<ListResult<CheckLogInfo>>> checkLog(String enterpriseID);

    Observable<Result<ListResult<CheckLogInfo>>> checkLogBySearch(String userName,
                                                                  String userNameID);

    Observable<Result<CheckLogDetailInfo>> checkLogDetail(String userName,
                                                          String userNameID);
}

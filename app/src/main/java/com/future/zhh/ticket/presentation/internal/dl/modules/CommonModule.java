package com.future.zhh.ticket.presentation.internal.dl.modules;

import com.future.zhh.ticket.data.repository.CommonRepository;
import com.future.zhh.ticket.domain.executor.PostExecutionThread;
import com.future.zhh.ticket.domain.executor.ThreadExecutor;
import com.future.zhh.ticket.domain.interactor.CarMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.CarNoList;
import com.future.zhh.ticket.domain.interactor.CheckByCustomercardNo;
import com.future.zhh.ticket.domain.interactor.CheckByOrder;
import com.future.zhh.ticket.domain.interactor.CheckLog;
import com.future.zhh.ticket.domain.interactor.CheckLogBySearch;
import com.future.zhh.ticket.domain.interactor.CheckLogDetail;
import com.future.zhh.ticket.domain.interactor.CheckSubmit;
import com.future.zhh.ticket.domain.interactor.CustomerMsgByCustomerID;
import com.future.zhh.ticket.domain.interactor.CustomerMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.ElectCarNo;
import com.future.zhh.ticket.domain.interactor.GasLabeEqLog;
import com.future.zhh.ticket.domain.interactor.GasLabeReplace;
import com.future.zhh.ticket.domain.interactor.GasModule;
import com.future.zhh.ticket.domain.interactor.GasMsgByLabel;
import com.future.zhh.ticket.domain.interactor.GasMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.GasMsgChange;
import com.future.zhh.ticket.domain.interactor.GasSetUpLog;
import com.future.zhh.ticket.domain.interactor.HotWaterHeaterModule;
import com.future.zhh.ticket.domain.interactor.LoginByPassword;
import com.future.zhh.ticket.domain.interactor.LoginByQrCode;
import com.future.zhh.ticket.domain.interactor.OrderMsgByOrderID;
import com.future.zhh.ticket.domain.interactor.OrderMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.SetUpScan;
import com.future.zhh.ticket.domain.interactor.SinceByCustomer;
import com.future.zhh.ticket.domain.interactor.SinceByOder;
import com.future.zhh.ticket.domain.interactor.SinceEqLog;
import com.future.zhh.ticket.domain.interactor.SinceOfPay;
import com.future.zhh.ticket.domain.interactor.SinceOfReclaim;
import com.future.zhh.ticket.domain.interactor.SinceTaskMsg;
import com.future.zhh.ticket.domain.interactor.TransportByCustomerCardNo;
import com.future.zhh.ticket.domain.interactor.TransportByOrderQrCode;
import com.future.zhh.ticket.domain.interactor.TransportIdEqTask;
import com.future.zhh.ticket.domain.interactor.TransportOfPay;
import com.future.zhh.ticket.domain.interactor.TransportOfReclaim;
import com.future.zhh.ticket.domain.interactor.TransportTaskDetail;
import com.future.zhh.ticket.domain.interactor.TransportTaskList;
import com.future.zhh.ticket.domain.interactor.WorkerMsgByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.model.SinceLog;
import com.future.zhh.ticket.presentation.internal.dl.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/11/19.
 */
@Module
public class CommonModule {
    public CommonModule() {

    }
    @Provides
    @PerActivity
    @Named("loginByPassword")
    Case provideLoginByPasswordCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new LoginByPassword(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("loginByQrCode")
    Case provideLoginByQRcodeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new LoginByQrCode(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("carNoList")
    Case provideCarNoListCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CarNoList(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("electCarNo")
    Case provideElectCarNoCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new ElectCarNo(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("gasMsgByQrCode")
    Case provideGasMsgByQrCodeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasMsgByQrCode(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("customerMsgByQrCode")
    Case provideCustomerMsgByQrCodeCase(CommonRepository repository, ThreadExecutor threadExecutor
                    , PostExecutionThread postExecutionThread) {
        return new CustomerMsgByQrCode(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("workerMsgByQrCode")
    Case provideWorkerMsgByQrCodeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new WorkerMsgByQrCode(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("carMsgByQrCode")
    Case provideCarMsgByQrCodeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CarMsgByQrCode(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("orderMsgByQrCode")
    Case provideOrderMsgByQrCodeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new OrderMsgByQrCode(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("transportTaskList")
    Case provideTransportTaskListCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new TransportTaskList(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("transportTaskDetail")
    Case provideTransportTaskDetailCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new TransportTaskDetail(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("orderMsgByOrderID")
    Case provideOrderMsgByOrderIDCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new OrderMsgByOrderID(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("customerMsgByCustomerID")
    Case provideCustomerMsgByCustomerIDCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CustomerMsgByCustomerID(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("gasMsgByLabel")
    Case provideGasMsgByLabelCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasMsgByLabel(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("transportOfPay")
    Case provideTransportOfPayCase(CommonRepository repository, ThreadExecutor threadExecutor
                    , PostExecutionThread postExecutionThread) {
        return new TransportOfPay(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("transportOfReclaim")
    Case provideTransportOfReclaimCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new TransportOfReclaim(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("transportIdEqTask")
    Case provideTransportIdEqTaskmCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new TransportIdEqTask(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("transportByCustomerCardNo")
    Case provideTransportByCustomerCardNoCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new TransportByCustomerCardNo(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("transportByOrderQrCode")
    Case provideTransportByOrderQrCodeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new TransportByOrderQrCode(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("gasModule")
    Case provideGasModuleCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasModule(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("setUpScan")
    Case provideSetUpScanCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SetUpScan(threadExecutor, postExecutionThread, repository);
    }


    @Provides
    @PerActivity
    @Named("gasMsgChange")
    Case provideGasMsgChangeCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasMsgChange(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("gasSetUpLog")
    Case provideGasSetUpLogCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasSetUpLog(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("gasLabeEqLog")
    Case provideGasLabeEqLogCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasLabeEqLog(threadExecutor, postExecutionThread, repository);
    }


    @Provides
    @PerActivity
    @Named("gasLabeReplace")
    Case provideGasLabeReplaceCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new GasLabeReplace(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("sinceByOder")
    Case provideSinceByOderCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceByOder(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("sinceByCustomer")
    Case provideSinceByCustomerCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceByCustomer(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("sinceOfPay")
    Case provideSinceOfPayCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceOfPay(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("sinceOfReclaim")
    Case provideSinceOfReclaimCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceOfReclaim(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("sinceLog")
    Case provideSinceLogCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceLog(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("sinceEqLog")
    Case provideSinceEqLogCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceEqLog(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("sinceTaskMsg")
    Case provideSinceTaskMsgCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new SinceTaskMsg(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("checkByCustomercardNo")
    Case provideCheckByCustomercardNoCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CheckByCustomercardNo(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("checkByOrder")
    Case provideCheckByOrderCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CheckByOrder(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("hotWaterHeaterModule")
    Case provideHotWaterHeaterModuleCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new HotWaterHeaterModule(threadExecutor, postExecutionThread, repository);
    }


    @Provides
    @PerActivity
    @Named("checkSubmit")
    Case provideCheckSubmitCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CheckSubmit(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("checkLog")
    Case provideCheckLogCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CheckLog(threadExecutor, postExecutionThread, repository);
    }
    @Provides
    @PerActivity
    @Named("checkLogBySearch")
    Case provideCheckLogBySearchCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CheckLogBySearch(threadExecutor, postExecutionThread, repository);
    }

    @Provides
    @PerActivity
    @Named("checkLogDetail")
    Case provideCheckLogDetailCase(CommonRepository repository, ThreadExecutor threadExecutor
            , PostExecutionThread postExecutionThread) {
        return new CheckLogDetail(threadExecutor, postExecutionThread, repository);
    }
}

package com.future.zhh.ticket.presentation.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinawidth.zzmandroid.decode.OnDecodeCompletionListener;
import com.chinawidth.zzmandroid.ui.CodeUtils;
import com.chinawidth.zzmandroid.ui.ScannerFragment;
import com.future.zhh.ticket.R;
import com.future.zhh.ticket.data.net.NetUtil;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.domain.model.CheckByCustomercardNoInfo;
import com.future.zhh.ticket.domain.model.CheckByOrderInfo;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskInfo;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.GasLabeReplaceInfo;
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.domain.model.OrderTaskInfo;
import com.future.zhh.ticket.domain.model.SetUpLabelInfo;
import com.future.zhh.ticket.domain.model.SinceByCustomerInfo;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.domain.model.UserInfo;
import com.future.zhh.ticket.domain.model.WorkerInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerScanInfoActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ScanInfoActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CarMsgByQrCodePresenter;
import com.future.zhh.ticket.presentation.presenters.CheckByCustomercardNoPersenter;
import com.future.zhh.ticket.presentation.presenters.CheckByOrderPresenter;
import com.future.zhh.ticket.presentation.presenters.CustomerMsgByCustomerIDPresenter;
import com.future.zhh.ticket.presentation.presenters.GasLabeReplacePersent;
import com.future.zhh.ticket.presentation.presenters.GasMsgByQrCodePresenters;
import com.future.zhh.ticket.presentation.presenters.LoginByQrCodePresenter;
import com.future.zhh.ticket.presentation.presenters.OrderMsgByOrderIDPresenter;
import com.future.zhh.ticket.presentation.presenters.SetUpScanPersenter;
import com.future.zhh.ticket.presentation.presenters.SinceByCustomerPresenter;
import com.future.zhh.ticket.presentation.presenters.SinceByOderPresenter;
import com.future.zhh.ticket.presentation.presenters.TransportByCustomerCardNoPresenter;
import com.future.zhh.ticket.presentation.presenters.TransportByOrderQrCodePresenter;
import com.future.zhh.ticket.presentation.presenters.WorkerMsgByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.AESUtil;
import com.future.zhh.ticket.presentation.utils.CardTypeUtils;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.CheckByCustomercardNoView;
import com.future.zhh.ticket.presentation.view.CheckByOrderView;
import com.future.zhh.ticket.presentation.view.CustomerMsgByCustomerIDView;
import com.future.zhh.ticket.presentation.view.GasLabeReplaceView;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.LoginByQrCodeView;
import com.future.zhh.ticket.presentation.view.OrderMsgByOrderIDView;
import com.future.zhh.ticket.presentation.view.SetUpScanView;
import com.future.zhh.ticket.presentation.view.SinceByCustomerView;
import com.future.zhh.ticket.presentation.view.SinceByOderView;
import com.future.zhh.ticket.presentation.view.TransportByCustomerCardNoView;
import com.future.zhh.ticket.presentation.view.TransportByOrderQrCodeView;
import com.future.zhh.ticket.presentation.view.WorkerMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.ActivityIndicatorView;
import com.future.zhh.ticket.presentation.view.widgets.CameraErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.NetErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.ScanErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/11/20.
 * 扫码获取信息功能页面
 */

public class ScanInfoActivity extends BaseActivity implements OrderMsgByOrderIDView, CustomerMsgByCustomerIDView, OnDecodeCompletionListener, GasMsgByQrCodeView, LoginByQrCodeView, TransportByCustomerCardNoView, TransportByOrderQrCodeView, WorkerMsgByQrCodeView, CarMsgByQrCodeView, SinceByCustomerView, SinceByOderView, CheckByCustomercardNoView, CheckByOrderView, SetUpScanView {

    @BindView(R.id.img_ibtnBack)
    ImageView imgIbtnBack;
    @BindView(R.id.ibtnBack)
    LinearLayout ibtnBack;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ibtnSetting)
    ImageButton ibtnSetting;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.cbFlashLight)
    CheckBox cbFlashLight;

    public final static String TAG = ScanInfoActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private ScanInfoActivityComponent component;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private ActivityIndicatorView activityIndicatorView;
    private NetUtil netUtil;
    private ScannerFragment scannerFragment;


    //扫码登录
    public final static int REQUEST_LOGIN_SCAN = 1;
    //建档扫瓶
    public final static int REQUEST_BYPUTTING_SCAN = 2;
    //建档修改标签
    public final static int REQUEST_CHANGE_LABEL = 3;

    private int tapFrom;
    private String userQrCode = "";
    private String enterpriseID = "";
    private String results = "";


    @Inject
    LoginByQrCodePresenter loginByQrCodePresenter;
    @Inject
    OrderMsgByOrderIDPresenter orderMsgByOrderIDPresenter;
    @Inject
    CustomerMsgByCustomerIDPresenter customerMsgByCustomerIDPresenter;
    @Inject
    GasMsgByQrCodePresenters gasMsgByQrCodePresenters;
    @Inject
    SetUpScanPersenter setUpScanPersenter;
    @Inject
    TransportByOrderQrCodePresenter transportByOrderQrCodePresenter;
    @Inject
    TransportByCustomerCardNoPresenter transportByCustomerCardNoPresenter;
    @Inject
    WorkerMsgByQrCodePresenter workerMsgByQrCodePresenter;
    @Inject
    CarMsgByQrCodePresenter carMsgByQrCodePresenter;

    @Inject
    SinceByCustomerPresenter sinceByCustomerPresenter;
    @Inject
    SinceByOderPresenter sinceByOderPresenter;
    @Inject
    CheckByCustomercardNoPersenter checkByCustomercardNoPersenter;
    @Inject
    CheckByOrderPresenter checkByOrderPresenter;


    public static Intent getCallingIntent(Context context, int tapFrom) {
        Intent intent = new Intent(context, ScanInfoActivity.class);
        intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerScanInfoActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_info);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initZcode();
        initView();
    }

    private void init() {
        activityIndicatorView = new ActivityIndicatorView(mContext);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        orderMsgByOrderIDPresenter.setView(this);
        sinceByOderPresenter.setView(this);

        sinceByCustomerPresenter.setView(this);
        setUpScanPersenter.setView(this);
        customerMsgByCustomerIDPresenter.setView(this);
        transportByCustomerCardNoPresenter.setView(this);
        gasMsgByQrCodePresenters.setView(this);
        loginByQrCodePresenter.setView(this);
        transportByOrderQrCodePresenter.setView(this);
        workerMsgByQrCodePresenter.setView(this);
        carMsgByQrCodePresenter.setView(this);
        checkByCustomercardNoPersenter.setView(this);
        checkByOrderPresenter.setView(this);
        netUtil = new NetUtil(mContext);
        if (getIntent() != null) {
            tapFrom = getIntent().getIntExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_HOME_BUTTON);
        }
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
    }

    /**
     * 初始化真知码
     */
    private void initZcode() {
        scannerFragment = new ScannerFragment();
        CodeUtils.setFragmentArgs(scannerFragment, R.layout.scan_cylinder_view);
        scannerFragment.setOnDecodeListener(this);
        addFragment(R.id.flContainer, scannerFragment, ScannerFragment.class.getSimpleName(), false);
        cbFlashLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    scannerFragment.flashLightController(true);
                } else {
                    scannerFragment.flashLightController(false);
                }
            }
        });
    }


    private void initView() {
        if (tapFrom == ApplicationDatas.TAP_FROM_LOGIN) {
            tvToolbarTitle.setText("登录扫码");
            textTip.setText("请将扫从业人员卡放置扫描框中");
        } else if (tapFrom == ApplicationDatas.TAP_FROM_HOME_BUTTON) {
            tvToolbarTitle.setText("查询扫码");
            textTip.setText("请将从业人员卡,气瓶标签,用户卡,车辆卡,订单号,放置于扫描框中");

        } else if (tapFrom == ApplicationDatas.TAP_FROM_JF_AND_HS) {
            tvToolbarTitle.setText("配送扫码");
            textTip.setText("请将用户卡/订单二维码放置于扫描框中");
        } else if (tapFrom == ApplicationDatas.TAP_FROM_INPUTTING) {
            tvToolbarTitle.setText("建档扫码");
            textTip.setText("请将气瓶标签码放置于扫描框中");
        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JFHS) {
            tvToolbarTitle.setText("自提扫码");
            textTip.setText("请将用户卡/订单二维码放置于扫描框中");
            ibtnSetting.setVisibility(View.VISIBLE);
            //气瓶自提历史记录
            ibtnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, SinceLogActivity.class);
                    startActivity(intent);
                }
            });
        } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK) {
            tvToolbarTitle.setText("入户检查扫码");
            textTip.setText("请将用户卡/订单二维码放置于扫描框中");
            ibtnSetting.setVisibility(View.VISIBLE);
            //入户安全检查记录
            ibtnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CheckLogActivity.class);
                    startActivity(intent);
                }
            });
        }else if (tapFrom==ApplicationDatas.TAP_FROM_CHANGE_LABEL){
            tvToolbarTitle.setText("修改气瓶标签扫码");
            textTip.setText("请将气瓶标签放置于扫描框中");
        }
    }


    @OnClick({R.id.ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 扫码
     *
     * @param result
     * @param bundle
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onDecodeCompletion(String result, Bundle bundle) {
        if (result == null) {

            new CircleDialog.Builder(this)
                    .setWidth((float) 0.8)
                    .setTitle("提示")
                    .setText("无网络")
                    .setPositive("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            scannerFragment.restart();
                        }
                    })
                    .show();
        } else {
            if (netUtil.isNetworkConnected()) {
                try {
                    String resultCode = result.substring(0, 1);
                    results = result.substring(1, result.length());
                    if (!resultCode.equalsIgnoreCase("3"))
                        results = AESUtil.decryptWithBase64(result);

                    if (results.contains("+")) {
                        results = results.substring(results.indexOf("+") + 1, results.indexOf(result.length()));
                    }
                    //扫码登录
                    if (tapFrom == ApplicationDatas.TAP_FROM_LOGIN) {
                        if (netUtil.isNetworkConnected()) {
                            if (CardTypeUtils.isWorkerCard(results)) {
                                loginByQrCodePresenter.loginByQrCode(result);
                                scannerFragment.restart();
                            } else {

                                new CircleDialog.Builder(this)
                                        .setWidth((float) 0.8)
                                        .setTitle("提示")
                                        .setText("扫码失败")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();
                                scannerFragment.restart();
                            }
                        } else {
                            new CircleDialog.Builder(this)
                                    .setWidth((float) 0.8)
                                    .setTitle("提示")
                                    .setText("无网络")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                            scannerFragment.restart();
                        }
                        //扫码查询详情
                    } else if (tapFrom == ApplicationDatas.TAP_FROM_HOME_BUTTON) {
                        if (netUtil.isNetworkConnected()) {

                            if (CardTypeUtils.isGasLabelCard(results)) {
                                //气瓶标签
                                gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                                scannerFragment.restart();
                            } else if (CardTypeUtils.isCustomerCard(results)) {
                                //用户卡
                                customerMsgByCustomerIDPresenter.customerMsgByCustomerIDView(results);
                                scannerFragment.restart();
                            } else if (CardTypeUtils.isWorkerCard(results)) {
                                //从业人员卡
                                workerMsgByQrCodePresenter.workerMsgByQrCode(results);
                                scannerFragment.restart();
                            } else if (CardTypeUtils.isCarCard(results)) {
                                //车辆卡
                                carMsgByQrCodePresenter.carMsgByQrCode(results);
                                scannerFragment.restart();

                            } else if (CardTypeUtils.isOrderCard(results)) {
                                //订单号
                                orderMsgByOrderIDPresenter.orderMsgByOrderID(results);
                                scannerFragment.restart();
                            } else {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("查询信息请扫气瓶标签,用户卡,从业人员卡,车辆卡,订单二维码")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();

                            }

                        } else {

                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("扫码失败")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                        }
                    }
                    //配送任务交付回收
                    else if (tapFrom == ApplicationDatas.TAP_FROM_JF_AND_HS) {
                        //扫订单
                        if (netUtil.isNetworkConnected()) {
                            if (CardTypeUtils.isOrderCard(results)) {
                                transportByOrderQrCodePresenter.transportByOrderQrCode(results);
                                scannerFragment.restart();
                            }
                            //扫用户卡
                            else if (CardTypeUtils.isCustomerCard(results)) {
                                transportByCustomerCardNoPresenter.transportByCustomerCardNo(results);
                                scannerFragment.restart();
                            } else {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("配送任务交付回收请扫用户卡/订单二维码")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();

                            }
                        } else {

                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("无网络")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                        }
                    }
                    //气瓶建档
                    else if (tapFrom == ApplicationDatas.TAP_FROM_INPUTTING) {
                        if (netUtil.isNetworkConnected()) {
                            //扫气瓶
                            if (CardTypeUtils.isGasLabelCard(results)) {
                                setUpScanPersenter.orderMsgByQrCode(results);
                                scannerFragment.restart();

                            } else {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("气瓶建档请扫气瓶二维码")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();

                            }
                        } else {

                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("无网络")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                            scannerFragment.restart();
                        }
                    }
                    //气瓶建档修改气瓶标签
                    else if (tapFrom == ApplicationDatas.TAP_FROM_CHANGE_LABEL) {
                        if (CardTypeUtils.isGasLabelCard(results)) {
//                            gasLabeReplacePersent.gasLabeReplace(results);

                            gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                            scannerFragment.restart();
                        } else {
                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("修改气瓶标签请扫气瓶二维码")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();

                        }

                    }
                    //自提交付回收
                    else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JFHS) {
                        if (CardTypeUtils.isCustomerCard(results)) {
                            //扫用户卡
                            sinceByCustomerPresenter.sinceByCustomer(results);
                            scannerFragment.restart();

                        } else if (CardTypeUtils.isOrderCard(results)) {
                            //扫订单
                            sinceByOderPresenter.sinceByOder(results);
                            scannerFragment.restart();

                        } else {
                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("自提交付回收请扫用户卡/订单二维码")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();

                        }
                    } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK) {
                        if (CardTypeUtils.isCustomerCard(results)) {
                            checkByCustomercardNoPersenter.checkByCustomercardNo(results);
                            scannerFragment.restart();

                        } else if (CardTypeUtils.isOrderCard(results)) {
                            checkByOrderPresenter.checkByOrder(results);
                            scannerFragment.restart();
                        } else {
                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("入户检查请扫用户卡/订单二维码")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("扫码失败")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();

                }
            } else {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("无网络")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                            }
                        })
                        .show();
            }
        }
    }

    /**
     * 查询订单信息
     *
     * @param isSuccess
     * @param result
     * @param msg
     */

    @Override
    public void retOrderMsgByOrderIDView(boolean isSuccess, OrderInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    Intent intent = new Intent(this, OrderMsgActivity.class);
                    intent.putExtra(ApplicationDatas.QR_CODE, results);
                    mContext.startActivity(intent);
                    finish();
                } else {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业订单无法查看详细!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 查询客户信息
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retCustomerMsgByCustomerIDView(boolean isSuccess, CustomerInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    Intent intent = new Intent(this, CustomerMsgActivity.class);
                    intent.putExtra(ApplicationDatas.QR_CODE, results);
                    mContext.startActivity(intent);
                    finish();
                } else {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业用户无法查看详细!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    /***
     * 查询气瓶
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retGasMsgByQrCodeView(boolean isSuccess, GasInfo result, String msg) {
        if (isSuccess) {

            if (tapFrom == ApplicationDatas.TAP_FROM_CHANGE_LABEL) {
                if (result.getUseStatus().equals("0")) {
                    showToast("扫码成功");
                    Intent intent = new Intent();
                    intent.putExtra("label", results);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (result.getUseStatus().equals("1")) {

//                    new AlertDialog.Builder(this)
//                            .setMessage("该气瓶标签已建档!")
//                            .setNegativeButton("查看气瓶档案", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Intent intent = new Intent(ScanInfoActivity.this, ByInputtingEditActivity.class);
//                                    intent.putExtra("label", results);
//                                    mContext.startActivity(intent);
//                                    finish();
//                                }
//                            })
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    scannerFragment.restart();
//                                }
//                            })
//                            .show();

                    new CircleDialog.Builder(this)
                            .setCanceledOnTouchOutside(false)
                            .setCancelable(false)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已建档!")
                            .setNegative("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .setPositive("查看气瓶档案", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ScanInfoActivity.this, ByInputtingEditActivity.class);
                                    intent.putExtra("label", results);
                                    mContext.startActivity(intent);
                                    finish();
                                }
                            })
                            .show();

                } else if (result.getUseStatus().equals("2")) {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("未导入该气瓶标签,无法更换!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                } else if (result.getUseStatus().equals("3")) {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已作废!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }else if (result.getUseStatus().equalsIgnoreCase("4")){

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已过期!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }

                else {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("更改气瓶标签只能扫气瓶二维码!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            } else if (result != null) {
                if (result.getUseStatus().equalsIgnoreCase("0")){
                Intent intent = new Intent(this, GasMsgActivity.class);
                intent.putExtra(ApplicationDatas.QR_CODE, results);
                mContext.startActivity(intent);
                finish();}else if (result.getUseStatus().equals("1")) {

                    new CircleDialog.Builder(this)
                            .setCanceledOnTouchOutside(false)
                            .setCancelable(false)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已建档!")
                            .setNegative("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .setPositive("查看气瓶档案", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ScanInfoActivity.this, ByInputtingEditActivity.class);
                                    intent.putExtra("label", results);
                                    mContext.startActivity(intent);
                                    finish();
                                }
                            })
                            .show();


                } else if (result.getUseStatus().equals("2")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("未导入该气瓶标签，无法更换!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();

                } else if (result.getUseStatus().equals("3")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已作废!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }else if (result.getUseStatus().equalsIgnoreCase("4")){
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已过期!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
            scannerFragment.restart();
        }


    }

    /**
     * 扫码登录
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retLoginByQrCodeView(boolean isSuccess, UserInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {


                if (result.getUseStatus().equals("0")) {
                    Intent intent = new Intent();
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_QR_CODE, results);
                    userQrCode = results;
                    intent.putExtra(SharedPreferencesUtils.SHARED_USER_QR_CODE, userQrCode);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (result.getUseStatus().equals("1")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("从业人员使用状态错误,禁止登录")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                } else if (result.getUseStatus().equals("2")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("登录人员已被停用请联系管理员")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }


        } else {
            showToast(msg);
        }
    }

    /**
     * 配送任务扫用户卡
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retTransportByCustomerCardNoView(boolean isSuccess, CustomerCardNoTaskInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    if (result.getOrderStatus().equals("0")) {
                        Intent intent = new Intent(this, CustomerJfHsMsgActivity.class);
                        intent.putExtra("customerCardNo", results);
                        mContext.startActivity(intent);
                        finish();
                    } else if (result.getOrderStatus().equals("1")) {


                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该用户无与本人关联的配送任务，无法交付回收!")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    }
                } else {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业用户,无法交付/回收!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 配送任务扫订单卡
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retOrderMsgByQrCodeView(boolean isSuccess, OrderTaskInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {

                    if (enterpriseID.equals(result.getEnterpriseID())) {

                        if (result.getOrderStatus().equals("0")) {
                            Intent intent = new Intent(this, OrderJfHsMsgActivity.class);
                            intent.putExtra("orderQRcode", results);
                            mContext.startActivity(intent);
                            finish();
                        } else if (result.getOrderStatus().equals("1")) {
                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("没有分配给本人的配送任务,无法交付回收!")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                        } else if (result.getOrderStatus().equals("2")) {
                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("该订单为自提订单,无法交付回收!")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                        }

                    } else {
                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("非本订单,不允许交付/回收!")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    }

                } else {
                    showToast(msg);

                }
            }
        }
    }

    /**
     * 查询从业人员信息
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retWorkerMsgByQrCodeView(boolean isSuccess, WorkerInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    Intent intent = new Intent(this, WorkerMsgActivity.class);
                    intent.putExtra(ApplicationDatas.QR_CODE, results);
                    mContext.startActivity(intent);
                    finish();
                } else {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("不是本企业从业人员,无法查看信息!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 查询车辆信息
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retCarMsgByQrCodeView(boolean isSuccess, CarInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    Intent intent = new Intent(this, CarMsgActivity.class);
                    intent.putExtra(ApplicationDatas.QR_CODE, results);
                    mContext.startActivity(intent);
                    finish();
                } else {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("不是本企业车辆,无法查看信息!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

//    /**
//     * 气瓶建档修改气瓶标签
//     *
//     * @param isSuccess
//     * @param result
//     * @param msg
//     */
//    @Override
//    public void retGasLabeReplaceView(boolean isSuccess, GasLabeReplaceInfo result, final String msg) {
//        if (isSuccess) {
//            if (result != null) {
//                if (result.getGasState().equals("0")) {
//                    showToast("扫码成功");
//                    Intent intent = new Intent();
//                    intent.putExtra("label", results);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                } else if (result.getGasState().equals("1")) {
//                    new AlertDialog.Builder(this)
//                            .setMessage("该气瓶标签已建档!")
//                            .setNegativeButton("查看气瓶档案", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Intent intent = new Intent(ScanInfoActivity.this, ByInputtingEditActivity.class);
//                                    intent.putExtra("label", results);
//                                    mContext.startActivity(intent);
//                                    finish();
//                                }
//                            })
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    scannerFragment.restart();
//                                }
//                            })
//                            .show();
//
//                } else if (result.getGasState().equals("2")) {
//                    new AlertDialog.Builder(this)
//                            .setMessage("未导入该气瓶标签，无法更换!")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    scannerFragment.restart();
//                                }
//                            })
//                            .show();
//
//                } else if (result.getGasState().equals("3")) {
//                    new AlertDialog.Builder(this)
//                            .setMessage("该气瓶标签已作废!")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    scannerFragment.restart();
//                                }
//                            })
//                            .show();
//                } else {
//                    new AlertDialog.Builder(this)
//                            .setMessage("更改气瓶标签只能扫气瓶二维码!")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    scannerFragment.restart();
//                                }
//                            })
//                            .show();
//                }
//            }
//        } else {
//            showToast(msg);
//            scannerFragment.restart();
//        }
//    }

    /**
     * 自提用户卡
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retSinceByCustomerView(boolean isSuccess, SinceByCustomerInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {

                    Intent intent = new Intent(this, SinceCustomerActivity.class);
                    intent.putExtra("customerCardNo", result.getCustomerCardNo());
                    mContext.startActivity(intent);
                    finish();
                }
            } else {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("非本企业用户,无法交付回收!")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                            }
                        })
                        .show();
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 自提订单号
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retSinceByOderView(boolean isSuccess, SinceByOrderInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    if (result.getType().equals("0")) {
                        if (result.getState().equals("1")) {
                            Intent intent = new Intent(this, SinceNotStateOrderActivity.class);
                            intent.putExtra("orderID", result.getOrderID());
                            mContext.startActivity(intent);
                            finish();
                        } else if (result.getState().equals("0")) {
                            Intent intent = new Intent(this, SinceYesStateOrderActivity.class);
                            intent.putExtra("orderID", result.getOrderID());
                            mContext.startActivity(intent);
                            finish();
                        }
                    } else if (result.getType().equalsIgnoreCase("1")) {
                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该订单为配送订单,无法交付回收!")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    }
                } else {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业订单,无法交付回收!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            } else {
                showToast(msg);
            }
        }
    }


    /**
     * 打开相机出错
     */
    private void openCameraErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_camera_error, null);
        final CameraErrorDialog.Builder builder = new CameraErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        CameraErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    /***
     * 扫码失败
     */
    private void sanErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_scan_error, null);
        final ScanErrorDialog.Builder builder = new ScanErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ScanErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    /**
     * 无网络
     */
    private void netErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_net_error, null);
        final NetErrorDialog.Builder builder = new NetErrorDialog.Builder(mContext);
        builder.setNegativeButton("重试", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        NetErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    /**
     * 入户安全检查 扫用户卡
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retCheckByCustomercardNoView(boolean isSuccess, CheckByCustomercardNoInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    Intent intent = new Intent(mContext, CheckActivity.class);
                    intent.putExtra("customerCardNo", result.getCustomerCardNo());
                    mContext.startActivity(intent);
                    finish();
                } else {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业用户,无法检查!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 入户安全检查  扫订单号
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retCheckByOrderView(boolean isSuccess, CheckByOrderInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    Intent intent = new Intent(mContext, CheckActivity.class);
                    intent.putExtra("orderID", result.getOrderID());
                    mContext.startActivity(intent);
                    finish();
                } else {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业订单,无法检查!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    @Override
    public void retSetUpScanView(boolean isSuccess, SetUpLabelInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getUseStatus().equals("0")) {
                    Intent intent = new Intent();
                    intent.putExtra("label", results);
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (result.getUseStatus().equals("1")) {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶标签已作废!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                } else if (result.getUseStatus().equals("2")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶已建档!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                } else if (result.getUseStatus().equals("3")) {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶未导入!")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    scannerFragment.restart();
                                }
                            })
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }
}
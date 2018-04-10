package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinawidth.zzmandroid.decode.OnDecodeCompletionListener;
import com.chinawidth.zzmandroid.ui.CodeUtils;
import com.chinawidth.zzmandroid.ui.ScannerFragment;
import com.future.zhh.ticket.R;
import com.future.zhh.ticket.data.net.NetUtil;
import com.future.zhh.ticket.domain.model.GasCheckScanLogInfo;
import com.future.zhh.ticket.domain.model.GasHsLogInfo;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.GasJfLogInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerScanGasInfoActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ScanGasInfoActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.presenters.GasMsgByQrCodePresenters;
import com.future.zhh.ticket.presentation.presenters.SinceOfPayPresenter;
import com.future.zhh.ticket.presentation.presenters.SinceOfReclaimPersenter;
import com.future.zhh.ticket.presentation.presenters.TransportOfPayPresenter;
import com.future.zhh.ticket.presentation.presenters.TransportOfReclaimPresenter;
import com.future.zhh.ticket.presentation.utils.AESUtil;
import com.future.zhh.ticket.presentation.utils.CardTypeUtils;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.SinceOfPayView;
import com.future.zhh.ticket.presentation.view.SinceOfReclaimView;
import com.future.zhh.ticket.presentation.view.TransportOfPayView;
import com.future.zhh.ticket.presentation.view.TransportOfReclaimView;
import com.future.zhh.ticket.presentation.view.adapters.MyViewPageAdapter;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.widgets.ActivityIndicatorView;
import com.future.zhh.ticket.presentation.view.widgets.CameraErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.NetErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.ScanErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/27.
 * 扫气瓶码交付回收功能页面
 */

public class ScanGasInfoActivity extends BaseActivity implements GasMsgByQrCodeView, Serializable, TransportOfPayView, TransportOfReclaimView, OnDecodeCompletionListener, SinceOfPayView, SinceOfReclaimView {
    public final static String TAG = ScanGasInfoActivity.class.getSimpleName();
    @BindView(R.id.img_ibtnBack)
    ImageView imgIbtnBack;
    @BindView(R.id.ibtnBack)
    LinearLayout ibtnBack;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.rbLvRU)
    RadioButton rbLvRU;
    @BindView(R.id.rbDelete)
    RadioButton rbDelete;
    @BindView(R.id.rgMode)
    RadioGroup rgMode;
    @BindView(R.id.textTip)
    TextView textTip;
    @BindView(R.id.cbFlashLight)
    CheckBox cbFlashLight;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ivDot1)
    ImageView ivDot1;
    @BindView(R.id.ivDot2)
    ImageView ivDot2;
    @BindView(R.id.llDot)
    LinearLayout llDot;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.llTotal)
    LinearLayout llTotal;
    @BindView(R.id.llbottom)
    LinearLayout llbottom;
    @BindView(R.id.rlViewPage)
    RelativeLayout rlViewPage;
    @BindView(R.id.tv_jf)
    TextView tvJf;
    @BindView(R.id.tv_hs)
    TextView tvHs;
    @BindView(R.id.ibtnSetting)
    ImageButton ibtnSetting;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    GasJfLogInfo gasJfLogInfo = new GasJfLogInfo();
    GasHsLogInfo gasHsLogInfo = new GasHsLogInfo();
    GasCheckScanLogInfo gasCheckScanLogInfo = new GasCheckScanLogInfo();
    int scrappingGasTotal;
    int outTimeGasTotal;
    @Inject
    TransportOfPayPresenter transportOfPayPresenter;
    @Inject
    TransportOfReclaimPresenter transportOfReclaimPresenter;
    @Inject
    SinceOfPayPresenter sinceOfPayPresenter;
    @Inject
    SinceOfReclaimPersenter sinceOfReclaimPersenter;
    @Inject
    GasMsgByQrCodePresenters gasMsgByQrCodePresenters;
    @BindView(R.id.tv_Tittle)
    TextView tvTittle;
    private CommonLog logger = LogFactory.createLog(TAG);
    private ScanGasInfoActivityComponent component;
    private Context mContext;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private ActivityIndicatorView activityIndicatorView;
    private NetUtil netUtil;
    private MyViewPageAdapter myViewPageAdapter;
    private View scanFirstView;
    private TextView gasLabel;
    private TextView gasModule;
    private int tapFrom;
    private String[] tabTitles;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;
    private List<GasJfLogInfo> gasJfLogInfoList = new ArrayList<>();
    private List<GasHsLogInfo> gasHsLogInfoList = new ArrayList<>();
    private List<GasJfLogInfo> gasSinceJfLogInfoList = new ArrayList<>();
    private List<GasHsLogInfo> gasSinceHsLogInfoList = new ArrayList<>();
    private List<GasCheckScanLogInfo> gasCheckScanLogInfoList = new ArrayList<>();
    private List<String> gasJfDatalist = new ArrayList<>();
    private List<String> gasHsDatalist = new ArrayList<>();
    private List<String> gasSinceJfDatalist = new ArrayList<>();
    private List<String> gasSinceHsDatalist = new ArrayList<>();
    private List<String> gasCheckScanDataList = new ArrayList<>();
    //配送
    private Boolean JfBoolean = false;
    private Boolean HsBoolean = false;
    private Boolean JfHsBoolean = false;
    private Boolean HsJfBoolean = false;
    //自提
    private Boolean SinceJfBoolean = false;
    private Boolean SinceHsBoolean = false;
    private Boolean SinceJfHsBoolean = false;
    private Boolean SinceHsJfBoolean = false;
    private String id;
    private String transportTaskID;
    private String gasData;
    private String enterpriseID = "";
    private String orderID = "";
    private String results;
    private ScannerFragment scannerFragment;
    private String clearTip;

    //入户检查扫气瓶
    public final static int REQUEST_CHECK_LABEL = 4;

    public static Intent getCallingIntent(Context context, int tapFrom) {
        Intent intent = new Intent(context, ScanGasInfoActivity.class);
        intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerScanGasInfoActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_gas_info);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initZcode();
        initView();
    }

    /**
     * 初始化真知码
     */
    private void initZcode() {
        scannerFragment = new ScannerFragment();
        CodeUtils.setFragmentArgs(scannerFragment, R.layout.scan_cylinder_view_two);
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

    private void init() {
        activityIndicatorView = new ActivityIndicatorView(mContext);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        netUtil = new NetUtil(mContext);
        if (getIntent() != null) {
            tapFrom = getIntent().getIntExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_HOME_BUTTON);
        }
        id = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ID);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
        gasMsgByQrCodePresenters.setView(this);
        transportOfPayPresenter.setView(this);
        transportOfReclaimPresenter.setView(this);
        sinceOfPayPresenter.setView(this);
        sinceOfReclaimPersenter.setView(this);
    }

    private void initView() {
        ivDot1.setVisibility(View.GONE);
        ivDot2.setVisibility(View.GONE);
        if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
            textTip.setText("请将气瓶二维码放置于扫描框中");
            tvHs.setAlpha(0.5f);
            tvJf.setAlpha(1f);
            tvSubmit.setText("确认配送交付完成");
            tvClear.setText("清空交付");
            //配送任务ID
            if (getIntent() != null) {
                transportTaskID = getIntent().getStringExtra("transportTaskID");

            }
        } else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
            textTip.setText("请将气瓶二维码放置于扫描框中");
            tvJf.setAlpha(0.5f);
            tvHs.setAlpha(1f);
            Log.e("jf``````", tapFrom + "");
            tvSubmit.setText("确认配送回收完成");
            tvClear.setText("清空回收");
            if (getIntent() != null) {
                //配送任务ID
                transportTaskID = getIntent().getStringExtra("transportTaskID");

            }
        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {

            textTip.setText("请将气瓶二维码放置于扫描框中");
            tvHs.setAlpha(0.5f);
            tvJf.setAlpha(1f);
            Log.e("hs``````", tapFrom + "");
            tvSubmit.setText("确认自提交付完成");
            tvClear.setText("清空交付");

            if (getIntent() != null) {
                //自提ID
                orderID = getIntent().getStringExtra("orderID");
            }
        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
            textTip.setText("请将气瓶二维码放置于扫描框中");
            tvJf.setAlpha(0.5f);
            tvHs.setAlpha(1f);
            tvSubmit.setText("确认自提回收完成");
            tvClear.setText("清空回收");
            if (getIntent() != null) {
                //自提ID
                orderID = getIntent().getStringExtra("orderID");
            }
        } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {

            textTip.setText("请将气瓶二维码放置于扫描框中");
            tvTittle.setVisibility(View.VISIBLE);
            tvJf.setVisibility(View.GONE);
            tvHs.setVisibility(View.GONE);
            tvSubmit.setText("确认入户检查完成");
            tvClear.setText("清空检查");
        }

        scanFirstView = View.inflate(this, R.layout.scan_first_view, null);

        gasLabel = scanFirstView.findViewById(R.id.tv_gasLabel);
        gasModule = scanFirstView.findViewById(R.id.tv_gasModule);

        List<View> viewList = new ArrayList<>();
        viewList.add(scanFirstView);
        myViewPageAdapter = new MyViewPageAdapter(viewList);
        viewPager.setAdapter(myViewPageAdapter);

    }


    @OnClick({R.id.ibtnBack, R.id.tv_submit, R.id.tv_clear, R.id.ibtnSetting, R.id.tv_jf, R.id.tv_hs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.tv_submit:
                if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
                    //配送交付
                    if (gasHsDatalist.isEmpty() && gasJfDatalist.isEmpty()) {
                        new CircleDialog.Builder(this)
                                .setWidth((float) 0.8)
                                .setTitle("提示信息")
                                .setText("没有录入气瓶")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    } else if (gasHsDatalist.isEmpty()) {
                        if (!gasJfDatalist.isEmpty()) {
                            JfBoolean = true;
                            transportOfPayPresenter.transportOfPay(transportTaskID, id, new Gson().toJson(gasJfDatalist), gasJfDatalist.size());
                        }
                    } else if (!gasHsDatalist.isEmpty()) {
                        if (!gasJfDatalist.isEmpty()) {
                            JfHsBoolean = true;
                            transportOfPayPresenter.transportOfPay(transportTaskID, id, new Gson().toJson(gasJfDatalist), gasJfDatalist.size());
                            transportOfReclaimPresenter.transportOfReclaim(transportTaskID, id, new Gson().toJson(gasHsDatalist), gasHsDatalist.size());
                        } else if (gasJfDatalist.isEmpty()) {
                            HsBoolean = true;
                            transportOfReclaimPresenter.transportOfReclaim(transportTaskID, id, new Gson().toJson(gasHsDatalist), gasHsDatalist.size());
                        }
                    }


                }
                //配送回收
                else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                    if (gasHsDatalist.isEmpty() && gasJfDatalist.isEmpty()) {
                        new CircleDialog.Builder(this)
                                .setWidth((float) 0.8)
                                .setTitle("提示信息")
                                .setText("没有录入气瓶")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    } else if (gasJfDatalist.isEmpty()) {
                        if (!gasHsDatalist.isEmpty()) {
                            HsBoolean = true;
                            transportOfReclaimPresenter.transportOfReclaim(transportTaskID, id, new Gson().toJson(gasHsDatalist), gasHsDatalist.size());
                        }
                    } else if (!gasJfDatalist.isEmpty()) {
                        if (!gasHsDatalist.isEmpty()) {
                            HsJfBoolean = true;
                            transportOfReclaimPresenter.transportOfReclaim(transportTaskID, id, new Gson().toJson(gasHsDatalist), gasHsDatalist.size());
                            transportOfPayPresenter.transportOfPay(transportTaskID, id, new Gson().toJson(gasJfDatalist), gasJfDatalist.size());
                        } else if (gasHsDatalist.isEmpty()) {
                            JfBoolean = true;
                            transportOfPayPresenter.transportOfPay(transportTaskID, id, new Gson().toJson(gasJfDatalist), gasJfDatalist.size());
                        }

                    }
                }
                //自提交付
                else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                    if (gasSinceJfDatalist.isEmpty() && gasSinceHsDatalist.isEmpty()) {
                        new CircleDialog.Builder(this)
                                .setWidth((float) 0.8)
                                .setTitle("提示信息")
                                .setText("没有录入气瓶")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    } else if (gasSinceHsDatalist.isEmpty()) {
                        if (!gasSinceJfDatalist.isEmpty()) {
                            SinceJfBoolean = true;
                            sinceOfPayPresenter.sinceOfPay(new Gson().toJson(gasSinceJfDatalist), orderID, id);
                        }
                    } else if (!gasSinceHsDatalist.isEmpty()) {
                        if (!gasSinceJfDatalist.isEmpty()) {
                            SinceJfHsBoolean = true;
                            sinceOfPayPresenter.sinceOfPay(new Gson().toJson(gasSinceJfDatalist), orderID, id);
                            sinceOfReclaimPersenter.sinceOfReclaim(new Gson().toJson(gasSinceHsDatalist), orderID, id);
                        } else if (gasSinceJfDatalist.isEmpty()) {
                            SinceHsBoolean = true;
                            sinceOfReclaimPersenter.sinceOfReclaim(new Gson().toJson(gasSinceHsDatalist), orderID, id);
                        }
                    }

                }
                //自提回收
                else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                    if (gasSinceJfDatalist.isEmpty() && gasSinceHsDatalist.isEmpty()) {
                        new CircleDialog.Builder(this)
                                .setWidth((float) 0.8)
                                .setTitle("提示信息")
                                .setText("没有录入气瓶")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
                    } else if (gasSinceJfDatalist.isEmpty()) {
                        if (!gasSinceHsDatalist.isEmpty()) {
                            SinceHsBoolean = true;
                            sinceOfReclaimPersenter.sinceOfReclaim(new Gson().toJson(gasSinceHsDatalist), orderID, id);
                        }
                    } else if (!gasSinceJfDatalist.isEmpty()) {
                        if (!gasSinceHsDatalist.isEmpty()) {
                            SinceHsJfBoolean = true;
                            sinceOfReclaimPersenter.sinceOfReclaim(new Gson().toJson(gasSinceHsDatalist), orderID, id);
                            sinceOfPayPresenter.sinceOfPay(new Gson().toJson(gasSinceJfDatalist), orderID, id);
                        } else if (gasSinceHsDatalist.isEmpty()) {
                            SinceJfBoolean = true;
                            sinceOfPayPresenter.sinceOfPay(new Gson().toJson(gasSinceJfDatalist), orderID, id);
                        }
                    }
                }
                //入户检查扫描气瓶
                else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
                    Intent intent = new Intent();
                    intent.putExtra("scrappingGasTotal", scrappingGasTotal + "");
                    intent.putExtra("outTimeGasTotal", outTimeGasTotal + "");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.tv_clear:
            if (tapFrom==ApplicationDatas.TAP_FROM_JF){
                clearTip="清空配送交付扫瓶记录";
            }else if (tapFrom==ApplicationDatas.TAP_FROM_HS){
                clearTip="清空配送回收扫瓶记录";
            }else if (tapFrom==ApplicationDatas.TAP_FROM_SINCE_JF){
                clearTip="清空自提交付扫瓶记录";
            }else  if (tapFrom==ApplicationDatas.TAP_FROM_SINCE_HS){
                clearTip="清空自提回收扫瓶记录";
            }else if (tapFrom==ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS){
                clearTip="清空入户检测扫瓶记录";
            }

                new CircleDialog.Builder(this)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setWidth((float) 0.8)
                        .setTitle("提示信息")
                        .setText(clearTip)
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                            }
                        })
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //显示
                                tvTotal.setText("0");
                                gasLabel.setText("");
                                gasModule.setText("");

                                //配送任务
                                if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
                                    if (gasJfDatalist != null) {
                                        gasJfDatalist.clear();
                                    }
                                    if (gasJfLogInfoList != null) {
                                        gasJfLogInfoList.clear();
                                    }
                                }
                                if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                                    if (gasHsDatalist != null) {
                                        gasHsDatalist.clear();
                                    }

                                    if (gasHsLogInfoList != null) {
                                        gasHsLogInfoList.clear();
                                    }
                                }

                                //自提
                                if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                                    if (gasSinceJfDatalist != null) {
                                        gasSinceJfDatalist.clear();
                                    }
                                    if (gasSinceJfLogInfoList != null) {
                                        gasSinceJfLogInfoList.clear();
                                    }
                                }
                                if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                                    if (gasSinceHsDatalist != null) {
                                        gasSinceHsDatalist.clear();
                                    }
                                    if (gasSinceHsLogInfoList != null) {
                                        gasSinceHsLogInfoList.clear();
                                    }
                                }


                                if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
                                    //入户检测
                                    if (gasCheckScanDataList != null) {
                                        gasCheckScanDataList.clear();
                                    }
                                    if (gasCheckScanLogInfoList != null) {
                                        gasCheckScanLogInfoList.clear();
                                    }
                                }
                                scannerFragment.restart();
                            }
                        })
                        .show();



                break;
            case R.id.ibtnSetting:
                //配送任务交付扫瓶记录
                if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
//                    navigator.navigateToJFHSGasScanLogActivity(this, ApplicationDatas.TAP_FROM_JF);
                    Intent intent = new Intent(this, JFHSGasScanLogActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
                    intent.putExtra("gasJfLogInfoList", (Serializable) (gasJfLogInfoList));
                    intent.putExtra("ToolbarTitle", "配送交付扫瓶记录");
                    this.startActivity(intent);

                }
                //配送任务回收扫瓶记录
                else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                    Intent intent = new Intent(this, JFHSGasScanLogActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
                    intent.putExtra("gasHsLogInfoList", (Serializable) (gasHsLogInfoList));
                    intent.putExtra("ToolbarTitle", "配送回收扫瓶记录");
                    this.startActivity(intent);
                }
                //自提交付扫瓶记录
                else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                    Intent intent = new Intent(this, JFHSGasScanLogActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
                    intent.putExtra("gasSinceJfLogInfoList", (Serializable) (gasSinceJfLogInfoList));
                    intent.putExtra("ToolbarTitle", "自提交付扫瓶记录");
                    this.startActivity(intent);
                }
                //自提回收扫瓶记录
                else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                    Intent intent = new Intent(this, JFHSGasScanLogActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
                    intent.putExtra("gasSinceHsLogInfoList", (Serializable) (gasSinceHsLogInfoList));
                    intent.putExtra("ToolbarTitle", "自提回收扫瓶记录");
                    this.startActivity(intent);
                }
                //入户检查扫瓶记录
                else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
                    Intent intent = new Intent(this, JFHSGasScanLogActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
                    intent.putExtra("gasCheckScanLogInfoList", (Serializable) (gasCheckScanLogInfoList));
                    intent.putExtra("ToolbarTitle", "入户检查扫瓶记录");
                    mContext.startActivity(intent);
                }
                break;
            case R.id.tv_jf:
                //切换模式

//                if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
////                    tvToolbarTitle.setText("扫码回收");
//                    tvHs.setAlpha(0.5f);
//                    tvJf.setAlpha(1f);
//                    tvSubmit.setText("确认交付完成");
//                } else
                if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                    tapFrom = ApplicationDatas.TAP_FROM_JF;
//                    tvToolbarTitle.setText("扫码交付");
                    tvJf.setAlpha(1f);
                    tvHs.setAlpha(0.5f);
                    tvSubmit.setText("确认配送交付完成");
                    tvClear.setText("清空交付");
                    gasLabel.setText("");
                    gasModule.setText("");
                    if (gasJfDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasJfDatalist.size()));
                    } else if (gasSinceJfDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasSinceJfDatalist.size()));
                    } else {
                        tvTotal.setText("0");
                    }
                } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                    tapFrom = ApplicationDatas.TAP_FROM_SINCE_JF;
//                    tvToolbarTitle.setText("扫码交付");
                    tvJf.setAlpha(1f);
                    tvHs.setAlpha(0.5f);
                    tvSubmit.setText("确认自提交付完成");
                    tvClear.setText("清空交付");
                    gasLabel.setText("");
                    gasModule.setText("");
                    if (gasJfDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasJfDatalist.size()));
                    } else if (gasSinceJfDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasSinceJfDatalist.size()));
                    } else {
                        tvTotal.setText("0");
                    }

                }

                break;
            case R.id.tv_hs:
                //切换模式
//                if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
////                    tvToolbarTitle.setText("扫码交付");
//                    tvJf.setAlpha(0.5f);
//                    tvHs.setAlpha(1f);
//                    tvSubmit.setText("确认回收完成");
//                } else


                if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
                    tapFrom = ApplicationDatas.TAP_FROM_HS;
//                    tvToolbarTitle.setText("扫码回收");
                    tvHs.setAlpha(1f);
                    tvJf.setAlpha(0.5f);
                    tvSubmit.setText("确认配送回收完成");
                    tvClear.setText("清空回收");
                    gasLabel.setText("");
                    gasModule.setText("");
                    if (gasHsDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasHsDatalist.size()));
                    } else if (gasSinceHsDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasSinceHsDatalist.size()));
                    } else {
                        tvTotal.setText("0");
                    }
                } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                    tapFrom = ApplicationDatas.TAP_FROM_SINCE_HS;
                    //                    tvToolbarTitle.setText("扫码回收");
                    tvHs.setAlpha(1f);
                    tvJf.setAlpha(0.5f);
                    tvSubmit.setText("确认自提回收完成");
                    tvClear.setText("清空回收");
                    gasLabel.setText("");
                    gasModule.setText("");
                    if (gasHsDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasHsDatalist.size()));
                    } else if (gasSinceHsDatalist.size() > 0) {
                        tvTotal.setText(String.valueOf(gasSinceHsDatalist.size()));
                    } else {
                        tvTotal.setText("0");
                    }
                }


                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

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
                String resultCode = result.substring(0, 1);
                results = result.substring(1, result.length());
                if (!resultCode.equalsIgnoreCase("3"))
                    results = AESUtil.decryptWithBase64(result);
                try {
                    if (CardTypeUtils.isGasLabelCard(results)) {
                        if (rgMode.getCheckedRadioButtonId() == R.id.rbLvRU) {
                            if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
                                if (!gasJfDatalist.contains(gasData)) {
                                    gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                                    scannerFragment.restart();
                                } else {

                                    new CircleDialog.Builder(this)
                                            .setWidth((float) 0.8)
                                            .setTitle("提示")
                                            .setText("该配送交付气瓶已录入")
                                            .setPositive("确定", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    scannerFragment.restart();
                                                }
                                            })
                                            .show();
                                }

                            } else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                                if (!gasHsDatalist.contains(gasData)) {
                                    gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                                    scannerFragment.restart();
                                } else {

                                    new CircleDialog.Builder(this)
                                            .setWidth((float) 0.8)
                                            .setTitle("提示")
                                            .setText("该配送回收气瓶已录入")
                                            .setPositive("确定", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    scannerFragment.restart();
                                                }
                                            })
                                            .show();
                                }
                            } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                                if (!gasSinceJfDatalist.contains(gasData)) {
                                    gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                                    scannerFragment.restart();
                                } else {


                                    new CircleDialog.Builder(this)
                                            .setWidth((float) 0.8)
                                            .setTitle("提示")
                                            .setText("该自提交付气瓶已录入")
                                            .setPositive("确定", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    scannerFragment.restart();
                                                }
                                            })
                                            .show();
                                }

                            } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                                if (!gasSinceHsDatalist.contains(gasData)) {
                                    gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                                    scannerFragment.restart();
                                } else {

                                    new CircleDialog.Builder(this)
                                            .setWidth((float) 0.8)
                                            .setTitle("提示")
                                            .setText("该自提回收气瓶已录入")
                                            .setPositive("确定", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    scannerFragment.restart();
                                                }
                                            })
                                            .show();
                                }
                            } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
                                if (!gasCheckScanDataList.contains(gasData)) {
                                    gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                                    scannerFragment.restart();
                                } else {

                                    new CircleDialog.Builder(this)
                                            .setWidth((float) 0.8)
                                            .setTitle("提示")
                                            .setText("该入户检查气瓶已录入")
                                            .setPositive("确定", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    scannerFragment.restart();
                                                }
                                            })
                                            .show();
                                }
                            }

                        } else if (rgMode.getCheckedRadioButtonId() == R.id.rbDelete) {
                            gasMsgByQrCodePresenters.gasMsgByQrCode(results);
                            scannerFragment.restart();
                        }
                    } else {
                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("交付回收只能扫气瓶二维码")
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        scannerFragment.restart();
                                    }
                                })
                                .show();
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
                scannerFragment.restart();
            }
        }
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
     * 交付回收扫气瓶
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retGasMsgByQrCodeView(boolean isSuccess, GasInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    if (rgMode.getCheckedRadioButtonId() == R.id.rbLvRU) {
                        if (result.getUseStatus().equals("0")) {
                            if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
                                gasLabel.setText(result.getGasLabe());
                                gasModule.setText(result.getGasModule());
                                gasData = result.getGasLabe() + "+" + result.getGasModule();
                                gasJfDatalist.add(gasData);
                                tvTotal.setText(String.valueOf(gasJfDatalist.size()));
                                gasJfLogInfo.setLabel(result.getGasLabe());
                                gasJfLogInfo.setType(result.getGasModule());
                                gasJfLogInfoList.add(gasJfLogInfo);
                                showToast("扫描成功");
                                scannerFragment.restart();
                            } else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                                gasLabel.setText(result.getGasLabe());
                                gasModule.setText(result.getGasModule());
                                gasData = result.getGasLabe() + "+" + result.getGasModule();
                                gasHsDatalist.add(gasData);
                                tvTotal.setText(String.valueOf(gasHsDatalist.size()));
                                gasHsLogInfo.setLabel(result.getGasLabe());
                                gasHsLogInfo.setType(result.getGasModule());
                                gasHsLogInfoList.add(gasHsLogInfo);
                                showToast("扫描成功");
                                scannerFragment.restart();
                            } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                                gasLabel.setText(result.getGasLabe());
                                gasModule.setText(result.getGasModule());
                                gasData = result.getGasLabe() + "+" + result.getGasModule();
                                gasSinceJfDatalist.add(gasData);
                                tvTotal.setText(String.valueOf(gasSinceJfDatalist.size()));
                                gasJfLogInfo.setLabel(result.getGasLabe());
                                gasJfLogInfo.setType(result.getGasModule());
                                gasSinceJfLogInfoList.add(gasJfLogInfo);
                                showToast("扫描成功");
                                scannerFragment.restart();
                            } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                                gasLabel.setText(result.getGasLabe());
                                gasModule.setText(result.getGasModule());
                                gasData = result.getGasLabe() + "+" + result.getGasModule();
                                gasSinceHsDatalist.add(gasData);
                                tvTotal.setText(String.valueOf(gasSinceHsDatalist.size()));
                                gasHsLogInfo.setLabel(result.getGasLabe());
                                gasHsLogInfo.setType(result.getGasModule());
                                gasSinceHsLogInfoList.add(gasHsLogInfo);
                                showToast("扫描成功");
                                scannerFragment.restart();
                            } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
                                gasLabel.setText(result.getGasLabe());
                                gasModule.setText(result.getGasModule());
                                gasData = result.getGasLabe() + "+" + result.getGasModule();
                                gasCheckScanDataList.add(gasData);
                                tvTotal.setText(String.valueOf(gasCheckScanDataList.size()));
                                gasCheckScanLogInfo.setLabel(result.getGasLabe());
                                gasCheckScanLogInfo.setType(result.getGasModule());
                                gasCheckScanLogInfoList.add(gasCheckScanLogInfo);
                                showToast("扫描成功");
                                scannerFragment.restart();
                            }

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
                            scrappingGasTotal++;
                        } else if (result.getUseStatus().equals("2")) {

                            new CircleDialog.Builder(this)
                                    .setTitle("提示")
                                    .setWidth((float) 0.8)
                                    .setText("该气瓶标签未建档!")
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
                                    .setText("该气瓶标签未导入!")
                                    .setPositive("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            scannerFragment.restart();
                                        }
                                    })
                                    .show();
                        } else if (result.getUseStatus().equals("4")) {
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
                            outTimeGasTotal++;
                        }
                    } else if (rgMode.getCheckedRadioButtonId() == R.id.rbDelete) {
                        if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
                            gasLabel.setText(result.getGasLabe());
                            gasModule.setText(result.getGasModule());
                            gasData = result.getGasLabe() + "+" + result.getGasModule();
                            if (gasJfDatalist.contains(gasData)) {
                                gasJfDatalist.remove(gasData);
                                tvTotal.setText(String.valueOf(gasJfDatalist.size()));
                                gasJfLogInfo.setLabel(result.getGasLabe());
                                gasJfLogInfo.setType(result.getGasModule());
                                gasJfLogInfoList.remove(gasJfLogInfo);
                                showToast("剔除成功");
                                scannerFragment.restart();
                            } else if (!gasJfDatalist.contains(gasData)) {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("未录入该气瓶!")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();
                            }
                        } else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
                            gasLabel.setText(result.getGasLabe());
                            gasModule.setText(result.getGasModule());
                            gasData = result.getGasLabe() + "+" + result.getGasModule();
                            if (gasHsDatalist.contains(gasData)) {
                                gasHsDatalist.remove(gasData);
                                tvTotal.setText(String.valueOf(gasHsDatalist.size()));
                                gasHsLogInfo.setLabel(result.getGasLabe());
                                gasHsLogInfo.setType(result.getGasModule());
                                gasHsLogInfoList.remove(gasHsLogInfo);
                                showToast("剔除成功");
                                scannerFragment.restart();
                            } else if (!gasHsDatalist.contains(gasData)) {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("未录入该气瓶!")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();
                            }
                        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
                            gasLabel.setText(result.getGasLabe());
                            gasModule.setText(result.getGasModule());
                            gasData = result.getGasLabe() + "+" + result.getGasModule();
                            if (gasSinceJfDatalist.contains(gasData)) {
                                gasSinceJfDatalist.remove(gasData);
                                tvTotal.setText(String.valueOf(gasSinceJfDatalist.size()));
                                gasJfLogInfo.setLabel(result.getGasLabe());
                                gasJfLogInfo.setType(result.getGasModule());
                                gasSinceJfLogInfoList.remove(gasJfLogInfo);
                                showToast("剔除成功");
                                scannerFragment.restart();
                            } else if (!gasSinceJfDatalist.contains(gasData)) {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("未录入该气瓶!")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();
                            }
                        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
                            gasLabel.setText(result.getGasLabe());
                            gasModule.setText(result.getGasModule());
                            gasData = result.getGasLabe() + "+" + result.getGasModule();
                            if (gasSinceHsDatalist.contains(gasData)) {
                                gasSinceHsDatalist.remove(gasData);
                                tvTotal.setText(String.valueOf(gasSinceHsDatalist.size()));
                                gasHsLogInfo.setLabel(result.getGasLabe());
                                gasHsLogInfo.setType(result.getGasModule());
                                gasSinceHsLogInfoList.remove(gasHsLogInfo);
                                showToast("剔除成功");
                                scannerFragment.restart();
                            } else if (!gasSinceHsDatalist.contains(gasData)) {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("未录入该气瓶!")
                                        .setPositive("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                scannerFragment.restart();
                                            }
                                        })
                                        .show();
                            }
                        } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
                            gasLabel.setText(result.getGasLabe());
                            gasModule.setText(result.getGasModule());
                            gasData = result.getGasLabe() + "+" + result.getGasModule();
                            if (gasCheckScanDataList.contains(gasData)) {
                                gasCheckScanDataList.remove(gasData);
                                tvTotal.setText(String.valueOf(gasCheckScanDataList.size()));
                                gasCheckScanLogInfo.setLabel(result.getGasLabe());
                                gasCheckScanLogInfo.setType(result.getGasModule());
                                gasCheckScanLogInfoList.remove(gasCheckScanLogInfo);
                                showToast("剔除成功");
                                scannerFragment.restart();
                            } else if (!gasCheckScanDataList.contains(gasData)) {
                                new CircleDialog.Builder(this)
                                        .setTitle("提示")
                                        .setWidth((float) 0.8)
                                        .setText("未录入该气瓶!")
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
                } else {
                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业气瓶,不允许操作!")
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
     * 配送任务交付
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retTransportOfPayView(boolean isSuccess, Result result, String msg) {

        if (isSuccess) {
            if (HsBoolean == false && JfBoolean == true) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("配送交付完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                            }
                        })
                        .show();

                HsBoolean = false;
                JfBoolean = false;
            } else if (HsJfBoolean == true) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("配送交付/回收完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                HsJfBoolean = false;
            }
        } else {
            showToast(msg);
        }
    }

    /***
     * 配送任务回收
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retTransportOfReclaimView(boolean isSuccess, Result result, String msg) {

        if (isSuccess) {

            if (JfBoolean == false && HsBoolean == true) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("配送回收完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                HsBoolean = false;
            } else if (JfHsBoolean == true) {


                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("配送回收/交付完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                JfHsBoolean = false;
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 自提交付
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retSinceOfPayView(boolean isSuccess, Result result, String msg) {


        if (isSuccess) {

            if (SinceHsBoolean == false && SinceJfBoolean == true) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("自提交付完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                SinceHsBoolean = false;
                SinceJfBoolean = false;
            } else if (SinceHsJfBoolean == true) {


                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("自提交付/回收完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                SinceHsJfBoolean = false;
            }
        } else {
            showToast(msg);
        }

    }

    /**
     * 自提回收
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retSinceOfReclaimView(boolean isSuccess, Result result, String msg) {


        if (isSuccess) {

            if (SinceJfBoolean == false && SinceHsBoolean == true) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("自提回收完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                SinceJfBoolean = false;
                SinceHsBoolean = false;
            } else if (SinceJfHsBoolean == true) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("自提回收/交付完成")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                scannerFragment.restart();
                                finish();
                            }
                        })
                        .show();
                SinceJfHsBoolean = false;
            }
        } else {
            showToast(msg);
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

}

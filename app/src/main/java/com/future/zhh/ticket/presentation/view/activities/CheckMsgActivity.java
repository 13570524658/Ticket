package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.HotWaterHeaterModuleListInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckMsgActivityComponents;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCheckMsgActivityComponents;
import com.future.zhh.ticket.presentation.presenters.CheckSubmitPresenter;
import com.future.zhh.ticket.presentation.presenters.HotWaterHeaterModulePresenter;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.CheckSubmitView;
import com.future.zhh.ticket.presentation.view.HotWaterHeaterModuleView;
import com.future.zhh.ticket.presentation.view.adapters.HotWaterHeaterModuleSpinnerAdapter;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigButton;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.ButtonParams;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.DialogParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.future.zhh.ticket.presentation.view.widgets.headimage.FileUtil.getRealFilePathFromUri;

/**
 * Created by Administrator on 2017/12/8.
 * 入户检查内容页面
 */

public class CheckMsgActivity extends BaseActivity implements HotWaterHeaterModuleView, CheckSubmitView {
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
    @BindView(R.id.iv_closeOne)
    ImageView ivCloseOne;
    @BindView(R.id.rb_yAirVents)
    RadioButton rbYAirVents;
    @BindView(R.id.rb_nAirVents)
    RadioButton rbNAirVents;
    @BindView(R.id.rg_isAirVents)
    RadioGroup rgIsAirVents;
    @BindView(R.id.rb_ySafetyWarning)
    RadioButton rbYSafetyWarning;
    @BindView(R.id.rb_nSafetyWarning)
    RadioButton rbNSafetyWarning;
    @BindView(R.id.rg_isSafetyWarning)
    RadioGroup rgIsSafetyWarning;
    @BindView(R.id.rb_yAlarm)
    RadioButton rbYAlarm;
    @BindView(R.id.rb_nAlarm)
    RadioButton rbNAlarm;
    @BindView(R.id.rg_isAlarm)
    RadioGroup rgIsAlarm;
    @BindView(R.id.rg_yTrueAlarm)
    RadioButton rbYTrueAlarm;
    @BindView(R.id.rg_nTrueAlarm)
    RadioButton rbNTrueAlarm;
    @BindView(R.id.rg_isTrueAlarm)
    RadioGroup rgIsTrueAlarm;
    @BindView(R.id.rb_yFireEquipment)
    RadioButton rbYFireEquipment;
    @BindView(R.id.rb_nFireEquipment)
    RadioButton rbNFireEquipment;
    @BindView(R.id.rg_isFireEquipment)
    RadioGroup rgIsFireEquipment;
    @BindView(R.id.ll_closeOne)
    LinearLayout llCloseOne;
    @BindView(R.id.iv_closeTwo)
    ImageView ivCloseTwo;
    @BindView(R.id.iv_san)
    ImageView ivSan;
    @BindView(R.id.et_outTimeGasNumber)
    EditText etOutTimeGasNumber;
    @BindView(R.id.et_scrappingGasNumber)
    EditText etScrappingGasNumber;
    @BindView(R.id.rb_kitchen)
    RadioButton rbKitchen;
    @BindView(R.id.rb_toilet)
    RadioButton rbToilet;
    @BindView(R.id.rb_other)
    RadioButton rbOther;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.rg_isGasLocation)
    RadioGroup rgIsGasLocation;
    @BindView(R.id.rb_1m)
    RadioButton rb1m;
    @BindView(R.id.rb_2m)
    RadioButton rb2m;
    @BindView(R.id.rb_3m)
    RadioButton rb3m;
    @BindView(R.id.rg_spacing)
    RadioGroup rgSpacing;
    @BindView(R.id.rb_yNearStove)
    RadioButton rbYNearStove;
    @BindView(R.id.rb_nNearStove)
    RadioButton rbNNearStove;
    @BindView(R.id.rg_isNearStove)
    RadioGroup rgIsNearStove;
    @BindView(R.id.rb_yAirLeakageValve)
    RadioButton rbYAirLeakageValve;
    @BindView(R.id.rb_nAirLeakageValve)
    RadioButton rbNAirLeakageValve;
    @BindView(R.id.rg_isAirLeakageValve)
    RadioGroup rgIsAirLeakageValve;
    @BindView(R.id.iv_closeThree)
    ImageView ivCloseThree;
    @BindView(R.id.rb_yProtectionDeviceStove)
    RadioButton rbYProtectionDeviceStove;
    @BindView(R.id.rb_nProtectionDeviceStove)
    RadioButton rbNProtectionDeviceStove;
    @BindView(R.id.rg_isProtectionDeviceStove)
    RadioGroup rgIsProtectionDeviceStove;
    @BindView(R.id.rb_yToolUseYearStove)
    RadioButton rbYToolUseYearStove;
    @BindView(R.id.rb_nToolUseYearStove)
    RadioButton rbNToolUseYearStove;
    @BindView(R.id.rg_isToolUseYearStove)
    RadioGroup rgIsToolUseYearStove;
    @BindView(R.id.rb_ySmokeRoad)
    RadioButton rbYSmokeRoad;
    @BindView(R.id.rb_nSmokeRoad)
    RadioButton rbNSmokeRoad;
    @BindView(R.id.rg_isSmokeRoad)
    RadioGroup rgIsSmokeRoad;
    @BindView(R.id.sp_hotWaterToolModule)
    Spinner spHotWaterToolModule;
    @BindView(R.id.rb_yHotWaterToolUseYear)
    RadioButton rbYHotWaterToolUseYear;
    @BindView(R.id.rb_nHotWaterToolUseYear)
    RadioButton rbNHotWaterToolUseYear;
    @BindView(R.id.rg_isHotWaterToolUseYear)
    RadioGroup rgIsHotWaterToolUseYear;
    @BindView(R.id.ll_closeTwo)
    LinearLayout llCloseTwo;
    @BindView(R.id.iv_closeFour)
    ImageView ivCloseFour;
    @BindView(R.id.rb_yVoltageRegulatorAirLeakage)
    RadioButton rbYVoltageRegulatorAirLeakage;
    @BindView(R.id.rb_nVoltageRegulatorAirLeakage)
    RadioButton rbNVoltageRegulatorAirLeakage;
    @BindView(R.id.rg_isVoltageRegulatorAirLeakage)
    RadioGroup rgIsVoltageRegulatorAirLeakage;
    @BindView(R.id.rb_yConnectingPipeAging)
    RadioButton rbYConnectingPipeAging;
    @BindView(R.id.rb_nConnectingPipeAging)
    RadioButton rbNConnectingPipeAging;
    @BindView(R.id.rg_isConnectingPipeAging)
    RadioGroup rgIsConnectingPipeAging;
    @BindView(R.id.ll_closeThree)
    LinearLayout llCloseThree;
    @BindView(R.id.iv_closeFive)
    ImageView ivCloseFive;
    @BindView(R.id.iv_checkImgOne)
    ImageView ivCheckImgOne;
    @BindView(R.id.iv_checkImgTwo)
    ImageView ivCheckImgTwo;
    @BindView(R.id.iv_checkImgThree)
    ImageView ivCheckImgThree;
    @BindView(R.id.ll_closeFour)
    LinearLayout llCloseFour;
    @BindView(R.id.iv_closeSix)
    ImageView ivCloseSix;
    @BindView(R.id.et_otherRemark)
    EditText etOtherRemark;
    @BindView(R.id.ll_closeFive)
    LinearLayout llCloseFive;
    @BindView(R.id.iv_closeSeven)
    ImageView ivCloseSeven;
    @BindView(R.id.rb_yPass)
    RadioButton rbYPass;
    @BindView(R.id.rb_nPass)
    RadioButton rbNPass;
    @BindView(R.id.rg_isPass)
    RadioGroup rgIsPass;
    @BindView(R.id.ll_closeSix)
    LinearLayout llCloseSix;
    @BindView(R.id.tv_saveBtn)
    TextView tvSaveBtn;
    @BindView(R.id.tv_cancelBtn)
    TextView tvCancelBtn;
    @BindView(R.id.ll_closeSeven)
    LinearLayout llCloseSeven;
    private static final int TAKE_PHOTO = 0;
    private static final int PICK_PHOTO = 1;

    SharedPreferencesUtils sharedPreferencesUtils;
    @Inject
    HotWaterHeaterModulePresenter hotWaterHeaterModulePresenter;
    CheckMsgActivityComponents component;

    private Context mContext;
    private String customerName;
    private String address;
    private String enterpriseID;
    private String customerID;

    private String base64image1;
    private String base64image2;
    private String base64image3;
    private int OutTimeGasNumbe;
    private int ScrappingGasNumbe;
    private String ScrappingGasNumbes;
    private String OutTimeGasNumbes;
    private String OtherRemark;

    private String YAirVents;
    private String NAirVents;
    private String YSafetyWarning;
    private String NSafetyWarning;
    private String YAlarm;
    private String NAlarm;
    private String YTrueAlarm;
    private String NTrueAlarm;
    private String YFireEquipment;
    private String NFireEquipment;
    private String Kitchen;
    private String Toilet;
    private String other;
    private String m1;
    private String m2;
    private String m3;
    private String YNearStove;
    private String NNearStove;
    private String YAirLeakageValve;
    private String NAirLeakageValve;
    private String YProtectionDeviceStove;
    private String NProtectionDeviceStove;
    private String YToolUseYearStove;
    private String NToolUseTearStove;
    private String YSmokeRoad;
    private String NSmokeRoad;
    private String YHotWaterToolUseYear;
    private String NHotWaterToolUseYear;
    private String YVoltageRegulatorAirLeakage;
    private String NVoltageRegulatorAirLeakage;
    private String YConnectingPipeAging;
    private String NConnectingPipeAging;
    private String YPass;
    private String NPass;

    private String isAirVents;
    private String isSafetyWarning;
    private String isAlarm;
    private String isTrueAlarm;
    private String isFireEquipment;
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
    private String isPass;

    @Inject
    CheckSubmitPresenter checkSubmitPresenter;
    private List<HotWaterHeaterModuleListInfo> hotWaterHeaterModuleListInfo;
    private HotWaterHeaterModuleSpinnerAdapter hotWaterHeaterModuleSpinnerAdapter;
    private Uri photoUri;
    private String photoPath;
    private int image;

    private String scrappingGasTotal;
    private String outTimeGasTotal;

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);//将bitmap放入字节数组流中

                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();

                byte[] bitmapByte = bos.toByteArray();
                result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCheckMsgActivityComponents
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_msg);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }

    private void init() {
        if (getIntent() != null) {
            customerName = getIntent().getStringExtra("customerName");
            address = getIntent().getStringExtra("address");
            customerID=getIntent().getStringExtra("customerID");
        }
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
        hotWaterHeaterModulePresenter.setView(this);
        checkSubmitPresenter.setView(this);
    }

    private void initView() {
        hotWaterHeaterModulePresenter.hotWaterHeaterModule(enterpriseID);


        spinner();
        radioButton();
    }

    private void spinner() {
        spHotWaterToolModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int postions = spHotWaterToolModule.getSelectedItemPosition();
                hotWaterToolModule = hotWaterHeaterModuleListInfo.get(postions).getHotWaterToolModule();
                spHotWaterToolModule.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void radioButton() {
        rgIsAirVents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYAirVents.getId() == i) {
                    YAirVents = rbYAirVents.getText().toString();
                    isAirVents=YAirVents;
                } else if (rbNAirVents.getId() == i) {
                    NAirVents = rbNAirVents.getText().toString();
                    isAirVents=NAirVents;
                }
            }
        });

        rgIsSafetyWarning.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYSafetyWarning.getId() == i) {
                    YSafetyWarning = rbYSafetyWarning.getText().toString();
                    isSafetyWarning=YSafetyWarning;
                } else if (rbNSafetyWarning.getId() == i) {
                    NSafetyWarning = rbNSafetyWarning.getText().toString();
                    isSafetyWarning=NSafetyWarning;
                }
            }
        });
        rgIsAlarm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYAlarm.getId() == i) {
                    YAlarm = rbYAlarm.getText().toString();
                    isAlarm=YAlarm;
                } else if (rbNAlarm.getId() == i) {
                    NAlarm = rbNAlarm.getText().toString();
                    isAlarm=NAlarm;
                }
            }
        });
        rgIsTrueAlarm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYTrueAlarm.getId() == i) {
                    YTrueAlarm = rbYTrueAlarm.getText().toString();
                    isTrueAlarm=YTrueAlarm;
                } else if (rbNTrueAlarm.getId() == i) {
                    NTrueAlarm = rbNTrueAlarm.getText().toString();
                    isTrueAlarm=NTrueAlarm;
                }
            }
        });
        rgIsFireEquipment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYFireEquipment.getId() == i) {
                    YFireEquipment = rbYFireEquipment.getText().toString();
                    isFireEquipment=YFireEquipment;
                } else if (rbNFireEquipment.getId() == i) {
                    NFireEquipment = rbNFireEquipment.getText().toString();
                    isFireEquipment=NFireEquipment;
                }
            }
        });
        rgIsGasLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbKitchen.getId() == i) {
                    Kitchen = rbKitchen.getText().toString();
                    gasLocation=Kitchen;
                } else if (rbToilet.getId() == i) {
                    Toilet = rbToilet.getText().toString();
                    gasLocation=Toilet;
                } else if (rbOther.getId() == i) {
                    other = etOther.getText().toString().trim();
                    gasLocation=other;
                }
            }
        });
        rgSpacing.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb1m.getId() == i) {
                    m1 = "1m以内";
                    spacing=m1;
                } else if (rb2m.getId() == i) {
                    m2 = "1m-2m";
                    spacing=m2;
                } else if (rb3m.getId() == i) {
                    m3 = "2m以上";
                    spacing=m3;
                }
            }
        });

        rgIsNearStove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYNearStove.getId() == i) {
                    YNearStove = rbYNearStove.getText().toString();
                    isNearStove=YNearStove;
                } else if (rbNNearStove.getId() == i) {
                    NNearStove = rbNNearStove.getText().toString();
                    isNearStove=NNearStove;
                }
            }
        });
        rgIsAirLeakageValve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYAirLeakageValve.getId() == i) {
                    YAirLeakageValve = rbYAirLeakageValve.getText().toString();
                    isAirLeakageValve=YAirLeakageValve;
                } else if (rbNAirLeakageValve.getId() == i) {
                    NAirLeakageValve = rbNAirLeakageValve.getText().toString();
                    isAirLeakageValve=NAirLeakageValve;
                }
            }
        });

        rgIsProtectionDeviceStove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYProtectionDeviceStove.getId() == i) {
                    YProtectionDeviceStove = rbYProtectionDeviceStove.getText().toString();
                    isProtectionDeviceStove=YProtectionDeviceStove;
                } else if (rbNProtectionDeviceStove.getId() == i) {
                    NProtectionDeviceStove = rbNProtectionDeviceStove.getText().toString();
                    isProtectionDeviceStove=NProtectionDeviceStove;
                }
            }
        });
        rgIsToolUseYearStove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYToolUseYearStove.getId() == i) {
                    YToolUseYearStove = rbYToolUseYearStove.getText().toString();
                    isToolUseYearStove=YToolUseYearStove;
                } else if (rbNToolUseYearStove.getId() == i) {
                    NToolUseTearStove = rbNToolUseYearStove.getText().toString();
                    isToolUseYearStove=NToolUseTearStove;
                }
            }
        });
        rgIsSmokeRoad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYSmokeRoad.getId() == i) {
                    YSmokeRoad = rbYSmokeRoad.getText().toString();
                    isSmokeRoad=YSmokeRoad;
                } else if (rbNSmokeRoad.getId() == i) {
                    NSmokeRoad = rbNSmokeRoad.getText().toString();
                    isSmokeRoad=NSmokeRoad;
                }
            }
        });
        rgIsHotWaterToolUseYear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYHotWaterToolUseYear.getId() == i) {
                    YHotWaterToolUseYear = rbYHotWaterToolUseYear.getText().toString();
                    isHotWaterToolUseYear=YHotWaterToolUseYear;
                } else if (rbNHotWaterToolUseYear.getId() == i) {
                    NHotWaterToolUseYear = rbNHotWaterToolUseYear.getText().toString();
                    isHotWaterToolUseYear=NHotWaterToolUseYear;
                }
            }
        });

        rgIsVoltageRegulatorAirLeakage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYVoltageRegulatorAirLeakage.getId() == i) {
                    YVoltageRegulatorAirLeakage = rbYVoltageRegulatorAirLeakage.getText().toString();
                    isVoltageRegulatorAirLeakage=YVoltageRegulatorAirLeakage;
                } else if (rbNVoltageRegulatorAirLeakage.getId() == i) {
                    NVoltageRegulatorAirLeakage = rbNVoltageRegulatorAirLeakage.getText().toString();
                    isVoltageRegulatorAirLeakage=NVoltageRegulatorAirLeakage;
                }
            }
        });
        rgIsConnectingPipeAging.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYConnectingPipeAging.getId() == i) {
                    YConnectingPipeAging = rbYConnectingPipeAging.getText().toString();
                    isConnectingPipeAging=YConnectingPipeAging;
                } else if (rbNConnectingPipeAging.getId() == i) {
                    NConnectingPipeAging = rbNConnectingPipeAging.getText().toString();
                    isConnectingPipeAging=NConnectingPipeAging;
                }
            }
        });
        rgIsPass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rbYPass.getId() == i) {
                    YPass = rbYPass.getText().toString();
                    isPass = YPass;
                } else if (rbNPass.getId() == i) {
                    NPass = rbNPass.getText().toString();
                    isPass=NPass;
                }
            }
        });
//
//        if (YAirVents != null&&!YAirVents.equalsIgnoreCase("") ) {
//            isAirVents = YAirVents;
//        } else if (NAirVents != null&&!NAirVents.equalsIgnoreCase("")) {
//            isAirVents = NAirVents;
//        }
//        if (YSafetyWarning != null&&!YSafetyWarning.equalsIgnoreCase("")) {
//            isSafetyWarning = YSafetyWarning;
//        } else if (NSafetyWarning != null&&!NSafetyWarning.equalsIgnoreCase("")) {
//            isSafetyWarning = NSafetyWarning;
//        }
//        if (YAlarm != null&&!YAlarm.equalsIgnoreCase("")) {
//            isAlarm = YAlarm;
//        } else if (NAlarm != null&&!NAlarm.equalsIgnoreCase("")) {
//            isAlarm = NAlarm;
//        }
//        if (YTrueAlarm != null &&!YTrueAlarm.equalsIgnoreCase("") ) {
//            isTrueAlarm = YTrueAlarm;
//        } else if (NTrueAlarm != null&&!NTrueAlarm.equalsIgnoreCase("") ) {
//            isTrueAlarm = NTrueAlarm;
//        }
//
//        if (YFireEquipment != null &&!YFireEquipment.equalsIgnoreCase("")) {
//            isFireEquipment = YFireEquipment;
//        } else if (NFireEquipment != null&&!NFireEquipment.equalsIgnoreCase("")) {
//            isFireEquipment = NFireEquipment;
//        }
//
//        if ( Kitchen != null&&!Kitchen.equalsIgnoreCase("")) {
//            gasLocation = Kitchen;
//        } else if (Toilet != null&&!Toilet.equalsIgnoreCase("") ) {
//            gasLocation = Toilet;
//        } else if ( other != null&&!other.equalsIgnoreCase("") ) {
//            gasLocation = other;
//        }
//
//        if ( m1 != null&&!m1.equalsIgnoreCase("")) {
//            spacing = m1;
//        } else if ( m2 != null&&!m2.equalsIgnoreCase("") ) {
//            spacing = m2;
//        } else if (m3 != null&&!m3.equalsIgnoreCase("")) {
//            spacing = m3;
//        }
//
//        if (YNearStove != null&&!YNearStove.equalsIgnoreCase("") ) {
//            isNearStove = YNearStove;
//        } else if (NNearStove != null&&!NNearStove.equalsIgnoreCase("") ) {
//            isNearStove = NNearStove;
//        }
//        if (YAirLeakageValve != null&&!YAirLeakageValve.equalsIgnoreCase("")) {
//            isAirLeakageValve = YAirLeakageValve;
//        } else if (NAirLeakageValve != null&&!NAirLeakageValve.equalsIgnoreCase("") ) {
//            isAirLeakageValve = NAirLeakageValve;
//        }
//
//        if (YProtectionDeviceStove != null&&!YProtectionDeviceStove.equalsIgnoreCase("") ) {
//            isProtectionDeviceStove = YProtectionDeviceStove;
//        } else if (NProtectionDeviceStove != null&&!NProtectionDeviceStove.equalsIgnoreCase("")) {
//            isProtectionDeviceStove = NProtectionDeviceStove;
//        }
//        if (YToolUseYearStove != null&&!YToolUseYearStove.equalsIgnoreCase("") ) {
//            isToolUseYearStove = YToolUseYearStove;
//        } else if ( NToolUseTearStove != null&&!NToolUseTearStove.equalsIgnoreCase("") ) {
//            isToolUseYearStove = NToolUseTearStove;
//        }
//        if (YSmokeRoad != null&&!YSmokeRoad.equalsIgnoreCase("") ) {
//            isSmokeRoad = YSmokeRoad;
//        } else if (NSmokeRoad != null&&!NSmokeRoad.equalsIgnoreCase("") ) {
//            isSmokeRoad = NSmokeRoad;
//        }
//        if (YHotWaterToolUseYear != null&&!YHotWaterToolUseYear.equalsIgnoreCase("")) {
//            isHotWaterToolUseYear = YHotWaterToolUseYear;
//        } else if (NHotWaterToolUseYear != null&&!NHotWaterToolUseYear.equalsIgnoreCase("") ) {
//            isHotWaterToolUseYear = NHotWaterToolUseYear;
//        }
//
//
//        if (YVoltageRegulatorAirLeakage != null&&!YVoltageRegulatorAirLeakage.equalsIgnoreCase("")) {
//            isVoltageRegulatorAirLeakage = YVoltageRegulatorAirLeakage;
//        } else if (NVoltageRegulatorAirLeakage != null&&!NVoltageRegulatorAirLeakage.equalsIgnoreCase("")) {
//            isVoltageRegulatorAirLeakage = NVoltageRegulatorAirLeakage;
//        }
//        if ( YConnectingPipeAging != null&&!YConnectingPipeAging.equalsIgnoreCase("")) {
//            isConnectingPipeAging = YConnectingPipeAging;
//        } else if ( NConnectingPipeAging != null&&!NConnectingPipeAging.equalsIgnoreCase("")) {
//            isConnectingPipeAging = NConnectingPipeAging;
//        }
//
//        if (YPass != null&&!YPass.equalsIgnoreCase("") ) {
//            isPass = YPass;
//        } else if (NPass != null&&!NPass.equalsIgnoreCase("")) {
//            isPass = NPass;
//        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.ibtnBack, R.id.iv_checkImgOne, R.id.iv_checkImgTwo, R.id.iv_checkImgThree, R.id.tv_cancelBtn, R.id.tv_saveBtn,
            R.id.iv_san, R.id.iv_closeOne, R.id.iv_closeTwo, R.id.iv_closeThree, R.id.iv_closeFour, R.id.iv_closeFive, R.id.iv_closeSix,R.id.iv_closeSeven})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.iv_checkImgOne:
                image = 1;
                uploadImage();
                break;
            case R.id.iv_checkImgTwo:
                image = 2;
                uploadImage();
                break;
            case R.id.iv_checkImgThree:
                image = 3;
                uploadImage();
                break;
            case R.id.tv_saveBtn:
//                if ((rbYAirVents.isChecked()==true||rbNAirVents.isChecked()==true)&&(rbYSafetyWarning.isChecked()==true&&rbNSafetyWarning.isChecked()==true)&&
//                        (rbYAlarm.isChecked()==true||rbNAlarm.isChecked()==true)&&(rbYTrueAlarm.isChecked()==true||rbNTrueAlarm.isChecked()==true)&&
//                        (rbYFireEquipment.isChecked()==true||rbNFireEquipment.isChecked()==true)&&(!etOutTimeGasNumber.getText().toString().trim().equalsIgnoreCase("")&&
//                        (!etScrappingGasNumber.getText().toString().trim().equalsIgnoreCase(""))&&(!gasLocation.equalsIgnoreCase("")&&(rbYAirLeakageValve.isChecked()==true||rbNAirLeakageValve.isChecked()==true)
//               &&(rbYVoltageRegulatorAirLeakage.isChecked()==true||rbNVoltageRegulatorAirLeakage.isChecked()==true)&&(rbYConnectingPipeAging.isChecked()==true||rbNConnectingPipeAging.isChecked()==true) &&(rbYPass.isChecked()==true||rbNPass.isChecked()==true)))){
                if (!etOutTimeGasNumber.getText().toString().equalsIgnoreCase("")) {
                    OutTimeGasNumbe = Integer.parseInt(etOutTimeGasNumber.getText().toString().trim());
                }
                if (!etScrappingGasNumber.getText().toString().equalsIgnoreCase("")) {
                    ScrappingGasNumbe = Integer.parseInt(etScrappingGasNumber.getText().toString().trim());
                }

                Log.e("_________","isAirVents-----"+isAirVents+"isSafetyWarning-----"+isSafetyWarning+"isAlarm---"+isAlarm+"isTrueAlarm----"+isTrueAlarm+"isFireEquipment----"+isFireEquipment
                        +"OutTimeGasNumbe-----"+OutTimeGasNumbe+"ScrappingGasNumbe-----"+ScrappingGasNumbe+"gasLocation-----"+gasLocation+"spacing----"+spacing+"isNearStove-----"+isNearStove+"isAirLeakageValve----"+isAirLeakageValve+"isProtectionDeviceStove----"+isProtectionDeviceStove+
                        "isToolUseYearStove----"+isToolUseYearStove+"isSmokeRoad----"+isSmokeRoad+"isHotWaterToolUseYear----"+isHotWaterToolUseYear+"hotWaterToolModule-----"+hotWaterToolModule+"isVoltageRegulatorAirLeakage----"+isVoltageRegulatorAirLeakage+"isConnectingPipeAging----"+isConnectingPipeAging
                        +"base64image1----"+base64image1+"base64image2----"+base64image2+"base64image3---"+base64image3+"OtherRemark----"+OtherRemark+"isPass----"+isPass+"address---"+address+"customerName---"+customerName);
       if ((isAirVents!=null&&!isAirVents.equalsIgnoreCase(""))
               &&(isSafetyWarning!=null&&!isSafetyWarning.equalsIgnoreCase(""))
               &&(isAlarm!=null&&!isAlarm.equalsIgnoreCase(""))
               &&(isTrueAlarm!=null&&!isTrueAlarm.equalsIgnoreCase(""))
               &&(isFireEquipment!=null&&!isFireEquipment.equalsIgnoreCase(""))
               &&!etOutTimeGasNumber.getText().toString().trim().equalsIgnoreCase("")
               &&!etScrappingGasNumber.getText().toString().trim().equalsIgnoreCase("")
               &&(gasLocation!=null&&!gasLocation.equalsIgnoreCase(""))
               &&(isAirLeakageValve!=null&&!isAirLeakageValve.equalsIgnoreCase(""))
               &&(isVoltageRegulatorAirLeakage!=null&&!isVoltageRegulatorAirLeakage.equalsIgnoreCase(""))
               &&(isConnectingPipeAging!=null&&!isConnectingPipeAging.equalsIgnoreCase(""))
               &&(isPass!=null&&!isPass.equalsIgnoreCase(""))) {

           OtherRemark = etOtherRemark.getText().toString();
           checkSubmitPresenter.checkSubmit(isAirVents, isSafetyWarning, isAlarm, isTrueAlarm, isFireEquipment,
                   OutTimeGasNumbe, ScrappingGasNumbe, gasLocation, spacing, isNearStove,
                   isAirLeakageValve, isProtectionDeviceStove, isToolUseYearStove, isSmokeRoad,
                   isHotWaterToolUseYear, hotWaterToolModule, isVoltageRegulatorAirLeakage, isConnectingPipeAging,
                   base64image1, base64image2, base64image3, OtherRemark, isPass, address, customerName);



       }
                else {
                    new AlertDialog.Builder(this)
                            .setMessage("带*号的为必填项")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(mContext,CheckLogMsgActivity.class);
                                    intent.putExtra("customerID",customerID);
                                    intent.putExtra("customerName",customerName);
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
                break;
            case R.id.tv_cancelBtn:
                new AlertDialog.Builder(this)
                        .setTitle("取消当前入户安全检查")
                        .setMessage("确认取消吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.iv_san:
                Intent intent = new Intent(mContext, ScanGasInfoActivity.class);
                intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS);
                startActivityForResult(intent, ScanGasInfoActivity.REQUEST_CHECK_LABEL);
                break;
            case R.id.iv_closeOne:
                int isVisibility = llCloseOne.getVisibility();
                if (isVisibility == View.GONE) {
                    ivCloseOne.setBackgroundResource(R.drawable.icon_open);
                    llCloseOne.setVisibility(View.VISIBLE);
                } else if (isVisibility == View.VISIBLE) {
                    ivCloseOne.setBackgroundResource(R.drawable.icon_fold);
                    llCloseOne.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_closeTwo:
                int isVisibility2 = llCloseTwo.getVisibility();
                if (isVisibility2 == View.GONE) {
                    ivCloseTwo.setBackgroundResource(R.drawable.icon_open);
                    llCloseTwo.setVisibility(View.VISIBLE);
                } else if (isVisibility2 == View.VISIBLE) {
                    ivCloseTwo.setBackgroundResource(R.drawable.icon_fold);
                    llCloseTwo.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_closeThree:
                int isVisibility3 = llCloseThree.getVisibility();
                if (isVisibility3 == View.GONE) {
                    ivCloseThree.setBackgroundResource(R.drawable.icon_open);
                    llCloseThree.setVisibility(View.VISIBLE);
                } else if (isVisibility3 == View.VISIBLE) {
                    ivCloseThree.setBackgroundResource(R.drawable.icon_fold);
                    llCloseThree.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_closeFour:
                int isVisibility4 = llCloseFour.getVisibility();
                if (isVisibility4 == View.GONE) {
                    ivCloseFour.setBackgroundResource(R.drawable.icon_open);
                    llCloseFour.setVisibility(View.VISIBLE);
                } else if (isVisibility4 == View.VISIBLE) {
                    ivCloseFour.setBackgroundResource(R.drawable.icon_fold);
                    llCloseFour.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_closeFive:
                int isVisibility5 = llCloseFive.getVisibility();
                if (isVisibility5 == View.GONE) {
                    ivCloseFive.setBackgroundResource(R.drawable.icon_open);
                    llCloseFive.setVisibility(View.VISIBLE);
                } else if (isVisibility5 == View.VISIBLE) {
                    ivCloseFive.setBackgroundResource(R.drawable.icon_fold);
                    llCloseFive.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_closeSix:
                int isVisibility6 = llCloseSix.getVisibility();
                if (isVisibility6 == View.GONE) {
                    ivCloseSix.setBackgroundResource(R.drawable.icon_open);
                    llCloseSix.setVisibility(View.VISIBLE);
                } else if (isVisibility6 == View.VISIBLE) {
                    ivCloseSix.setBackgroundResource(R.drawable.icon_fold);
                    llCloseSix.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_closeSeven:
                int isVisibility7=llCloseSeven.getVisibility();
                if (isVisibility7==View.GONE){
                    ivCloseSeven.setBackgroundResource(R.drawable.icon_open);
                    llCloseSeven.setVisibility(View.VISIBLE);
                }else if (isVisibility7==View.VISIBLE){
                    ivCloseSeven.setBackgroundResource(R.drawable.icon_fold);
                    llCloseSeven.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传头像
     */
    private void uploadImage() {
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
//        TextView btnCarema = view.findViewById(R.id.btn_camera);
//        TextView btnPhoto = view.findViewById(R.id.btn_photo);
//        TextView btnCancel = view.findViewById(R.id.btn_cancel);
//        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
//        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        //popupWindow在弹窗的时候背景半透明
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                params.alpha = 1.0f;
//                getWindow().setAttributes(params);
//            }
//        });
//
//        btnCarema.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= 23) {
//                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(CheckMsgActivity.this, Manifest.permission.CAMERA);
//                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(CheckMsgActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
//                        return;
//                    } else {
//                        //只是加了一个uri作为地址传入
//                        Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
//                        intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                        startActivityForResult(intents, TAKE_PHOTO);
//                    }
//                } else {
//                    //只是加了一个uri作为地址传入
//                    Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
//                    intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    startActivityForResult(intents, TAKE_PHOTO);
//                }
//                popupWindow.dismiss();
//            }
//        });
//
//        btnPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent, "请选择图片"), PICK_PHOTO);
//                popupWindow.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });


        final String[] items = {"拍照", "从相册选择"};
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setTitle("上传图片")
                .setTitleColor(Color.parseColor("#ff13b3e9"))
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int
                            position, long id) {
                        if (position==0){
                            if (Build.VERSION.SDK_INT >=23) {
                                int checkCallPhonePermission = ContextCompat.checkSelfPermission(CheckMsgActivity.this, Manifest.permission.CAMERA);
                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(CheckMsgActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
                                    return;
                                } else {
                                    //只是加了一个uri作为地址传入
                                    Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
                                    intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(intents, TAKE_PHOTO);
                                }
                            } else {
                                //只是加了一个uri作为地址传入
                                Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
                                intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                startActivityForResult(intents, TAKE_PHOTO);
                            }
                        }else if (position==1){
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "请选择图片"), PICK_PHOTO);
                        }

                    }
                })
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        //取消按钮字体颜色
                        params.textColor = Color.RED;
                    }
                })
                .show();
    }

    /**
     * 自定义图片名，获取照片的file
     */
    private File createImgFile() {
        //创建文件
        String fileName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";//确定文件名
//        File dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File dir=Environment.getExternalStorageDirectory();
        File dir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory();
        } else {
            dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        File tempFile = new File(dir, fileName);
        try {
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件路径
        photoPath = tempFile.getAbsolutePath();
        return tempFile;
    }

    /**
     * 压缩图片
     */
    private void setImageBitmap() {
        //获取imageview的宽和高
        int targetWidth = 0;
        int targetHeight = 0;
        if (image == 1) {
            targetWidth = ivCheckImgOne.getWidth();
            targetHeight = ivCheckImgOne.getHeight();
        }
        if (image == 2) {
            targetWidth = ivCheckImgTwo.getWidth();
            targetHeight = ivCheckImgTwo.getHeight();
        }
        if (image == 3) {
            targetWidth = ivCheckImgThree.getWidth();
            targetHeight = ivCheckImgThree.getHeight();
        }

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        //获取缩放比例
        int inSampleSize = 1;
        if (photoWidth > targetWidth || photoHeight > targetHeight) {
            int widthRatio = Math.round((float) photoWidth / targetWidth);
            int heightRatio = Math.round((float) photoHeight / targetHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        if (image == 1) {
            ivCheckImgOne.setImageBitmap(bitmap);
            base64image1 = bitmapToBase64(bitmap);
        }
        if (image == 2) {
            ivCheckImgTwo.setImageBitmap(bitmap);
            base64image2 = bitmapToBase64(bitmap);
        }
        if (image == 3) {
            ivCheckImgThree.setImageBitmap(bitmap);
            base64image3 = bitmapToBase64(bitmap);
        }

    }

    /**
     * 将图片添加进手机相册
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    setImageBitmap();
                    galleryAddPic();
                    break;
                case PICK_PHOTO:
                    Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (image == 1) {
                        ivCheckImgOne.setImageBitmap(bitMap);
                    } else if (image == 2) {
                        ivCheckImgTwo.setImageBitmap(bitMap);
                    } else if (image == 3) {
                        ivCheckImgThree.setImageBitmap(bitMap);
                    }
                    break;
            }

            if (resultCode == RESULT_OK && requestCode == ScanGasInfoActivity.REQUEST_CHECK_LABEL) {
                ScrappingGasNumbes = data.getStringExtra("scrappingGasTotal");
                OutTimeGasNumbes = data.getStringExtra("outTimeGasTotal");
                if (ScrappingGasNumbes!=null&&ScrappingGasNumbes.length()>0){
                etScrappingGasNumber.setText(ScrappingGasNumbes);
                }
                if (OutTimeGasNumbes!=null&&OutTimeGasNumbes.length()>0){
                etOutTimeGasNumber.setText(OutTimeGasNumbes);
                }
            }
        }
    }

    /**
     * 热水器类型
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retHotWaterHeaterModuleView(boolean isSuccess, List<HotWaterHeaterModuleListInfo> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                hotWaterHeaterModuleListInfo = result;
                hotWaterHeaterModuleSpinnerAdapter = new HotWaterHeaterModuleSpinnerAdapter(this, hotWaterHeaterModuleListInfo);
                spHotWaterToolModule.setAdapter(hotWaterHeaterModuleSpinnerAdapter);
            }
        } else {
            showToast(msg);
        }
    }


    @Override
    public void retCheckSubmitView(boolean isSuccess, Result result, final String msg) {
        if (isSuccess) {
            if (result != null) {


                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setText("保存成功")
                        .setWidth((float) 0.8)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(mContext,CheckLogMsgActivity.class);
                                intent.putExtra("customerID",customerID);
                                intent.putExtra("customerName",customerName);
                                finish();
                            }
                        })
                        .show();
            }
        } else {
            showToast(msg);
        }
    }
}

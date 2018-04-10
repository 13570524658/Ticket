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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ByInputtingEditActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerByInputtingEditActivityComponent;
import com.future.zhh.ticket.presentation.presenters.GasModulePresenter;
import com.future.zhh.ticket.presentation.presenters.GasMsgByQrCodePresenters;
import com.future.zhh.ticket.presentation.presenters.GasMsgChangePresent;
import com.future.zhh.ticket.presentation.utils.Base64Util;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.GasModuleView;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.GasMsgChangeView;
import com.future.zhh.ticket.presentation.view.adapters.GasModuleSpinnerAdapter;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigButton;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.ButtonParams;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.DialogParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_ID;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_INFO;
import static com.future.zhh.ticket.presentation.view.widgets.headimage.FileUtil.getRealFilePathFromUri;

/**
 * Created by Administrator on 2017/12/2.
 * 气瓶建档编辑页面
 */

public class ByInputtingEditActivity extends BaseActivity implements GasMsgByQrCodeView, GasModuleView, GasMsgChangeView {
    @BindView(R.id.et_gasLabel)
    EditText etGasLabel;
    @BindView(R.id.et_steelCode)
    EditText etSteelCode;
    @BindView(R.id.et_factory_company)
    EditText etFactoryCompany;
    @BindView(R.id.et_gasModule)
    EditText etGasModule;
    @BindView(R.id.ll_etGasModule)
    LinearLayout llEtGasModule;
    @BindView(R.id.sp_gasModule)
    Spinner spGasModule;
    @BindView(R.id.ll_spGasModule)
    LinearLayout llSpGasModule;

    @BindView(R.id.et_gasWeight)
    EditText etGasWeight;
    @BindView(R.id.et_productTime)
    EditText etProductTime;
    @BindView(R.id.et_lastCheckCompany)
    EditText etLastCheckCompany;
    @BindView(R.id.et_lastCheckTime)
    EditText etLastCheckTime;
    @BindView(R.id.et_nextCheckTime)
    EditText etNextCheckTime;
    @BindView(R.id.et_setUpPersion)
    EditText etSetUpPersion;
    @BindView(R.id.et_setUpTime)
    EditText etSetUpTime;
    @BindView(R.id.iv_image1)
    ImageView ivImage1;
    @BindView(R.id.iv_image2)
    ImageView ivImage2;
    @BindView(R.id.iv_image3)
    ImageView ivImage3;
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
    @BindView(R.id.tv_replaceLabel)
    TextView tvReplaceLabel;
    @BindView(R.id.et_factoryCompanyID)
    EditText etFactoryCompanyID;
    @BindView(R.id.img_productTime)
    ImageView imgProductTime;
    @BindView(R.id.img_lastCheckTime)
    ImageView imgLastCheckTime;
    @BindView(R.id.img_nextCheckTime)
    ImageView imgNextCheckTime;
    @BindView(R.id.img_close1)
    ImageView imgClose1;
    @BindView(R.id.img_close2)
    ImageView imgClose2;
    @BindView(R.id.img_close3)
    ImageView imgClose3;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.img_setUpTime)
    ImageView imgSetUpTime;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private String label = "";
    private ByInputtingEditActivityComponent component;

    private GasModuleSpinnerAdapter gasModuleSpinnerAdapter;
    Context mContext;
    private String id;
    @Inject
    GasMsgByQrCodePresenters gasMsgByQrCodePresenters;

    @Inject
    GasMsgChangePresent gasMsgChangePresent;
    @Inject
    GasModulePresenter gasModulePresenter;

    private String enterpriseID;

    private String edit = "N";

    private String gasModule;
    private String gasModuleID;

    private List<GasmoduleList> gasmoduleListList;

    private Uri photoUri;
    private String photoPath;
    private static final int TAKE_PHOTO = 0;
    private static final int PICK_PHOTO = 1;
    String base64image1;
    String base64image2;
    String base64image3;

    String image="";

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerByInputtingEditActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_in_putting_edit);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }

    private void init() {
        if (getIntent() != null) {
            label = getIntent().getStringExtra("label");
            gasMsgByQrCodePresenters.setView(this);

            gasModulePresenter.setView(this);
            gasMsgChangePresent.setView(this);

            etGasLabel.setFocusable(false);
            etGasLabel.setFocusableInTouchMode(false);
            etSteelCode.setFocusable(false);
            etSteelCode.setFocusableInTouchMode(false);
            etFactoryCompany.setFocusable(false);
            etFactoryCompany.setFocusableInTouchMode(false);
            etGasModule.setFocusable(false);
            etGasModule.setFocusableInTouchMode(false);

            etGasWeight.setFocusable(false);
            etGasWeight.setFocusableInTouchMode(false);
            etProductTime.setFocusable(false);
            etProductTime.setFocusableInTouchMode(false);
            etLastCheckCompany.setFocusable(false);
            etLastCheckCompany.setFocusableInTouchMode(false);
            etLastCheckTime.setFocusable(false);
            etLastCheckTime.setFocusableInTouchMode(false);
            etNextCheckTime.setFocusable(false);
            etNextCheckTime.setFocusableInTouchMode(false);
            etSetUpPersion.setFocusable(false);
            etSetUpPersion.setFocusableInTouchMode(false);
            etSetUpTime.setFocusable(false);
            etSetUpTime.setFocusableInTouchMode(false);
            etFactoryCompanyID.setFocusable(false);
            etFactoryCompanyID.setFocusableInTouchMode(false);

        }
    }

    private void initView() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        gasMsgByQrCodePresenters.gasMsgByQrCode(label);
        if (getIntent() != null) {
            enterpriseID = getIntent().getStringExtra("enterpriseID");
        }
        gasModulePresenter.gasModule(enterpriseID);

        sharedPreferencesUtils = new SharedPreferencesUtils(this, SHARED_USER_INFO);
        id = sharedPreferencesUtils.loadStringSharedPreference(SHARED_ID);
    }

    private void initSpinner() {

        spGasModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int postions = spGasModule.getSelectedItemPosition();
                gasModule = gasmoduleListList.get(postions).getModuleType();
                gasModuleID = gasmoduleListList.get(position).getId();
                spGasModule.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.ibtnBack, R.id.ibtnSetting, R.id.img_close1, R.id.img_close2, R.id.img_close3,R.id.iv_image1,R.id.iv_image2,
            R.id.iv_image3, R.id.img_productTime, R.id.img_lastCheckTime,R.id.img_setUpTime, R.id.img_nextCheckTime, R.id.tv_save, R.id.tv_cancel,
            R.id.tv_replaceLabel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.ibtnSetting:
                if (edit.equalsIgnoreCase("N")) {
                    edit = "Y";
                    llEtGasModule.setVisibility(View.GONE);
                    llSpGasModule.setVisibility(View.VISIBLE);
                    initSpinner();
                    etGasLabel.setFocusable(false);
                    etGasLabel.setFocusableInTouchMode(false);

                    etSteelCode.setFocusable(true);
                    etSteelCode.setFocusableInTouchMode(true);
                    etSteelCode.requestFocus();
                    etFactoryCompany.setFocusable(true);
                    etFactoryCompany.setFocusableInTouchMode(true);
                    etFactoryCompany.requestFocus();
                    etGasModule.setFocusable(true);
                    etGasModule.setFocusableInTouchMode(true);
                    etGasModule.requestFocus();
                    etGasWeight.setFocusable(true);
                    etGasWeight.setFocusableInTouchMode(true);
                    etGasWeight.requestFocus();
//                etProductTime.setFocusable(true);
                    etLastCheckCompany.setFocusable(true);
                    etLastCheckCompany.setFocusableInTouchMode(true);
                    etLastCheckCompany.requestFocus();
//                etLastCheckTime.setFocusable(true);
//                etNextCheckTime.setFocusable(true);
//                    etSetUpPersion.setFocusable(true);
//                    etSetUpPersion.setFocusableInTouchMode(true);
//                    etSetUpPersion.requestFocus();
//                    etSetUpTime.setFocusable(true);
//                    etSetUpTime.setFocusableInTouchMode(true);
//                    etSetUpTime.requestFocus();
                    etFactoryCompanyID.setFocusable(true);
                    etFactoryCompanyID.setFocusableInTouchMode(true);
                    etFactoryCompanyID.requestFocus();

                    imgLastCheckTime.setVisibility(View.VISIBLE);
                    imgNextCheckTime.setVisibility(View.VISIBLE);
                    imgProductTime.setVisibility(View.VISIBLE);
//                    imgSetUpTime.setVisibility(View.VISIBLE);
                    if (ivImage1.getVisibility()==View.VISIBLE){
                    imgClose1.setVisibility(View.VISIBLE);}
                    if (ivImage2.getVisibility()==View.VISIBLE){
                    imgClose2.setVisibility(View.VISIBLE);}
                    if (ivImage3.getVisibility()==View.VISIBLE){
                    imgClose3.setVisibility(View.VISIBLE);}
                    llSubmit.setVisibility(View.VISIBLE);
                    tvReplaceLabel.setVisibility(View.VISIBLE);
                } else if (edit.equalsIgnoreCase("Y")) {
                    edit = "N";
                    llEtGasModule.setVisibility(View.VISIBLE);
                    llSpGasModule.setVisibility(View.GONE);
                    imgLastCheckTime.setVisibility(View.GONE);
                    imgNextCheckTime.setVisibility(View.GONE);
                    imgSetUpTime.setVisibility(View.GONE);
                    imgProductTime.setVisibility(View.GONE);
                    imgClose1.setVisibility(View.GONE);
                    imgClose2.setVisibility(View.GONE);
                    imgClose3.setVisibility(View.GONE);
                    llSubmit.setVisibility(View.GONE);
                    tvReplaceLabel.setVisibility(View.GONE);
                    etGasLabel.setFocusable(false);
                    etGasLabel.setFocusableInTouchMode(false);
                    etSteelCode.setFocusable(false);
                    etSteelCode.setFocusableInTouchMode(false);
                    etFactoryCompany.setFocusable(false);
                    etFactoryCompany.setFocusableInTouchMode(false);
                    etGasModule.setFocusable(false);
                    etGasModule.setFocusableInTouchMode(false);
                    etGasWeight.setFocusable(false);
                    etGasWeight.setFocusableInTouchMode(false);
                    etProductTime.setFocusable(false);
                    etProductTime.setFocusableInTouchMode(false);
                    etLastCheckCompany.setFocusable(false);
                    etLastCheckCompany.setFocusableInTouchMode(false);
                    etLastCheckTime.setFocusable(false);
                    etLastCheckTime.setFocusableInTouchMode(false);
                    etNextCheckTime.setFocusable(false);
                    etNextCheckTime.setFocusableInTouchMode(false);
                    etSetUpPersion.setFocusable(false);
                    etSetUpPersion.setFocusableInTouchMode(false);
                    etSetUpTime.setFocusable(false);
                    etSetUpTime.setFocusableInTouchMode(false);
                    etFactoryCompanyID.setFocusable(false);
                    etFactoryCompanyID.setFocusableInTouchMode(false);
                }
                break;
            case R.id.img_close1:
                ivImage1.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                break;
            case R.id.img_close2:
                ivImage2.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                break;
            case R.id.img_close3:
                ivImage3.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                break;
            case R.id.iv_image1:
                if (edit.equalsIgnoreCase("Y")){
                image = "1";
                uploadImage();}
                break;
            case R.id.iv_image2:
                if (edit.equalsIgnoreCase("Y")){
                image = "2";
                uploadImage();}
                break;
            case R.id.iv_image3:
                if (edit.equalsIgnoreCase("Y")){
                image = "3";
                uploadImage();}
                break;
            case R.id.img_productTime:
                TimePickerView pvTime1 = new TimePickerView.Builder(ByInputtingEditActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etProductTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择下次日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime1.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime1.show();
                break;
            case R.id.img_lastCheckTime:
                TimePickerView pvTime2 = new TimePickerView.Builder(ByInputtingEditActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etLastCheckTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择下次日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime2.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime2.show();
                break;
            case R.id.img_nextCheckTime:
                TimePickerView pvTime3 = new TimePickerView.Builder(ByInputtingEditActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etNextCheckTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择下次日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime3.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime3.show();
                break;
            case R.id.img_setUpTime:
                TimePickerView pvTime4 = new TimePickerView.Builder(ByInputtingEditActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etSetUpTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择下次日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime4.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime4.show();
                break;
            case R.id.tv_save:
                gasMsgChangePresent.gasMsgChange(id, etGasLabel.getText().toString(), etFactoryCompany.getText().toString(),
                        etFactoryCompanyID.getText().toString(), etGasWeight.getText().toString(), etGasModule.getText().toString()
                        , etSteelCode.getText().toString(), etProductTime.getText().toString(), etLastCheckCompany.getText().toString(),
                        etLastCheckTime.getText().toString(), etNextCheckTime.getText().toString(),stringToBase64(ivImage1.toString()), stringToBase64(ivImage2.toString()), stringToBase64(ivImage3.toString()));
                break;
            case R.id.tv_cancel:
                llEtGasModule.setVisibility(View.VISIBLE);
                spGasModule.setVisibility(View.GONE);
                etGasLabel.setFocusable(false);
                etSteelCode.setFocusable(false);
                etFactoryCompany.setFocusable(false);
                etGasModule.setFocusable(false);
                etGasWeight.setFocusable(false);
                etProductTime.setFocusable(false);
                etLastCheckCompany.setFocusable(false);
                etLastCheckTime.setFocusable(false);
                etNextCheckTime.setFocusable(false);
                etSetUpPersion.setFocusable(false);
                etSetUpTime.setFocusable(false);
                etFactoryCompanyID.setFocusable(false);
                llSpGasModule.setVisibility(View.GONE);
                llEtGasModule.setVisibility(View.VISIBLE);

                imgLastCheckTime.setVisibility(View.GONE);
                imgNextCheckTime.setVisibility(View.GONE);
                imgProductTime.setVisibility(View.GONE);
                imgSetUpTime.setVisibility(View.GONE);

                imgClose1.setVisibility(View.GONE);
                imgClose2.setVisibility(View.GONE);
                imgClose3.setVisibility(View.GONE);

                llSubmit.setVisibility(View.GONE);
                tvReplaceLabel.setVisibility(View.GONE);
                break;
            case R.id.tv_replaceLabel:
                Intent intent = new Intent(this, ByInputtingChangeLabelActivity.class);
                intent.putExtra("label", etGasLabel.getText().toString());
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    public static String stringToBase64(String ss) {
        byte[] bytes = ss.getBytes();
        String encode = Base64Util.encode(bytes);
        return encode;
    }

    @Override
    public void retGasMsgByQrCodeView(boolean isSuccess, GasInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getUseStatus().equals("0") || result.getUseStatus().equals("2")) {
                    etGasLabel.setText(result.getGasLabe());
                    etSteelCode.setText(result.getGasSteelCode());
                    etFactoryCompanyID.setText(result.getProductFactoryID());
                    etFactoryCompany.setText(result.getProductCompany());
                    etGasModule.setText(result.getGasModule());
                    etGasWeight.setText(result.getGasWeight());
                    etProductTime.setText(result.getProductTime());
                    etLastCheckCompany.setText(result.getLastTestCompany());
                    etLastCheckTime.setText(result.getLastTestTime());
                    etNextCheckTime.setText(result.getNextTestTime());
                    etSetUpPersion.setText(result.getSetUpPersion());
                    etProductTime.setText(result.getUpDataTime());
                    etSetUpTime.setText(result.getSetUpTime());
                    if (result.getGasImage1()!=null&&!result.getGasImage1().equalsIgnoreCase("")){
                    ivImage1.setImageBitmap(convertStringToIcon(result.getGasImage1()));
                    }
                    if (result.getGasImage2()!=null&&!result.getGasImage2().equalsIgnoreCase("")){
                    ivImage2.setImageBitmap(convertStringToIcon(result.getGasImage2()));}
                    if (result.getGasImage3()!=null&&!result.getGasImage3().equalsIgnoreCase("")){
                    ivImage3.setImageBitmap(convertStringToIcon(result.getGasImage3()));}
                } else if (result.getUseStatus().equals("1")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶已作废!")
                            .setPositive("确定", null)
                            .show();
                } else if (result.getUseStatus().equals("3")) {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("该气瓶未导入!")
                            .setPositive("确定", null)
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void retGasModuleView(boolean isSuccess, List<GasmoduleList> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                gasmoduleListList = result;
                gasModuleSpinnerAdapter = new GasModuleSpinnerAdapter(this, gasmoduleListList);
                spGasModule.setAdapter(gasModuleSpinnerAdapter);
            }
        } else {
            showToast(msg);
        }
    }

    @Override
    public void retGasMsgChangeView(boolean isSuccess, Result result, String msg) {
        if (isSuccess) {
            if (msg.equals("保存成功")) {


                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶信息修改完成!")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })

                        .show();
            }
        } else {
            if (msg.equals("气瓶标签已作废")) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶标签已作废!")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })

                        .show();
            } else if (msg.equals("气瓶标签已被使用")) {


                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶标签已建档!")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })

                        .show();

            } else if (msg.equals("气瓶标签未导入")) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶标签未导入!")
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        })

                        .show();
            }
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
//                if (Build.VERSION.SDK_INT >=23) {
//                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(ByInputtingEditActivity.this, Manifest.permission.CAMERA);
//                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(ByInputtingEditActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
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
                                int checkCallPhonePermission = ContextCompat.checkSelfPermission(ByInputtingEditActivity.this, Manifest.permission.CAMERA);
                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ByInputtingEditActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
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
        if (image.equalsIgnoreCase("1")) {
            targetWidth = ivImage1.getWidth();
            targetHeight = ivImage1.getHeight();
        }
        if (image.equalsIgnoreCase("2")) {
            targetWidth = ivImage2.getWidth();
            targetHeight = ivImage2.getHeight();
        }
        if (image.equalsIgnoreCase("3")) {
            targetWidth = ivImage3.getWidth();
            targetHeight = ivImage3.getHeight();
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
        if (image.equalsIgnoreCase("1")) {
            ivImage1.setImageBitmap(bitmap);
            base64image1 = bitmapToBase64(bitmap);
        }
        if (image.equalsIgnoreCase("2")) {
            ivImage2.setImageBitmap(bitmap);
            base64image2 = bitmapToBase64(bitmap);
        }
        if (image.equalsIgnoreCase("3")) {
            ivImage3.setImageBitmap(bitmap);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ScanInfoActivity.REQUEST_BYPUTTING_SCAN) {
            label = data.getStringExtra("label");
//                gasMsgByQrCodePresenters.gasMsgByQrCode(label);
            etGasLabel.setText(label);

        }
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
                    if (image.equalsIgnoreCase("1")) {
                        ivImage1.setImageBitmap(bitMap);
                    } else if (image.equalsIgnoreCase("2")) {
                        ivImage2.setImageBitmap(bitMap);
                    } else if (image.equalsIgnoreCase("3")) {
                        ivImage3.setImageBitmap(bitMap);
                    }
                    break;
            }
        }
    }
}

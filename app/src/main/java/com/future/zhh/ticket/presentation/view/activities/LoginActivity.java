package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.data.net.NetUtil;
import com.future.zhh.ticket.domain.model.CarNoListInfo;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.UserInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerLoginActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.LoginActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CarNoListPresenters;
import com.future.zhh.ticket.presentation.presenters.ElectCarNoPresenters;
import com.future.zhh.ticket.presentation.presenters.LoginByPasswordPresenter;
import com.future.zhh.ticket.presentation.presenters.LoginByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.CarNoListView;
import com.future.zhh.ticket.presentation.view.ElectCarNoView;
import com.future.zhh.ticket.presentation.view.LoginByPasswordView;
import com.future.zhh.ticket.presentation.view.LoginByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.ActivityIndicatorView;
import com.future.zhh.ticket.presentation.view.widgets.NetErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.ProgressButton.ProgressButton;
import com.future.zhh.ticket.presentation.view.widgets.WorkerStatusErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.future.zhh.ticket.presentation.view.widgets.selectbox.IContent;
import com.future.zhh.ticket.presentation.view.widgets.selectbox.PickerDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_PASSWORD;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_ID;

/**
 * Created by Administrator on 2017/11/19.
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements LoginByQrCodeView, LoginByPasswordView, PickerDialog.OnSelectedListener, CarNoListView, ElectCarNoView {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivScan)
    ImageView ivScan;
    @BindView(R.id.selected_tv)
    TextView mSelected;
    @BindView(R.id.tvLogin)
    ProgressButton pb_button;
    @BindView(R.id.user)
    ImageView user;
    @BindView(R.id.vLine1)
    View vLine1;
    @BindView(R.id.cbEyes)
    CheckBox cbEyes;
    @BindView(R.id.vLine2)
    View vLine2;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    private String loginType; //1=密码登录 2=扫码登录

    public final static String TAG = LoginActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private LoginActivityComponent component;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private ActivityIndicatorView activityIndicatorView;
    private NetUtil netUtil;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    //车辆选择
    PickerDialog mSingleDialog;

    private String userQrCode ="";
    private String userID ="";
    private String password ="";

    private String id = "";    //人员id
    private String carNo = ""; //车牌号码

    @Inject
    LoginByQrCodePresenter loginByQrCodePresenter;
    @Inject
    LoginByPasswordPresenter loginByPasswordPresenter;
    @Inject
    CarNoListPresenters carNoListPresenters;//获取可用车辆信息
    @Inject
    ElectCarNoPresenters electCarNoPresenters;//提交选中车辆

    private DialogFragment dialogFragment;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerLoginActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }


    private void init() {
        netUtil = new NetUtil(mContext);
        activityIndicatorView = new ActivityIndicatorView(mContext);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        loginByQrCodePresenter.setView(this);
        loginByPasswordPresenter.setView(this);
        carNoListPresenters.setView(this);
        electCarNoPresenters.setView(this);

//        pb_button=(ProgressButton)findViewById(R.id.tvLogin);
        pb_button.setBgColor(Color.parseColor("#ff13b3e9"));
        pb_button.setTextColor(Color.WHITE);
        pb_button.setProColor(Color.WHITE);
        pb_button.setButtonText("登录");
    }

    private void initView() {

//        Log.e("carNoListInfo",new Gson().toJson(carNoListInfo));


    }


    @OnClick({R.id.ivScan, R.id.tvLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivScan:
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 666);
                        return;
                    } else {
                        Intent intent = new Intent(LoginActivity.this, ScanInfoActivity.class);
                        intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_LOGIN);
                        startActivityForResult(intent, ScanInfoActivity.REQUEST_LOGIN_SCAN);
                    }

                } else {
                    Intent intent = new Intent(LoginActivity.this, ScanInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_LOGIN);
                    startActivityForResult(intent, ScanInfoActivity.REQUEST_LOGIN_SCAN);
                }

                break;
            case R.id.tvLogin:
                if (checkInput()) {
                    if (netUtil.isNetworkConnected()) {
                        userID = etUserName.getText().toString();
                        password = etPassword.getText().toString();
//                        showLoading();
//                        dialogFragment = new CircleDialog.Builder(this)
//                                .setProgressText("登录中...")
//                                .setWidth((float) 0.5)
//                                .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
//                                .show();
                        loginType = "1";
                        pb_button.startAnim();
                        Message m = mHandler.obtainMessage();
                        mHandler.sendMessageDelayed(m, 1000);
                        pb_button.setClickable(false);
                    } else {
                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setText("无网络")
                                .setPositive("确定", null)
                                .show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @OnCheckedChanged({R.id.cbEyes})
    public void onCheckChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cbEyes:
                if (isChecked)
                    //选择状态 显示明文--设置为可见的密码
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            default:
                break;
        }
    }

    private boolean checkInput() {
        if (etUserName.getText() == null || etUserName.getText().toString().equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.username_not_null));
            return false;
        }
        if (etPassword.getText() == null || etPassword.getText().toString().equalsIgnoreCase("")) {
            showToast(getResources().getString(R.string.password_not_null));
            return false;
        }
        return true;
    }

    /**
     * 动态申请安卓7.0权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 666:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(LoginActivity.this, ScanInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_LOGIN);
                    startActivityForResult(intent, ScanInfoActivity.REQUEST_LOGIN_SCAN);
                } else {
                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                    break;
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 回调扫码登录
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ScanInfoActivity.REQUEST_LOGIN_SCAN) {
            userQrCode = data.getStringExtra(SharedPreferencesUtils.SHARED_USER_QR_CODE);
            loginType = "2";
            pb_button.startAnim();
            Message m = mHandler.obtainMessage();
            mHandler.sendMessageDelayed(m, 1000);
//            dialogFragment = new CircleDialog.Builder(this)
//                    .setWidth((float) 0.5)
//                    .setProgressText("登录中...")
//                    .setProgressStyle(ProgressParams.STYLE_SPINNER)
//                        .setProgressDrawable(R.drawable.bg_progress_s)
//                    .show();
        }
    }

    @Override
    public void showLoading() {
        activityIndicatorView.setMessage("正在登录...");
    }

    @Override
    public void hideLoading() {
        activityIndicatorView.dismiss();
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
        pb_button.setClickable(true);
        if (isSuccess) {
            sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_IS_LOGINED, true);
            soundPlayUtil.play(R.raw.login_success);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    dialogFragment.dismiss();
//                }
//            }, 2000);
            if (result != null) {
                if (result.getUseStatus().equals("0")) {

                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_NAME, result.getName());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_ID, result.getId());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_IS_ADMIN, result.getIsAdmin());

                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_GAS_STATION_NAME, result.getGasStationName());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_QR_CODE_IMAGE, result.getUserQrCode());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_ENTERPRISE_ID, result.getEnterpriseID());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_STATION_ID, result.getGasStationID());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_SHOP_ID, result.getShopID());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_DEP_ID, result.getDeptID());
                    id = result.getId();
                    if (!result.getIsAdmin().equalsIgnoreCase("6")){
                        navigator.navigateToMainActivity(this);
                        finish();
                    }

                    if (result.getIsAdmin().equals("6")) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialogFragment.dismiss();
//                                carNoListPresenters.carNoList(id);
//                            }
//                        }, 2000);
                        carNoListPresenters.carNoList(id);
                    }
                } else {

                    new CircleDialog.Builder(this)
                            .setTitle("禁止登录")
                            .setWidth((float) 0.8)
                            .setText("从业人员使用状态错误,禁止登录。")
                            .setPositive("确定", null)
                            .show();
                }
            }
        } else {
            soundPlayUtil.play(R.raw.login_error);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    dialogFragment.dismiss();
//                }
//            }, 2000);
            showToast(msg);
        }
    }

    /**
     * 密码登录
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retLoginByPasswordView(boolean isSuccess, UserInfo result, final String msg) {
        pb_button.setClickable(true);
        if (isSuccess) {
            soundPlayUtil.play(R.raw.login_success);
            sharedPreferencesUtils.saveSharedPreferences(SHARED_USER_ID, userID);
            sharedPreferencesUtils.saveSharedPreferences(SHARED_PASSWORD, password);
            sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_IS_LOGINED, true);

            if (result != null) {
                if (result.getUseStatus().equals("0")) {

                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_NAME, result.getName());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_ID, result.getId());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_IS_ADMIN, result.getIsAdmin());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_GAS_STATION_NAME, result.getGasStationName());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_QR_CODE_IMAGE, result.getUserQrCode());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_ENTERPRISE_ID, result.getEnterpriseID());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_STATION_ID, result.getGasStationID());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_SHOP_ID, result.getShopID());
                    sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_DEP_ID, result.getDeptID());
                    id = result.getId();
                    if (!result.getIsAdmin().equalsIgnoreCase("6")){
                        navigator.navigateToMainActivity(this);
                        finish();
                    }
                    if (result.getIsAdmin().equals("6")) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialogFragment.dismiss();
                        carNoListPresenters.carNoList(id);
//                            }
//                        }, 2000);

                    }
                } else {
                    new CircleDialog.Builder(this)
                            .setTitle("禁止登录")
                            .setWidth((float) 0.8)
                            .setText("从业人员使用状态错误,禁止登录。")
                            .setPositive("确定", null)
                            .show();
                }
            }
        } else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    dialogFragment.dismiss();
//                }
//            }, 2000);
            soundPlayUtil.play(R.raw.login_error);
            showToast(msg);
        }
    }

    /**
     * 获取车辆列表
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retCarNoListView(boolean isSuccess, List<CarNoListInfo> result, String msg) {

        if (isSuccess) {
            if (result != null) {
                mSingleDialog = PickerDialog.newInstance(1, "", (ArrayList<? extends IContent>) result);
                mSingleDialog.setOnSelectedListener(this);
                mSingleDialog.show(getSupportFragmentManager(), "single");
            }
        } else {
            showToast(msg);
        }
    }


    /**
     * 选择车辆
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retElectCarNoView(boolean isSuccess, Result result, String msg) {
        if (isSuccess) {
            showToast(carNo);
            navigator.navigateToMainActivity(this);
            finish();
        } else {
            showToast(msg);
        }
    }

    /**
     * 处理选择车辆结果
     *
     * @param pos
     */
    @Override
    public void onSelected(List<? extends IContent> pos) {
//        String content="已选择：\n";
//        for (IContent c: pos) {
//            content+="<"+c.getDesc()+">";
//        }

        for (IContent c : pos) {
//         showToast(c.getDesc());
            carNo = c.getDesc();
        }

        if (!carNo.equalsIgnoreCase("")) {
            electCarNoPresenters.electCarNo(id, carNo);
        }
    }

    /**
     * 没有网络弹出框
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
        NetErrorDialog netErrorDialog = builder.create();

        WindowManager.LayoutParams layoutParams = netErrorDialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        netErrorDialog.getWindow().setAttributes(layoutParams);

        netErrorDialog.show();
    }


    /**
     * 从业人员使用状态错误,禁止登录
     */
    private void workerStatusErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_worker_status_error, null);
        final WorkerStatusErrorDialog.Builder builder = new WorkerStatusErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        WorkerStatusErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            pb_button.stopAnim(new ProgressButton.OnStopAnim() {
                @Override
                public void Stop() {
                    if (loginType.equalsIgnoreCase("1")) {
                        loginByPasswordPresenter.loginByPassword(userID, password);
                    }
                    if (loginType.equalsIgnoreCase("2")) {
                        loginByQrCodePresenter.loginByQrCode(userQrCode);
                    }
                }
            });

        }
    };

}





package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.interactor.ElectCarNo;
import com.future.zhh.ticket.domain.model.UserInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerDefaultActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DefaultActivityComponent;
import com.future.zhh.ticket.presentation.presenters.LoginByPasswordPresenter;

import com.future.zhh.ticket.presentation.presenters.LoginByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.LoginByPasswordView;
import com.future.zhh.ticket.presentation.view.LoginByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.ActivityIndicatorView;
import com.future.zhh.ticket.presentation.view.widgets.WorkerStatusErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_IS_FIRST_STARTUP;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_IS_LOGINED;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_PASSWORD;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_ID;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_INFO;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_QR_CODE;

/**
 * Created by Administrator on 2017/11/19.
 * 默认启动页面
 */

public class DefaultActivity extends BaseActivity implements LoginByQrCodeView, LoginByPasswordView {

    public final static String TAG = DefaultActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private DefaultActivityComponent component;

    @Inject
    LoginByPasswordPresenter loginByPasswordPresenter;
    @Inject
    LoginByQrCodePresenter loginByQrCodePresenter;

    private ActivityIndicatorView activityIndicatorView;

    private String userQrCode = "";
    private String userID = "";
    private String password = "";
    private int retryCount = 0;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerDefaultActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this, SHARED_USER_INFO);
        activityIndicatorView = new ActivityIndicatorView(mContext);
        loginByQrCodePresenter.setView(this);
        loginByPasswordPresenter.setView(this);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 666);
                return;
            }
        }
    }

    private void initView() {
        /**
         * 如果第一次登陆,则跳转引导页面
         */
        if (!sharedPreferencesUtils.loadBooleanSharedPreference(SHARED_IS_FIRST_STARTUP)) {
            navigator.navigateToGuidePageActivity(this);
        } else {

            /**
             * 是否已登录
             */
            if (sharedPreferencesUtils.loadBooleanSharedPreference(SHARED_IS_LOGINED)) {
                /**
                 * 已登录,如果有监管的登录接口则每次都要重新发送请求
                 */
                if (ApplicationDatas.CONNECT_SUPERVISION) {
                    userQrCode = sharedPreferencesUtils.loadStringSharedPreference(SHARED_USER_QR_CODE);
                    userID = sharedPreferencesUtils.loadStringSharedPreference(SHARED_USER_ID);
                    password = sharedPreferencesUtils.loadStringSharedPreference(SHARED_PASSWORD);
                    if (userQrCode!=null||!userQrCode.equals("")) {
                        loginByQrCodePresenter.loginByQrCode(userQrCode);
                        showLoading();
                    } else if (userID!=null||!userID.equals("") & password!=null||!password.equals("")) {
                        loginByPasswordPresenter.loginByPassword(userID, password);
                        showLoading();
                    }
                }
                /**
                 * 已登录,没有监管,不用请求,直接到主界面
                 */
                else {
                    navigator.navigateToMainActivity(this);
                    finish();
                }
            } else {

                /**
                 * 启动页面延时效果
                 */
                Timer time = new Timer();
                TimerTask tk = new TimerTask() {
                    Intent intent = new Intent(DefaultActivity.this, LoginActivity.class);

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        startActivity(intent);
                        finish();
                    }
                };
                time.schedule(tk, 2500);
            }
        }
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

                } else {
                    new CircleDialog.Builder((FragmentActivity) mContext)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
     * 账号密码登录
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retLoginByPasswordView(boolean isSuccess, UserInfo result, String msg) {
        hideLoading();
        if (isSuccess) {
            if (result.getUseStatus().equals("0")) {
                navigator.navigateToMainActivity(this);
                finish();
            } else {
                workerStatusErrorDialog();
            }
        } else {
            if (msg != null)
                showToast(msg);
            if (retryCount >= 3) {
                sharedPreferencesUtils.saveSharedPreferences(SHARED_IS_LOGINED, false);
                showToast(R.string.login_retry_count);
                navigator.navigateToLoginActivity(this);
                finish();
            }else {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.alert_dialog_tips)
                        .setMessage(R.string.init_failed)
                        .setCancelable(false)
                        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setPositiveButton(R.string.alert_dialog_retry, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                retryCount++;
                                if ((userID!=null||!userID.equals("")) & (password!=null||!password.equals(""))) {
                                    loginByPasswordPresenter.loginByPassword(userID, password);
                                } else if (userQrCode!=null||!userQrCode.equals("")) {
                                    loginByQrCodePresenter.loginByQrCode(userQrCode);
                                }
                                showLoading();
                            }
                        }).show();
            }
        }
    }

    /**
     * 二维码登录
     *
     * @param isSuccess
     * @param result
     * @param msg
     */
    @Override
    public void retLoginByQrCodeView(boolean isSuccess, UserInfo result, final String msg) {
        hideLoading();
        if (isSuccess) {
            if (result.getUseStatus().equals("0")) {
                navigator.navigateToMainActivity(this);
                finish();
            } else {
                workerStatusErrorDialog();
            }
        } else {
            if (msg != null)
                showToast(msg);
            if (retryCount >= 3) {
                sharedPreferencesUtils.saveSharedPreferences(SHARED_IS_LOGINED, false);
                showToast(R.string.login_retry_count);
                navigator.navigateToLoginActivity(this);
                finish();
            } else {
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.alert_dialog_tips)
                        .setMessage(R.string.init_failed)
                        .setCancelable(false)
                        .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setPositiveButton(R.string.alert_dialog_retry, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                retryCount++;
                                if ((userID!=null||!userID.equals("")) & (password!=null||!password.equals(""))) {
                                    loginByPasswordPresenter.loginByPassword(userID, password);
                                } else if (userQrCode!=null||!userQrCode.equals("")) {
                                    loginByQrCodePresenter.loginByQrCode(userQrCode);
                                }
                                showLoading();
                            }
                        }).show();
            }
        }
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
}

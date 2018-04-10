package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerMainActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.MainActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.HomeFragment;
import com.future.zhh.ticket.presentation.view.fragments.MyFragment;
import com.future.zhh.ticket.presentation.view.widgets.ActivityIndicatorView;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
* 主页面
* */
public class MainActivity extends BaseActivity implements HomeFragment.HomeButtonClickListener, HasComponent<MainActivityComponent> {
    @BindView(R.id.ivMy)
    ImageView ivMy;
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.tvHome)
    TextView tvHome;
    @BindView(R.id.tvMy)
    TextView tvMy;
    @BindView(R.id.fab)
    TextView fab;

    public final static String TAG = LoginActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private ActivityIndicatorView activityIndicatorView;
    private MainActivityComponent component;

    private BaseFragment currentFragment;

    private int SELECT_BTN_ID = R.id.llHome;


    public static Intent getCallingIntent(Context context){
        return new Intent(context,MainActivity.class);
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerMainActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
    }

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext=this;
        init();
        initView();
    }


    private void init() {
        activityIndicatorView = new ActivityIndicatorView(mContext);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
    }
    private void initView() {
        ivHome.setImageResource(R.mipmap.sy_sy_xz);
        tvHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        currentFragment = HomeFragment.getInstance();
        addFragment(R.id.flContainer, HomeFragment.getInstance(), HomeFragment.TAG,false);
    }
    @OnClick({R.id.llHome,R.id.llMy,R.id.ivScanERcode})
    public void onClick(View view){
        if(SELECT_BTN_ID == view.getId()) return;
        switch (view.getId()){
            case R.id.llHome:
                SELECT_BTN_ID = R.id.llHome;
                ivHome.setImageResource(R.mipmap.sy_sy_xz);
                tvHome.setTextColor(getResources().getColor(R.color.center));
                ivMy.setImageResource(R.mipmap.sy_wd_mr);
                tvMy.setTextColor(getResources().getColor(R.color.bottom));
                if(currentFragment != null)
                    hideFragment(currentFragment,false);
                currentFragment = HomeFragment.getInstance();
                addFragment(R.id.flContainer, HomeFragment.getInstance(), HomeFragment.TAG,false);
                break;
            case R.id.llMy:
                SELECT_BTN_ID = R.id.llMy;
                ivHome.setImageResource(R.mipmap.sy_sy_mr);
                tvHome.setTextColor(getResources().getColor(R.color.bottom));
                ivMy.setImageResource(R.mipmap.xy_wd_xz);
                tvMy.setTextColor(getResources().getColor(R.color.center));
                if(currentFragment != null)
                    hideFragment(currentFragment,false);
                currentFragment = MyFragment.getInstance();
                addFragment(R.id.flContainer, MyFragment.getInstance(),MyFragment.TAG,false);
                break;
            case R.id.ivScanERcode:

                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 666);
                        return;
                    } else {
                        navigator.navigateToScanInfoActivity(this, ApplicationDatas.TAP_FROM_HOME_BUTTON);
                    }
                } else {
                    navigator.navigateToScanInfoActivity(this, ApplicationDatas.TAP_FROM_HOME_BUTTON);
                }
                break;
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
                    navigator.navigateToScanInfoActivity(this, ApplicationDatas.TAP_FROM_HOME_BUTTON);
                } else {
                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限被禁用")
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
    public void homeButtonClick(Intent nextAcitivtyIntent) {
        startActivity(nextAcitivtyIntent);
    }

}

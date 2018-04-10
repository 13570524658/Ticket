package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerDeliveryManagerActivityCompany;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerActivityCompany;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerFragment;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/24.
 *
 * 配送任务列表页面
 */

public class DeliveryManagerActivity extends BaseActivity implements HasComponent<DeliveryManagerActivityCompany>,ItemClickCallBack {
    @BindView(R.id.img_ibtnBack)
    ImageView imgIbtnBack;
    @BindView(R.id.ibtnBack)
    LinearLayout ibtnBack;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ibtnScan)
    ImageButton ibtnScan;
    @BindView(R.id.ibtnSearch)
    ImageButton ibtnSearch;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public final static String TAG = DeliveryManagerActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private DeliveryManagerActivityCompany component;

    private String[] tabTitles;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;


    public static Intent getCallingIntent(Context context){
        return new Intent(context,DeliveryManagerActivity.class);
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerDeliveryManagerActivityCompany.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
    }
    @Override
    public DeliveryManagerActivityCompany getComponent() {
        return component;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_task);
        mContext = this;
        ButterKnife.bind(this);
        initViewPage();
    }

    private void initViewPage() {
        tabTitles = mContext.getResources().getStringArray(R.array.distribution_item);
        for(int i=0; i<tabTitles.length; i++){

            DeliveryManagerFragment deliveryManagerFragment = new DeliveryManagerFragment();
            fragmentList.add(deliveryManagerFragment);
        }

        ((DeliveryManagerFragment)fragmentList.get(0)).setType("0");
        ((DeliveryManagerFragment)fragmentList.get(1)).setType("1");

        List<Map<String,Object>> listFg = new ArrayList<>();
        for(int i=0; i<fragmentList.size(); i++){
            Map<String,Object> mapFg = new HashMap<>();
            mapFg.put(SimplePageAdapter.TITLE,tabTitles[i]);
            mapFg.put(SimplePageAdapter.FRAGS,fragmentList.get(i));
            listFg.add(mapFg);
        }

        mSimplePageAdapter = new SimplePageAdapter(this.getSupportFragmentManager(),listFg,mContext);
        viewPager.setAdapter(mSimplePageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(mSimplePageAdapter);//给Tabs设置适配器
    }

    @SuppressLint("LongLogTag")
    @OnClick({R.id.ibtnSearch, R.id.ibtnBack, R.id.tvSearch,R.id.ibtnScan})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.ibtnSearch:
                showSearchLayout();
                break;
            case R.id.tvSearch:
                if (!etSearch.getText().toString().trim().equalsIgnoreCase("")){
                    Log.e("etSearch.getText().toString().trim()","______"+etSearch.getText().toString().trim());
                if(fragmentList != null){
                        ((DeliveryManagerFragment)fragmentList.get(0)).searchData(0,etSearch.getText().toString());
                           ((DeliveryManagerFragment)fragmentList.get(1)).searchData(1,etSearch.getText().toString());
                }}else {
                    showToast("请输入搜索内容");
                }
                break;
            case  R.id.ibtnScan:
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 666);
                        return;
                    } else {
                        navigator.navigateToScanInfoActivity(this, ApplicationDatas.TAP_FROM_JF_AND_HS);
                    }
                } else {
                    navigator.navigateToScanInfoActivity(this, ApplicationDatas.TAP_FROM_JF_AND_HS);
                }
                break;
            default:
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
                    navigator.navigateToScanInfoActivity(this, ApplicationDatas.TAP_FROM_JF_AND_HS);
                } else {


                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showSearchLayout() {
        if (llSearch.getVisibility() != View.VISIBLE){
            TranslateAnimation translateAnimation =
                    new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    llSearch.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            llSearch.startAnimation(translateAnimation);
            llSearch.setVisibility(View.VISIBLE);
        }else{
            TranslateAnimation translateAnimation =
                    new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,-1.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    llSearch.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            llSearch.startAnimation(translateAnimation);
            llSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int pos) {
//        showToast(pos+"activity");
    }
}

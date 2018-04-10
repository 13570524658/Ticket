package com.future.zhh.ticket.presentation.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.HomeIconModel;
import com.future.zhh.ticket.domain.model.enums.HomeCode;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.MainActivityComponent;
import com.future.zhh.ticket.presentation.listener.OnItemClickListener;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.activities.ByInputtingActivity;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerActivity;
import com.future.zhh.ticket.presentation.view.activities.MainActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanGasInfoActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanInfoActivity;
import com.future.zhh.ticket.presentation.view.adapters.HomeAdapter;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/21.
 */

public class HomeFragment extends BaseFragment implements OnItemClickListener<HomeIconModel> {
    public final static String TAG = HomeFragment.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private static HomeFragment fragment;
    private Unbinder unbinder;
    private View fragmentView;
    private HomeButtonClickListener homeButtonClickListener;
    private HomeAdapter homeAdapter;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private List<HomeIconModel> homeIconModelList;
    @BindView(R.id.gvHome)
    GridView gvHome;
    private String admin;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        return fragment;
    }


    public interface HomeButtonClickListener {
        void homeButtonClick(Intent nextAcitivtyIntent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainActivityComponent.class).inject(this);
        if (getActivity() instanceof MainActivity) {
            homeButtonClickListener = (MainActivity) getActivity();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = getActivity();
        init();
        initView();
        return fragmentView;
    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        admin = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_IS_ADMIN);
    }

    private void initView() {
        homeIconModelList = new ArrayList<>();
//        if (admin .equals("2")  || admin .equals("6")) {
            homeIconModelList.add(new HomeIconModel(HomeCode.DELIVERY_MANAGER.getId(), HomeCode.DELIVERY_MANAGER.getDescription(), HomeCode.DELIVERY_MANAGER.getIcon()));
//        }
//        if (admin .equals("5")) {
            homeIconModelList.add(new HomeIconModel(HomeCode.SET_UP_DATA.getId(), HomeCode.SET_UP_DATA.getDescription(), HomeCode.SET_UP_DATA.getIcon()));
//        }
//        if (admin.equals("2") || admin .equals("6")) {
            homeIconModelList.add(new HomeIconModel(HomeCode.CHECK_CUSTOMER.getId(), HomeCode.CHECK_CUSTOMER.getDescription(), HomeCode.CHECK_CUSTOMER.getIcon()));
//        }
        homeIconModelList.add(new HomeIconModel(HomeCode.JFHS.getId(), HomeCode.JFHS.getDescription(), HomeCode.JFHS.getIcon()));

        int k = homeIconModelList.size() % 3;
        int surplus = k == 0 ? 0 : 3 - k;
        for (int i = 0; i < surplus; i++) {
            homeIconModelList.add(new HomeIconModel(HomeCode.BLANK.getId(), HomeCode.BLANK.getDescription(), HomeCode.BLANK.getIcon()));
        }
        homeAdapter = new HomeAdapter(mContext);
        homeAdapter.setData(homeIconModelList);
        homeAdapter.setListener(this);
        gvHome.setAdapter(homeAdapter);

    }

    @Override
    public void onItemClick(HomeIconModel data) {
        if (HomeCode.DELIVERY_MANAGER.getId() == data.getHomeId()) {
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    HomeFragment.this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 333);
                    return;
                } else {
                    homeButtonClickListener.homeButtonClick(DeliveryManagerActivity.getCallingIntent(mContext));
                }
            } else {
                homeButtonClickListener.homeButtonClick(DeliveryManagerActivity.getCallingIntent(mContext));
            }
        }
        if (HomeCode.SET_UP_DATA.getId() == data.getHomeId()) {
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    HomeFragment.this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 444);
                    return;
                } else {
                    homeButtonClickListener.homeButtonClick(ByInputtingActivity.getCallingIntent(mContext));
                }
            } else {
                homeButtonClickListener.homeButtonClick(ByInputtingActivity.getCallingIntent(mContext));
            }
        }
        if (HomeCode.CHECK_CUSTOMER.getId() == data.getHomeId()) {
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    HomeFragment.this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 555);
                    return;
                } else {
                    homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_CHECK));
                }
            } else {
                homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_CHECK));
            }
        }
        if (HomeCode.JFHS.getId() == data.getHomeId()) {
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    HomeFragment.this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 666);
                    return;
                } else {
                    homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_SINCE_JFHS));
                }
            } else {
                homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_SINCE_JFHS));
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
            case 333:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    homeButtonClickListener.homeButtonClick(DeliveryManagerActivity.getCallingIntent(mContext));
                } else {
                    new CircleDialog.Builder((FragmentActivity) mContext)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
                break;
            case 444:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    homeButtonClickListener.homeButtonClick(ByInputtingActivity.getCallingIntent(mContext));
                } else {

                    new CircleDialog.Builder((FragmentActivity) mContext)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
                break;
            case 555:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_CHECK));
                } else {
                    new CircleDialog.Builder((FragmentActivity) mContext)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
                break;
            case 666:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_SINCE_JFHS));
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
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}

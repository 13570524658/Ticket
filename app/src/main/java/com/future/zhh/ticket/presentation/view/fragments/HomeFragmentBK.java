package com.future.zhh.ticket.presentation.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.MainActivityComponent;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.view.activities.ByInputtingActivity;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerActivity;
import com.future.zhh.ticket.presentation.view.activities.MainActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanInfoActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/21.
 */

public class HomeFragmentBK extends BaseFragment {
    public final static String TAG = HomeFragmentBK.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private static HomeFragmentBK fragment;
    private Unbinder unbinder;
    private View fragmentView;
    private HomeButtonClickListener homeButtonClickListener;

    public HomeFragmentBK() {
        // Required empty public constructor
    }

    public static HomeFragmentBK getInstance (){
        if(fragment == null){
            fragment = new HomeFragmentBK();
        }
        return fragment;
    }

    public interface HomeButtonClickListener{
        void homeButtonClick(Intent nextAcitivtyIntent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainActivityComponent.class).inject(this);
        if(getActivity() instanceof MainActivity){
//            homeButtonClickListener = (MainActivity)getActivity();

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_home_bk, container, false);
        ButterKnife.bind(this,fragmentView);
        mContext = getActivity();
        return fragmentView;
    }

    @OnClick({R.id.llDeliveryManager,R.id.llSetUpData,R.id.llCheckCustomer,R.id.llJfHs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDeliveryManager:
                homeButtonClickListener.homeButtonClick(DeliveryManagerActivity.getCallingIntent(mContext));
                break;
            case R.id.llSetUpData:
                homeButtonClickListener.homeButtonClick(ByInputtingActivity.getCallingIntent(mContext));
                break;
            case R.id.llCheckCustomer:
                homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext, ApplicationDatas.TAP_FROM_CHECK));
                break;
            case R.id.llJfHs:

                homeButtonClickListener.homeButtonClick(ScanInfoActivity.getCallingIntent(mContext,ApplicationDatas.TAP_FROM_SINCE_JFHS));


                break;
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

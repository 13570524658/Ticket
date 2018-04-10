package com.future.zhh.ticket.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.future.zhh.ticket.presentation.view.activities.AboutActivity;
import com.future.zhh.ticket.presentation.view.activities.CarMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.CustomerJfHsMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.CustomerMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerActivity;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.GuidePageActivity;
import com.future.zhh.ticket.presentation.view.activities.JFHSGasScanLogActivity;
import com.future.zhh.ticket.presentation.view.activities.LoginActivity;
import com.future.zhh.ticket.presentation.view.activities.MainActivity;
import com.future.zhh.ticket.presentation.view.activities.OrderJfHsMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.OrderMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanInfoActivity;
import com.future.zhh.ticket.presentation.view.activities.WorkerMsgActivity;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/11/19.
 */

public class Navigator {
    @Inject
    public Navigator() {}

    public void navigateToGuidePageActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = GuidePageActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToLoginActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = LoginActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }

    public void navigateToMainActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }

    public void navigateToScanInfoActivity(Context mContext,int tapFrom){
        if (mContext != null) {
            Intent intentToLaunch = ScanInfoActivity.getCallingIntent(mContext,tapFrom);
            mContext.startActivity(intentToLaunch);
        }
    }

    public void navigateToJFHSGasScanLogActivity(Context mContext,int tapFrom){
        if (mContext != null) {
            Intent intentToLaunch = JFHSGasScanLogActivity.getCallingIntent(mContext,tapFrom);
            mContext.startActivity(intentToLaunch);
        }
    }


    public void navigateToDeliveryManagerActivity(Context mContext ){
        if (mContext != null) {
            Intent intentToLaunch = DeliveryManagerActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToDeliveryManagerMsgActivity(Context mContext ){
        if (mContext != null) {
            Intent intentToLaunch = DeliveryManagerMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToOrderJfHsMsgActivity(Context mContext ){
        if (mContext != null) {
            Intent intentToLaunch = OrderJfHsMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToCustomerJfHsMsgActivity(Context mContext ){
        if (mContext != null) {
            Intent intentToLaunch = CustomerJfHsMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }


    public void navigateToCarMsgActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = CarMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToCustomerMsgActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = CustomerMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToGasMsgActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = GasMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToOrderMsgActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = OrderMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
    public void navigateToWorkerMsgActivity(Context mContext){
        if (mContext != null) {
            Intent intentToLaunch = WorkerMsgActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }

    public void navigateToAboutActivity(Context mContext ){
        if (mContext != null) {
            Intent intentToLaunch = AboutActivity.getCallingIntent(mContext);
            mContext.startActivity(intentToLaunch);
        }
    }
}

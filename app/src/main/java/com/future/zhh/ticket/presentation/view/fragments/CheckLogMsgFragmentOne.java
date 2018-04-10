package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CheckLogDetailInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CheckLogDetailPresenter;
import com.future.zhh.ticket.presentation.view.CheckLogDetailView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/13.
 */

public class CheckLogMsgFragmentOne extends BaseFragment implements CheckLogDetailView {

    @BindView(R.id.tv_isAirVents)
    TextView tvIsAirVents;
    @BindView(R.id.tv_isSafetyWarning)
    TextView tvIsSafetyWarning;
    @BindView(R.id.tv_isAlarm)
    TextView tvIsAlarm;
    @BindView(R.id.tv_isTrueAlarm)
    TextView tvIsTrueAlarm;
    @BindView(R.id.tv_isFireEquipment)
    TextView tvIsFireEquipment;
    @BindView(R.id.tv_outTimeGasTotal)
    TextView tvOutTimeGasTotal;
    @BindView(R.id.tv_scrappingGasTotal)
    TextView tvScrappingGasTotal;
    @BindView(R.id.tv_gasLocation)
    TextView tvGasLocation;
    @BindView(R.id.tv_spacing)
    TextView tvSpacing;
    @BindView(R.id.tv_isNearStove)
    TextView tvIsNearStove;
    @BindView(R.id.tv_isAirLeakageValve)
    TextView tvIsAirLeakageValve;
    @BindView(R.id.tv_isProtectionDeviceStove)
    TextView tvIsProtectionDeviceStove;
    @BindView(R.id.tv_isToolUseYearStove)
    TextView tvIsToolUseYearStove;
    @BindView(R.id.tv_isSmokeRoad)
    TextView tvIsSmokeRoad;
    @BindView(R.id.tv_hotWaterToolModule)
    TextView tvHotWaterToolModule;
    @BindView(R.id.tv_isHotWaterToolUseYear)
    TextView tvIsHotWaterToolUseYear;
    @BindView(R.id.tv_isVoltageRegulatorAirLeakage)
    TextView tvIsVoltageRegulatorAirLeakage;
    @BindView(R.id.tv_isConnectingPipeAging)
    TextView tvIsConnectingPipeAging;
    @BindView(R.id.tv_checkPersion)
    TextView tvCheckPersion;
    @BindView(R.id.tv_checkTime)
    TextView tvCheckTime;
    @BindView(R.id.iv_checkImageOne)
    ImageView ivCheckImageOne;
    @BindView(R.id.iv_checkImageTwo)
    ImageView ivCheckImageTwo;
    @BindView(R.id.iv_checkImageThree)
    ImageView ivCheckImageThree;
    @BindView(R.id.tv_otherRemark)
    TextView tvOtherRemark;
    @BindView(R.id.tv_isPass)
    TextView tvIsPass;
    private View fragmentView;
    private Context mContext;
    private static CheckLogMsgFragmentOne fragment;
    private String index;
    private String customerName;
    private String customerID;
    @Inject
    CheckLogDetailPresenter checkLogDetailPresenter;

    public static CheckLogMsgFragmentOne getInstance() {
        if (fragment == null) {
            fragment = new CheckLogMsgFragmentOne();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CheckLogMsgActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_check_log_msg_one, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }


    private void init() {
        checkLogDetailPresenter.setView(this);

    }

    private void initView() {
        checkLogDetailPresenter.checkLogDetail(customerName, customerID);
    }


    @Override
    protected void lazyLoad() {

    }

    public void setType(String index, String customerName, String customerID) {
        this.index = index;
        this.customerName = customerName;
        this.customerID = customerID;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retCheckLogDetailView(boolean isSuccess, CheckLogDetailInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getIsAirVents().equalsIgnoreCase("0")) {
                    tvIsAirVents.setText("是");
                } else if (result.getIsAirVents().equalsIgnoreCase("1")) {
                    tvIsAirVents.setText("否");
                }
                if (result.getIsSafetyWarning().equalsIgnoreCase("0")) {
                    tvIsSafetyWarning.setText("是");
                } else if (result.getIsSafetyWarning().equalsIgnoreCase("1")) {
                    tvIsSafetyWarning.setText("否");
                }
                if (result.getIsAlarm().equalsIgnoreCase("0")) {
                    tvIsAlarm.setText("是");
                } else if (result.getIsAlarm().equalsIgnoreCase("1")) ;
                {
                    tvIsAlarm.setText("否");
                }
                if (result.getIsTrueAlarm().equalsIgnoreCase("0")) {
                    tvIsTrueAlarm.setText("是");
                } else if (result.getIsTrueAlarm().equalsIgnoreCase("1")) {
                    tvIsTrueAlarm.setText("否");
                }
                if (result.getIsFireEquipment().equalsIgnoreCase("0")) {
                    tvIsFireEquipment.setText("是");
                } else if (result.getIsFireEquipment().equalsIgnoreCase("1")) {
                    tvIsFireEquipment.setText("否");
                }

                tvOutTimeGasTotal.setText(String.valueOf(result.getOutTimeGasTotal()));
                tvScrappingGasTotal.setText(String.valueOf(result.getScrapGasTotal()));
                tvGasLocation.setText(result.getGasLocation());
                if (result.getSpacing().equalsIgnoreCase("0")){
                    tvSpacing.setText("1m");
                }else if (result.getSpacing().equalsIgnoreCase("1")){
                    tvSpacing.setText("2m");
                }else if (result.getSpacing().equalsIgnoreCase("2")){
                    tvSpacing.setText("2m以上");
                }
            if (result.getIsNearStove().equalsIgnoreCase("0")){
                tvIsNearStove.setText("是");
            }else if (result.getIsNearStove().equalsIgnoreCase("1")){
                tvIsNearStove.setText("否");
            }
            if (result.getIsAirLeakageValue().equalsIgnoreCase("0")){
                tvIsAirLeakageValve.setText("是");
            }else if (result.getIsAirLeakageValue().equalsIgnoreCase("1")){
                tvIsAirLeakageValve.setText("否");
            }
            if (result.getIsProctectionDeviceStove().equalsIgnoreCase("0")){
                tvIsProtectionDeviceStove.setText("是");
            }else if (result.getIsProctectionDeviceStove().equalsIgnoreCase("1")){
                tvIsProtectionDeviceStove.setText("否");
            }

            if (result.getIsToolUseYearStove().equalsIgnoreCase("0")){
                tvIsToolUseYearStove.setText("是");
            }else if (result.getIsToolUseYearStove().equalsIgnoreCase("1")){
                tvIsToolUseYearStove.setText("否");
            }
            if (result.getIsSmokeRoad().equalsIgnoreCase("0")){
                tvIsSmokeRoad.setText("是");
            }
            else if (result.getIsSmokeRoad().equalsIgnoreCase("1")){
                tvIsSmokeRoad.setText("否");
            }

                tvHotWaterToolModule.setText(result.getHotWaterToolModule());
            if (result.getIsHotwaterToolUseYear().equalsIgnoreCase("0")){
                tvIsHotWaterToolUseYear.setText("是");
            }else if (result.getIsHotwaterToolUseYear().equalsIgnoreCase("1")){
                tvIsHotWaterToolUseYear.setText(result.getIsHotwaterToolUseYear());
                tvIsHotWaterToolUseYear.setText("否");
            }
            if (result.getIsVoltageRegulatorAirLeakage().equalsIgnoreCase("0")){
                tvIsVoltageRegulatorAirLeakage.setText("是");
            }else if (result.getIsVoltageRegulatorAirLeakage().equalsIgnoreCase("1")){
                tvIsVoltageRegulatorAirLeakage.setText("否");
            }
        if (result.getIsConnectingPipeAping().equalsIgnoreCase("0")){
            tvIsConnectingPipeAging.setText("是");
        }else if (result.getIsConnectingPipeAping().equalsIgnoreCase("1")){
            tvIsConnectingPipeAging.setText("否");
        }

                if (result.getCheckImageOne()!=null&&!result.getCheckImageOne().equalsIgnoreCase("")){
                ivCheckImageOne.setVisibility(View.VISIBLE);
                ivCheckImageOne.setImageBitmap(convertStringToIcon(result.getCheckImageOne()));}
                if (result.getCheckImageTwo()!=null&&!result.getCheckImageTwo().equalsIgnoreCase("")){
                    ivCheckImageTwo.setVisibility(View.VISIBLE);
                ivCheckImageTwo.setImageBitmap(convertStringToIcon(result.getCheckImageTwo()));}
                if (result.getCheckImageThree()!=null&&!result.getCheckImageThree().equalsIgnoreCase("")){
                    ivCheckImageThree.setVisibility(View.VISIBLE);
                ivCheckImageThree.setImageBitmap(convertStringToIcon(result.getCheckImageThree()));}
                tvOtherRemark.setText(result.getOtherRemark());
                if (result.getIsPass().equals("0")) {
                    tvIsPass.setText("合格");
                    tvIsPass.setTextColor(Color.GREEN);
                } else if (result.getIsPass().equals("1")) {
                    tvIsPass.setText("不合格");
                    tvIsPass.setTextColor(Color.RED);
                }
                tvCheckPersion.setText(result.getCheckPersion());
                tvCheckTime.setText(result.getCheckTime());

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
}

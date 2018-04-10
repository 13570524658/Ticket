package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceYesStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceByOderPresenter;
import com.future.zhh.ticket.presentation.view.SinceByOderView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceYesStateOrderFragmentOne extends BaseFragment implements SinceByOderView {


    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_transportType)
    TextView tvTransportType;
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerAddress)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_customPhone)
    TextView tvCustomPhone;
    @BindView(R.id.tv_customCardNo)
    TextView tvCustomCardNo;
    @BindView(R.id.tv_station)
    TextView tvStation;
    @BindView(R.id.tv_orderTime)
    TextView tvOrderTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    private View fragmentView;
    private Context mContext;
    private static SinceYesStateOrderFragmentOne fragment;
    private String s = "";
    private String orderID = "";
    @Inject
    SinceByOderPresenter sinceByOderPresenter;

    public static SinceYesStateOrderFragmentOne getInstance() {
        if (fragment == null) {
            fragment = new SinceYesStateOrderFragmentOne();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getComponent(SinceYesStateOrderActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_one_sine_yes_state_order, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }


    private void init() {
        sinceByOderPresenter.setView(this);

    }

    private void initView() {
        sinceByOderPresenter.sinceByOder(orderID);
    }


    @Override
    protected void lazyLoad() {

    }

    public void setType(String s, String orderID) {
        this.s = s;
        this.orderID = orderID;
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
    public void retSinceByOderView(boolean isSuccess, SinceByOrderInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getState().equals("0")){
                tvOrderID.setText(result.getOrderID());
                tvFrom.setText(result.getFrom());
                tvTransportType.setText(result.getType());
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerAddress.setText(result.getAddress());
                tvCustomPhone.setText(result.getPhone());
                tvCustomCardNo.setText(result.getCustomerCardNo());
                tvStation.setText(result.getStation());
                tvOrderTime.setText(result.getOrderTime());
                tvEndTime.setText(result.getEndTime());
                tvState.setText("已完成");
                }
            }
        } else {
            showToast(msg);
        }
    }
}

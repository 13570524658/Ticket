package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.TransportTaskDetailInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerMsgActivityCompany;
import com.future.zhh.ticket.presentation.presenters.TransportTaskDetailPresenter;
import com.future.zhh.ticket.presentation.view.TransportTaskDetailView;
import com.future.zhh.ticket.presentation.view.activities.CustomerMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.OrderMsgActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/25.
 */

public class DeliveryManagerMsgFragmentOne extends BaseFragment implements TransportTaskDetailView {
    @BindView(R.id.tv_transportTaskID)
    TextView tvTransportTaskID;
    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.transportType)
    TextView transportType;
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerAddress)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_customerPhone)
    TextView tvCustomerPhone;
    @BindView(R.id.tv_customerCardID)
    TextView tvCustomerCardID;
    @BindView(R.id.tv_station)
    TextView tvStation;
    @BindView(R.id.tv_creatTime)
    TextView tvCreatTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    private String type;
    private String transportID;
    private String transportTaskID;

    private static DeliveryManagerMsgFragmentOne fragment;
    private View fragmentView;
    private Context mContext;

    @Inject
    TransportTaskDetailPresenter transportTaskDetailPresenter;

    public static DeliveryManagerMsgFragmentOne getInstance() {
        if (fragment == null) {
            fragment = new DeliveryManagerMsgFragmentOne();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getComponent(DeliveryManagerMsgActivityCompany.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_one_delivery_manager_msg, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }

    private void init() {
        transportTaskDetailPresenter.setView(this);
    }

    private void initView() {
        transportTaskDetailPresenter.transportTaskDetail(transportID, transportTaskID);
        tvOrderID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderMsgActivity.class);
                intent.putExtra("orderID", tvOrderID.getText().toString());
                mContext.startActivity(intent);
            }
        });
        tvCustomerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CustomerMsgActivity.class);
                intent.putExtra("customerID",tvCustomerID .getText().toString());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    public void setType(String type, String transportID, String transportTaskID) {
        this.type = type;
        this.transportID = transportID;
        this.transportTaskID = transportTaskID;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retTransportTaskDetailView(boolean isSuccess, TransportTaskDetailInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                tvTransportTaskID.setText(result.getTransportTaskID());
                tvOrderID.setText(result.getOrderID());
                transportType.setText(result.getTransportType());
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerAddress.setText(result.getAddress1());
                tvCustomerPhone.setText(result.getPhone1());
                tvCustomerCardID.setText(result.getCustomerCardID());
                tvStation.setText(result.getGasStation());
                tvCreatTime.setText(result.getTransportStartTime());
                tvEndTime.setText(result.getTransportEndTime());
            }
        } else {
            showToast(msg);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
    }
}

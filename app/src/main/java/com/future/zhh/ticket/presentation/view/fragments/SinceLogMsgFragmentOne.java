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
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceTaskMsgPresenter;
import com.future.zhh.ticket.presentation.view.SinceTaskMsgView;
import com.future.zhh.ticket.presentation.view.activities.CustomerMsgActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceLogMsgFragmentOne extends BaseFragment implements SinceTaskMsgView {

    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_orderFrom)
    TextView tvOrderFrom;
    @BindView(R.id.tv_transportType)
    TextView tvTransportType;
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerPhone)
    TextView tvCustomerPhone;
    @BindView(R.id.tv_customerCardNo)
    TextView tvCustomerCardNo;
    @BindView(R.id.tv_gastion)
    TextView tvGastion;
    @BindView(R.id.tv_orderTime)
    TextView tvOrderTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.tv_customerAddress)
    TextView tvCustomerAddress;
    private View fragmentView;
    private Context mContext;

    private static SinceLogMsgFragmentOne fragment;
    private String s;
    private String orderID;
    @Inject
    SinceTaskMsgPresenter sinceTaskMsgPresenter;

    public static SinceLogMsgFragmentOne getInstance() {
        if (fragment == null) {
            fragment = new SinceLogMsgFragmentOne();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getComponent(SinceLogMsgActivityComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_one_since_log_msg, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }


    private void init() {
        sinceTaskMsgPresenter.setView(this);
    }

    private void initView() {
        sinceTaskMsgPresenter.sinceTaskMsg(orderID);
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
    public void retSinceTaskMsgView(boolean isSuccess, final SinceTaskMsgInfo result, final String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getState()==0){
                tvOrderID.setText(result.getOrderID());
                tvState.setText("已完成");
                tvOrderFrom.setText(result.getFrom());
                tvTransportType.setText(result.getTranSportType());
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerAddress.setText(result.getAddress());
                tvCustomerPhone.setText(result.getPhone());
                tvCustomerCardNo.setText(result.getCustomerCardNo());
                tvGastion.setText(result.getStation());
                tvOrderTime.setText(result.getOrderTime());
                tvEndTime.setText(result.getEndTime());
                    tvCustomerID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(mContext, CustomerMsgActivity.class);
                            intent.putExtra("customerID",result.getCustomerID());
                            mContext.startActivity(intent);
                        }
                    });

                }
            }
        } else {
            showToast(msg);
        }
    }
}

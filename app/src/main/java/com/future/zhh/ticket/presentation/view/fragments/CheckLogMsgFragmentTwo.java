package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CheckGasLabelAndType;
import com.future.zhh.ticket.domain.model.CheckLogDetailInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CheckLogDetailPresenter;
import com.future.zhh.ticket.presentation.view.CheckLogDetailView;
import com.future.zhh.ticket.presentation.view.activities.CustomerMsgActivity;
import com.future.zhh.ticket.presentation.view.adapters.CheckLogMsgAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/13.
 */

public class CheckLogMsgFragmentTwo extends BaseFragment implements CheckLogDetailView {

    private static CheckLogMsgFragmentTwo fragment;
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_customerCardNo)
    TextView tvCustomerCardNo;
    @BindView(R.id.tv_station)
    TextView tvStation;
    @BindView(R.id.tv_checkGasTotal)
    TextView tvCheckGasTotal;
    @BindView(R.id.rv_gaslabeMsg)
    RecyclerView rvGaslabeMsg;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    private View fragmentView;
    private Context mContext;

    private String index;
    private String customerName;
    private String customerID;
    List<CheckGasLabelAndType> checkGasLabelAndTypeList;

    @Inject
    CheckLogDetailPresenter checkLogDetailPresenter;

    public static CheckLogMsgFragmentTwo getInstance() {
        if (fragment == null) {
            fragment = new CheckLogMsgFragmentTwo();
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
        fragmentView = inflater.inflate(R.layout.fragment_check_log_msg_two, container, false);
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
    public void retCheckLogDetailView(boolean isSuccess, CheckLogDetailInfo result, final String msg) {
        if (isSuccess) {
            if (result != null) {
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, CustomerMsgActivity.class);
                        intent.putExtra("customerID",tvCustomerID.getText().toString());
                        mContext.startActivity(intent);
                    }
                });
                tvAddress.setText(result.getAddress());
                tvPhone.setText(result.getPhone());
                tvCustomerCardNo.setText(result.getCustomerCardNo());
                tvStation.setText(result.getStation());
                tvCheckGasTotal.setText(result.getGasTotal());

                checkGasLabelAndTypeList = result.getCheckGasLabelAndTypeList();
                if (checkGasLabelAndTypeList.size()>0){
                llTable.setVisibility(View.VISIBLE);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvGaslabeMsg.setLayoutManager(layoutManager);


                CheckLogMsgAdapter checkLogMsgAdapter = new CheckLogMsgAdapter(checkGasLabelAndTypeList, mContext);
                rvGaslabeMsg.setAdapter(checkLogMsgAdapter);
                checkLogMsgAdapter.notifyDataSetChanged();
            }
        } else {
            showToast(msg);
        }
    }
}

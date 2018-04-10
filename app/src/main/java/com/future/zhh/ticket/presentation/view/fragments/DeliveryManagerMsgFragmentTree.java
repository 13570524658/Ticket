package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
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
import com.future.zhh.ticket.domain.model.ReclaimGasInfo;
import com.future.zhh.ticket.domain.model.TransportTaskDetailInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerMsgActivityCompany;
import com.future.zhh.ticket.presentation.presenters.TransportTaskDetailPresenter;
import com.future.zhh.ticket.presentation.view.TransportTaskDetailView;
import com.future.zhh.ticket.presentation.view.adapters.DeliveryManagerMsgHSAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/25.
 */

public class DeliveryManagerMsgFragmentTree extends BaseFragment implements TransportTaskDetailView {

    @BindView(R.id.task_total)
    TextView taskTotal;
    @BindView(R.id.tv_hsGasTotal)
    TextView tvHsGasTotal;
    @BindView(R.id.ll_titel)
    LinearLayout llTitel;
    @BindView(R.id.rv_hsGas)
    RecyclerView rvHsGas;
    @BindView(R.id.tv_jfGasLabel)
    TextView tvJfGasLabel;
    @BindView(R.id.tv_jsGasModule)
    TextView tvJsGasModule;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    private String type;
    private String transportID;
    private String transportTaskID;
    private Unbinder unbinder;
    @Inject
    TransportTaskDetailPresenter transportTaskDetailPresenter;

    private List<ReclaimGasInfo> reclaimGasInfoList;

    private static DeliveryManagerMsgFragmentTree fragment;
    private DeliveryManagerMsgHSAdapter deliveryManagerMsgHSAdapter;
    private ArrayList<String> listData;

    private View fragmentView;
    private Context mContext;


    public static DeliveryManagerMsgFragmentTree getInstance() {
        if (fragment == null) {
            fragment = new DeliveryManagerMsgFragmentTree();
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
        fragmentView = inflater.inflate(R.layout.fragment_tree_delivery_manager_msg, container, false);
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
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
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
                if (result.getReclaimGasInfoList().size() == 0) {
                    llTitel.setVisibility(View.GONE);
                    llTable.setVisibility(View.GONE);
                } else if (result.getReclaimGasInfoList().size() > 0) {
                    llTitel.setVisibility(View.VISIBLE);
                    llTable.setVisibility(View.VISIBLE);
                    reclaimGasInfoList = result.getReclaimGasInfoList();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvHsGas.setLayoutManager(layoutManager);
                    tvHsGasTotal.setText(String.valueOf(result.getReclaimTotal()));
                    deliveryManagerMsgHSAdapter = new DeliveryManagerMsgHSAdapter(reclaimGasInfoList, mContext);
                    rvHsGas.setAdapter(deliveryManagerMsgHSAdapter);
                    deliveryManagerMsgHSAdapter.notifyDataSetChanged();
                }
            }
        } else {
            showToast(msg);
        }
    }
}

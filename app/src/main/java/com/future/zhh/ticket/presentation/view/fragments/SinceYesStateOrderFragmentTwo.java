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
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.domain.model.SincePayGasInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceYesStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceByOderPresenter;
import com.future.zhh.ticket.presentation.view.SinceByOderView;
import com.future.zhh.ticket.presentation.view.adapters.SincePayGasInfoListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceYesStateOrderFragmentTwo extends BaseFragment implements SinceByOderView {
    private static SinceYesStateOrderFragmentTwo fragment;
    @BindView(R.id.tv_gas_pay_total)
    TextView tvGasPayTotal;
    @BindView(R.id.rv_gasRecycling)
    RecyclerView rvGasPay;
    @BindView(R.id.ll_Total)
    LinearLayout llTotal;


    private View fragmentView;
    private Context mContext;
    private String s;
    private String orderID;

    private List<SincePayGasInfo> sincePayGasInfoList;
    private SincePayGasInfoListAdapter sincePayGasInfoListAdapter;
    @Inject
    SinceByOderPresenter sinceByOderPresenter;

    public static SinceYesStateOrderFragmentTwo getInstance() {
        if (fragment == null) {
            fragment = new SinceYesStateOrderFragmentTwo();
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
        fragmentView = inflater.inflate(R.layout.fragment_two_sine_yes_state_order, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }

    private void initView() {
        sinceByOderPresenter.sinceByOder(orderID);
    }


    private void init() {
        sinceByOderPresenter.setView(this);
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
                if (result.getPayGasInfoList() == null) {
                    llTotal.setVisibility(View.GONE);
                }else       if (result.getPayGasInfoList() != null && result.getRecyclingGasInfoList().size() > 0) {
                    tvGasPayTotal.setText(result.getPayTotal());
                    sincePayGasInfoList = result.getPayGasInfoList();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvGasPay.setLayoutManager(layoutManager);

                    sincePayGasInfoListAdapter = new SincePayGasInfoListAdapter(sincePayGasInfoList, mContext);
                    rvGasPay.setAdapter(sincePayGasInfoListAdapter);
                    sincePayGasInfoListAdapter.notifyDataSetChanged();
                }
            }
        } else {
            showToast(msg);
        }
    }
}

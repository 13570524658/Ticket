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
import com.future.zhh.ticket.domain.model.RecyclingGasInfo;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceYesStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceByOderPresenter;
import com.future.zhh.ticket.presentation.view.SinceByOderView;
import com.future.zhh.ticket.presentation.view.adapters.SinceRecyclingGasInfoListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceYesStateOrderFragmentTree extends BaseFragment implements SinceByOderView {

    private static SinceYesStateOrderFragmentTree fragment;
    @BindView(R.id.tv_gas_recycling_total)
    TextView tvGasRecyclingTotal;
    @BindView(R.id.rv_gasRecycling)
    RecyclerView rvGasRecycling;
    @BindView(R.id.ll_Total)
    LinearLayout llTotal;
    private View fragmentView;
    private Context mContext;
    private String s;
    private String orderID;
    private SinceRecyclingGasInfoListAdapter sinceRecyclingGasInfoListAdapter;
    private List<RecyclingGasInfo> sinceRecyclingGasInfoList;
    @Inject
    SinceByOderPresenter sinceByOderPresenter;

    public static SinceYesStateOrderFragmentTree getInstance() {
        if (fragment == null) {
            fragment = new SinceYesStateOrderFragmentTree();
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
        fragmentView = inflater.inflate(R.layout.fragment_tree_sine_yes_state_order, container, false);
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
                if (result.getRecyclingGasInfoList() == null) {
                    llTotal.setVisibility(View.GONE);
                } else if (result.getRecyclingGasInfoList() != null) {
                    tvGasRecyclingTotal.setText(result.getRecyclingTotal());


                    sinceRecyclingGasInfoList = result.getRecyclingGasInfoList();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvGasRecycling.setLayoutManager(layoutManager);

                    sinceRecyclingGasInfoListAdapter = new SinceRecyclingGasInfoListAdapter(sinceRecyclingGasInfoList, mContext);
                    rvGasRecycling.setAdapter(sinceRecyclingGasInfoListAdapter);
                    sinceRecyclingGasInfoListAdapter.notifyDataSetChanged();
                }

            }
        } else {
            showToast(msg);
        }
    }
}

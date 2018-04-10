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
import com.future.zhh.ticket.domain.model.RecyclingGasListInfo;
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceTaskMsgPresenter;
import com.future.zhh.ticket.presentation.view.SinceTaskMsgView;
import com.future.zhh.ticket.presentation.view.adapters.SinceLogMsgTwoAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceLogMsgFragmentTree extends BaseFragment implements SinceTaskMsgView {
    private static SinceLogMsgFragmentOne fragment;
    @BindView(R.id.rv_gasReclaimList)
    RecyclerView rvGasReclaimList;
    @BindView(R.id.tv_ReclaimGasTotal)
    TextView tvReclaimGasTotal;
    @BindView(R.id.ll_titel)
    LinearLayout llTitel;
    @BindView(R.id.tv_jfGasLabel)
    TextView tvJfGasLabel;
    @BindView(R.id.tv_jsGasModule)
    TextView tvJsGasModule;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    private View fragmentView;
    private Context mContext;
    private String s;
    private String orderID;
    private SinceLogMsgTwoAdapter sinceLogMsgTwoAdapter;
    @Inject
    SinceTaskMsgPresenter sinceTaskMsgPresenter;

    private List<RecyclingGasListInfo> reclaimGasInfoList;

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
        fragmentView = inflater.inflate(R.layout.fragment_tree_since_log_msg, container, false);
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
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retSinceTaskMsgView(boolean isSuccess, SinceTaskMsgInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getPayGasInfoList().size() == 0) {
                    llTitel.setVisibility(View.GONE);
                    llTable.setVisibility(View.GONE);
                } else if (result.getPayGasInfoList().size() > 0) {
                    llTitel.setVisibility(View.VISIBLE);
                    llTable.setVisibility(View.VISIBLE);
                    reclaimGasInfoList = result.getRecyclingGasInfoList();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvGasReclaimList.setLayoutManager(layoutManager);

                    tvReclaimGasTotal.setText(String.valueOf(reclaimGasInfoList.size()));
                    sinceLogMsgTwoAdapter = new SinceLogMsgTwoAdapter(reclaimGasInfoList, mContext);
                    rvGasReclaimList.setAdapter(sinceLogMsgTwoAdapter);
                    sinceLogMsgTwoAdapter.notifyDataSetChanged();
                }
            }
        } else {
            showToast(msg);
        }
    }
}

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
import com.future.zhh.ticket.domain.model.PayGasListInfo;
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceTaskMsgPresenter;
import com.future.zhh.ticket.presentation.view.SinceTaskMsgView;
import com.future.zhh.ticket.presentation.view.adapters.SinceLogMsgAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceLogMsgFragmentTwo extends BaseFragment implements SinceTaskMsgView {

    private static SinceLogMsgFragmentOne fragment;
    @BindView(R.id.rv_gaspayList)
    RecyclerView rvGaspayList;
    @BindView(R.id.tv_payGasTotal)
    TextView tvPayGasTotal;
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
    @Inject
    SinceTaskMsgPresenter sinceTaskMsgPresenter;
    List<PayGasListInfo> payGasListInfoList;
    SinceLogMsgAdapter sinceLogMsgAdapter;

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
        fragmentView = inflater.inflate(R.layout.fragment_two_since_log_msg, container, false);
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
                    payGasListInfoList = result.getPayGasInfoList();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvGaspayList.setLayoutManager(layoutManager);

//            listData = new ArrayList<String>();
//            for(int i = 0; i < 15;i++){
//                listData.add("item" + i);
//            }
                    tvPayGasTotal.setText(String.valueOf(payGasListInfoList.size()));
                    sinceLogMsgAdapter = new SinceLogMsgAdapter(payGasListInfoList, mContext);
                    rvGaspayList.setAdapter(sinceLogMsgAdapter);
                    sinceLogMsgAdapter.notifyDataSetChanged();
                }
            }
        } else {
            showToast(msg);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.SinceByCustomerInfo;
import com.future.zhh.ticket.domain.model.SinceByCustomerListInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerSinceCustomerActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceCustomerActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceByCustomerPresenter;
import com.future.zhh.ticket.presentation.view.SinceByCustomerView;
import com.future.zhh.ticket.presentation.view.adapters.SinceCustomerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceCustomerActivity extends BaseActivity implements SinceByCustomerView {
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerCardID)
    TextView tvCustomerCardID;
    @BindView(R.id.tv_station)
    TextView tvStation;
    @BindView(R.id.rv_title)
    LinearLayout rvTitle;
    @BindView(R.id.rv_view)
    RecyclerView rvView;
    @BindView(R.id.img_ibtnBack)
    ImageView imgIbtnBack;
    @BindView(R.id.ibtnBack)
    LinearLayout ibtnBack;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ibtnSetting)
    ImageButton ibtnSetting;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    private SinceCustomerActivityComponent component;
    @Inject
    SinceByCustomerPresenter sinceByCustomerPresenter;
    Context mContext;
    private String customerCardNo = "";
    private List<SinceByCustomerListInfo> sinceByCustomerListInfoList;
    private SinceCustomerAdapter sinceCustomerAdapter;

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerSinceCustomerActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_since_customer);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }


    private void init() {
        sinceByCustomerPresenter.setView(this);
        if (getIntent() != null) {
            customerCardNo = getIntent().getStringExtra("customerCardNo");
        }
    }

    private void initView() {
        sinceByCustomerPresenter.sinceByCustomer(customerCardNo);
    }

    @OnClick({R.id.ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retSinceByCustomerView(boolean isSuccess, SinceByCustomerInfo result, final String msg) {
        if (isSuccess) {
            if (result != null) {
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerCardNo());
                tvCustomerID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext,CustomerMsgActivity.class);
                        intent.putExtra("customerID",tvCustomerID.getText().toString().trim());
                        mContext.startActivity(intent);
                    }
                });
                tvCustomerCardID.setText(result.getCustomerCardNo());
                tvStation.setText(result.getStation());

                if (result.getSinceByCustomerInfoList()!=null&&result.getSinceByCustomerInfoList().size()>0) {
                    sinceByCustomerListInfoList = result.getSinceByCustomerInfoList();
                    rvTitle.setVisibility(View.VISIBLE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rvView.setLayoutManager(layoutManager);

                    sinceCustomerAdapter = new SinceCustomerAdapter(sinceByCustomerListInfoList, mContext);
                    rvView.setAdapter(sinceCustomerAdapter);
                    sinceCustomerAdapter.notifyDataSetChanged();
                } else {
                    rvTitle.setVisibility(View.GONE);
                }
            }
        } else {
            showToast(msg);
        }
    }
}

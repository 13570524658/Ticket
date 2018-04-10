package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.SinceByOrderInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerSinceNotStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceNotStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceByOderPresenter;
import com.future.zhh.ticket.presentation.view.SinceByOderView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4.
 * 自提订单未完成状态
 */

public class SinceNotStateOrderActivity extends BaseActivity implements SinceByOderView {

    @Inject
    SinceByOderPresenter sinceByOderPresenter;
    @BindView(R.id.tv_from)
    TextView tvFrom;
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

    private SinceNotStateOrderActivityComponent component;

    Context mContext;
    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_transportType)
    TextView tvTransportType;
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerAddress)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_customerPhone)
    TextView tvCustomerPhone;
    @BindView(R.id.tv_customerCardNo)
    TextView tvCustomerCardNo;
    @BindView(R.id.tv_Station)
    TextView tvStation;
    @BindView(R.id.tv_orderTime)
    TextView tvOrderTime;
    @BindView(R.id.tv_jf)
    TextView tvJf;
    @BindView(R.id.tv_hs)
    TextView tvHs;
    private String orderID;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, SinceNotStateOrderActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerSinceNotStateOrderActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_since_not_state_order);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }


    private void init() {
        sinceByOderPresenter.setView(this);
        if (getIntent() != null) {
            orderID = getIntent().getStringExtra("orderID");
        }
    }

    @OnClick({ R.id.ibtnBack})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            default:
                break;
        }
    }

    private void initView() {
        sinceByOderPresenter.sinceByOder(orderID);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }



    @Override
    public void retSinceByOderView(boolean isSuccess, final SinceByOrderInfo result, final String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result.getState().equals("1")) {
                    tvState.setText("已发货");
                }
                tvOrderID.setText(result.getOrderID());
                tvOrderID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext,OrderMsgActivity.class);
                        intent.putExtra("orderID",tvOrderID.getText().toString().trim());
                        mContext.startActivity(intent);
                    }
                });
                tvFrom.setText(result.getFrom());
                tvTransportType.setText(result.getType());
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext,CustomerMsgActivity.class);
                        intent.putExtra("customerID",tvCustomerID.getText().toString().trim());
                        mContext.startActivity(intent);
                    }
                });
                tvCustomerAddress.setText(result.getAddress());
                tvCustomerPhone.setText(result.getPhone());
                tvCustomerCardNo.setText(result.getCustomerCardNo());
                tvStation.setText(result.getStation());
                tvOrderTime.setText(result.getOrderTime());

                tvJf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ScanGasInfoActivity.class);
                        intent.putExtra("orderID", result.getOrderID());
                        intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_SINCE_JF);
                        mContext.startActivity(intent);
                    }
                });
                tvHs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ScanGasInfoActivity.class);
                        intent.putExtra("orderID", result.getOrderID());
                        intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_SINCE_HS);
                        mContext.startActivity(intent);
                    }
                });
            }
        } else {
            showToast(msg);
        }
    }
}

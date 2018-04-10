package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.OrderTaskInfo;
import com.future.zhh.ticket.domain.model.OrderTaskListInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerOrderJfHsMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.OrderJfHsMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.TransportByOrderQrCodePresenter;
import com.future.zhh.ticket.presentation.view.TransportByOrderQrCodeView;
import com.future.zhh.ticket.presentation.view.adapters.OrderJFHSMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/27.
 *
 * 配送任务进行订单交付回收页面
 */

public class OrderJfHsMsgActivity extends BaseActivity implements TransportByOrderQrCodeView {

    @BindView(R.id.ibtnBack)
    ImageView ibtnBack;
    @BindView(R.id.ll_ibtnBack)
    LinearLayout llIbtnBack;
    @BindView(R.id.ToolbarTitle)
    TextView ToolbarTitle;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.tv_stutas)
    TextView tvStutas;
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_customerPhone)
    TextView tvCustomerPhone;
    @BindView(R.id.tv_customerCardID)
    TextView tvCustomerCardID;
    @BindView(R.id.tv_gasStation)
    TextView tvGasStation;
    @BindView(R.id.tv_orderTime)
    TextView tvOrderTime;
    @BindView(R.id.rv_tranSportTask)
    RecyclerView rvTranSportTask;
    @BindView(R.id.tv_transportType)
    TextView tvTransportType;
    @BindView(R.id.ll_Task)
    LinearLayout llTask;

    private OrderJfHsMsgActivityComponent component;
    private Context mContext;
    private OrderJFHSMsgAdapter orderJfMsgAdapter;
    private ArrayList<String> listData;
    private List<OrderTaskListInfo> orderTaskListInfoList;
    private String orderQRcode;
    @Inject
    TransportByOrderQrCodePresenter transportByOrderQrCodePresenter;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, OrderJfHsMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerOrderJfHsMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_jfhs_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        transportByOrderQrCodePresenter.setView(this);
        orderQRcode =getIntent().getStringExtra("orderQRcode");
    }

    private void initView() {
        transportByOrderQrCodePresenter.transportByOrderQrCode(orderQRcode);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.ll_ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_ibtnBack:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void retOrderMsgByQrCodeView(boolean isSuccess, OrderTaskInfo result, final String msg) {
        if (isSuccess) {
            if (result != null) {
                tvOrderID.setText(result.getOrderID());
                tvOrderID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(OrderJfHsMsgActivity.this,OrderMsgActivity.class);
                        intent.putExtra("orderID",tvOrderID.getText().toString());
                        mContext.startActivity(intent);
                    }
                });
                tvTransportType.setText(result.getTransportType());
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(OrderJfHsMsgActivity.this,CustomerMsgActivity.class);
                        intent.putExtra("customerID",tvCustomerID.getText().toString());
                        mContext.startActivity(intent);
                    }
                });
                tvAddress.setText(result.getCustomerAddress());
                tvCustomerPhone.setText(result.getPhone());
                tvCustomerCardID.setText(result.getCustomerCardNo());
                tvGasStation.setText(result.getStationName());
                tvOrderTime.setText(result.getTransportTime());
                if (result.getTransportStatus().equals("2")){
                    tvStutas.setText("待回单");
                }else if (result.getTransportStatus().equals("1")){
                    tvStutas.setText("已发货");
                }else if (result.getTransportStatus().equals("0")){
                    tvStutas.setText("已完成");
                }
            if (result.getOrderTaskListInfoList().size()>0){
                llTask.setVisibility(View.VISIBLE);
            }
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvTranSportTask.setLayoutManager(layoutManager);

                orderTaskListInfoList = result.getOrderTaskListInfoList();

                orderJfMsgAdapter = new OrderJFHSMsgAdapter(orderTaskListInfoList, mContext);
                rvTranSportTask.setAdapter(orderJfMsgAdapter);
                orderJfMsgAdapter.notifyDataSetChanged();
            }
        } else {
            showToast(msg);
        }
    }


}

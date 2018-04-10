package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskInfo;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskListInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CustomerJfHsMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCustomerJfHsMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.TransportByCustomerCardNoPresenter;
import com.future.zhh.ticket.presentation.view.TransportByCustomerCardNoView;
import com.future.zhh.ticket.presentation.view.adapters.CustomerJfHsMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/27.
 * <p>
 * 配送任务进行用户卡交付回收页面
 */

public class CustomerJfHsMsgActivity extends BaseActivity implements TransportByCustomerCardNoView {
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerCardID)
    TextView tvCustomerCardID;
    @BindView(R.id.tv_gasStation)
    TextView tvGasStation;
    @BindView(R.id.ll_tasksportTask)
    LinearLayout llTasksportTask;
    @BindView(R.id.rv_tasksportTask)
    RecyclerView rvTasksportTask;

    private CustomerJfHsMsgActivityComponent component;
    private CustomerJfHsMsgAdapter customerJfHsMsgAdapter;
    private ArrayList<String> listData;
    private Context mContext;
    private List<CustomerCardNoTaskListInfo> customerCardNoTaskListInfoList;
    private String customerCardNo;

    @Inject
    TransportByCustomerCardNoPresenter transportByCustomerCardNoPresenter;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CustomerJfHsMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCustomerJfHsMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_jfhs_msg);
        ButterKnife.bind(this);
        mContext=this;
        init();
        initView();
    }

    private void init() {
        if (getIntent()!=null){
            customerCardNo =   getIntent().getStringExtra("customerCardNo");
        }
        transportByCustomerCardNoPresenter.setView(this);
    }

    private void initView() {
    transportByCustomerCardNoPresenter.transportByCustomerCardNo(customerCardNo);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void retTransportByCustomerCardNoView(boolean isSuccess, final CustomerCardNoTaskInfo result, String msg) {
        if (isSuccess){
            if (result!=null){
                    if (result.getCustomerCardNoTaskListInfoList().size()>0){
                        llTasksportTask.setVisibility(View.VISIBLE);
                    }
                    tvCustomerName.setText(result.getCustomerName());
                    tvCustomerID.setText(result.getCustomerID());
                    tvCustomerID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(CustomerJfHsMsgActivity.this,CustomerMsgActivity.class);
                            intent.putExtra("customerID",tvCustomerID.getText().toString());
                            startActivity(intent);
                        }
                    });
                    tvCustomerCardID.setText(result.getCustomerCardNo());
                    tvGasStation.setText(result.getStationName());

                customerCardNoTaskListInfoList  = result.getCustomerCardNoTaskListInfoList();

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvTasksportTask.setLayoutManager(layoutManager);

//                listData = new ArrayList<String>();
//                for (int i = 0; i < 15; i++) {
//                    listData.add("item" + i);
//                }
                customerJfHsMsgAdapter = new CustomerJfHsMsgAdapter(customerCardNoTaskListInfoList, mContext);
                rvTasksportTask.setAdapter(customerJfHsMsgAdapter);
                customerJfHsMsgAdapter.notifyDataSetChanged();
            }
        }else {

        }
    }
}

package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.AddressListInfo;
import com.future.zhh.ticket.domain.model.CheckByCustomercardNoInfo;
import com.future.zhh.ticket.domain.model.CheckByOrderInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCheckActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CheckByCustomercardNoPersenter;
import com.future.zhh.ticket.presentation.presenters.CheckByOrderPresenter;
import com.future.zhh.ticket.presentation.view.CheckByCustomercardNoView;
import com.future.zhh.ticket.presentation.view.CheckByOrderView;
import com.future.zhh.ticket.presentation.view.adapters.AdderssSpinnerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/2.
 * 入户检查用户信息页面
 */

public class CheckActivity extends BaseActivity implements CheckByCustomercardNoView, CheckByOrderView {

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
    @BindView(R.id.tv_checkBtn)
    TextView tvCheckBtn;
    @BindView(R.id.sp_address)
    Spinner spAddress;
    private Context mContext;

    private String orderID;
    private String customerCardNo;
    private List<AddressListInfo> addressListInfoList;
    private AdderssSpinnerAdapter adderssSpinnerAdapter;
    private String address;

    @Inject
    CheckByCustomercardNoPersenter checkByCustomercardNoPersenter;
    @Inject
    CheckByOrderPresenter checkByOrderPresenter;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CheckActivity.class);
        return intent;
    }

    CheckActivityComponent component;

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCheckActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }

    private void init() {
        if (getIntent() != null) {
            orderID = getIntent().getStringExtra("orderID");
            customerCardNo = getIntent().getStringExtra("customerCardNo");
        }

        checkByCustomercardNoPersenter.setView(this);
        checkByOrderPresenter.setView(this);
    }

    private void initView() {
        if (orderID != null && orderID.length() !=0) {
            spAddress.setVisibility(View.VISIBLE);
            tvAddress.setVisibility(View.GONE);
        }
        if (customerCardNo != null&& customerCardNo.length()!=0) {
            spAddress.setVisibility(View.GONE);
            tvAddress.setVisibility(View.VISIBLE);
        }
        checkByCustomercardNoPersenter.checkByCustomercardNo(customerCardNo);
        checkByOrderPresenter.checkByOrder(orderID);
        spinner();
    }

    private void spinner() {
        spAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int postions = spAddress.getSelectedItemPosition();
                address = addressListInfoList.get(postions).getAddress();

                spAddress.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.ibtnBack, R.id.tv_checkBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.tv_checkBtn:
                Intent intent=new Intent(mContext,CheckMsgActivity.class);
                intent.putExtra("customerName",tvCustomerName.getText().toString());
                intent.putExtra("customerID",tvCustomerID.getText().toString());
                if (tvAddress!=null||!tvAddress.equals("")){
                intent.putExtra("address",tvAddress.getText().toString());}else {
                    intent.putExtra("address",address);
                }
                mContext.startActivity(intent);
                finish();
                break;
            default:
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
    public void retCheckByCustomercardNoView(boolean isSuccess, CheckByCustomercardNoInfo result, String msg) {
        if (isSuccess) {
            tvCustomerName.setText(result.getCustomerName());
            tvCustomerID.setText(result.getCustomerID());
            addressListInfoList = result.getAddressInfoList();
            adderssSpinnerAdapter = new AdderssSpinnerAdapter(this, addressListInfoList);
            spAddress.setAdapter(adderssSpinnerAdapter);
            tvPhone.setText(result.getPhone());
            tvCustomerCardNo.setText(result.getCustomerCardNo());
            tvStation.setText(result.getStation());
        } else {
            showToast(msg);
        }
    }

    @Override
    public void retCheckByOrderView(boolean isSuccess, CheckByOrderInfo result, String msg) {
        if (isSuccess) {
            tvCustomerName.setText(result.getCustomerName());
            tvCustomerID.setText(result.getCustomerID());
            tvAddress.setText(result.getAddress());
            tvPhone.setText(result.getPhone());
            tvCustomerCardNo.setText(result.getCustomerCardNo());
            tvStation.setText(result.getStation());
        } else {
            showToast(msg);
        }
    }
}

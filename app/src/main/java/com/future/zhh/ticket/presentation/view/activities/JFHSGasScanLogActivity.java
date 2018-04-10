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
import com.future.zhh.ticket.domain.model.GasCheckScanLogInfo;
import com.future.zhh.ticket.domain.model.GasHsLogInfo;
import com.future.zhh.ticket.domain.model.GasJfLogInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerJFHSGasScanLogActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.JFHSGasScanLogActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.view.adapters.GasCheckScanLogInfoAdapter;
import com.future.zhh.ticket.presentation.view.adapters.HsGasScanLogAdapter;
import com.future.zhh.ticket.presentation.view.adapters.JfGasScanLogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/27.
 * 扫气瓶交付回收即时记录页面
 */

public class JFHSGasScanLogActivity extends BaseActivity {
    @BindView(R.id.ibtnBack)
    ImageView ibtnBack;
    @BindView(R.id.ll_ibtnBack)
    LinearLayout llIbtnBack;
    @BindView(R.id.ToolbarTitle)
    TextView ToolbarTitle;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_gasInfo)
    RecyclerView rvGasInfo;
    @BindView(R.id.tv_gasTotalTips)
    TextView tvGasTotalTips;
    @BindView(R.id.tv_gasTotal)
    TextView tvGasTotal;
    @BindView(R.id.tv_line_top)
    TextView tvLineTop;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    @BindView(R.id.llgasTotalTips)
    LinearLayout llgasTotalTips;

    private int tapFrom;
    private JFHSGasScanLogActivityComponent component;
    private Context mContext;
    private JfGasScanLogAdapter jfGasScanLogAdapter;
    private HsGasScanLogAdapter hsGasScanLogAdapter;
    private GasCheckScanLogInfoAdapter gasCheckScanLogInfoAdapter;
    //private ArrayList<String> listData;
    List<GasJfLogInfo> gasJfLogInfoList=new ArrayList<>();
    List<GasHsLogInfo> gasHsLogInfoList=new ArrayList<>();

    List<GasJfLogInfo> gasSinceJfLogInfoList=new ArrayList<>();
    List<GasHsLogInfo> gasSinceHsLogInfoList=new ArrayList<>();

    List<GasCheckScanLogInfo> gasCheckScanLogInfoList=new ArrayList<>();


    public static Intent getCallingIntent(Context context, int tapFrom) {
        Intent intent = new Intent(context, JFHSGasScanLogActivity.class);
        intent.putExtra(ApplicationDatas.TAP_FROM, tapFrom);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerJFHSGasScanLogActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jfhs_gas_scan_log);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }

    private void init() {
        if (getIntent() != null) {
            tapFrom = getIntent().getIntExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_HOME_BUTTON);
            gasJfLogInfoList = (List<GasJfLogInfo>) getIntent().getSerializableExtra("gasJfLogInfoList");
            gasHsLogInfoList = (List<GasHsLogInfo>) getIntent().getSerializableExtra("gasHsLogInfoList");
            gasSinceJfLogInfoList = (List<GasJfLogInfo>) getIntent().getSerializableExtra("gasSinceJfLogInfoList");
            gasSinceHsLogInfoList = (List<GasHsLogInfo>) getIntent().getSerializableExtra("gasSinceHsLogInfoList");
            gasCheckScanLogInfoList = (List<GasCheckScanLogInfo>) getIntent().getSerializableExtra("gasCheckScanLogInfoList");
            String ToolbarTitles;
            ToolbarTitles = getIntent().getStringExtra("ToolbarTitle");
            ToolbarTitle.setText(ToolbarTitles);
        }

        if ((gasJfLogInfoList!=null&&gasJfLogInfoList.size()!=0) ||(gasHsLogInfoList!=null&&gasHsLogInfoList.size()!=0)|| (gasSinceJfLogInfoList!=null&&gasSinceJfLogInfoList.size()!=0)||(gasSinceHsLogInfoList!=null&&gasSinceHsLogInfoList.size()!=0) || (gasCheckScanLogInfoList!=null&&gasCheckScanLogInfoList.size()!=0)) {
            llTable.setVisibility(View.VISIBLE);
            llgasTotalTips.setVisibility(View.VISIBLE);}

    }

    private void initView() {
        if (tapFrom == ApplicationDatas.TAP_FROM_JF) {
            tvGasTotalTips.setText("已交付气瓶数量:");
            tvGasTotal.setText(String.valueOf(gasJfLogInfoList.size()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvGasInfo.setLayoutManager(layoutManager);


//        listData = new ArrayList<String>();
//        for (int i = 0; i < 15; i++) {
//            listData.add("item" + i);
//        }
            jfGasScanLogAdapter = new JfGasScanLogAdapter(gasJfLogInfoList, mContext);
            rvGasInfo.setAdapter(jfGasScanLogAdapter);
            jfGasScanLogAdapter.notifyDataSetChanged();


        } else if (tapFrom == ApplicationDatas.TAP_FROM_HS) {
            tvGasTotalTips.setText("已回收气瓶数量:");
            tvGasTotal.setText(String.valueOf(gasHsLogInfoList.size()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvGasInfo.setLayoutManager(layoutManager);


//
//        listData = new ArrayList<String>();
//        for (int i = 0; i < 15; i++) {
//            listData.add("item" + i);
//        }
            hsGasScanLogAdapter = new HsGasScanLogAdapter(gasHsLogInfoList, mContext);
            rvGasInfo.setAdapter(hsGasScanLogAdapter);
            hsGasScanLogAdapter.notifyDataSetChanged();

        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_JF) {
            tvGasTotalTips.setText("已交付气瓶数量:");
            tvGasTotal.setText(String.valueOf(gasSinceJfLogInfoList.size()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvGasInfo.setLayoutManager(layoutManager);

            jfGasScanLogAdapter = new JfGasScanLogAdapter(gasSinceJfLogInfoList, mContext);
            rvGasInfo.setAdapter(jfGasScanLogAdapter);
            jfGasScanLogAdapter.notifyDataSetChanged();

        } else if (tapFrom == ApplicationDatas.TAP_FROM_SINCE_HS) {
            tvGasTotalTips.setText("已回收气瓶数量:");
            tvGasTotal.setText(String.valueOf(gasSinceHsLogInfoList.size()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvGasInfo.setLayoutManager(layoutManager);

            hsGasScanLogAdapter = new HsGasScanLogAdapter(gasSinceHsLogInfoList, mContext);
            rvGasInfo.setAdapter(hsGasScanLogAdapter);
            hsGasScanLogAdapter.notifyDataSetChanged();

        } else if (tapFrom == ApplicationDatas.TAP_FROM_CHECK_SCAN_GAS) {
            tvGasTotalTips.setText("入户检查气瓶数量:");
            tvGasTotal.setText(String.valueOf(gasCheckScanLogInfoList.size()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvGasInfo.setLayoutManager(layoutManager);
            gasCheckScanLogInfoAdapter = new GasCheckScanLogInfoAdapter(gasCheckScanLogInfoList, mContext);
            rvGasInfo.setAdapter(gasCheckScanLogInfoAdapter);
            gasCheckScanLogInfoAdapter.notifyDataSetChanged();
        }


    }

    @OnClick({R.id.ll_ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_ibtnBack:
                this.onBackPressed();
                break;
            default:
                break;
        }
    }
}

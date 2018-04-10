package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerAboutActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerDeliveryManagerMsgActivityCompany;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerActivityCompany;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerMsgActivityCompany;
import com.future.zhh.ticket.presentation.presenters.TransportTaskDetailPresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/25.
 *
 * 配送任务明细页面
 */

public class DeliveryManagerMsgActivity extends BaseActivity implements HasComponent<DeliveryManagerMsgActivityCompany> {
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
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public final static String TAG = DeliveryManagerMsgActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);

    private Context mContext;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;
    private String[] tabTitles;

    private DeliveryManagerMsgActivityCompany component;

    private String transportID;
    private String transportTaskID;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DeliveryManagerMsgActivity.class);
    }
    @Override
    public DeliveryManagerMsgActivityCompany getComponent() {
        return component;
    }
    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerDeliveryManagerMsgActivityCompany
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_manager_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
        initViewPager();
    }


    private void init() {
        if (getIntent()!=null){
            transportID  =  getIntent().getStringExtra("transportID");
            transportTaskID = getIntent().getStringExtra("transportTaskID");
        }
    }
    private void initView() {
    }

    private void initViewPager() {
        fragmentList.clear();
        tabTitles = mContext.getResources().getStringArray(R.array.distribution_msg_item);
            DeliveryManagerMsgFragmentOne deliveryManagerMsgFragmentOne = new DeliveryManagerMsgFragmentOne();
            DeliveryManagerMsgFragmentTwo deliveryManagerMsgFragmentTwo = new DeliveryManagerMsgFragmentTwo();
            DeliveryManagerMsgFragmentTree deliveryManagerMsgFragmentTree = new DeliveryManagerMsgFragmentTree();
            fragmentList.add(deliveryManagerMsgFragmentOne);
            fragmentList.add(deliveryManagerMsgFragmentTwo);
            fragmentList.add(deliveryManagerMsgFragmentTree);

        ((DeliveryManagerMsgFragmentOne)fragmentList.get(0)).setType("0",transportID,transportTaskID);
        ((DeliveryManagerMsgFragmentTwo)fragmentList.get(1)).setType("1",transportID,transportTaskID);
        ((DeliveryManagerMsgFragmentTree)fragmentList.get(2)).setType("2",transportID,transportTaskID);

        List<Map<String,Object>> listFg = new ArrayList<>();
        for(int i=0; i<fragmentList.size(); i++){
            Map<String,Object> mapFg = new HashMap<>();
            mapFg.put(SimplePageAdapter.TITLE,tabTitles[i]);
            mapFg.put(SimplePageAdapter.FRAGS,fragmentList.get(i));
            listFg.add(mapFg);
        }
        mSimplePageAdapter = new SimplePageAdapter(this.getSupportFragmentManager(),listFg,mContext);
        viewPager.setAdapter(mSimplePageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(mSimplePageAdapter);//给Tabs设置适配器
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
}

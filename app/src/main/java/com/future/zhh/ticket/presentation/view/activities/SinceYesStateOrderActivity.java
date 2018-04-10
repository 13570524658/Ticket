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
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerSinceYesStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerMsgActivityCompany;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceYesStateOrderActivityComponent;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerMsgFragmentTwo;
import com.future.zhh.ticket.presentation.view.fragments.SinceYesStateOrderFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.SinceYesStateOrderFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.SinceYesStateOrderFragmentTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4.
 * 自提订单完成状态
 */

public class SinceYesStateOrderActivity extends BaseActivity implements HasComponent<SinceYesStateOrderActivityComponent> {

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
    private SinceYesStateOrderActivityComponent component;
    Context mContext;
    private String orderID;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;
    private String[] tabTitles;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, SinceYesStateOrderActivity.class);
        return intent;
    }
    @Override
    public SinceYesStateOrderActivityComponent getComponent() {
        return component;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerSinceYesStateOrderActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_since_yew_state_order);
        mContext=this;
        ButterKnife.bind(this);
        init();
        initView();
        initViewPager();
    }

    private void init() {
        if (getIntent()!=null){
            orderID = getIntent().getStringExtra("orderID");
        }
    }
    private void initView() {
    }


    @OnClick({R.id.ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
        }
    }
    private void initViewPager() {
        fragmentList.clear();
        tabTitles = mContext.getResources().getStringArray(R.array.distribution_msg_item);
        SinceYesStateOrderFragmentOne sinceYesStateOrderFragmentOne = new SinceYesStateOrderFragmentOne();
        SinceYesStateOrderFragmentTwo sinceYesStateOrderFragmentTwo = new SinceYesStateOrderFragmentTwo();
        SinceYesStateOrderFragmentTree sinceYesStateOrderFragmentTree = new SinceYesStateOrderFragmentTree();
        fragmentList.add(sinceYesStateOrderFragmentOne);
        fragmentList.add(sinceYesStateOrderFragmentTwo);
        fragmentList.add(sinceYesStateOrderFragmentTree);

        ((SinceYesStateOrderFragmentOne)fragmentList.get(0)).setType("0",orderID);
        ((SinceYesStateOrderFragmentTwo)fragmentList.get(1)).setType("1",orderID);
        ((SinceYesStateOrderFragmentTree)fragmentList.get(2)).setType("2",orderID);

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
}

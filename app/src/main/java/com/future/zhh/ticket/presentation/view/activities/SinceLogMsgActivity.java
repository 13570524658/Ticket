package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
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
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerSinceLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.presenters.SinceTaskMsgPresenter;
import com.future.zhh.ticket.presentation.view.SinceTaskMsgView;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.SinceLogMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.SinceLogMsgFragmentTree;
import com.future.zhh.ticket.presentation.view.fragments.SinceLogMsgFragmentTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/6.
 * 自提订单记录详细
 */

public class SinceLogMsgActivity extends BaseActivity implements HasComponent<SinceLogMsgActivityComponent> {
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

    SinceLogMsgActivityComponent component;
    private Context mContext;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;
    private String[] tabTitles;

    private String orderID;
    @Override
    public SinceLogMsgActivityComponent getComponent() {
        return component;
    }
    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerSinceLogMsgActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_since_log_msg);
        mContext=this;
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        if (getIntent() != null) {
            orderID = getIntent().getStringExtra("orderID");
        }

    }
    private void initView() {
        fragmentList.clear();
        tabTitles = mContext.getResources().getStringArray(R.array.distribution_msg_item);
        SinceLogMsgFragmentOne sinceLogMsgFragmentOne = new SinceLogMsgFragmentOne();
        SinceLogMsgFragmentTwo sinceLogMsgFragmentTwo = new SinceLogMsgFragmentTwo();
        SinceLogMsgFragmentTree sinceLogMsgFragmentTree = new SinceLogMsgFragmentTree();
        fragmentList.add(sinceLogMsgFragmentOne);
        fragmentList.add(sinceLogMsgFragmentTwo);
        fragmentList.add(sinceLogMsgFragmentTree);

        ((SinceLogMsgFragmentOne)fragmentList.get(0)).setType("0",orderID);
        ((SinceLogMsgFragmentTwo)fragmentList.get(1)).setType("1",orderID);
        ((SinceLogMsgFragmentTree)fragmentList.get(2)).setType("2",orderID);

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

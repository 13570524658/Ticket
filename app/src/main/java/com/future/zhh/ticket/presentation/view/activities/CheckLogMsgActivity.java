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
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCheckLogMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.presenters.CheckLogDetailPresenter;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.CheckLogMsgFragmentOne;
import com.future.zhh.ticket.presentation.view.fragments.CheckLogMsgFragmentTwo;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogMsgActivity extends BaseActivity implements HasComponent<CheckLogMsgActivityComponent> {

    CheckLogMsgActivityComponent component;
    @BindView(R.id.img_ibtnBack)
    ImageView imgIbtnBack;
    @BindView(R.id.ibtnBack)
    LinearLayout ibtnBack;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ibtnSearch)
    ImageButton ibtnSearch;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String[] tabTitles;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;
    private Context mContext;
    private String customerName="";
    private String customerID="";

    @Override
    public CheckLogMsgActivityComponent getComponent() {
        return component;
    }
    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCheckLogMsgActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_log_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initViewPage();
    }

    private void init() {
        if (getIntent()!=null){
            customerName=getIntent().getStringExtra("customerName");
            customerID=getIntent().getStringExtra("customerID");
        }
    }

    private void initViewPage() {

        tabTitles = mContext.getResources().getStringArray(R.array.items);

        CheckLogMsgFragmentOne checkLogMsgFragmentOne = new CheckLogMsgFragmentOne();
        CheckLogMsgFragmentTwo checkLogMsgFragmentTwo=new CheckLogMsgFragmentTwo();
        fragmentList.add(checkLogMsgFragmentOne);
        fragmentList.add(checkLogMsgFragmentTwo);

        //这里可以通过setArgument传入，不用这样设置。官方不建议
        ((CheckLogMsgFragmentOne) fragmentList.get(0)).setType("0",customerName,customerID);
        ((CheckLogMsgFragmentTwo) fragmentList.get(1)).setType("1",customerName,customerID);

        //这里不用这样组装，直接穿给adapter，然后让adapter在getItem的时候设置进去就好了
        List<Map<String, Object>> listFg = new ArrayList<>();
        for (int i = 0; i < fragmentList.size(); i++) {
            Map<String, Object> mapFg = new HashMap<>();
            mapFg.put(SimplePageAdapter.TITLE, tabTitles[i]);
            mapFg.put(SimplePageAdapter.FRAGS, fragmentList.get(i));
            listFg.add(mapFg);
        }

        mSimplePageAdapter = new SimplePageAdapter(this.getSupportFragmentManager(), listFg, mContext);
        viewPager.setAdapter(mSimplePageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabs.setTabsFromPagerAdapter(mSimplePageAdapter);//给Tabs设置适配器
    }

    @OnClick({R.id.ibtnBack})
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

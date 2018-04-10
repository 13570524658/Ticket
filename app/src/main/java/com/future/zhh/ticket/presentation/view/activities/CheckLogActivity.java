package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckLogActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCheckLogActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerActivityCompany;
import com.future.zhh.ticket.presentation.view.adapters.SimplePageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;
import com.future.zhh.ticket.presentation.view.fragments.CheckLogFragment;
import com.future.zhh.ticket.presentation.view.fragments.DeliveryManagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/11.
 */

public class CheckLogActivity extends BaseActivity implements HasComponent<CheckLogActivityComponent> {
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
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private CheckLogActivityComponent component;
    private   Context mContext;

    private String[] tabTitles;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private SimplePageAdapter mSimplePageAdapter;
    @Override
    public CheckLogActivityComponent getComponent() {
        return component;
    }
    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCheckLogActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_log);
        mContext = this;
        ButterKnife.bind(this);
        initViewPage();
    }
    private void initViewPage() {
        tabTitles = mContext.getResources().getStringArray(R.array.item);
        for(int i=0; i<tabTitles.length; i++){

            CheckLogFragment checkLogFragment = new CheckLogFragment();
            fragmentList.add(checkLogFragment);
        }
        ((CheckLogFragment)fragmentList.get(0)).setType("0");

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
        tabs.setVisibility(View.GONE);
    }

    @OnClick({R.id.ibtnSearch, R.id.ibtnBack, R.id.tvSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.ibtnSearch:
                showSearchLayout();
                break;
            case R.id.tvSearch:
                if (!etSearch.getText().toString().trim().equalsIgnoreCase("")){
                if(fragmentList != null){
                    ((CheckLogFragment)fragmentList.get(0)).searchData(0,etSearch.getText().toString());
                }
                }else {
                    showToast("请输入搜索内容");
                }
                break;
            default:
                break;
        }
    }


    private void showSearchLayout() {
        if (llSearch.getVisibility() != View.VISIBLE){
            TranslateAnimation translateAnimation =
                    new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    llSearch.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            llSearch.startAnimation(translateAnimation);
            llSearch.setVisibility(View.VISIBLE);
        }else{
            TranslateAnimation translateAnimation =
                    new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,-1.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    llSearch.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            llSearch.startAnimation(translateAnimation);
            llSearch.setVisibility(View.GONE);
        }
    }

}

package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasSetUpLogInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ByInputtingLogActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerByInputtingLogActivityComponent;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.presenters.GasLabeEqLogPresenter;
import com.future.zhh.ticket.presentation.presenters.GasSetUpLogPresenter;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.GasLabeEqLogView;
import com.future.zhh.ticket.presentation.view.GasSetUpLogView;
import com.future.zhh.ticket.presentation.view.adapters.ByInputtingLogAdapter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_ID;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_INFO;

/**
 * Created by Administrator on 2017/12/1.
 * 气瓶建档记录页面
 */

public class ByInputtingLogActivity extends BaseActivity implements GasSetUpLogView, GasLabeEqLogView {
    @BindView(R.id.ibtnBack)
    ImageView ibtnBack;
    @BindView(R.id.ll_ibtnBack)
    LinearLayout llIbtnBack;
    @BindView(R.id.ToolbarTitle)
    TextView ToolbarTitle;
    @BindView(R.id.ibtn_setting)
    ImageButton ibtnSetting;
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
    @BindView(R.id.xrv_inputLog)
    XRecyclerView xrvInputLog;
    @BindView(R.id.tv_Total)
    TextView tvTotal;
    @BindView(R.id.ll_titel)
    LinearLayout llTitel;
    private ByInputtingLogActivityComponent component;

    private ByInputtingLogAdapter byInputtingLogAdapter;

    private SharedPreferencesUtils sharedPreferencesUtils;

    @Inject
    GasSetUpLogPresenter gasSetUpLogPresenter;
    @Inject
    GasLabeEqLogPresenter gasLabeEqLogPresenter;
    private Context mContext;
    private String id;
    private String enterpriseID;
    private int pageSize = 5;
    private int pageIndex = 1;
    private int refreshTime = 0;//刷新的次数
    private int times = 0;//加载次数

 List<GasSetUpLogListInfo> gasSetUpLogListInfoList;
    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerByInputtingLogActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_in_putting_log);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }

    private void initView() {
        gasSetUpLogPresenter.gasSetUpLog( enterpriseID, pageIndex, pageSize);
//        gasSetUpLogPresenter.gasSetUpLog("801871A5-1BB3-793B-5A3C-8BFB29D0A09D", pageIndex, pageSize);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrvInputLog.setLayoutManager(layoutManager);
        xrvInputLog.setLoadingMoreEnabled(true);
        xrvInputLog.setPullRefreshEnabled(true);


        byInputtingLogAdapter = new ByInputtingLogAdapter(gasSetUpLogListInfoList, mContext);
        xrvInputLog.setAdapter(byInputtingLogAdapter);

    }

    private void init() {
        gasSetUpLogPresenter.setView(this);
        gasLabeEqLogPresenter.setView(this);
        sharedPreferencesUtils = new SharedPreferencesUtils(this, SHARED_USER_INFO);
        id = sharedPreferencesUtils.loadStringSharedPreference(SHARED_ID);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
    }

    @OnClick({R.id.ibtn_setting, R.id.ll_ibtnBack, R.id.tvSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ibtnBack:
                this.onBackPressed();
                break;
            case R.id.ibtn_setting:
                showSearchLayout();
                break;
            case R.id.tvSearch:
                if (!etSearch.getText().toString().trim().equalsIgnoreCase("")){
                if (isContainNumber(etSearch.getText().toString())) {
                    gasLabeEqLogPresenter.gasLabeEqLog(etSearch.getText().toString(), null);
                } else {
                    gasLabeEqLogPresenter.gasLabeEqLog(null, etSearch.getText().toString());
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
        if (llSearch.getVisibility() != View.VISIBLE) {
            TranslateAnimation translateAnimation =
                    new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llSearch.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            llSearch.startAnimation(translateAnimation);
            llSearch.setVisibility(View.VISIBLE);
        } else {
            TranslateAnimation translateAnimation =
                    new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llSearch.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            llSearch.startAnimation(translateAnimation);
            llSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retGasSetUpLogView(boolean isSuccess, List<GasSetUpLogListInfo> listResult, String msg) {
        if (isSuccess) {
            if (listResult != null) {
               if (listResult==null&&listResult.size()==0){
                   llTitel.setVisibility(View.GONE);
               }else {
                   llTitel.setVisibility(View.VISIBLE);
               }

                tvTotal.setText(String.valueOf(listResult.size()));
//                GasSetUpLogListInfoList = result;
                gasSetUpLogListInfoList  =  listResult;
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                xrvInputLog.setLayoutManager(layoutManager);
                xrvInputLog.setLoadingMoreEnabled(true);
                xrvInputLog.setPullRefreshEnabled(true);

                byInputtingLogAdapter = new ByInputtingLogAdapter(gasSetUpLogListInfoList, mContext);
                xrvInputLog.setAdapter(byInputtingLogAdapter);

                byInputtingLogAdapter.notifyDataSetChanged();

                byInputtingLogAdapter.setClickCallBack(new ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos) {
//                showToast(pos+"");
                    }
                });
                xrvInputLog.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                xrvInputLog.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
                xrvInputLog.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        refreshTime++;
                        times = 0;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

//                                listData.clear();
//                                for(int i = 0; i < 15 ;i++){
//                                    listData.add("item" + i + "after " + refreshTime + " times of refresh");
//                                }
                                gasSetUpLogListInfoList.clear();


                                gasSetUpLogPresenter.gasSetUpLog( enterpriseID, pageIndex, pageSize);
//                                gasSetUpLogPresenter.gasSetUpLog("801871A5-1BB3-793B-5A3C-8BFB29D0A09D", pageIndex, pageSize);
                                byInputtingLogAdapter.notifyDataSetChanged();
                                xrvInputLog.refreshComplete();
                            }

                        }, 1000);            //refresh data here
                    }

                    @Override
                    public void onLoadMore() {
                        if (times < 2) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    xrvInputLog.loadMoreComplete();
//                                    for(int i = 0; i < 15 ;i++){
//                                        listData.add("item" + (i + listData.size()) );
//                                    }
                                    gasSetUpLogPresenter.gasSetUpLog( enterpriseID, pageIndex, pageSize);
//                                    gasSetUpLogPresenter.gasSetUpLog("801871A5-1BB3-793B-5A3C-8BFB29D0A09D", pageIndex, pageSize);
                                    xrvInputLog.loadMoreComplete();
                                    byInputtingLogAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
//                                    for(int i = 0; i < 9 ;i++){
//                                        listData.add("item" + (1 + listData.size() ) );
//                                    }
                                    gasSetUpLogPresenter.gasSetUpLog( enterpriseID, pageIndex, pageSize);
//                                    gasSetUpLogPresenter.gasSetUpLog("801871A5-1BB3-793B-5A3C-8BFB29D0A09D", pageIndex, pageSize);
                                    xrvInputLog.setNoMore(true);
                                    byInputtingLogAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                        }
                        times++;
                    }
                });

            }
        } else {
            showToast(msg);
        }
    }

    @Override
    public void retGasLabeEqLogView(boolean isSuccess, List<GasSetUpLogListInfo> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (result==null&&result.size()==0){
                    llTitel.setVisibility(View.GONE);
                }else {
                    llTitel.setVisibility(View.VISIBLE);
                }
                tvTotal.setText(String.valueOf(result.size()));
                gasSetUpLogListInfoList = result;
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                xrvInputLog.setLayoutManager(layoutManager);
                xrvInputLog.setLoadingMoreEnabled(true);
                xrvInputLog.setPullRefreshEnabled(true);

                byInputtingLogAdapter = new ByInputtingLogAdapter(gasSetUpLogListInfoList, mContext);
                xrvInputLog.setAdapter(byInputtingLogAdapter);
                byInputtingLogAdapter.notifyDataSetChanged();
            }
        } else {
            showToast(msg);
        }
    }


    public static boolean isContainNumber(String company) {

        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }



}

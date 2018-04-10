package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.presentation.internal.dl.components.CheckLogActivityComponent;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.presenters.CheckLogBySearchPresenter;
import com.future.zhh.ticket.presentation.presenters.CheckLogPresenter;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.CheckLogBySearchView;
import com.future.zhh.ticket.presentation.view.CheckLogView;
import com.future.zhh.ticket.presentation.view.adapters.CheckLogAdapter;
import com.future.zhh.ticket.presentation.view.adapters.DeliveryManagerAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogFragment extends BaseFragment implements CheckLogView, CheckLogBySearchView {
    @BindView(R.id.task_total)
    TextView taskTotal;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.ll_TotalTitle)
    LinearLayout llTotalTitle;
    @BindView(R.id.x_recyclerview)
    XRecyclerView xRecyclerview;
    private String type;
    private int i;
    private String s;
    private static CheckLogFragment fragment;
    private Context mContext;
    private View fragmentView;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private     CheckLogAdapter     checkLogAdapter;
    private String enterpriseID;
    private int refreshTime = 0;//刷新的次数
    private int times = 0;//加载次数
    List<CheckLogInfo> checkLogInfoList;

    @Inject
    CheckLogPresenter checkLogPresenter;
    @Inject
    CheckLogBySearchPresenter checkLogBySearchPresenter;

    public static CheckLogFragment getInstance() {
        if (fragment == null) {
            fragment = new CheckLogFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CheckLogActivityComponent.class).inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_check_log, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }


    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
        checkLogPresenter.setView(this);
        checkLogBySearchPresenter.setView(this);
    }

    private void initView() {
        if (type.equals("0")) {
            checkLogPresenter.checkLog(enterpriseID);
        }
    }

    @Override
    protected void lazyLoad() {

    }

    public void setType(String type) {
        this.type = type;
    }

    public void searchData(int i, String s) {
        this.i = i;
        this.s = s;
        if (isContainNumber(s)) {
            checkLogBySearchPresenter.checkByOrder(null,s);
        }else {
            checkLogBySearchPresenter.checkByOrder(s,
                    null);
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retCheckLogView(boolean isSuccess, ListResult<CheckLogInfo> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                tvTotal.setText(String.valueOf(result.getTotal()));
                 checkLogInfoList = result.getRows();
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                xRecyclerview.setLayoutManager(layoutManager);
                xRecyclerview.setLoadingMoreEnabled(true);
                xRecyclerview.setPullRefreshEnabled(true);
                     checkLogAdapter = new CheckLogAdapter(checkLogInfoList, mContext);
                xRecyclerview.setAdapter(checkLogAdapter);
                xRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                xRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
                checkLogAdapter.setClickCallBack(new ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos) {
//                showToast(pos+"");
                    }
                });
                xRecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                                checkLogInfoList.clear();
                                if (type.equals("0")) {
                                  checkLogPresenter.checkLog(enterpriseID);
                                }
                                checkLogAdapter.notifyDataSetChanged();
                                xRecyclerview.refreshComplete();
                            }

                        }, 1000);            //refresh data here
                    }

                    @Override
                    public void onLoadMore() {
                        if (times < 2) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    xRecyclerview.loadMoreComplete();
//                                    for(int i = 0; i < 15 ;i++){
//                                        listData.add("item" + (i + listData.size()) );
//                                    }
                                    if (type.equals("0")) {

                                        checkLogPresenter.checkLog(enterpriseID);
                                    }
                                    xRecyclerview.loadMoreComplete();
                                    checkLogAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
//                                    for(int i = 0; i < 9 ;i++){
//                                        listData.add("item" + (1 + listData.size() ) );
//                                    }
                                    if (type.equals("0")) {

                                        checkLogPresenter.checkLog(enterpriseID);
                                    }
                                    xRecyclerview.setNoMore(true);
                                    checkLogAdapter.notifyDataSetChanged();
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void retCheckLogBySearchView(boolean isSuccess, ListResult<CheckLogInfo> result, String msg) {
        if (isSuccess){
            if (result!=null){
                llTotalTitle.setVisibility(View.VISIBLE);
                tvTotal.setText(String.valueOf(result.getTotal()));
                checkLogInfoList = result.getRows();
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                xRecyclerview.setLayoutManager(layoutManager);
                xRecyclerview.setLoadingMoreEnabled(true);
                xRecyclerview.setPullRefreshEnabled(true);
                checkLogAdapter = new CheckLogAdapter(checkLogInfoList, mContext);
                xRecyclerview.setAdapter(checkLogAdapter);
            }
        }else {
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

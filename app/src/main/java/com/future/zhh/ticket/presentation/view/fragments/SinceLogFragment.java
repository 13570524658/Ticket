package com.future.zhh.ticket.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.SinceLog;
import com.future.zhh.ticket.domain.model.SinceLogInfo;
import com.future.zhh.ticket.domain.model.SinceLogListInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.SinceLogActivityComponent;
import com.future.zhh.ticket.presentation.presenters.SinceEqLogPresenter;
import com.future.zhh.ticket.presentation.presenters.SinceLogPersenter;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.SinceEqLogView;
import com.future.zhh.ticket.presentation.view.SinceLogView;
import com.future.zhh.ticket.presentation.view.adapters.HsGasScanLogAdapter;
import com.future.zhh.ticket.presentation.view.adapters.SinceLogAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_ID;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceLogFragment extends BaseFragment implements SinceLogView, SinceEqLogView {

    private static DeliveryManagerFragment fragment;
    @BindView(R.id.rvSinceLog)
    XRecyclerView rvSinceLog;
    private View fragmentView;

    private Context mContext;
    private String type;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String id = "";
    private List<SinceLogListInfo> sinceLogListInfoList;
    private SinceLogAdapter sinceLogAdapter;
    @Inject
    SinceLogPersenter sinceLogPersenter;
    @Inject
    SinceEqLogPresenter sinceEqLogPresenter;

    private String orderID = "";
    private String customerName = "";
    private int i;
    private String s = "";


    public static DeliveryManagerFragment getInstance() {
        if (fragment == null) {
            fragment = new DeliveryManagerFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(SinceLogActivityComponent.class).inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_since_log, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }

    private void init() {
        sinceLogPersenter.setView(this);
        sinceEqLogPresenter.setView(this);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        id = sharedPreferencesUtils.loadStringSharedPreference(SHARED_ID);
    }

    private void initView() {
        sinceLogPersenter.sinceLog(id);
    }


    @Override
    protected void lazyLoad() {

    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retSinceLogView(boolean isSuccess, ListResult<SinceLogListInfo> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rvSinceLog.setLayoutManager(layoutManager);
                sinceLogListInfoList = result.getRows();
                sinceLogAdapter = new SinceLogAdapter(sinceLogListInfoList, mContext);
                rvSinceLog.setAdapter(sinceLogAdapter);
                sinceLogAdapter.notifyDataSetChanged();
                rvSinceLog.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                rvSinceLog.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
                rvSinceLog.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {

                        new Handler().postDelayed(new Runnable() {
                            public void run() {


                                sinceLogListInfoList.clear();

                                sinceLogPersenter.sinceLog(id);

                                sinceLogAdapter.notifyDataSetChanged();
                                rvSinceLog.refreshComplete();
                            }

                        }, 1000);            //refresh data here
                    }

                    @Override
                    public void onLoadMore() {

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                rvSinceLog.loadMoreComplete();
                                sinceLogPersenter.sinceLog(id);
                                rvSinceLog.loadMoreComplete();
                                sinceLogAdapter.notifyDataSetChanged();
                            }
                        }, 1000);

                    }
                });
            }
        } else {
            showToast(msg);
        }
    }

    public void searchData(int i, String s) {
        this.i = i;
        this.s = s;
        if (isContainChinese(s)) {
            customerName = s;
            sinceEqLogPresenter.sinceEqLog(null, customerName);
        } else {
            orderID = s;
            sinceEqLogPresenter.sinceEqLog(orderID, null);
        }
    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void retSinceEqLogView(boolean isSuccess, ListResult<SinceLogListInfo> result, String msg) {
        if (isSuccess) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvSinceLog.setLayoutManager(layoutManager);
            sinceLogListInfoList = result.getRows();
            sinceLogAdapter = new SinceLogAdapter(sinceLogListInfoList, mContext);
            rvSinceLog.setAdapter(sinceLogAdapter);
            sinceLogAdapter.notifyDataSetChanged();
        } else {
            showToast(msg);
        }
    }
}

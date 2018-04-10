package com.future.zhh.ticket.presentation.view.fragments;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.ListResult;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.domain.model.TransportTaskListInfo;
import com.future.zhh.ticket.presentation.internal.dl.components.DeliveryManagerActivityCompany;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.presenters.TransportIdEqTaskPresenter;
import com.future.zhh.ticket.presentation.presenters.TransportTaskListViewPresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.TransportIdEqTaskView;
import com.future.zhh.ticket.presentation.view.TransportTaskListView;
import com.future.zhh.ticket.presentation.view.adapters.DeliveryManagerAdapter;
import com.future.zhh.ticket.presentation.view.widgets.ActivityIndicatorView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/24.
 */

public class DeliveryManagerFragment extends BaseFragment implements TransportTaskListView, TransportIdEqTaskView {
    public final static String TAG = DeliveryManagerFragment.class.getSimpleName();

    @BindView(R.id.ll_TotalTitle)
    LinearLayout llTotalTitle;
    @BindView(R.id.task_total)
    TextView taskTotal;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    private CommonLog logger = LogFactory.createLog(TAG);
    private Unbinder unbinder;
    private Context mContext;
    private String type;

    private static DeliveryManagerFragment fragment;
    private View fragmentView;
    private final static int PAGE_SIZE = 10;
    private ActivityIndicatorView activityIndicatorView;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private DeliveryManagerAdapter deliveryManagerAdapter;
    private LinearLayoutManager linearLayoutManager;

    private ItemClickCallBack clickCallBack;
    @BindView(R.id.x_recyclerview)
    XRecyclerView xRecyclerview;
    //    private ArrayList<String> listData;
    private int refreshTime = 0;//刷新的次数
    private int times = 0;//加载次数
    private int state = 0;//默认是0=全部,1=已发货
    private String searchData;

    private int searchState;//0=全部,1=已发货
    @Inject
    TransportTaskListViewPresenter transportTaskListViewPresenter;
    @Inject
    TransportIdEqTaskPresenter transportIdEqTaskPresenter;

    private String id = "";
    private String enterpriseID = "";
    private int pageIndex = 1;
    private int pageSize = 5;

    private List<TransportTaskInfo> transportTaskInfoList;

    public DeliveryManagerFragment() {
        // Required empty public constructor
    }

    public static DeliveryManagerFragment getInstance() {
        if (fragment == null) {
            fragment = new DeliveryManagerFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(DeliveryManagerActivityCompany.class).inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_delivery_manager, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = this.getActivity();
        init();
        initView();
        this.lazyLoad();
        return fragmentView;
    }

    private void init() {
        activityIndicatorView = new ActivityIndicatorView(mContext);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        transportTaskListViewPresenter.setView(this);
        transportIdEqTaskPresenter.setView(this);
    }

    private void initView() {
        id = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ID);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);

        if (type.equals("0")) {
            state = 0;
            transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
        } else if (type.equals("1")) {
            state = 1;
            transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
        }
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
    public void retTransportTaskListView(boolean isSuccess, ListResult<TransportTaskInfo> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                llTotalTitle.setVisibility(View.VISIBLE);
            if (result.getRows()!=null){
                transportTaskInfoList = result.getRows();
                tvTotal.setText(String.valueOf(result.getTotal()));
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                xRecyclerview.setLayoutManager(layoutManager);
                xRecyclerview.setLoadingMoreEnabled(true);
                xRecyclerview.setPullRefreshEnabled(true);
//                listData = new ArrayList<String>();
//                for(int i = 0; i < 15;i++){
//                    listData.add("item" + i);
//                }

                deliveryManagerAdapter = new DeliveryManagerAdapter(transportTaskInfoList, mContext);
                xRecyclerview.setAdapter(deliveryManagerAdapter);

                xRecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
                xRecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
                deliveryManagerAdapter.setClickCallBack(new ItemClickCallBack() {
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
                                transportTaskInfoList.clear();
                                if (type.equals("0")) {
                                    state = 0;
                                    transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
                                } else if (type.equals("1")) {
                                    state = 1;
                                    transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
                                }
                                deliveryManagerAdapter.notifyDataSetChanged();
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
                                        state = 0;
                                        transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
                                    } else if (type.equals("1")) {
                                        state = 1;
                                        transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
                                    }
                                    xRecyclerview.loadMoreComplete();
                                    deliveryManagerAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
//                                    for(int i = 0; i < 9 ;i++){
//                                        listData.add("item" + (1 + listData.size() ) );
//                                    }
                                    if (type.equals("0")) {
                                        state = 0;
                                        transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
                                    } else if (type.equals("1")) {
                                        state = 1;
                                        transportTaskListViewPresenter.transportTaskList(id, enterpriseID, pageIndex, pageSize, state, null, null, null);
                                    }
                                    xRecyclerview.setNoMore(true);
                                    deliveryManagerAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                        }
                        times++;
                    }
                });
            }
            }
        } else {
            showToast(msg);
        }
    }

    public void searchData(int searchState, String searchData) {
        this.searchState = searchState;
        this.searchData = searchData;
        if (isContainNumber(searchData)) {
            transportIdEqTaskPresenter.transportIdEqTask(searchData, null, state);
        } else {
            transportIdEqTaskPresenter.transportIdEqTask(null, searchData, state);
        }


    }

    @Override
    public void retTransportIdEqTaskView(boolean isSuccess, ListResult<TransportTaskInfo> result, String msg) {
        if (isSuccess) {
            if (result!=null){
            llTotalTitle.setVisibility(View.VISIBLE);
            tvTotal.setText(String.valueOf(result.getTotal()));}
            if (result.getRows()!=null){
            transportTaskInfoList = result.getRows();
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            xRecyclerview.setLayoutManager(layoutManager);
            xRecyclerview.setLoadingMoreEnabled(true);
            xRecyclerview.setPullRefreshEnabled(true);
            deliveryManagerAdapter = new DeliveryManagerAdapter(transportTaskInfoList, mContext);
            xRecyclerview.setAdapter(deliveryManagerAdapter);
                deliveryManagerAdapter.setClickCallBack(new ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos) {
//                showToast(pos+"");
                    }
                });
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

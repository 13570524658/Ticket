package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerGuidePageActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.GuidePageActivityComponent;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.adapters.MyViewPageAdapter;
import com.future.zhh.ticket.presentation.view.fragments.HomeFragment;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_IS_FIRST_STARTUP;
import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_USER_INFO;

/**
 * Created by Administrator on 2017/11/19.
 * 引导进场页面
 */

public class GuidePageActivity extends BaseActivity {

    public final static String TAG = GuidePageActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private GuidePageActivityComponent component;


    private MyViewPageAdapter myViewPageAdapter;
    private List<View> viewList;
    private View guidePageOne, guidePageTwo, guidePageThree, guidePageFour;


    private TextView tvUseNow;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ivDot1)
    ImageView ivDot1;
    @BindView(R.id.ivDot2)
    ImageView ivDot2;
    @BindView(R.id.ivDot3)
    ImageView ivDot3;
    @BindView(R.id.ivDot4)
    ImageView ivDot4;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, GuidePageActivity.class);
    }


    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerGuidePageActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }


    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this, SHARED_USER_INFO);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
               this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 666);
                return;
            }
        }
    }

    /**
     * 动态申请安卓7.0权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 666:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    new CircleDialog.Builder((FragmentActivity) mContext)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initView() {
        guidePageOne = View.inflate(this, R.layout.guide_page_one, null);
        guidePageTwo = View.inflate(this, R.layout.guide_page_two, null);
        guidePageThree = View.inflate(this, R.layout.guide_page_three, null);
        guidePageFour = View.inflate(this, R.layout.guide_page_four, null);
        tvUseNow = guidePageFour.findViewById(R.id.tvUseNow);
        viewList = new ArrayList<>();
        viewList.add(guidePageOne);
        viewList.add(guidePageTwo);
        viewList.add(guidePageThree);
        viewList.add(guidePageFour);
        myViewPageAdapter = new MyViewPageAdapter(viewList);
        viewPager.setAdapter(myViewPageAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ivDot1.setImageResource(R.mipmap.punctuation_one);
                    ivDot2.setImageResource(R.mipmap.punctuation_two);
                    ivDot3.setImageResource(R.mipmap.punctuation_two);
                    ivDot4.setImageResource(R.mipmap.punctuation_two);
                } else if (position == 1) {
                    ivDot1.setImageResource(R.mipmap.punctuation_two);
                    ivDot2.setImageResource(R.mipmap.punctuation_one);
                    ivDot3.setImageResource(R.mipmap.punctuation_two);
                    ivDot4.setImageResource(R.mipmap.punctuation_two);
                } else if (position == 2) {
                    ivDot1.setImageResource(R.mipmap.punctuation_two);
                    ivDot2.setImageResource(R.mipmap.punctuation_two);
                    ivDot3.setImageResource(R.mipmap.punctuation_one);
                    ivDot4.setImageResource(R.mipmap.punctuation_two);
                } else if (position == 3) {
                    ivDot1.setImageResource(R.mipmap.punctuation_two);
                    ivDot2.setImageResource(R.mipmap.punctuation_two);
                    ivDot3.setImageResource(R.mipmap.punctuation_two);
                    ivDot4.setImageResource(R.mipmap.punctuation_one);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 立即体验
         */
        tvUseNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesUtils.saveSharedPreferences(SHARED_IS_FIRST_STARTUP, true);
                navigator.navigateToLoginActivity(mContext);
                finish();
            }
        });
    }
}

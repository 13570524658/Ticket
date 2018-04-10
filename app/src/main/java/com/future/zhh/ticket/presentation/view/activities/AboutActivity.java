package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.internal.dl.components.AboutActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerAboutActivityComponent;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.VersionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/11/22.
 * 我的页面
 */

public class AboutActivity extends BaseActivity {

    public final static String TAG = AboutActivity.class.getSimpleName();
    @BindView(R.id.ibtnBack)
    ImageView ibtnBack;
    @BindView(R.id.ll_ibtnBack)
    LinearLayout llIbtnBack;
    @BindView(R.id.ToolbarTitle)
    TextView ToolbarTitle;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_versionLogo)
    ImageView imgVersionLogo;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private AboutActivityComponent component;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerAboutActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext = this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String versionName = VersionUtils.getInstance(mContext).getVersionName();
        tvVersion.setText("版本号 " + versionName);
    }

    @OnClick({R.id.ll_ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_ibtnBack:
                this.onBackPressed();
                break;
            default:
                break;
        }
    }
}

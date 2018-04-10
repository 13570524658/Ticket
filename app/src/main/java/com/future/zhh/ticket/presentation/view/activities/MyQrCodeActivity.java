package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerMyQrCodeActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.MyQrCodeActivityComponent;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2017/11/23.
 * 我的二维码页面
 */

public class MyQrCodeActivity extends BaseActivity {
    public final static String TAG = ScanInfoActivity.class.getSimpleName();

    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    MyQrCodeActivityComponent component;

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
    @BindView(R.id.img_token)
    ImageView imgToken;
    @BindView(R.id.tv_workerName)
    TextView tvWorkerName;
    @BindView(R.id.tv_workerID)
    TextView tvWorkerID;
    @BindView(R.id.img_workerQRcode)
    ImageView imgWorkerQRcode;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private String userQrCodeImage;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, MyQrCodeActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerMyQrCodeActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
    String    name=sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_USER_NAME);
    String  id=sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ID);

    tvWorkerName.setText(name);
    tvWorkerID.setText(id);
    }

    private void initView() {
        userQrCodeImage = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_USER_QR_CODE_IMAGE);
        createQRCode();

    }

    /**
     * 生成二维码
     */
    private void createQRCode() {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(userQrCodeImage, BGAQRCodeUtil.dp2px(MyQrCodeActivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgWorkerQRcode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MyQrCodeActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @OnClick({R.id.ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            default:
                break;
        }
    }
}

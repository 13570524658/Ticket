package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.WorkerInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerWorkerMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.WorkerMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.WorkerMsgByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.WorkerMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.EnterpriseWorkerMsgErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2017/11/23.
 * 从业人员详情页面
 * <p>
 * 字段编号规则：
 * 员工编号：6位数，前缀3+后面5位后缀，如300001
 * 二维码编号规则：
 * 员工二维码：气站ID+员工ID
 */

public class WorkerMsgActivity extends BaseActivity implements WorkerMsgByQrCodeView {


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
    @BindView(R.id.tv_workerName)
    TextView tvWorkerName;
    @BindView(R.id.tv_workerID)
    TextView tvWorkerID;
    @BindView(R.id.tv_workerSex)
    TextView tvWorkerSex;
    @BindView(R.id.tv_workerDep)
    TextView tvWorkerDep;
    @BindView(R.id.tv_workerPhone)
    TextView tvWorkerPhone;
    @BindView(R.id.tv_workerClass)
    TextView tvWorkerClass;
    @BindView(R.id.tv_workerStation)
    TextView tvWorkerStation;
    @BindView(R.id.img_workerQRCode)
    ImageView imgWorkerQRCode;
    public final static String TAG = ScanInfoActivity.class.getSimpleName();

    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private WorkerMsgActivityComponent component;
    @Inject
    WorkerMsgByQrCodePresenter workerMsgByQrCodePresenter;
    private String WorkerQrCode;
    private String qrCode="";
    private String enterpriseID="";
    private SharedPreferencesUtils sharedPreferencesUtils;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, WorkerMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerWorkerMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }
    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        if (getIntent()!=null){
            qrCode=getIntent().getStringExtra(ApplicationDatas.QR_CODE);
        }
        workerMsgByQrCodePresenter.setView(this);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
    }
    private void initView() {
        workerMsgByQrCodePresenter.workerMsgByQrCode(qrCode);
    }

    @OnClick({R.id.ibtnBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retWorkerMsgByQrCodeView(boolean isSuccess, WorkerInfo result, String msg) {
        if (isSuccess){
            if (result!=null){
                if (enterpriseID.equals(result.getEnterpriseID())){
                tvWorkerName.setText(result.getWorkerName());
                tvWorkerID.setText(result.getWorkerID());
                tvWorkerSex.setText(result.getSex());
                tvWorkerDep.setText(result.getDetName());
                tvWorkerPhone.setText(result.getPhone());
                if (result.getIsAdmin().equalsIgnoreCase("0")){
                    tvWorkerClass.setText("企业");
                }else if (result.getIsAdmin().equalsIgnoreCase("1")){
                    tvWorkerClass.setText("店长");
                }else if (result.getIsAdmin().equalsIgnoreCase("2")){
                    tvWorkerClass.setText("送气工");
                }else if (result.getIsAdmin().equalsIgnoreCase("3")){
                    tvWorkerClass.setText("充装工");
                }else if (result.getIsAdmin().equalsIgnoreCase("4")){
                    tvWorkerClass.setText("收银员");
                }else if (result.getIsAdmin().equalsIgnoreCase("5")){
                    tvWorkerClass.setText("仓管员");
                }else if (result.getIsAdmin().equalsIgnoreCase("6")){
                    tvWorkerClass.setText("司机");
                }else if (result.getIsAdmin().equalsIgnoreCase("7")){
                    tvWorkerClass.setText("门卫");
                }

                tvWorkerStation.setText(result.getGasStation());
                WorkerQrCode    =  result.getWorkerQrcodeImage();
                createQRCode();}else {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业从业人员，无法显示详情!")
                            .setPositive("确定", null)
                            .show();
                }
            }
        }else {
            showToast(msg);
        }
    }
    /**
     * 生成二维码
     */
    private void createQRCode() {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(WorkerQrCode, BGAQRCodeUtil.dp2px(WorkerMsgActivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgWorkerQRCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(WorkerMsgActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
    /**
     * 非本企业从业人员，无法显示详情
     */
    private void enterpriseWorkerMsgErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_worker_msg_error, null);
        final EnterpriseWorkerMsgErrorDialog.Builder builder = new EnterpriseWorkerMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        EnterpriseWorkerMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }
}

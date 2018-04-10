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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CustomerInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CustomerMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCustomerMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CustomerMsgByCustomerIDPresenter;
import com.future.zhh.ticket.presentation.presenters.CustomerMsgByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.CustomerMsgByCustomerIDView;
import com.future.zhh.ticket.presentation.view.CustomerMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.EnterpriseCustomerMsgErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2017/11/23.
 * 客户详情页面
 * <p>
 * 客户编号：7位数，前缀4+后面6位后缀，如4000001
 * 用户卡编号：10位数，前缀２+后面９位随机数，如2123456789
 */

public class CustomerMsgActivity extends BaseActivity implements CustomerMsgByQrCodeView, CustomerMsgByCustomerIDView {
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
    @BindView(R.id.tv_customerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    @BindView(R.id.tv_customerCardID)
    TextView tvCustomerCardID;
    @BindView(R.id.tv_customerPhone)
    TextView tvCustomerPhone;
    @BindView(R.id.tv_customerAddress)
    TextView tvCustomerAddress;
    @BindView(R.id.tv_customerArea)
    TextView tvCustomerArea;
    @BindView(R.id.tv_customerClassOne)
    TextView tvCustomerClassOne;
    @BindView(R.id.tv_customerClassTwo)
    TextView tvCustomerClassTwo;
    @BindView(R.id.tv_customerStation)
    TextView tvCustomerStation;
    @BindView(R.id.img_customerQRCode)
    ImageView imgCustomerQRCode;
    @BindView(R.id.tv_customerLevel)
    TextView tvCustomerLevel;
    public final static String TAG = GasMsgActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private String enterpriseID="";
    private SharedPreferencesUtils sharedPreferencesUtils;
    private CustomerMsgActivityComponent component;
    @Inject
    CustomerMsgByQrCodePresenter customerMsgByQrCodePresenter;
    @Inject
    CustomerMsgByCustomerIDPresenter customerMsgByCustomerIDPresenter;
    private String CustomerQRCode;
    private String qrCode="";
    private String customerID="";
    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CustomerMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCustomerMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        if (getIntent()!=null){
            qrCode=getIntent().getStringExtra(ApplicationDatas.QR_CODE);
            customerID=getIntent().getStringExtra("customerID");
        }
        customerMsgByQrCodePresenter.setView(this);
        customerMsgByCustomerIDPresenter.setView(this);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
    }

    private void initView() {
        if (qrCode!=null){
        customerMsgByQrCodePresenter.customerMsgByQrCode(qrCode);}
        if (customerID!=null){
            customerMsgByCustomerIDPresenter.customerMsgByCustomerIDView(customerID);
        }
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
    public void retCustomerMsgByQrCodeView(boolean isSuccess, CustomerInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
            if (enterpriseID.equalsIgnoreCase(result.getEnterpriseID())){
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerCardID.setText(result.getCustomerCardNo());
                tvCustomerPhone.setText(result.getPhone1());
                tvCustomerAddress.setText(result.getAddress1());
                tvCustomerArea.setText(result.getArea());
                tvCustomerLevel.setText(result.getCustomerLevel());
                tvCustomerClassOne.setText(result.getCustomerClass1());
                tvCustomerClassTwo.setText(result.getCustomerClass2());
                tvCustomerStation.setText(result.getGasStaion());
                CustomerQRCode = result.getUserQrCodeImage();
                createQRCode();
            }else {
                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setText("非本企业用户,无法显示详情!")
                        .setPositive("确定", null)
                        .show();
            }}
        } else {

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
                return QRCodeEncoder.syncEncodeQRCode(CustomerQRCode, BGAQRCodeUtil.dp2px(CustomerMsgActivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgCustomerQRCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(CustomerMsgActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    /**
     * 非本企业用户，无法显示详情
     */
    private void enterpriseCustomerMsgErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_customer_msg_error, null);
        final EnterpriseCustomerMsgErrorDialog.Builder builder = new EnterpriseCustomerMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        EnterpriseCustomerMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    @Override
    public void retCustomerMsgByCustomerIDView(boolean isSuccess, CustomerInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())){
                tvCustomerName.setText(result.getCustomerName());
                tvCustomerID.setText(result.getCustomerID());
                tvCustomerCardID.setText(result.getCustomerCardNo());
                tvCustomerPhone.setText(result.getPhone1());
                tvCustomerAddress.setText(result.getAddress1());
                tvCustomerArea.setText(result.getArea());
                tvCustomerLevel.setText(result.getCustomerLevel());
                tvCustomerClassOne.setText(result.getCustomerClass1());
                tvCustomerClassTwo.setText(result.getCustomerClass2());
                tvCustomerStation.setText(result.getGasStaion());
                CustomerQRCode = result.getUserQrCodeImage();
                createQRCode();}else {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业用户,无法显示详情!")
                            .setPositive("确定", null)
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }
}

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
import com.future.zhh.ticket.domain.model.OrderInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerOrderMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.OrderMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.OrderMsgByOrderIDPresenter;
import com.future.zhh.ticket.presentation.presenters.OrderMsgByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.OrderMsgByOrderIDView;
import com.future.zhh.ticket.presentation.view.OrderMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.EnterpriseOrderMsgErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2017/11/23.
 * 订单详情页面
 * <p>
 * 字段编号规则：
 * 订单编号：11位数，117+后面8位随机数，如11712345678
 * 配送任务编号：根据订单号，订单号+后缀-1，如11712345678-1，11712345678-2
 * 二维码编号规则：
 * 订单二维码：气站ID+员工ID
 */

public class OrderMsgActivity extends BaseActivity implements OrderMsgByQrCodeView, OrderMsgByOrderIDView {
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
    @BindView(R.id.tv_orderID)
    TextView tvOrderID;
    @BindView(R.id.tv_orderFrom)
    TextView tvOrderFrom;
    @BindView(R.id.tv_orderStatus)
    TextView tvOrderStatus;
    @BindView(R.id.tv_orderTransportType)
    TextView tvOrderTransportType;
    @BindView(R.id.tv_orderCustomerName)
    TextView tvOrderCustomerName;
    @BindView(R.id.tv_orderCustomerPhone)
    TextView tvOrderCustomerPhone;
    @BindView(R.id.tv_orderCustomerAddress)
    TextView tvOrderCustomerAddress;
    @BindView(R.id.tv_orderCustomerTime)
    TextView tvOrderCustomerTime;
    @BindView(R.id.img_orderCustomerQRCode)
    ImageView imgOrderCustomerQRCode;
    public final static String TAG = ScanInfoActivity.class.getSimpleName();
    @BindView(R.id.tv_customerID)
    TextView tvCustomerID;
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    OrderMsgActivityComponent component;
    private String enterpriseID = "";
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String qrCode = "";
    private String orderQrcode;
    private String order = "";
    private String orderID = "";


    @Inject
    OrderMsgByQrCodePresenter orderMsgByQrCodePresenter;
    @Inject
    OrderMsgByOrderIDPresenter orderMsgByOrderIDPresenter;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, OrderMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerOrderMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_msg);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }


    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
        if (getIntent() != null) {
            qrCode = getIntent().getStringExtra(ApplicationDatas.QR_CODE);
//            order=getIntent().getStringExtra("order");
            orderID = getIntent().getStringExtra("orderID");
        }
        orderMsgByQrCodePresenter.setView(this);
        orderMsgByOrderIDPresenter.setView(this);
    }

    private void initView() {
        if (qrCode != null) {
            orderMsgByQrCodePresenter.orderMsgByQrCode(qrCode);
        }
//        if (order!=null){
//            orderMsgByOrderIDPresenter.orderMsgByOrderID(order);
//        }
        if (orderID != null) {
            orderMsgByOrderIDPresenter.orderMsgByOrderID(orderID);
        }

    }

    @OnClick({R.id.ibtnBack,R.id.tv_customerID})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.tv_customerID:
                Intent intent=new Intent(this,CustomerMsgActivity.class);
                intent.putExtra("customerID",tvCustomerID.getText().toString().trim());
                mContext.startActivity(intent);
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
    public void retOrderMsgByQrCodeView(boolean isSuccess, OrderInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    tvOrderID.setText(result.getOrderID());
                    tvOrderFrom.setText(result.getOrderFrom());
                    tvOrderStatus.setText(result.getOrderStatus());
                    tvOrderTransportType.setText(result.getTranSportType());
                    tvOrderCustomerName.setText(result.getCustomerName());
                    tvOrderCustomerPhone.setText(result.getPhone1());
                    tvCustomerID.setText(result.getCustomerID());
                    tvOrderCustomerAddress.setText(result.getAddress1());
                    tvOrderCustomerTime.setText(result.getOrderTime());
                    orderQrcode = result.getOrderQrCodeImage();
                    createQRCode();
                } else {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业气瓶，无法显示详情!")
                            .setPositive("确定", null)
                            .show();
                }
            }
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
                return QRCodeEncoder.syncEncodeQRCode(orderQrcode, BGAQRCodeUtil.dp2px(OrderMsgActivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgOrderCustomerQRCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(OrderMsgActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    public void retOrderMsgByOrderIDView(boolean isSuccess, OrderInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    tvOrderID.setText(result.getOrderID());
                    tvOrderFrom.setText(result.getOrderFrom());
                    tvOrderStatus.setText(result.getOrderStatus());
                    tvOrderTransportType.setText(result.getTranSportType());
                    tvOrderCustomerName.setText(result.getCustomerName());
                    tvCustomerID.setText(result.getCustomerID());
                    tvOrderCustomerPhone.setText(result.getPhone1());
                    tvOrderCustomerAddress.setText(result.getAddress1());
                    tvOrderCustomerTime.setText(result.getOrderTime());
                    orderQrcode = result.getOrderQrCodeImage();
                    createQRCode();
                } else {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业气瓶，无法显示详情!")
                            .setPositive("确定", null)
                            .show();


                }
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 非本企业气瓶，无法显示详情
     */
    private void enterpriseOrderMsgErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_order_msg_error, null);
        final EnterpriseOrderMsgErrorDialog.Builder builder = new EnterpriseOrderMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        EnterpriseOrderMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }
}

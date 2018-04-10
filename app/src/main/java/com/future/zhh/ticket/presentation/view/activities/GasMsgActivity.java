package com.future.zhh.ticket.presentation.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerGasMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.GasMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.GasMsgByLabelPresenter;
import com.future.zhh.ticket.presentation.presenters.GasMsgByQrCodePresenters;
import com.future.zhh.ticket.presentation.utils.BitmapUtil;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.GasMsgByLabelView;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.EnterpriseGasMsgErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by Administrator on 2017/11/22.
 * 气瓶详情页面
 * <p>
 * 字段编号规则：
 * 气瓶标签：10位数，前缀１+后面９位随机数，如1123456789
 * 二维码编号规则：
 * 气瓶二维码：气瓶标签号
 */

public class GasMsgActivity extends BaseActivity implements GasMsgByQrCodeView, GasMsgByLabelView {
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
    @BindView(R.id.tv_gasLabel)
    TextView tvGasLabel;
    @BindView(R.id.tv_gsteelCode)
    TextView tvGsteelCode;
    @BindView(R.id.tv_GasFactoryID)
    TextView tvGasFactoryID;
    @BindView(R.id.tv_gasFactoryCompany)
    TextView tvGasFactoryCompany;
    @BindView(R.id.tv_gasModule)
    TextView tvGasModule;
    @BindView(R.id.tv_gasType)
    TextView tvGasType;
    @BindView(R.id.tv_gasWeight)
    TextView tvGasWeight;
    @BindView(R.id.tv_gasProductTime)
    TextView tvGasProductTime;
    @BindView(R.id.tv_checkCompany)
    TextView tvCheckCompany;
    @BindView(R.id.tv_gasLastCheckTime)
    TextView tvGasLastCheckTime;
    @BindView(R.id.tv_next_CheckTime)
    TextView tvNextCheckTime;
    @BindView(R.id.tv_gasSetUpPersion)
    TextView tvGasSetUpPersion;
    @BindView(R.id.tv_gasSetUpTime)
    TextView tvGasSetUpTime;
    @BindView(R.id.tv_up_dataPersion)
    TextView tvUpDataPersion;
    @BindView(R.id.tv_gasUpDataTime)
    TextView tvGasUpDataTime;
    @BindView(R.id.img_gasLabel)
    ImageView imgGasLabel;
    @BindView(R.id.img_gasSteelCode)
    ImageView imgGasSteelCode;
    @BindView(R.id.img_gasBady)
    ImageView imgGasBady;
    @BindView(R.id.img_gasQRCode)
    ImageView imgGasQRCode;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    public final static String TAG = GasMsgActivity.class.getSimpleName();

    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private GasMsgActivityComponent component;
    @Inject
    GasMsgByQrCodePresenters gasMsgByQrCodePresenters;
    @Inject
    GasMsgByLabelPresenter gasMsgByLabelPresenter;
    private String qrCode = "";
    private String qrCodeImage;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private String label = "";
    private String enterpriseID = "";
    private SharedPreferencesUtils sharedPreferencesUtils;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, GasMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerGasMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();

    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        if (getIntent() != null) {
            qrCode = getIntent().getStringExtra(ApplicationDatas.QR_CODE);
            label = getIntent().getStringExtra("label");
        }
        gasMsgByQrCodePresenters.setView(this);
        gasMsgByLabelPresenter.setView(this);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
    }

    private void initView() {
        if (qrCode != null) {
            gasMsgByQrCodePresenters.gasMsgByQrCode(qrCode);
        }
        if (label != null) {
            gasMsgByLabelPresenter.customerMsgByCustomerIDView(label);
        }

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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retGasMsgByQrCodeView(boolean isSuccess, GasInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    if (result.getUseStatus().equals("0")) {
                        tvGasLabel.setText(result.getGasLabe());
                        tvGsteelCode.setText(result.getGasSteelCode());
                        tvGasFactoryID.setText(result.getProductFactoryID());
                        tvGasFactoryCompany.setText(result.getProductCompany());
                        tvGasType.setText(result.getGasType());
                        tvGasWeight.setText(result.getGasWeight());
                        tvGasModule.setText(result.getGasModule());
                        tvGasProductTime.setText(result.getProductTime());
                        tvCheckCompany.setText(result.getLastTestCompany());
                        tvGasLastCheckTime.setText(result.getLastTestTime());
                        tvNextCheckTime.setText(result.getNextTestTime());
                        tvGasSetUpPersion.setText(result.getSetUpPersion());
                        tvGasSetUpTime.setText(result.getSetUpTime());
                        tvUpDataPersion.setText(result.getUpDataPersion());
                        tvGasUpDataTime.setText(result.getUpDataTime());

                        if (result.getGasImage1() != null && !result.getGasImage1().equals("")) {
                            image1 = BitmapUtil.stringtoBitmap(result.getGasImage1());
                            imgGasLabel.setImageBitmap(image1);
                        }
                        if (result.getGasImage2() != null && !result.getGasImage2().equals("")) {
                            image2 = BitmapUtil.stringtoBitmap(result.getGasImage2());
                            imgGasSteelCode.setImageBitmap(image2);
                        }
                        if (result.getGasImage3() != null && !result.getGasImage3().equals("")) {
                            image3 = BitmapUtil.stringtoBitmap(result.getGasImage3());
                            imgGasBady.setImageBitmap(image3);
                        }


                        qrCodeImage = result.getGasQrCodeImage();
                        createQRCode();
                    } else if (result.getUseStatus().equals("1")) {


                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该气瓶标签已作废,无法显示详情!")
                                .setPositive("确定", null)
                                .show();
                    } else if (result.getUseStatus().equals("2")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该气瓶未建档,无法显示详情!")
                                .setPositive("确定", null)
                                .show();
                    } else if (result.getUseStatus().equals("3")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("未导入该气瓶标签,无法显示详情!")
                                .setPositive("确定", null)
                                .show();
                    }
                } else {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业气瓶,无法显示详情!")
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
                return QRCodeEncoder.syncEncodeQRCode(qrCodeImage, BGAQRCodeUtil.dp2px(GasMsgActivity.this, 150));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imgGasQRCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(GasMsgActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    /**
     * 非本企业气瓶，无法显示详情
     */
    private void enterpriseGasMsgErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_gas_msg_error, null);
        final EnterpriseGasMsgErrorDialog.Builder builder = new EnterpriseGasMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        EnterpriseGasMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }


    /**
     * 未导入该气瓶标签，无法显示详情
     */
    private void enterpriseGasMsgErrorDialog2() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_gas_msg_error, null);
        final EnterpriseGasMsgErrorDialog.Builder builder = new EnterpriseGasMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMessage("未导入该气瓶标签，无法显示详情!");
        EnterpriseGasMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    /**
     * 该气瓶未建档，无法显示详情
     */
    private void enterpriseGasMsgErrorDialog3() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_gas_msg_error, null);
        final EnterpriseGasMsgErrorDialog.Builder builder = new EnterpriseGasMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMessage("气瓶未建档，无法显示详情!");
        EnterpriseGasMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    /**
     * 该气瓶标签已作废，无法显示详情！
     */
    private void enterpriseGasMsgErrorDialog4() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_gas_msg_error, null);
        final EnterpriseGasMsgErrorDialog.Builder builder = new EnterpriseGasMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setMessage("该气瓶标签已作废，无法显示详情!");
        EnterpriseGasMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    @Override
    public void retGasMsgByLabelView(boolean isSuccess, GasInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())) {
                    if (result.getUseStatus().equals("0")) {
                        tvGasLabel.setText(result.getGasLabe());
                        tvGsteelCode.setText(result.getGasSteelCode());
                        tvGasFactoryID.setText(result.getProductFactoryID());
                        tvGasFactoryCompany.setText(result.getProductCompany());
                        tvGasType.setText(result.getGasType());
                        tvGasWeight.setText(result.getGasWeight());
                        tvGasModule.setText(result.getGasModule());
                        tvGasProductTime.setText(result.getProductTime());
                        tvCheckCompany.setText(result.getLastTestCompany());
                        tvGasLastCheckTime.setText(result.getLastTestTime());
                        tvNextCheckTime.setText(result.getNextTestTime());
                        tvGasSetUpPersion.setText(result.getSetUpPersion());
                        tvGasSetUpTime.setText(result.getSetUpTime());
                        tvUpDataPersion.setText(result.getUpDataPersion());
                        tvGasUpDataTime.setText(result.getUpDataTime());
                        if (result.getGasImage1() != null && !result.getGasImage1().equalsIgnoreCase("")) {
                            imgGasLabel.setVisibility(View.VISIBLE);
                            image1 = BitmapUtil.stringtoBitmap(result.getGasImage1());
                            imgGasLabel.setImageBitmap(image1);
                        }
                        if (result.getGasImage2() != null && !result.getGasImage2().equalsIgnoreCase("")) {
                            imgGasSteelCode.setVisibility(View.VISIBLE);
                            image2 = BitmapUtil.stringtoBitmap(result.getGasImage2());
                            imgGasSteelCode.setImageBitmap(image2);
                        }
                        if (result.getGasImage3() != null && !result.getGasImage3().equalsIgnoreCase("")) {
                            imgGasBady.setVisibility(View.VISIBLE);
                            image3 = BitmapUtil.stringtoBitmap(result.getGasImage3());
                            imgGasBady.setImageBitmap(image3);
                        }

                        qrCodeImage = result.getGasQrCodeImage();
                        createQRCode();
                    } else if (result.getUseStatus().equals("1")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该气瓶标签已作废,无法显示详情!")
                                .setPositive("确定", null)
                                .show();
                    } else if (result.getUseStatus().equals("2")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该气瓶未建档,无法显示详情!")
                                .setPositive("确定", null)
                                .show();
                    } else if (result.getUseStatus().equals("3")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("未导入该气瓶标签,无法显示详情!")
                                .setPositive("确定", null)
                                .show();
                    }
                } else {

                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业气瓶,无法显示详情!")
                            .setPositive("确定", null)
                            .show();
                }
            }
        } else {
            showToast(msg);
        }
    }
}

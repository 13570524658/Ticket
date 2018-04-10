package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CarInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.CarMsgActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerCarMsgActivityComponent;
import com.future.zhh.ticket.presentation.presenters.CarMsgByQrCodePresenter;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.CarMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.widgets.EnterpriseCarMsgErrorDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/23.
 * 车辆详情页面
 * <p>
 * 字段编号规则：
 * 车辆编号：6位数，前缀５+后面5位后缀，如500001
 */

public class CarMsgActivity extends BaseActivity implements CarMsgByQrCodeView {


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
    @BindView(R.id.tv_car_id)
    TextView tvCarId;
    @BindView(R.id.tv_car_use)
    TextView tvCarUse;
    @BindView(R.id.tv_car_dep)
    TextView tvCarDep;
    @BindView(R.id.tv_car_module)
    TextView tvCarModule;
    @BindView(R.id.tv_car_head)
    TextView tvCarHead;
    @BindView(R.id.tv_car_phone)
    TextView tvCarPhone;
    @BindView(R.id.tv_car_product_factory)
    TextView tvCarProductFactory;
    @BindView(R.id.tv_car_product_time)
    TextView tvCarProductTime;
    @BindView(R.id.tv_car_use_year)
    TextView tvCarUseYear;
    @BindView(R.id.tv_car_check_time)
    TextView tvCarCheckTime;
    @BindView(R.id.tv_car_now_check_time)
    TextView tvCarNowCheckTime;
    @BindView(R.id.tv_car_now_gas_station)
    TextView tvCarNowGasStation;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    public final static String TAG = ScanInfoActivity.class.getSimpleName();
    @BindView(R.id.tv_carNo)
    TextView tvCarNo;
    private String enterpriseID="";
    private SharedPreferencesUtils sharedPreferencesUtils;
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;

    private CarMsgActivityComponent component;

    @Inject
    CarMsgByQrCodePresenter carMsgByQrCodePresenter;

    private String qrCode="";

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, CarMsgActivity.class);
        return intent;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerCarMsgActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_msg);
        mContext = this;
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        if (getIntent() != null) {
            qrCode = getIntent().getStringExtra(ApplicationDatas.QR_CODE);
        }
        carMsgByQrCodePresenter.setView(this);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
    }

    private void initView() {
        carMsgByQrCodePresenter.carMsgByQrCode(qrCode);
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
    public void retCarMsgByQrCodeView(boolean isSuccess, CarInfo result, String msg) {
        if (isSuccess) {
            if (result != null) {
                if (enterpriseID.equals(result.getEnterpriseID())){
                tvCarNo.setText(result.getCarNo());
                tvCarId.setText(result.getCarID());
                tvCarUse.setText(result.getCarUse());
                tvCarDep.setText(result.getDetName());
                tvCarModule.setText(result.getCarModule());
                tvCarHead.setText(result.getCarResponsiblePerson());
                tvCarPhone.setText(result.getPhone());
                tvCarProductFactory.setText(result.getProductFactory());
                tvCarProductTime.setText(result.getProductTime());
                tvCarUseYear.setText(result.getUserTime());
                tvCarCheckTime.setText(result.getMaintenanceTime());
                tvCarNowCheckTime.setText(result.getNowMaintenanceTime());
                tvCarNowGasStation.setText(result.getGasStation());}else {


                    new CircleDialog.Builder(this)
                            .setTitle("提示")
                            .setWidth((float) 0.8)
                            .setText("非本企业车辆，无法显示详情!")
                            .setPositive("确定", null)
                            .show();
                }
            }
        } else {

            showToast(msg);
        }
    }
    /**
     * 非本企业车辆，无法显示详情
     */
    private void enterpriseCarMsgErrorDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View layout = inflater.inflate(R.layout.dialog_enterprise_car_msg_error, null);
        final EnterpriseCarMsgErrorDialog.Builder builder = new EnterpriseCarMsgErrorDialog.Builder(mContext);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        EnterpriseCarMsgErrorDialog dialog = builder.create();

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;

        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }
}

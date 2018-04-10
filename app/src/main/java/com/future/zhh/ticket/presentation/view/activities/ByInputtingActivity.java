package com.future.zhh.ticket.presentation.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ByInputtingActivityComponents;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerByInputtingActivityComponents;
import com.future.zhh.ticket.presentation.presenters.GasModulePresenter;
import com.future.zhh.ticket.presentation.presenters.GasMsgByQrCodePresenters;
import com.future.zhh.ticket.presentation.presenters.GasMsgChangePresent;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.GasModuleView;
import com.future.zhh.ticket.presentation.view.GasMsgByQrCodeView;
import com.future.zhh.ticket.presentation.view.GasMsgChangeView;
import com.future.zhh.ticket.presentation.view.adapters.GasModuleSpinnerAdapter;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigButton;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.ButtonParams;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.DialogParams;
import com.future.zhh.ticket.presentation.view.widgets.keyboard.VirtualKeyboardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils.SHARED_ID;
import static com.future.zhh.ticket.presentation.view.widgets.headimage.FileUtil.getRealFilePathFromUri;

/**
 * Created by Administrator on 2017/11/29.
 * 气瓶建档页面
 */

public class ByInputtingActivity extends BaseActivity implements GasModuleView, GasMsgChangeView {
    @BindView(R.id.ibtnBack)
    ImageView ibtnBack;
    @BindView(R.id.ll_ibtnBack)
    LinearLayout llIbtnBack;
    @BindView(R.id.ToolbarTitle)
    TextView ToolbarTitle;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_gasLabel)
    EditText etGasLabel;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.et_steel_code)
    EditText etSteelCode;
    @BindView(R.id.et_factory_id)
    EditText etFactoryId;
    @BindView(R.id.et_factory_company)
    EditText etFactoryCompany;
    @BindView(R.id.sp_gasModule)
    Spinner spGasModule;
    @BindView(R.id.et_productTime)
    EditText etProductTime;
    @BindView(R.id.iv_productTime)
    ImageView ivProductTime;
    @BindView(R.id.et_lastCheckCompany)
    EditText etLastCheckCompany;
    @BindView(R.id.et_lastCheckTime)
    EditText etLastCheckTime;
    @BindView(R.id.iv_lastCheckTime)
    ImageView ivLastCheckTime;
    @BindView(R.id.et_nextCheckTime)
    EditText etNextCheckTime;
    @BindView(R.id.iv_nextCheckTime)
    ImageView ivNextCheckTime;
    @BindView(R.id.iv_addImage1)
    ImageView ivAddImage1;
    @BindView(R.id.iv_close1)
    ImageView ivClose1;
    @BindView(R.id.iv_addImage2)
    ImageView ivAddImage2;
    @BindView(R.id.iv_close2)
    ImageView ivClose2;
    @BindView(R.id.iv_addImage3)
    ImageView ivAddImage3;
    @BindView(R.id.iv_close3)
    ImageView ivClose3;
    @BindView(R.id.tv_saveEndContinue)
    TextView tvSaveEndContinue;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.llcontent)
    LinearLayout llcontent;
    @BindView(R.id.et_gas_weight)
    EditText etGasWeight;
    @BindView(R.id.ibtn_inputLog)
    ImageButton ibtnInputLog;

    private Context mContext;
    ByInputtingActivityComponents component;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String label;

    private GasModuleSpinnerAdapter gasModuleSpinnerAdapter;
    private List<GasmoduleList> gasmoduleListList;
    private String gasModule = "";
    private String gasModuleID="";

    private String image;
    private Uri photoUri;
    private String photoPath;
    private static final int TAKE_PHOTO = 0;
    private static final int PICK_PHOTO = 1;
    String base64image1;
    String base64image2;
    String base64image3;
    private int tag;
    private String id;
    private String enterpriseID;






    @Inject
    GasModulePresenter gasModulePresenter;
    @Inject
    GasMsgChangePresent gasMsgChangePresent;



    public static Intent getCallingIntent(Context context){
        return new Intent(context,ByInputtingActivity.class);
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerByInputtingActivityComponents
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_in_putting);
        mContext = this;
        ButterKnife.bind(this);
        init();
        intitView();

    }
    private void init() {
        etGasLabel.setFocusable(false);
        etGasLabel.setFocusableInTouchMode(false);
        etGasLabel.setClickable(false);
        etGasLabel.setCursorVisible(false);

        etProductTime.setFocusable(false);
        etProductTime.setFocusableInTouchMode(false);
        etProductTime.setClickable(false);
        etProductTime.setCursorVisible(false);

        etLastCheckTime.setFocusable(false);
        etLastCheckTime.setCursorVisible(false);
        etLastCheckTime.setFocusableInTouchMode(false);
        etLastCheckTime.setClickable(false);
        etLastCheckTime.setCursorVisible(false);
        etNextCheckTime.setFocusable(false);
        etNextCheckTime.setFocusableInTouchMode(false);
        etNextCheckTime.setClickable(false);
        etNextCheckTime.setCursorVisible(false);
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
        id = sharedPreferencesUtils.loadStringSharedPreference(SHARED_ID);
        enterpriseID = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ENTERPRISE_ID);
        gasModulePresenter.setView(this);
        gasMsgChangePresent.setView(this);

    }

    private void intitView() {

        gasModulePresenter.gasModule(enterpriseID);
        spinner();
        etGasLabel.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        etGasWeight.setInputType(EditorInfo.TYPE_CLASS_PHONE);
    }








    private void spinner() {
        spGasModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int postions = spGasModule.getSelectedItemPosition();
                gasModule = gasmoduleListList.get(postions).getModuleType();
                gasModuleID=gasmoduleListList.get(position).getId();
                spGasModule.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @OnClick({R.id.ll_ibtnBack, R.id.iv_scan, R.id.iv_addImage1, R.id.iv_addImage2, R.id.iv_addImage3, R.id.iv_close1, R.id.iv_close2, R.id.iv_close3,
            R.id.iv_productTime, R.id.iv_lastCheckTime, R.id.iv_nextCheckTime, R.id.tv_cancel, R.id.tv_save, R.id.tv_saveEndContinue,
    R.id.ibtn_inputLog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_ibtnBack:
                this.onBackPressed();
                break;
            case R.id.iv_scan:
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 666);
                        return;
                    } else {
                        Intent intent = new Intent(mContext, ScanInfoActivity.class);
                        intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_INPUTTING);
                        startActivityForResult(intent, ScanInfoActivity.REQUEST_BYPUTTING_SCAN);
                    }
                } else {
                    Intent intent = new Intent(mContext, ScanInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_INPUTTING);
                    startActivityForResult(intent, ScanInfoActivity.REQUEST_BYPUTTING_SCAN);
                }

                break;
            case R.id.iv_addImage1:
                image = "1";
                uploadImage();
                break;
            case R.id.iv_addImage2:
                image = "2";
                uploadImage();
                break;
            case R.id.iv_addImage3:
                image = "3";
                uploadImage();
                break;
            case R.id.iv_close1:
                ivAddImage1.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                break;
            case R.id.iv_close2:
                ivAddImage2.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                break;
            case R.id.iv_close3:
                ivAddImage3.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                break;
            case R.id.iv_productTime:
                Calendar calendar1=Calendar.getInstance();
                Calendar calendar2=Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = dateFormat.parse("2011-01-01");
                    calendar1.setTime(date1);
                    calendar2.setTime(new Date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TimePickerView pvTime = new TimePickerView.Builder(ByInputtingActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etProductTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择生产日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        .setRangDate(calendar1,calendar2)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.iv_lastCheckTime:
                Calendar calendar3=Calendar.getInstance();
                Calendar calendar4=Calendar.getInstance();
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = dateFormat2.parse("2011-01-01");
                    calendar3.setTime(date1);
                    calendar4.setTime(new Date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TimePickerView pvTime2 = new TimePickerView.Builder(ByInputtingActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etLastCheckTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择上次检测日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        .setRangDate(calendar3,calendar4)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime2.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime2.show();
                break;
            case R.id.iv_nextCheckTime:
                Calendar calendar5=Calendar.getInstance();
                Calendar calendar6=Calendar.getInstance();
                SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = dateFormat3.parse("2011-01-01");
                    calendar5.setTime(date1);
                    calendar6.setTime(new Date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TimePickerView pvTime3 = new TimePickerView.Builder(ByInputtingActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        etNextCheckTime.setText(time);
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("请选择下次日期")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.parseColor("#ff13b3e9"))//设置选中项的颜色
                        .setTitleColor(Color.parseColor("#ffffff"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#ffffff"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#ffffff"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#ff13b3e9"))//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                        .setRangDate(calendar5,calendar6)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime3.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime3.show();
                break;
            case R.id.tv_cancel:
                new AlertDialog.Builder(this)
                        .setTitle("取消建档")
                        .setMessage("确认取消吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                etGasLabel.setText("");
//                                etSteelCode.setText("");
//                                etFactoryId.setText("");
//                                etFactoryCompany.setText("");
//                                etProductTime.setText("");
//                                etGasWeight.setText("");
//                                etLastCheckCompany.setText("");
//                                etLastCheckTime.setText("");
//                                etNextCheckTime.setText("");
//                                ivAddImage1.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
//                                ivAddImage2.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
//                                ivAddImage3.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                                finish();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_save:
                if (!etGasLabel.getText().toString().equalsIgnoreCase("")) {
                    tag = 0;
                    gasMsgChangePresent.gasMsgChange(id, etGasLabel.getText().toString(), etProductTime.getText().toString(),
                            etFactoryCompany.getText().toString(), etGasWeight.getText().toString(), gasModuleID, etSteelCode.getText().toString(),
                            etProductTime.getText().toString(), etLastCheckCompany.getText().toString(), etLastCheckTime.getText().toString(),
                            etNextCheckTime.getText().toString(), base64image1, base64image2, base64image3);
                }else {
                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("提示")
                            .setText("气瓶标签不能为空")
                            .setPositive("确定", null)
                            .show();
                }

                break;
            case R.id.tv_saveEndContinue:
                if (!etGasLabel.getText().toString().equalsIgnoreCase("")) {
                    tag = 1;
                    gasMsgChangePresent.gasMsgChange(id, etGasLabel.getText().toString(), etProductTime.getText().toString(),
                            etFactoryCompany.getText().toString(), etGasWeight.getText().toString(), gasModuleID, etSteelCode.getText().toString(),
                            etProductTime.getText().toString(), etLastCheckCompany.getText().toString(), etLastCheckTime.getText().toString(),
                            etNextCheckTime.getText().toString(), base64image1, base64image2, base64image3);
                }else {
                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("提示")
                            .setText("气瓶标签不能为空")
                            .setPositive("确定", null)
                            .show();
                }
//                gasMsgChangePresent.gasMsgChange("app登录人id", etGasLabel.getText().toString(), etProductTime.getText().toString(),
//                        etFactoryCompany.getText().toString(), etGasWeight.getText().toString(), gasModuleID, etSteelCode.getText().toString(),
//                        etProductTime.getText().toString(), etLastCheckCompany.getText().toString(), etLastCheckTime.getText().toString(),
//                        etNextCheckTime.getText().toString(), base64image1, base64image2, base64image3);
                break;
            case R.id.ibtn_inputLog:
                  Intent intent =new Intent(this,ByInputtingLogActivity.class);
                  mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 自定义图片名，获取照片的file
     */
    private File createImgFile() {
        //创建文件
        String fileName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";//确定文件名
//        File dir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File dir=Environment.getExternalStorageDirectory();
        File dir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory();
        } else {
            dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        File tempFile = new File(dir, fileName);
        try {
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件路径
        photoPath = tempFile.getAbsolutePath();
        return tempFile;
    }

    /**
     * 压缩图片
     */
    private void setImageBitmap() {
        //获取imageview的宽和高
        int targetWidth = 0;
        int targetHeight = 0;
        if (image .equalsIgnoreCase("1")) {
            targetWidth = ivAddImage1.getWidth();
            targetHeight = ivAddImage1.getHeight();
        }
        if (image.equalsIgnoreCase("2")) {
            targetWidth = ivAddImage2.getWidth();
            targetHeight = ivAddImage2.getHeight();
        }
        if (image.equalsIgnoreCase("3")) {
            targetWidth = ivAddImage3.getWidth();
            targetHeight = ivAddImage3.getHeight();
        }

        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        //获取缩放比例
        int inSampleSize = 1;
        if (photoWidth > targetWidth || photoHeight > targetHeight) {
            int widthRatio = Math.round((float) photoWidth / targetWidth);
            int heightRatio = Math.round((float) photoHeight / targetHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        if (image .equalsIgnoreCase("1")) {
            ivAddImage1.setImageBitmap(bitmap);
            base64image1 = bitmapToBase64(bitmap);
        }
        if (image.equalsIgnoreCase("2")) {
            ivAddImage2.setImageBitmap(bitmap);
            base64image2 = bitmapToBase64(bitmap);
        }
        if (image.equalsIgnoreCase("3")) {
            ivAddImage3.setImageBitmap(bitmap);
            base64image3 = bitmapToBase64(bitmap);
        }

    }

    /**
     * 将图片添加进手机相册
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream bos = null;
        try {
            if (null != bitmap) {
                bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);//将bitmap放入字节数组流中

                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();

                byte[] bitmapByte = bos.toByteArray();
                result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == ScanInfoActivity.REQUEST_BYPUTTING_SCAN) {
                label = data.getStringExtra("label");
//                gasMsgByQrCodePresenters.gasMsgByQrCode(label);
                etGasLabel.setText(label);

            }
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case TAKE_PHOTO:
                        setImageBitmap();
                        galleryAddPic();
                        break;
                    case PICK_PHOTO:
                        Uri uri = data.getData();
                        if (uri == null) {
                            return;
                        }
                        String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                        Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                        if (image.equalsIgnoreCase("1")) {
                            ivAddImage1.setImageBitmap(bitMap);
                        } else if (image.equalsIgnoreCase("2")) {
                            ivAddImage2.setImageBitmap(bitMap);
                        } else if (image.equalsIgnoreCase("3")) {
                            ivAddImage3.setImageBitmap(bitMap);
                        }
                        break;
                }
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case 111:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
//                    openCamra();
                    //只是加了一个uri作为地址传入
                    Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
                    intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intents, TAKE_PHOTO);
                } else {
                    // Permission Denied

                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
                break;
            case 666:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(mContext, ScanInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_INPUTTING);
                    startActivityForResult(intent, ScanInfoActivity.REQUEST_BYPUTTING_SCAN);
                } else {
                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("相机权限已禁用")
                            .setText("很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。")
                            .setPositive("确定", null)
                            .show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

//    @Override
//    public void retGasMsgByQrCodeView(boolean isSuccess, GasInfo result, String msg) {
//        if (isSuccess) {
//            if (result != null) {
//                etGasLabel.setText(result.getGasLabe());
//                etSteelCode.setText(result.getGasSteelCode());
//                etGasWeight.setText(result.getGasWeight());
//                etFactoryCompany.setText(result.getProductCompany());
//                etFactoryId.setText(result.getProductFactoryID());
//                etProductTime.setText(result.getProductTime());
//                etLastCheckTime.setText(result.getLastTestTime());
//                etNextCheckTime.setText(result.getNextTestTime());
//                etLastCheckCompany.setText(result.getLastTestCompany());
//            }
//        } else {
//            showToast(msg);
//        }
//    }

    @Override
    public void retGasModuleView(boolean isSuccess, List<GasmoduleList> result, String msg) {
        if (isSuccess) {
            if (result != null) {
                gasmoduleListList = result;
                gasModuleSpinnerAdapter = new GasModuleSpinnerAdapter(this, gasmoduleListList);
                spGasModule.setAdapter(gasModuleSpinnerAdapter);
            }
        } else {
            showToast(msg);
        }
    }

    /**
     * 上传头像
     */
    private void uploadImage() {
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
//        TextView btnCarema = view.findViewById(R.id.btn_camera);
//        TextView btnPhoto = view.findViewById(R.id.btn_photo);
//        TextView btnCancel = view.findViewById(R.id.btn_cancel);
//        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
//        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        //popupWindow在弹窗的时候背景半透明
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                params.alpha = 1.0f;
//                getWindow().setAttributes(params);
//            }
//        });
//
//        btnCarema.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >=23) {
//                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(ByInputtingActivity.this, Manifest.permission.CAMERA);
//                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(ByInputtingActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
//                        return;
//                    } else {
//                        //只是加了一个uri作为地址传入
//                        Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
//                        intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                        startActivityForResult(intents, TAKE_PHOTO);
//                    }
//                } else {
//                    //只是加了一个uri作为地址传入
//                    Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
//                    intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    startActivityForResult(intents, TAKE_PHOTO);
//                }
//                popupWindow.dismiss();
//            }
//        });
//
//        btnPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(intent, "请选择图片"), PICK_PHOTO);
//                popupWindow.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });

        final String[] items = {"拍照", "从相册选择"};
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setTitle("上传图片")
                .setTitleColor(Color.parseColor("#ff13b3e9"))
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int
                            position, long id) {
                        if (position==0){
                            if (Build.VERSION.SDK_INT >=23) {
                                int checkCallPhonePermission = ContextCompat.checkSelfPermission(ByInputtingActivity.this, Manifest.permission.CAMERA);
                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(ByInputtingActivity.this, new String[]{Manifest.permission.CAMERA}, 111);
                                    return;
                                } else {
                                    //只是加了一个uri作为地址传入
                                    Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
                                    intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    startActivityForResult(intents, TAKE_PHOTO);
                                }
                            } else {
                                //只是加了一个uri作为地址传入
                                Intent intents = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                photoUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImgFile());
                                intents.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                startActivityForResult(intents, TAKE_PHOTO);
                            }
                        }else if (position==1){
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "请选择图片"), PICK_PHOTO);
                        }

                    }
                })
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        //取消按钮字体颜色
                        params.textColor = Color.RED;
                    }
                })
                .show();

    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void retGasMsgChangeView(boolean isSuccess, Result result, String msg) {
        if (isSuccess) {

                if (msg.equals("保存成功")) {

                    new CircleDialog.Builder(this)
                            .setWidth((float) 0.8)
                            .setTitle("提示")
                            .setText("保存成功")
                            .setPositive("确定", null)
                            .show();
                    if (tag==0){
                        finish();
                    }else if (tag==1){
                    }
                }

        } else {
            if (msg.equals("气瓶标签已作废")) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶标签已作废!")
                        .setPositive("确定", null)
                        .show();
            } else if (msg.equals("气瓶标签已被使用")) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶标签已建档!")
                        .setPositive("确定", null)
                        .show();

            } else if (msg.equals("气瓶标签未导入")) {

                new CircleDialog.Builder(this)
                        .setTitle("提示")
                        .setWidth((float) 0.8)
                        .setText("该气瓶标签未导入!")
                        .setPositive("确定", null)
                        .show();
            }
        }
    }


}


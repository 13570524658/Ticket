package com.future.zhh.ticket.presentation.view.fragments;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.presentation.AppApplication;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;
import com.future.zhh.ticket.presentation.view.activities.AboutActivity;
import com.future.zhh.ticket.presentation.view.activities.ByInputtingActivity;
import com.future.zhh.ticket.presentation.view.activities.ClipImageActivity;
import com.future.zhh.ticket.presentation.view.activities.LoginActivity;
import com.future.zhh.ticket.presentation.view.activities.MyQrCodeActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanInfoActivity;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigButton;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.callback.ConfigDialog;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.ButtonParams;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.params.DialogParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static com.future.zhh.ticket.presentation.view.widgets.headimage.FileUtil.getRealFilePathFromUri;

/**
 * Created by Administrator on 2017/11/21.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.ibtnDetail)
    ImageButton ibtnDetail;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ibtnSetting)
    ImageButton ibtnSetting;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.line_one)
    TextView lineOne;
    @BindView(R.id.tv_workerID)
    TextView tvWorkerID;
    @BindView(R.id.line_two)
    TextView lineTwo;
    @BindView(R.id.ivJY)
    ImageView ivJY;
    @BindView(R.id.ivGY)
    ImageView ivGY;
    @BindView(R.id.ivTC)
    ImageView ivTC;
    @BindView(R.id.rlQRcode)
    RelativeLayout rlQRcode;
    @BindView(R.id.rlAbout)
    RelativeLayout rlAbout;
    @BindView(R.id.rlLogOut)
    RelativeLayout rlLogOut;
    public final static String TAG = MyFragment.class.getSimpleName();
    @BindView(R.id.tv_Admin)
    TextView tvAdmin;
    @BindView(R.id.tv_station)
    TextView tvStation;
    @BindView(R.id.iv_headImage)
    ImageView ivHeadImage;
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;
    private static MyFragment fragment;
    private View fragmentView;
    private Unbinder unbinder;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private String station = "";

    private String admin;
    private String id = "";
    private String userName = "";

    private String base64image;
    private static final int TAKE_PHOTO = 0;
    private static final int PICK_PHOTO = 1;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    public  Uri photoUri;
    private String photoPath;
    private     File tempFile;
    private int type=1;
    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment getInstance() {
        if (fragment == null) {
            fragment = new MyFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, fragmentView);
        mContext = getActivity();
        init();
        initView();
        return fragmentView;
    }

    private void init() {
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext, SharedPreferencesUtils.SHARED_USER_INFO);
    }

    private void initView() {
        station = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_GAS_STATION_NAME);
        admin = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_IS_ADMIN);
        id = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_ID);
        userName = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_USER_NAME);
        tvUserName.setText(userName);
        if (admin .equals("0")) {
            tvAdmin.setText("企业");
        } else if (admin.equals("1") ) {
            tvAdmin.setText("店长");
        } else if (admin.equals("2")) {
            tvAdmin.setText("送气工");
        } else if (admin.equals("3")) {
            tvAdmin.setText("充装工");
        } else if (admin.equals("4")) {
            tvAdmin.setText("收银员");
        } else if (admin.equals("5")) {
            tvAdmin.setText("仓管员");
        } else if (admin.equals("6")) {
            tvAdmin.setText("司机");
        } else if (admin.equals("7")) {
            tvAdmin.setText("门卫");
        } else {
            tvAdmin.setText("测试");
        }
        tvStation.setText(station);
        tvWorkerID.setText(id);
        if ((station != null || station.equals("")) & (id != null || id.equals(""))) {
            lineOne.setVisibility(View.VISIBLE);
            lineTwo.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.rlQRcode, R.id.rlAbout, R.id.rlLogOut,R.id.iv_headImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlQRcode:
                Intent intentToLaunch1 = MyQrCodeActivity.getCallingIntent(mContext);
                mContext.startActivity(intentToLaunch1);
                break;
            case R.id.rlAbout:
                Intent intentToLaunch2 = AboutActivity.getCallingIntent(mContext);
                mContext.startActivity(intentToLaunch2);
                break;
            case R.id.rlLogOut:

                new CircleDialog.Builder((FragmentActivity) mContext)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setWidth((float) 0.8)
                        .setTitle("提示")
                        .setText("确定退出?")
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_IS_LOGINED, false);
                                sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_QR_CODE, "");
                                sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_USER_ID, "");
                                sharedPreferencesUtils.saveSharedPreferences(SharedPreferencesUtils.SHARED_PASSWORD, "");
                                ((AppApplication) getActivity().getApplication()).finishAll();
                                startActivity(LoginActivity.getCallingIntent(mContext));
                            }
                        })
                        .show();
                break;
            case R.id.iv_headImage:
                uploadImage();
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }
    /**
     * 上传头像
     */
    private void uploadImage() {
        final String[] items = {"拍照", "从相册选择"};
        new CircleDialog.Builder((FragmentActivity) mContext)
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
                                int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
                                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, 111);
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
            dir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
         tempFile = new File(dir, fileName);
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

            targetWidth = ivHeadImage.getWidth();
            targetHeight = ivHeadImage.getHeight();


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
            base64image = bitmapToBase64(bitmap);
    }

    /**
     * 将图片添加进手机相册
     */
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        mContext.sendBroadcast(mediaScanIntent);
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
//        fragment.onActivityResult(requestCode, resultCode, intent);

            switch (requestCode) {
                case TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                        setImageBitmap();
                        galleryAddPic();

                    }
                    break;
                case PICK_PHOTO:
                    if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();

                    gotoClipActivity(uri);
                    }
                    break;
                case REQUEST_CROP_PHOTO:
                    if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(mContext.getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);

                    ivHeadImage.setImageBitmap(bitMap);
                    }
                    break;
            }

    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mContext, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}

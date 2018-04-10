package com.chinawidth.zzmandroid.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;


import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by aaron on 16/7/27.
 * 二维码扫描工具类
 */
public class CodeUtils {


    public static final String LAYOUT_ID = "layout_id";


    /**
     * 为CaptureFragment设置layout参数
     * @param scannerFragment
     * @param layoutId
     */
    public static void setFragmentArgs(ScannerFragment scannerFragment, int layoutId) {
        if (scannerFragment == null || layoutId == -1) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_ID, layoutId);
        scannerFragment.setArguments(bundle);
    }

}

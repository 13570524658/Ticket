package com.future.zhh.ticket.presentation.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/11/22.
 * 获取App版本工具类
 */

public class VersionUtils {

    private static VersionUtils versionUtils;
    private Context mContext;

    public static VersionUtils getInstance(Context mContext){
        if(versionUtils == null){
            versionUtils = new VersionUtils(mContext);
        }
        return versionUtils;
    }

    public VersionUtils(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取版本号
     * @return
     */
    public String getVersionName(){
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

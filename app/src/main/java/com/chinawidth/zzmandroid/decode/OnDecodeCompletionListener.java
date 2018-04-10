package com.chinawidth.zzmandroid.decode;

import android.os.Bundle;


/**
 * 扫描成功后的回调接口，通过该接口获得扫描结果
 *
 * @author Edward Ye edwardye@21cn.com
 */
public interface OnDecodeCompletionListener {
    public void onDecodeCompletion(String code, Bundle bundle);
}

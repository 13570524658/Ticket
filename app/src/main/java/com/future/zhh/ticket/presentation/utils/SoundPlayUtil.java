package com.future.zhh.ticket.presentation.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Administrator on 2017/11/21.
 * 播放提示音工具类
 */

public class SoundPlayUtil {
    private Context context;
    private SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,5);
    public SoundPlayUtil(Context context) {
        this.context = context;
    }

    /**
     * 播放res文件（.ogg格式）
     * @param res
     */
    public void play(int res) {
        try {
            final int sourceid = soundPool.load(context, res, 0);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                    soundPool.play(sourceid, 2, 2, 0, 0, 1);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

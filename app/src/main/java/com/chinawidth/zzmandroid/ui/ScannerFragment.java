package com.chinawidth.zzmandroid.ui;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.chinawidth.zzmandroid.Constant;
import com.chinawidth.zzmandroid.decode.CameraManager;
import com.chinawidth.zzmandroid.decode.CaptureActivityHandler;
import com.chinawidth.zzmandroid.decode.OnDecodeCompletionListener;
import com.chinawidth.zzmandroid.decode.ViewfinderView;
import com.future.zhh.ticket.R;

import java.io.IOException;




/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements SurfaceHolder.Callback {

    public final static String TAG = ScannerFragment.class.getSimpleName();

    private View fragmentView;
    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;

    private Context context;

    private CameraManager cameraManager;
    private CaptureActivityHandler scanHandler;
    private OnDecodeCompletionListener decodeListener = null;

    private SurfaceHolder surfaceHolder;
    private static long time = 0;

    private boolean hasSurface;

    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private Camera.Parameters parameter = null;

    public Handler getHandler() {
        return scanHandler;
    }

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            int layoutId = bundle.getInt(CodeUtils.LAYOUT_ID);
            if (layoutId != -1) {
                fragmentView = inflater.inflate(layoutId, null);
            }
        }

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_scanner, null);
        }

        context = getActivity();
        initView();
        return fragmentView;
    }

    private void initView(){
        surfaceView = (SurfaceView)fragmentView.findViewById(R.id.surfaceView);
        viewfinderView = (ViewfinderView)fragmentView.findViewById(R.id.viewFinder);
        surfaceView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (scanHandler != null)
                    scanHandler.sendEmptyMessage(Constant.restart_preview);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "[ZZM ��Ϣ]:surfaceChanged( width=" + width + " height=" + height);
    }

    public void handleDecode(String code, Bundle bundle) {
        if (null != decodeListener) {
            playBeepSoundAndVibrate();
            decodeListener.onDecodeCompletion(code,bundle);
        }
    }

    /**
     * 设置扫描成功后的回调接口，通过该接口获得扫描结果
     *
     * @param decodeListener 实现接口的对象
     */
    public void setOnDecodeListener(OnDecodeCompletionListener decodeListener) {
        this.decodeListener = decodeListener;
    }



    @Override
    public void onResume() {
        super.onResume();
        cameraManager = new CameraManager(context);
        viewfinderView.setCameraManager(cameraManager);
        scanHandler = null;

        surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        playBeep = true;
        AudioManager audioService = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (scanHandler != null) {
            scanHandler.quitSynchronously();
            scanHandler = null;
        }
        cameraManager.closeDriver();
        Log.i(TAG, "close camera successfully!");
        if (!hasSurface) {
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
    }

    public void onRestart(){
        onPause();
        onResume();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);

            if (scanHandler == null) {
                scanHandler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            return;
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            return;
        }

    }
    /**
     * 重新扫描，适用于连续扫描
     *
     */
    public void restart() {
        if (scanHandler != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (scanHandler != null) {
//                        cameraManager.startPreview();
                        scanHandler.restartPreviewAndDecode();
                    }
                }
            },1500);
        }
    }


    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public Rect getFrameRect() {
        return cameraManager.getFramingRect();
    }

    public Point getScreenRect() {
        return cameraManager.getScreenRect();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public void flashLightController(boolean isOpen){
        try{
            if(isOpen){
                Camera camera = cameraManager.getCamera();
                parameter = cameraManager.getCamera().getParameters();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameter);
            }else{
                Camera camera = cameraManager.getCamera();
                parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

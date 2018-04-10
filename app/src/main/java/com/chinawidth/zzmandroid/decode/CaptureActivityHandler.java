package com.chinawidth.zzmandroid.decode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.chinawidth.zzmandroid.Constant;
import com.chinawidth.zzmandroid.ui.ScannerFragment;


//import android.annotation.SuppressLint;

//@SuppressLint("ShowToast")
public final class CaptureActivityHandler extends Handler {
	private static final String TAG = CaptureActivityHandler.class
			.getSimpleName();
	private final ScannerFragment scannerView;
	private final DecodeThread decodeThread;
	private State state;
	private final CameraManager cameraManager;

	private boolean isAuto = false;


	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(ScannerFragment scannerView, CameraManager cameraManager) {
		this.scannerView = scannerView;
		decodeThread = new DecodeThread(scannerView);
		decodeThread.start();
		state = State.PREVIEW;
		// Start ourselves capturing previews and decoding.
		this.cameraManager = cameraManager;
		cameraManager.startPreview();
		restartPreviewAndDecode();
	}

//	@SuppressLint("ShowToast")
	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case Constant.restart_preview:
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode();
			cameraManager.requestAutoFocus(this, Constant.auto_focus);
			break;
		case Constant.decode_succeeded:
			Log.d(TAG, "Got decode succeeded message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			scannerView.handleDecode(String.valueOf(message.obj), bundle);
			break;
		case Constant.auto_focus:
			// When one auto focus pass finishes, start another. This is the
			// closest thing to
			// continuous AF. It does seem to hunt a bit, but I'm not sure what
			// else to do.
			if (state == State.PREVIEW) {
				cameraManager.requestAutoFocus(this, Constant.auto_focus);
			}
			break;
		case Constant.decode_failed:
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			state = State.PREVIEW;
			if (!isAuto){
				cameraManager.requestAutoFocus(this, Constant.auto_focus);
				isAuto = true;
			}
			restartPreviewAndDecode();
			break;
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		cameraManager.stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), Constant.quit);
		quit.sendToTarget();
		try {
			// Wait at most half a second; should be enough time, and onPause()
			// will timeout quickly
			decodeThread.join(500L);
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(Constant.decode_succeeded);
		removeMessages(Constant.decode_failed);
	}

	public void restartPreviewAndDecode() {
		
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			cameraManager.requestPreviewFrame(decodeThread.getHandler(),Constant.decode);
			scannerView.drawViewfinder();
		}else if (state == State.PREVIEW) {
			cameraManager.requestPreviewFrame(decodeThread.getHandler(),Constant.decode);
			scannerView.drawViewfinder();
		}
		
	}

}

package com.chinawidth.zzmandroid.decode;

import android.os.Handler;
import android.os.Looper;

import com.chinawidth.zzmandroid.ui.ScannerFragment;

import java.util.concurrent.CountDownLatch;


/**
 * This thread does all the heavy lifting of decoding the images.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
final class DecodeThread extends Thread {
	public static final String BARCODE_BITMAP = "barcode_bitmap";
	private final ScannerFragment scannerView;
	private static Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(ScannerFragment scannerView) {
		this.scannerView = scannerView;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(scannerView);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
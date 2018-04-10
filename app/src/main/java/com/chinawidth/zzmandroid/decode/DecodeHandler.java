package com.chinawidth.zzmandroid.decode;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.chinawidth.zzmandroid.Constant;
import com.chinawidth.zzmandroid.ZZMJni;
import com.chinawidth.zzmandroid.ui.ScannerFragment;



final class DecodeHandler extends Handler {

	private static final String TAG = DecodeHandler.class.getSimpleName();

	private final ScannerFragment scannerView;
	private boolean running = true;
	private ZZMJni zzmDecoder = null;

	DecodeHandler(ScannerFragment scannerView) {
		this.scannerView = scannerView;
		zzmDecoder = new ZZMJni();
	}

	@Override
	public void handleMessage(Message message) {
		if (!running) {
			return;
		}
		switch (message.what) {
		case Constant.decode:
			decode((byte[]) message.obj, message.arg1, message.arg2);
			break;
		case Constant.quit:
			running = false;
			Looper.myLooper().quit();
			break;
		}
	}

	/**
	 * Decode the data within the viewfinder rectangle, and time how long it
	 * took. For efficiency, reuse the same reader objects from one decode to
	 * the next.
	 * 
	 * @param data
	 *            The YUV preview frame.
	 * @param width
	 *            The width of the preview frame.
	 * @param height
	 *            The height of the preview frame.
	 */
	private void decode(byte[] data, int width, int height) {
		Handler handler = scannerView.getHandler();
		String result = null;
		try {
			Rect rect = scannerView.getFrameRect();
			Point point = scannerView.getScreenRect();
//			Log.e(TAG,"-----point.x:"+point.x);
//			Log.e(TAG,"-----point.y:"+point.y);
//			Log.e(TAG,"-----rect.width():"+rect.width());
//			Log.e(TAG,"-----rect.height():"+rect.height());
//			Log.e(TAG,"-----width:"+width);
//			Log.e(TAG,"-----height:"+height);
			int top;
	        int left;
			int w = rect.height() * width / point.x+50;
			int h = rect.width() * height / point.y+50;
//			Log.e(TAG,"---------w:"+w);
//			Log.e(TAG,"---------h:"+h);
			top = rect.left*width / point.x+25;
			left = rect.top* height / point.y+25;
//			top = (height - h)/2;
//			left = (width - w) /2;
//			Log.e(TAG,"---------top:"+top);
//			Log.e(TAG,"---------left:"+left);
			byte[] buffer = new byte[w * h * 3 >> 1];

			int i, j;
			int base = top * width;
			int bufferBase = 0;
			for (i = 0; i < h; i++) {
				for (j = 0; j < w; j++) {
					buffer[bufferBase + j] = data[base + j + left];
				}
				bufferBase += w;
				base += width;
			}

			base = width * height + (top / 2 + top % 2) * width;
			bufferBase = w * h;
			for (i = 0; i < h / 2; i++) {
				for (j = 0; j < w; j++) {
					buffer[bufferBase + j] = data[base + j + left];
				}
				bufferBase += w;
				base += width;
			}
			result = zzmDecoder.decode(buffer, w, h);
		} catch (Exception e) {
			if(handler==null){
				/*try {
					String uploadURL = IPUtils.getBottleErrorRecordURL();
					Map<String, Object> map = new HashMap<String, Object>();
					JSONArray jArray = new JSONArray();
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("pageName", "test0332");
					jsonObject.put("phoneMsg", "");
					String operationMsg = "\n"+LogUtils.getOperation();
					jsonObject.put("errorMsg", "handler is null"+"\n"+"catch 异常信息"+e.toString()+operationMsg);
					jArray.put(jsonObject);
					map.put("data", jArray);
					HttpUtils.doPostFilesRestEx(uploadURL, map, new IHttpClientCallback() {
						
						@Override
						public void excute(int result, String resultData, int requestCode) {
							////scannerView.onResume();
						}
					},CrashHandler.REQUEST_UPLOAD_ERRORMSG);
					ActivityManagerUtil.getInstance().currentActivity().finish();
				} catch (Exception e1) {
					e.printStackTrace();
				}*/
				scannerView.onResume();
				//return;
				
			}
			if(e!=null)
				Log.e(TAG, " " + e.getMessage());
			
			if(handler!=null){
				Message message = Message.obtain(handler, Constant.decode_failed);
				message.obj = result;
				message.sendToTarget();
			}
		}

		if (result != null && !"".equals(result)) {
			if (handler != null) {
				//解码结果，包括第1位码类型(1-条形码，2-真知码，3-QR�?，第2位起为解码结�?				
			    String codeType = result.trim().substring(0,1);
				String resultCode = result.trim().substring(1);
				Message message = Message.obtain(handler,Constant.decode_succeeded);
				message.obj = result;
				Bundle bundle = new Bundle();
				bundle.putString("code", resultCode);
				bundle.putString("type", codeType);
				message.setData(bundle);
				message.sendToTarget();
			}
		} else {
			if (handler != null) {
				Message message = Message.obtain(handler, Constant.decode_failed);
				message.obj = result;
				message.sendToTarget();
			}
		
		}
	}


}

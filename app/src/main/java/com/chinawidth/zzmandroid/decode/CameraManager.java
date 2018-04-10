package com.chinawidth.zzmandroid.decode;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;


import com.future.zhh.ticket.libtooltips.DensityUtil;

import java.io.IOException;
import java.lang.reflect.Method;

public final class CameraManager {
	private static final String TAG = CameraManager.class.getSimpleName();
	private static int MIN_FRAME_WIDTH = 240;
	private static int MIN_FRAME_HEIGHT = 240;
	private static int MAX_FRAME_WIDTH = 600;
	private static int MAX_FRAME_HEIGHT = 400;

	public static int FRAME_MARGINTOP = -1;

	private CameraManager cameraManager;
	private final CameraConfigurationManager configManager;
	private final PreviewCallback previewCallback;
	private static final long AUTO_FOCUS_INTERVAL_MS = 2000L;
	private Handler autoFocusHandler;
	private int autoFocusMessage;
	private Camera camera;

	private Rect framingRect;
	private Rect framingRectInPreview;

	private boolean initialized;
	private boolean previewing;
	private int requestedFramingRectWidth;
	private int requestedFramingRectHeight;

	public CameraManager(Context context) {
		camera = null;
		initialized = false;
		previewing = false;
		configManager = new CameraConfigurationManager(context);
		previewCallback = new PreviewCallback(configManager);
		MIN_FRAME_WIDTH = DensityUtil.dip2px(context,170);
		MIN_FRAME_HEIGHT = DensityUtil.dip2px(context,170);
		MAX_FRAME_WIDTH = DensityUtil.dip2px(context,170);
		MAX_FRAME_HEIGHT = DensityUtil.dip2px(context,170);
	}

	/**
	 * Autofocus callbacks arrive here, and are dispatched to the Handler which
	 * requested them.
	 */
	private final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			if (autoFocusHandler != null) {
				Message message = autoFocusHandler.obtainMessage(
						autoFocusMessage, success);
				// Simulate continuous autofocus by sending a focus request
				// every 1.5 seconds.
				Log.d(TAG, "autoFocusCallback onAutoFocus");
				autoFocusHandler.sendMessageDelayed(message, AUTO_FOCUS_INTERVAL_MS);
				autoFocusHandler = null;
			}
		}
	};
	
	/**
	 * Opens the camera driver and initializes the hardware parameters.
	 * 
	 * @param holder
	 *            The surface object which the camera will draw preview frames
	 *            into.
	 * @throws java.io.IOException
	 *             Indicates the camera driver failed to open.
	 */
	public synchronized void openDriver(SurfaceHolder holder)
			throws IOException {
		Camera theCamera = camera;
		
		if (theCamera == null) {
			theCamera = Camera.open();
			if (theCamera == null && Integer.parseInt(Build.VERSION.SDK) > 8) {// 拿第�?��摄像�?.3以上版本才支�?				
			    try {
					Method openMethod = Camera.class.getMethod("open",
							new Class[] { Integer.TYPE });
					theCamera = (Camera) openMethod.invoke(null,
							new Object[] { new Integer(0) });
				} catch (Exception e) {
					theCamera = null;
					e.printStackTrace();
				}
			}
			if (theCamera == null) {
				throw new IOException();
			}
			camera = theCamera;
		}

		theCamera.setPreviewDisplay(holder);

		if (!initialized) {
			initialized = true;
			configManager.initFromCameraParameters(theCamera);
			if (requestedFramingRectWidth > 0 && requestedFramingRectHeight > 0) {
				setManualFramingRect(requestedFramingRectWidth,
						requestedFramingRectHeight);
				requestedFramingRectWidth = 0;
				requestedFramingRectHeight = 0;
			}
		}

		Camera.Parameters parameters = theCamera.getParameters();
		String parametersFlattened = parameters == null ? null : parameters
				.flatten();
		try {
			configManager.setDesiredCameraParameters(theCamera, false);
		} catch (RuntimeException re) {
			// Driver failed
			Log.w(TAG,
					"Camera rejected parameters. Setting only minimal safe-mode parameters");
			Log.i(TAG, "Resetting to saved camera params: "
					+ parametersFlattened);
			// Reset:
			if (parametersFlattened != null) {
				parameters = theCamera.getParameters();
				parameters.unflatten(parametersFlattened);
				try {
					theCamera.setParameters(parameters);
					configManager.setDesiredCameraParameters(theCamera, true);
				} catch (RuntimeException re2) {
					// Well, darn. Give up
					Log.w(TAG,
							"Camera rejected even safe-mode parameters! No configuration");
				}
			}
		}
	}

	public synchronized boolean isOpen() {
		return camera != null;
	}

	/**
	 * Closes the camera driver if still in use.
	 */
	public synchronized void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
			// Make sure to clear these each time we close the camera, so that
			// any scanning rect
			// requested by intent is forgotten.
			framingRect = null;
			framingRectInPreview = null;
		}
	}

	/**
	 * Asks the camera hardware to begin drawing preview frames to the screen.
	 */
	public synchronized void startPreview() {
		Camera theCamera = camera;
		if (theCamera != null && !previewing) {
			theCamera.startPreview();
			previewing = true;
		}
	}

	/**
	 * Tells the camera to stop drawing preview frames.
	 */
	public synchronized void stopPreview() {
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			theCamera.stopPreview();
			previewCallback.setHandler(null, 0);
			previewing = false;
			autoFocusHandler = null;
		}
	}

	/**
	 * A single preview frame will be returned to the handler supplied. The data
	 * will arrive as byte[] in the message.obj field, with width and height
	 * encoded as message.arg1 and message.arg2, respectively.
	 * 
	 * @param handler
	 *            The handler to send the message to.
	 * @param message
	 *            The what field of the message to be sent.
	 */
	public synchronized void requestPreviewFrame(Handler handler, int message) {
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			previewCallback.setHandler(handler, message);
			theCamera.setOneShotPreviewCallback(previewCallback);
		}
	}

	/**
	 * Asks the camera hardware to perform an autofocus.
	 * 
	 * @param handler
	 *            The Handler to notify when the autofocus completes.
	 * @param message
	 *            The message to deliver.
	 */
	public void requestAutoFocus(Handler handler, int message) {
		Camera theCamera = camera;
		if (theCamera != null && previewing) {
			autoFocusHandler = handler;
		    autoFocusMessage = message;
			theCamera.autoFocus(autoFocusCallback);
		}
	}

	/**
	 * Calculates the framing rect which the UI should draw to show the user
	 * where to place the barcode. This target helps with alignment as well as
	 * forces the user to hold the device far enough away to ensure the image
	 * will be in focus.
	 * 
	 * @return The rectangle to draw on screen in window coordinates.
	 */
	public synchronized Rect getFramingRect() {
		if (framingRect == null) {
			if (camera == null) {
				return null;
			}
			Point screenResolution = configManager.getScreenResolution();
			if (screenResolution == null) {
				// Called early, before init even finished
				return null;
			}
			//长方形扫码框
//			int width = screenResolution.y - 10;
//			if (width < MIN_FRAME_WIDTH) {
//				width = MIN_FRAME_WIDTH;
//			} else if (width > MAX_FRAME_WIDTH) {
//				width = MAX_FRAME_WIDTH;
//			}
//			int height = screenResolution.x * 2 / 5;
//			if (height < MIN_FRAME_HEIGHT) {
//				height = MIN_FRAME_HEIGHT;
//			} else if (height > MAX_FRAME_HEIGHT) {
//				height = MAX_FRAME_HEIGHT;
//			}
//			int leftOffset = (screenResolution.y - width) / 2;
//			int topOffset = (screenResolution.x - height) / 2;
//			framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
//					topOffset + height);
			
			//正方形扫码框
			int width = (int)(screenResolution.y * 3/4);
			if (width < MIN_FRAME_WIDTH) {
				width = MIN_FRAME_WIDTH;
			} else if (width > MAX_FRAME_WIDTH) {
				width = MAX_FRAME_WIDTH;
			}
			int leftOffset = (screenResolution.y - width) / 2;
			int topOffset = 0;
			if(FRAME_MARGINTOP != -1)
				topOffset = FRAME_MARGINTOP;
			else {
				topOffset = (screenResolution.x - width) / 2;
			}
			//int topOffset = (int)(screenResolution.x - width) * (1.4/3.0);
			framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
					topOffset + width);
			Log.e(TAG, "Calculated framing rect: " + framingRect);
		}
		return framingRect;
	}

	/**
	 * Like {@link #getFramingRect} but coordinates are in terms of the preview
	 * frame, not UI / screen.
	 */
	public synchronized Rect getFramingRectInPreview() {
		if (framingRectInPreview == null) {
			Rect framingRect = getFramingRect();
			if (framingRect == null) {
				return null;
			}
			Rect rect = new Rect(framingRect);
			Point cameraResolution = configManager.getCameraResolution();
			Point screenResolution = configManager.getScreenResolution();

			if (cameraResolution == null || screenResolution == null) {
				// Called early, before init even finished
				return null;
			}

			rect.left = rect.left * cameraResolution.y / screenResolution.x;
			rect.right = rect.right * cameraResolution.y / screenResolution.x;
			rect.top = rect.top * cameraResolution.x / screenResolution.y;
			rect.bottom = rect.bottom * cameraResolution.x / screenResolution.y;

			framingRectInPreview = rect;
		}
		return framingRectInPreview;
	}

	/**
	 * Allows third party apps to specify the scanning rectangle dimensions,
	 * rather than determine them automatically based on screen resolution.
	 * 
	 * @param width
	 *            The width in pixels to scan.
	 * @param height
	 *            The height in pixels to scan.
	 */
	public synchronized void setManualFramingRect(int width, int height) {
		if (initialized) {
			Point screenResolution = configManager.getScreenResolution();
			if (width > screenResolution.x) {
				width = screenResolution.x;
			}
			if (height > screenResolution.y) {
				height = screenResolution.y;
			}
			int leftOffset = (screenResolution.x - width) / 2;
			int topOffset = (screenResolution.y - height) / 2;
			framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
					topOffset + height);

			Log.e(TAG, "Calculated manual framing rect: " + framingRect);
			framingRectInPreview = null;
		} else {
			requestedFramingRectWidth = width;
			requestedFramingRectHeight = height;
		}
	}

	public synchronized Point getScreenRect() {
		return configManager.getScreenResolution();
	}

	protected void setDisplayOrientation(Camera camera, int angle) {
		Method downPolymorphic;
		try {
			downPolymorphic = camera.getClass().getMethod(
					"setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null)
				downPolymorphic.invoke(camera, new Object[] { angle });
		} catch (Exception e1) {
		}
	}
	
	public void setParameters(int zoom) {
		try {
			if(zoom > getMaxZoom())
				return;
			Camera.Parameters parameters = camera.getParameters();
			parameters.setZoom(zoom);
			camera.setParameters(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMaxZoom() {
		if (camera == null) {
			return 0;
		}
		Camera.Parameters parameters = camera.getParameters();
		int zoom = parameters.getMaxZoom();
		return zoom;
	}
	
	public boolean isZoom(){
		if (camera == null) {
			return false;
		}
		Camera.Parameters parameters = camera.getParameters();
		if (parameters.isZoomSupported() && getMaxZoom()>0) {
			return true;
		}
		return false;
	}

	public Camera getCamera() {
		return camera;
	}
	
	
}

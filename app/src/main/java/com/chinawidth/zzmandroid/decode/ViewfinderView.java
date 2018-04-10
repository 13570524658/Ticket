package com.chinawidth.zzmandroid.decode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.libtooltips.DensityUtil;


/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

  private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
  private static final long ANIMATION_DELAY = 80L;
  private static final int CURRENT_POINT_OPACITY = 0xA0;
 // private static final int MAX_RESULT_POINTS = 20;
  private static final int POINT_SIZE = 6;

  private CameraManager cameraManager;
  private final Paint paint;
  private Bitmap resultBitmap;
  private final int maskColor;
  private final int resultColor;
  private final int laserColor;
  private final int frameColor;
 // private final int resultPointColor;
  private int scannerAlpha;

  // This constructor is used when the class is built from an XML resource.
  public ViewfinderView(Context context, AttributeSet attrs) {
      super(context, attrs);

    // Initialize these once for performance rather than calling them every time in onDraw().
      paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    maskColor = 0x60000000;
//    resultColor = 0xb0000000;
//    laserColor = 0xcc7901;
//    frameColor = 0xffffffff;

      maskColor = 0x60000000;
      resultColor = 0xb0000000;
      frameColor = 0xffffffff;
      laserColor = 0xffffffff;
      scannerAlpha = 0;
      initInnerRect(context,attrs);
  }

  public void setCameraManager(CameraManager cameraManager) {
    this.cameraManager = cameraManager;
  }

    /**
     * 初始化内部框的大小
     * @param context
     * @param attrs
     */
    private void initInnerRect(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.innerrect);

        // 扫描框距离顶部
        float innerMarginTop = ta.getDimension(R.styleable.innerrect_inner_margintop, -1);
        if (innerMarginTop != -1) {
            CameraManager.FRAME_MARGINTOP = (int) innerMarginTop;
        }

//        // 扫描框的宽度
//        CameraManager.FRAME_WIDTH = (int) ta.getDimension(R.styleable.innerrect_inner_width, DisplayUtil.screenWidthPx / 2);
//
//        // 扫描框的高度
//        CameraManager.FRAME_HEIGHT = (int) ta.getDimension(R.styleable.innerrect_inner_height, DisplayUtil.screenWidthPx / 2);

//        // 扫描框边角颜色
//        innercornercolor = ta.getColor(R.styleable.innerrect_inner_corner_color, Color.parseColor("#45DDDD"));
//        // 扫描框边角长度
//        innercornerlength = (int) ta.getDimension(R.styleable.innerrect_inner_corner_length, 65);
//        // 扫描框边角宽度
//        innercornerwidth = (int) ta.getDimension(R.styleable.innerrect_inner_corner_width, 15);
//
//         //扫描bitmap
//        Drawable drawable = ta.getDrawable(R.styleable.innerrect_inner_scan_bitmap);
//        if (drawable != null) {
//        }
//
//        // 扫描控件
//        scanLight = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.innerrect_inner_scan_bitmap, R.drawable.scan_light));
//        // 扫描速度
//        SCAN_VELOCITY = ta.getInt(R.styleable.innerrect_inner_scan_speed, 5);
//
//        isCircle = ta.getBoolean(R.styleable.innerrect_inner_scan_iscircle, true);

        ta.recycle();
    }

  @Override
  public void onDraw(Canvas canvas) {
    if (cameraManager == null) {
      return; // not ready yet, early draw before done configuring
    }
    Rect frame = cameraManager.getFramingRect();
    if (frame == null) {
      return;
    }
    int width = canvas.getWidth();
    int height = canvas.getHeight();

    // Draw the exterior (i.e. outside the framing rect) darkened
    paint.setColor(resultBitmap != null ? resultColor : maskColor);

      canvas.drawRect(0, 0, width, frame.top, paint);
      canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
      canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
      canvas.drawRect(0, frame.bottom + 1, width, height, paint);

//    canvas.drawRect(0, 0, width, frame.top, paint);
//    canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
//    canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
//    canvas.drawRect(0, frame.bottom + 1, width, height, paint);

    // 这里画取景框四个角落的夹角
    paint.setColor(frameColor);

      int corWidth = DensityUtil.dip2px(getContext(),5);
      int corLength = DensityUtil.dip2px(getContext(),30);
      // 左上角
      canvas.drawRect(frame.left, frame.top, frame.left + corWidth, frame.top
              + corLength, paint);
      canvas.drawRect(frame.left, frame.top, frame.left
              + corLength, frame.top + corWidth, paint);
      // 右上角
      canvas.drawRect(frame.right - corWidth, frame.top, frame.right,
              frame.top + corLength, paint);
      canvas.drawRect(frame.right - corLength, frame.top,
              frame.right, frame.top + corWidth, paint);
      // 左下角
      canvas.drawRect(frame.left, frame.bottom - corLength,
              frame.left + corWidth, frame.bottom, paint);
      canvas.drawRect(frame.left, frame.bottom - corWidth, frame.left
              + corLength, frame.bottom, paint);
      // 右下角
      canvas.drawRect(frame.right - corWidth, frame.bottom - corLength,
              frame.right, frame.bottom, paint);
      canvas.drawRect(frame.right - corLength, frame.bottom - corWidth,
              frame.right, frame.bottom, paint);
    /************************/
    
    if (resultBitmap != null) {
      // Draw the opaque result bitmap over the scanning rectangle
      paint.setAlpha(CURRENT_POINT_OPACITY);
      canvas.drawBitmap(resultBitmap, null, frame, paint);
    } else {
      // Draw a red "laser scanner" line through the middle to show decoding is active
      paint.setColor(laserColor);
      paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
      scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
      int middle = frame.height() / 2 + frame.top;
//      canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);

      int hmiddle=frame.width()/2+frame.left;
      //中横线
      canvas.drawRect(hmiddle - 30, middle - 1, hmiddle - 10, middle + 1, paint);
      canvas.drawRect(hmiddle + 10, middle - 1, hmiddle + 30, middle + 1, paint);
      //中竖线
      canvas.drawRect(hmiddle-1, middle - 30, hmiddle+1, middle - 10, paint);
      canvas.drawRect(hmiddle-1, middle + 10, hmiddle+1, middle + 30, paint);

      postInvalidateDelayed(ANIMATION_DELAY,
                            frame.left - POINT_SIZE,
                            frame.top - POINT_SIZE,
                            frame.right + POINT_SIZE,
                            frame.bottom + POINT_SIZE);
    }
  }

  public void drawViewfinder() {
    Bitmap resultBitmap = this.resultBitmap;
    this.resultBitmap = null;
    if (resultBitmap != null) {
      resultBitmap.recycle();
    }
    invalidate();
  }

  /**
   * Draw a bitmap with the result points highlighted instead of the live scanning display.
   *
   * @param barcode An image of the decoded barcode.
   */
  public void drawResultBitmap(Bitmap barcode) {
    resultBitmap = barcode;
    invalidate();
  }

}

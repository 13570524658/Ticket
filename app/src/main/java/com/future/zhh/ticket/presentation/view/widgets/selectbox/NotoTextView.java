package com.future.zhh.ticket.presentation.view.widgets.selectbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/22.
 */

@SuppressLint("AppCompatCustomView")
public class NotoTextView extends TextView {
    public NotoTextView(Context context) {
        super(context);
        init(context);
    }

    public NotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "NotoSansUI-Regular.ttf");
        setTypeface(tf);
    }
}

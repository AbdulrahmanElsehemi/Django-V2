package com.minerva.unsplashapi.common.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Abdulrahman on 1/27/2019.
 */

public class AdaptiveImageView extends ImageView {

    private int defaultWidth = 0;
    private int defaultHeight = 0;


    private float scale = 0;

    public AdaptiveImageView(Context context) {
        super(context);
    }

    public AdaptiveImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public AdaptiveImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Bitmap bitmap = null;
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        bitmap = Bitmap.createBitmap(w, h, config);
        //canvas = new Canvas(bitmap);
        //drawable.setBounds(0, 0, w, h);
        //drawable.draw(canvas);
        if (bitmap.getWidth() == 0 || bitmap.getHeight() == 0) {
            return;
        }

        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }
        if (defaultHeight == 0) {
            defaultHeight = getHeight();
        }


        setMinimumHeight(getWidth());
        scale = (float) defaultWidth / (float) bitmap.getWidth();
        defaultHeight = Math.round(bitmap.getHeight() * scale);
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.width = defaultWidth;
        params.height = defaultHeight;
        this.setLayoutParams(params);
        super.onDraw(canvas);
    }
}

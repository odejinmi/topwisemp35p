//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LinePathView extends View {
    private Context mContext;
    private float mX;
    private float mY;
    private final Paint mGesturePaint = new Paint();
    private final Path mPath = new Path();
    private Canvas cacheCanvas;
    private Bitmap cachebBitmap;
    private boolean isTouched = false;
    private int mPaintWidth = 10;
    private int mPenColor = -16777216;
    private int mBackColor = 0;

    public LinePathView(Context context) {
        super(context);
        this.init(context);
    }

    public LinePathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public LinePathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public void init(Context context) {
        this.mContext = context;
        this.mGesturePaint.setAntiAlias(true);
        this.mGesturePaint.setStyle(Style.STROKE);
        this.mGesturePaint.setStrokeWidth((float)this.mPaintWidth);
        this.mGesturePaint.setColor(this.mPenColor);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.cachebBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Config.ARGB_8888);
        this.cacheCanvas = new Canvas(this.cachebBitmap);
        this.cacheCanvas.drawColor(this.mBackColor);
        this.isTouched = false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.touchDown(event);
                break;
            case 1:
                this.cacheCanvas.drawPath(this.mPath, this.mGesturePaint);
                this.mPath.reset();
                break;
            case 2:
                this.isTouched = true;
                this.touchMove(event);
        }

        this.invalidate();
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.cachebBitmap, 0.0F, 0.0F, this.mGesturePaint);
        canvas.drawPath(this.mPath, this.mGesturePaint);
    }

    private void touchDown(MotionEvent event) {
        this.mPath.reset();
        float x = event.getX();
        float y = event.getY();
        this.mX = x;
        this.mY = y;
        this.mPath.moveTo(x, y);
    }

    private void touchMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float previousX = this.mX;
        float previousY = this.mY;
        float dx = Math.abs(x - previousX);
        float dy = Math.abs(y - previousY);
        if (dx >= 3.0F || dy >= 3.0F) {
            float cX = (x + previousX) / 2.0F;
            float cY = (y + previousY) / 2.0F;
            this.mPath.quadTo(previousX, previousY, cX, cY);
            this.mX = x;
            this.mY = y;
        }

    }

    public void clean() {
        if (this.cacheCanvas != null) {
            this.isTouched = false;
            this.mGesturePaint.setColor(this.mPenColor);
            this.cacheCanvas.drawColor(this.mBackColor, Mode.CLEAR);
            this.mGesturePaint.setColor(this.mPenColor);
            this.invalidate();
        }

    }

    public void save(String path) throws IOException {
        this.save(path, false, 0);
    }

    public void save(String path, boolean clearBlank, int blank) throws IOException {
        Bitmap bitmap = this.cachebBitmap;
        if (clearBlank) {
            bitmap = this.clearBlank(bitmap, blank);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bos);
        byte[] buffer = bos.toByteArray();
        if (buffer != null) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.close();
        }

    }

    public Bitmap getBitMap() {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bitmap = this.getDrawingCache();
        this.setDrawingCacheEnabled(false);
        return bitmap;
    }

    private Bitmap clearBlank(Bitmap bp, int blank) {
        int HEIGHT = bp.getHeight();
        int WIDTH = bp.getWidth();
        int top = 0;
        int left = 0;
        int right = 0;
        int bottom = 0;
        int[] pixs = new int[WIDTH];

        boolean isStop;
        int x;
        int[] var12;
        int var13;
        int var14;
        int pix;
        for(x = 0; x < HEIGHT; ++x) {
            bp.getPixels(pixs, 0, WIDTH, 0, x, WIDTH, 1);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;

            for(var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    top = x;
                    isStop = true;
                    break;
                }
            }

            if (isStop) {
                break;
            }
        }

        for(x = HEIGHT - 1; x >= 0; --x) {
            bp.getPixels(pixs, 0, WIDTH, 0, x, WIDTH, 1);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;

            for(var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    bottom = x;
                    isStop = true;
                    break;
                }
            }

            if (isStop) {
                break;
            }
        }

        pixs = new int[HEIGHT];

        for(x = 0; x < WIDTH; ++x) {
            bp.getPixels(pixs, 0, 1, x, 0, 1, HEIGHT);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;

            for(var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    left = x;
                    isStop = true;
                    break;
                }
            }

            if (isStop) {
                break;
            }
        }

        for(x = WIDTH - 1; x > 0; --x) {
            bp.getPixels(pixs, 0, 1, x, 0, 1, HEIGHT);
            isStop = false;
            var12 = pixs;
            var13 = pixs.length;

            for(var14 = 0; var14 < var13; ++var14) {
                pix = var12[var14];
                if (pix != this.mBackColor) {
                    right = x;
                    isStop = true;
                    break;
                }
            }

            if (isStop) {
                break;
            }
        }

        if (blank < 0) {
            blank = 0;
        }

        left = left - blank > 0 ? left - blank : 0;
        top = top - blank > 0 ? top - blank : 0;
        right = right + blank > WIDTH - 1 ? WIDTH - 1 : right + blank;
        bottom = bottom + blank > HEIGHT - 1 ? HEIGHT - 1 : bottom + blank;
        return Bitmap.createBitmap(bp, left, top, right - left, bottom - top);
    }

    public void setPaintWidth(int mPaintWidth) {
        mPaintWidth = mPaintWidth > 0 ? mPaintWidth : 10;
        this.mPaintWidth = mPaintWidth;
        this.mGesturePaint.setStrokeWidth((float)mPaintWidth);
    }

    public void setBackColor(int backColor) {
        this.mBackColor = backColor;
    }

    public void setPenColor(int mPenColor) {
        this.mPenColor = mPenColor;
        this.mGesturePaint.setColor(mPenColor);
    }

    public boolean getTouched() {
        return this.isTouched;
    }
}

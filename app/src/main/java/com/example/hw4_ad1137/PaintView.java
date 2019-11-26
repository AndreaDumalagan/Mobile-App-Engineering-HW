package com.example.hw4_ad1137;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {

    public static int BRUSH_SIZE = 20;
    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;

    private int CLICK_ACTION_THRESHOLD = 200;
    private float startX;
    private float startY;
    private float mX, mY;

    private float multiX[]= new float[5];
    private float multiY[] = new float[5];
    private Paint[] multiPaint;
    private int multiCount;

    private float clickX, clickY;

    private Path mPath;
    private Paint mPaint;
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ArrayList<StrokePath> paths = new ArrayList<StrokePath>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private boolean emboss;
    private boolean blur;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);


    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        mEmboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.4f, 6, 3.5f);
        mBlur = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
    }

    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        circlePaint.setColor(Color.YELLOW);
        circlePaint.setStyle(Paint.Style.FILL);

        multiPaint = new Paint[5];
        for(int i = 0; i < 5; i++){
            multiPaint[i] = new Paint();
            multiPaint[i].setAntiAlias(true);
        }

        multiPaint[0].setColor(Color.MAGENTA);
        multiPaint[0].setStyle(Paint.Style.FILL);
        multiPaint[1].setColor(Color.CYAN);
        multiPaint[1].setStyle(Paint.Style.FILL);
        multiPaint[2].setColor(Color.GREEN);
        multiPaint[2].setStyle(Paint.Style.FILL);
        multiPaint[3].setColor(Color.BLUE);
        multiPaint[3].setStyle(Paint.Style.FILL);
        multiPaint[4].setColor(Color.YELLOW);
        multiPaint[4].setStyle(Paint.Style.FILL);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }

    public void normal() {
        emboss = false;
        blur = false;
    }

    public void clear() {
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        normal();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for (StrokePath strokePath : paths) {
            mPaint.setColor(strokePath.color);
            mPaint.setStrokeWidth(strokePath.strokeWidth);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(strokePath.path, mPaint);

            for(int i = 0; i < multiCount; i++){
                mCanvas.drawCircle(multiX[i], multiY[i], 150, multiPaint[i]);
            }

        }


        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        mPath = new Path();
        StrokePath strokePath = new StrokePath(currentColor, emboss, blur, strokeWidth, mPath);
        paths.add(strokePath);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int pointCount = event.getPointerCount();

        for(int i = 0; i < pointCount; i++){
            int id = event.getPointerId(i);

            if(id < 5){
                multiCount = pointCount;

                multiX[id] = event.getX(i);
                multiY[id] = event.getY(i);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        startX = event.getX();
                        startY = event.getY();

                        touchStart(x, y);
                        invalidate();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        clickX = x;
                        clickY = y;

                        touchMove(x, y);
                        invalidate();
                        break;

                    case MotionEvent.ACTION_UP:

                        float endX = event.getX(i);
                        float endY = event.getY(i);

                        if (isAClick(startX, endX, startY, endY)) {

                            multiX[id] = endX;
                            multiY[id] = endY;
                        }

                        touchUp();
                        invalidate();
                        break;
                }
            }
        }

        return true;
    }

    private boolean isAClick(float startX, float endX, float startY, float endY){
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        return !(differenceX > CLICK_ACTION_THRESHOLD/* =5 */ || differenceY > CLICK_ACTION_THRESHOLD);
    }
}
package com.example.module1_drawingnotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawingView extends View {
    private static final String TAG = "DrawingView";

    private Canvas canvas;
    private Path path;
    private Paint paint;

    private  int currentColor;
    private  int currentSize;

    private Bitmap bitmap;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(Color.GRAY);

        canvas = new Canvas();
        path = new Path();
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public void setCurrentColor(int currentColor) {
        this.currentColor = currentColor;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: DOWN " + event.getX() + " " + event.getY());

                paint.setColor(currentColor);
                paint.setStrokeWidth(currentSize);

                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: MOVE " + event.getX() + " " + event.getY());

                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: UP " + event.getX() + " " + event.getY());
                canvas.drawPath(path, paint);
                path.reset();
                break;
        }

        canvas.drawPath(path, paint);
        invalidate();

        return true;
    }
}

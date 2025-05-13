package com.example.overlaysensorapp;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class OverlayService extends Service {
    private WindowManager windowManager;
    private View overlayView;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        overlayView = new View(this) {
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();

                // Lingkaran Merah
                paint.setColor(Color.RED);
                canvas.drawCircle(100, 100, 50, paint);

                // Lingkaran Biru
                paint.setColor(Color.BLUE);
                canvas.drawCircle(300, 100, 50, paint);

                // Kotak Hijau
                paint.setColor(Color.GREEN);
                canvas.drawRect(150, 300, 250, 400, paint);
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = event.getX();
                    float y = event.getY();
                    if (x > 150 && x < 250 && y > 300 && y < 400) {
                        performClick();
                    }
                }
                return true;
            }
        };

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        windowManager.addView(overlayView, params);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayView != null) {
            windowManager.removeView(overlayView);
        }
    }
}

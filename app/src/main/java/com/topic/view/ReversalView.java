package com.topic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.topic.R;

/**
 * Created by 坚守内心的宁静 on 2017/12/30.
 */

public class ReversalView extends View {
    Bitmap bitmap;
    Camera camera;
    Paint paint;

    float degreesZ;
    float degreesY;
    float endY;

    public ReversalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Matrix matrix = new Matrix();
        matrix.postScale(0.4f, 0.4f);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lks_for_blank_url);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        camera = new Camera();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int x = centerX - bitmapWidth/2;
        int y = centerY - bitmapHeight/2;

        canvas.save();
        camera.save();
        //canvas.clipRect(centerX, y, x+ bitmapWidth, y + bitmapHeight);
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreesZ);
        camera.rotateY(degreesY);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreesZ);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        camera.restore();
        canvas.restore();

        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreesZ);
        camera.rotateY(endY);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        canvas.rotate(degreesZ);
        canvas.translate(-centerX, -centerY);

        canvas.drawBitmap(bitmap, x, y, paint);
        camera.restore();
        canvas.restore();

    }

    public void setDegreesZ(float degreesZ) {
        this.degreesZ = degreesZ;
        invalidate();
    }

    public void setDegreesY(float degreesY) {
        this.degreesY = degreesY;
        invalidate();
    }

    public void setEndY(float endY) {
        this.endY = endY;
        invalidate();
    }

    public void reset() {
        degreesY = 0;
        degreesZ = 0;
        endY = 0;
    }
}

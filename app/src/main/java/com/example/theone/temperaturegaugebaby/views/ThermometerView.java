package com.example.theone.temperaturegaugebaby.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.theone.temperaturegaugebaby.R;


/**
 * Created by yeah on 2016/4/16.
 */
public class ThermometerView extends FrameLayout {

    private int barResId = R.drawable.thermometer_bar;
    private int fgResId = R.drawable.thermometer_fg;
    private Bitmap mScaledBitmap;
    private Bitmap mBitmap;
    private float mRatio = 0.99f;
    private Paint paint;
    private Bitmap disPlayBitmap;

    public ThermometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
      paint =  new Paint();
        paint.setColor(getResources().getColor(R.color.blue));
    }

    public ThermometerView(Context context) {
        super(context);
        init();
    }

    public ThermometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int width = getWidth();
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeResource(getContext().getResources(), getBarResId());
            mScaledBitmap = BitmapFactory.decodeResource(getContext().getResources(), getFgResId());
        }
        int height =  mBitmap.getHeight();
        height*=(1-mRatio);

        disPlayBitmap = cropBitmap(mBitmap,height);
       canvas.drawBitmap(disPlayBitmap, 0f, height, null);

        canvas.drawBitmap(mScaledBitmap, 0f, 0f, null);

    }

    private Bitmap cropBitmap(Bitmap mBitmap, int height) {

        return Bitmap.createBitmap(mBitmap,0,height,mBitmap.getWidth(),mBitmap.getHeight()-height);
    }

    public int getBarResId() {
        return barResId;
    }

    public int getFgResId() {
        return fgResId;
    }

    public void setFgResId(int fgResId) {
        this.fgResId = fgResId;
    }
}

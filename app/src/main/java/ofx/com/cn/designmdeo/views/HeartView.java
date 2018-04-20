package ofx.com.cn.designmdeo.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by OFX040 on 2018/4/20.
 */

public class HeartView extends View {
    private Paint mPaint;
    private Path mPath, mPath2;
    private PathMeasure mMeasure;
    private float[] mCurrentDst = new float[2];

    public HeartView(Context context) {
        this(context,null);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initPanit();
        initPath();
        initAnimation();
    }



    private void initPath() {
        mPath = new Path();
        RectF ovall2 = new RectF(-100f,-100f,100f,100f);
        mPath.addArc(ovall2,0,-359.9f);


        mMeasure = new PathMeasure(mPath,false);
        mMeasure.getPosTan(0,mCurrentDst,null);

        mPath2 = new Path();
        mPath2.addCircle(0,0,20, Path.Direction.CW);
        mPath2.moveTo(mCurrentDst[0],mCurrentDst[1]);

    }

    private void initPanit() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }


    private void initAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,mMeasure.getLength());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mMeasure.getPosTan(value,mCurrentDst,null);
                Log.d("TAg",value+" x:"+mCurrentDst[0]+" y:"+mCurrentDst[1]);
                invalidate();
            }
        });
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    @Override
    protected void onDraw(Canvas c) {
        c.translate(getWidth()/2,getHeight()/2);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        c.drawPath(mPath,mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        c.drawCircle(mCurrentDst[0],mCurrentDst[1],10,mPaint);

    }
}

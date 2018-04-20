package ofx.com.cn.designmdeo.views;

import android.animation.Animator;
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
 * Created by OFX040 on 2018/4/19.
 */

public class FollowPathView extends View {
    private Path mPath_search;
    private Path mPath_cicrle;
    private PathMeasure mMeasure;
    private int mHeight;
    private int mWidth;
    private Seach_State mState = Seach_State.START;
    private ValueAnimator mValueAnimator_search;
    private long defaultduration = 3000;
    private Paint mPaint;
    private float cureentAnimaVa_lue;


    public FollowPathView(Context context) {
        this(context,null);
    }

    public FollowPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FollowPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        initPaint();
        initPath();
        initAnimation();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    private void initPath() {
        mPath_search = new Path();
        mPath_cicrle = new Path();
        mMeasure = new PathMeasure();

        RectF ovall = new RectF(-50f,-50f,50f,50f);
        mPath_search.addArc(ovall,45,359.9f);

        RectF ovall2 = new RectF(-100f,-100f,100f,100f);
        mPath_cicrle.addArc(ovall2,45,-359.9f);

        float[] pos = new float[2];

        mMeasure.setPath(mPath_cicrle,false);
        mMeasure.getPosTan(0,pos,null);

        mPath_search.lineTo(pos[0],pos[1]);
        Log.d("TAg", "pos"+pos[0]+":"+pos[1]);
    }

    private void initAnimation() {
        mValueAnimator_search = ValueAnimator.ofFloat(0.0f,1.0f).setDuration(defaultduration);
        mValueAnimator_search.setInterpolator(new LinearInterpolator());
        mValueAnimator_search.addUpdateListener(uplistener);
        mValueAnimator_search.addListener(listener);
        mValueAnimator_search.setDuration(3000);
        mValueAnimator_search.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator_search.start();
    }

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            Log.d("TAg","SmState:"+mState);
            if(mState == Seach_State.START){
                mState = Seach_State.SEARCHIN;
                Log.d("TAg","mState:"+mState);
                mValueAnimator_search.setRepeatMode(ValueAnimator.REVERSE);
            }
            else if(mState == Seach_State.SEARCHIN){
                mState = Seach_State.END;
                mValueAnimator_search.setRepeatMode(ValueAnimator.RESTART);
                Log.d("TAg","mState:"+mState);

            }
            else if(mState == Seach_State.END){
                mValueAnimator_search.cancel();
                Log.d("TAg","mState:"+mState);
                mState = Seach_State.STOP;
            }
            Log.d("TAg","EmState:"+mState);
        }
    };
    ValueAnimator.AnimatorUpdateListener uplistener =  new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            cureentAnimaVa_lue = (float) animation.getAnimatedValue();
            invalidate();
        }
    };



    public enum Seach_State{
        START,END,NONE,SEARCHIN,STOP
    }

    @Override
    protected void onDraw(Canvas c) {
        c.translate(mWidth/2, mHeight/2); //平移原点坐标到控件中间

        switch (mState){
            case NONE:
                c.drawPath(mPath_search,mPaint);
                break;

            case START:
                mMeasure.setPath(mPath_search,true);
                Path path = new Path();
                mMeasure.getSegment(mMeasure.getLength()*cureentAnimaVa_lue,mMeasure.getLength(),path,true);
                c.drawPath(path,mPaint);
                break;

            case SEARCHIN:
                mMeasure.setPath(mPath_cicrle,true);
                Path path_search = new Path();
                mMeasure.getSegment(mMeasure.getLength()*cureentAnimaVa_lue-30,mMeasure.getLength(),path_search,true);
                c.drawPath(path_search,mPaint);
                break;

            case END:
                mMeasure.setPath(mPath_search,true);
                Path path_view = new Path();
                mMeasure.getSegment(0,mMeasure.getLength()*cureentAnimaVa_lue,path_view,true);
                c.drawPath(path_view,mPaint);
                break;

            case STOP:
                c.drawPath(mPath_search,mPaint);
                c.drawPath(mPath_cicrle,mPaint);
                break;
        }
    }
}

























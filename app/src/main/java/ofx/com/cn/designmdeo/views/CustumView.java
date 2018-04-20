package ofx.com.cn.designmdeo.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by OFX040 on 2018/4/18.
 */

public class CustumView extends View {


    private Paint paint;
    private int raduis = 100;

    public CustumView(Context context) {
        this(context,null);
    }

    public CustumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

//        //定时器
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                raduis++;
//                if(raduis == 400)
//                    raduis = 100;
//                int al = (int) (((raduis-100)/300.0f)*255);
//                paint.setAlpha(255-al);
//                postInvalidate();
//            }
//        },10,10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withdMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(withdMode == MeasureSpec.UNSPECIFIED){
            Log.d("CustumTAg","withdMode=UNSPECIFIED");
        }
        if(heightMode == MeasureSpec.UNSPECIFIED){
            Log.d("CustumTAg","heightMode=UNSPECIFIED");
        }
        if(withdMode == MeasureSpec.AT_MOST){
            Log.d("CustumTAg","withdMode=AT_MOST");
        }
        if(heightMode == MeasureSpec.AT_MOST){
            Log.d("CustumTAg","heightMode=AT_MOST");
        }
        if(withdMode == MeasureSpec.EXACTLY){
            Log.d("CustumTAg","withdMode=EXACTLY");
        }
        if(heightMode == MeasureSpec.EXACTLY){
            Log.d("CustumTAg","heightMode=EXACTLY");
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(getWidth()/2,getHeight()/2,raduis,paint);
        PointF pointF;
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10f);
        for(float i = 0.0f; i < 100; i+=0.1f){
            pointF = new PointF();
            pointF.x = i;
            pointF.y = (float) Math.sin(i);
            canvas.drawPoint(pointF.x,pointF.y,paint);
        }


    }
}

package ofx.com.cn.designmdeo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.text);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mTv,"textSize",0f,40f);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator animator2 = ObjectAnimator.ofInt(mTv,"textColor", Color.rgb(255,0,0),Color.rgb(255,255,255));
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet set = new AnimatorSet();

        set.playTogether(animator2);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(1000);
        set.start();

    }


}

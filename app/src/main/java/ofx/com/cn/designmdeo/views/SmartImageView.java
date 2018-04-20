package ofx.com.cn.designmdeo.views;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.io.IOException;

import ofx.com.cn.designmdeo.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by OFX040 on 2018/4/18.
 */

public class SmartImageView extends AppCompatImageView {


    private String mImgUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2809576112,626361756&fm=27&gp=0.jpg";
    private byte[] picutru;
    private int mWidth,mHeight;
    private int mWidMode;
    private int mHeigMode;
    private int mScal = 1; //图片缩放比
    private int mSlefHeight = 0; //控件本身的高度
    private int mSlefWidth = 0;  //控件本身的宽度
    private float ratio = 0.0f;


    public SmartImageView(Context context) {
        this(context,null);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        intiView(context, attrs,defStyleAttr);
    }



    private void intiView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmartImageView);
        String url = a.getString(R.styleable.SmartImageView_url);
        if(url != null){
            mImgUrl = url;
        }
        System.out.println(mImgUrl);
        a.recycle();

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder() .url(mImgUrl) .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    picutru = response.body().bytes();
                    dealRespon(picutru);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }

    @SuppressLint("WrongCall")
    private void dealRespon(byte[] picutru) {
        Bitmap bitmap;
        if(picutru == null)
            System.out.println("获取图片为空");
        else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(picutru,0,picutru.length,options);
            mWidth = options.outWidth;
            mHeight = options.outHeight;
            System.out.println("width="+mWidth+"&&height="+mHeight);
//                        dealResult(bitmap);
            // 计算图片宽高比。
            ratio = (mWidth+0.0f)/mHeight;
            System.out.println("图片宽高比:"+ratio);
            onMeasure(mWidMode,mHeigMode);
            //计算图片缩放比
            if(mWidMode == MeasureSpec.EXACTLY){
                mScal = mWidth/getWidth();
                System.out.println("图片宽:"+getWidth()+" "+mWidth+" "+mScal);
            }
            else if(mHeigMode == MeasureSpec.EXACTLY){
                mScal = mHeight/getHeight();
                System.out.println("图片高:"+getWidth()+" "+mWidth+" "+mScal);
            }else {
                mScal = 1;
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = mScal;
            bitmap = BitmapFactory.decodeByteArray(picutru,0,picutru.length,options);
            System.out.println("图片缩放:"+bitmap.getWidth()+"--"+bitmap.getHeight());

            showImg(bitmap);
        }
    }

    private void showImg(final Bitmap bitmap) {
        try {
            post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams lp = getLayoutParams();
                    lp.width = bitmap.getWidth();
                    lp.height = bitmap.getHeight();
                    setLayoutParams(lp);
                    setImageBitmap(bitmap);
                    System.out.println("控件宽高:"+getWidth()+"--"+getHeight());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 父容器传过来的宽度方向上的模式
        mWidMode = MeasureSpec.getMode(widthMeasureSpec);
        // 父容器传过来的高度方向上的模式
        mHeigMode = MeasureSpec.getMode(heightMeasureSpec);

        // 父容器传过来的宽度的值
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
                - getPaddingRight();
        // 父容器传过来的高度的值
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft()
                - getPaddingRight();

        if (mWidMode == MeasureSpec.EXACTLY
                && mHeigMode != MeasureSpec.EXACTLY && ratio != 0.0f) {
            // 判断条件为，宽度模式为Exactly，也就是填充父窗体或者是指定宽度；
            // 且高度模式不是Exaclty，代表设置的既不是fill_parent也不是具体的值，于是需要具体测量
            // 且图片的宽高比已经赋值完毕，不再是0.0f
            // 表示宽度确定，要测量高度
            height = (int) (width / ratio + 0.5f);
            System.out.println("设置控件高度:"+height);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        } else if (mWidMode != MeasureSpec.EXACTLY
                && mHeigMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
            // 判断条件跟上面的相反，宽度方向和高度方向的条件互换
            // 表示高度确定，要测量宽度
            width = (int) (height * ratio + 0.5f);
            System.out.println("设置控件宽度:"+width);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}

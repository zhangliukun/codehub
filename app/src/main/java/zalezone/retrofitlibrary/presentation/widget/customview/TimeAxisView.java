package zalezone.retrofitlibrary.presentation.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import zalezone.retrofitlibrary.R;

/**
 * Created by zale on 2017/3/3.
 */

public class TimeAxisView extends View {

    public static final int LINE_POINT_BOTH = 0;
    public static final int LINE_POINT_UP = 1;
    public static final int LINE_POINT_DOWN = 2;

    public static final int IMAGE_TOP = 0;
    public static final int IMAGE_CENTER = 1;
    public static final int IMAGE_BOTTOM = 2;

    Context mContext;

    Paint mPaint;

    int mAxisImageResource;
    int mLinePoint;
    int mLineColor;
    int mLineThinkness;
    int mImageMode;

    Bitmap mAxisBitmap;

    RectF rectF;

    private int centerX;
    private int imageTop;
    private int imageBottom;


    public TimeAxisView(Context context) {
        this(context,null);
    }

    public TimeAxisView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeAxisView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimeAxisView);
        mAxisImageResource = a.getResourceId(R.styleable.TimeAxisView_axis_image_src,R.drawable.ic_account_circle_black_48dp);
        mLinePoint = a.getInt(R.styleable.TimeAxisView_line_point,0);
        mLineColor = a.getColor(R.styleable.TimeAxisView_line_color, Color.BLACK);
        mLineThinkness = a.getDimensionPixelSize(R.styleable.TimeAxisView_line_thinkness,1);
        mImageMode = a.getInt(R.styleable.TimeAxisView_axis_image_mode,IMAGE_CENTER);

        a.recycle();

        init();
    }

    private void init() {
        mAxisBitmap = BitmapFactory.decodeResource(getResources(),mAxisImageResource);
        rectF = new RectF(0,0,mAxisBitmap.getWidth(),mAxisBitmap.getHeight());
        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineThinkness);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        switch (mImageMode){
            case IMAGE_TOP:
                imageTop = 0;
                imageBottom = width;
                centerX = width/2;
                rectF.set(0,imageTop,width,imageBottom);
                break;
            case IMAGE_CENTER:
                imageTop = height/3;
                imageBottom = height/3*2;
                centerX = height/3/2;
                rectF.set(0,imageTop,height/3,imageBottom);
                break;
            case IMAGE_BOTTOM:
                imageTop = height-width;
                imageBottom = height;
                centerX = width/2;
                rectF.set(0,imageTop,width,imageBottom);
                break;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mAxisBitmap,null,rectF,null);

        switch (mLinePoint){
            case LINE_POINT_BOTH:
                canvas.drawLine(centerX,0,centerX,imageTop,mPaint);
                canvas.drawLine(centerX,imageBottom,centerX,getHeight(),mPaint);
                break;
            case LINE_POINT_UP:
                canvas.drawLine(centerX,0,centerX,imageTop,mPaint);
                break;
            case LINE_POINT_DOWN:
                canvas.drawLine(centerX,imageBottom,centerX,getHeight(),mPaint);
                break;
            default:
                break;
        }



    }
}

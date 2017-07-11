package com.xyan.dotlineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;

import com.xyan.dashedlineview.R;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;


/**
 * Created by chenxueqing on 2016/8/26.
 */

public class DashedLineView extends View {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(SOURCE)
    public @interface Orientation {}

    private Context mContext;
    private Path mPath;
    private Paint paint;

    //attr
    private int lineColor;
    private int solidLength;
    private int gapLength;
    private int strokeWidth;
    private int orientation;//0:horizontal; 1:vertical


    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
        initPain();
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.DashedLineView);
        lineColor = ta.getColor(R.styleable.DashedLineView_dlv_dashed_color, Color.GRAY);
        solidLength = ta.getDimensionPixelSize(R.styleable.DashedLineView_dlv_solid_length, 4);
        gapLength = ta.getDimensionPixelSize(R.styleable.DashedLineView_dlv_gap_length, 3);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.DashedLineView_dlv_stroke_width, 2);
        orientation = ta.getInt(R.styleable.DashedLineView_dlv_line_orientation, HORIZONTAL);
        ta.recycle();
    }

    private void initPain() {
        paint = new Paint();
        mPath = new Path();
        paint.setStyle(Paint.Style.STROKE);
        setPainData();
    }

    private void setPainData() {
        //dot gap and width
        PathEffect effects = new DashPathEffect(new float[]{solidLength, gapLength, solidLength, gapLength}, 0);
        paint.setPathEffect(effects);
        paint.setColor(lineColor);
        paint.setStrokeWidth(strokeWidth);
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        setPainData();
        invalidate();
    }

    public void setSolidLength(int solidLength) {
        this.solidLength = solidLength;
        setPainData();
        invalidate();
    }

    public void setGapLength(int gapLength) {
        this.gapLength = gapLength;
        setPainData();
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setPainData();
        invalidate();
    }

    public void setOrientation(@Orientation int orientation) {
        this.orientation = orientation;
        setPainData();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        switch (orientation) {
            case HORIZONTAL:
                float y = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2.0f + getPaddingTop();
                mPath.moveTo(getPaddingLeft(), y);//start position
                mPath.lineTo(getWidth() - getPaddingRight(), getHeight() / 2.0f);//end position
                break;
            case VERTICAL:
                float x = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2.0f + getPaddingLeft();
                mPath.moveTo(x, getPaddingTop());
                mPath.lineTo(x, getHeight() - getPaddingBottom());
                break;
        }
        canvas.drawPath(mPath, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = orientation == VERTICAL ? strokeWidth : 100;
            width += getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = orientation == HORIZONTAL ? strokeWidth : 100;
            height += getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }
}

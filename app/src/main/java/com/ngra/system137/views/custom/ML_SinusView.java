package com.ngra.system137.views.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.ngra.system137.R;


public class ML_SinusView extends View {

    private Paint DrawPaint;
    private Path mPath;
    private int height;
    private int width;
    int color;


    public ML_SinusView(Context context) {
        super(context);
        color = context.getResources().getColor(R.color.colorPrimary);
        init();
    }

    public ML_SinusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        color = context.getResources().getColor(R.color.colorPrimary);
        init();
    }


    private void init() {
        mPath = new Path();
        DrawPaint = new Paint();

        DrawPaint.setColor(color);
        setBackgroundColor(0);
    }


    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = getWidth();
        height = getHeight();
        int halfWidth = width * 50 / 100;
        int halfHeight = height * 50 / 100;
        int halfhalfHeight = halfHeight * 50 / 100;
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(0,halfHeight);

        Point mFirstCurveControlPoint1 = new Point();
        Point mFirstCurveControlPoint2 = new Point();

        mFirstCurveControlPoint1.set(halfWidth + height,-halfhalfHeight);
        mFirstCurveControlPoint2.set(halfWidth + height + height, height - halfHeight);
        mPath.cubicTo(mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                halfWidth + height, height - halfHeight);

        Point mSecondCurveControlPoint1 = new Point();
        Point mSecondCurveControlPoint2 = new Point();


        mSecondCurveControlPoint1.set(halfWidth + halfHeight,height - halfhalfHeight);
        mSecondCurveControlPoint2.set(width + halfhalfHeight, height + halfhalfHeight);

        mPath.cubicTo(mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                width, height - halfhalfHeight);

        mPath.lineTo(width, 0);
        mPath.lineTo(0,0);

        mPath.close();


    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, DrawPaint);

    }



}

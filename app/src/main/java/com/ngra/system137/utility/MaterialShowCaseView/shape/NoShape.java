package com.ngra.system137.utility.MaterialShowCaseView.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ngra.system137.utility.MaterialShowCaseView.target.Target;

public class NoShape implements Shape {

    @Override
    public void updateTarget(Target target) {
        // do nothing
    }

    @Override
    public int getTotalRadius() {
        return 0;
    }

    @Override
    public void setPadding(int padding) {
        // do nothing
    }

    @Override
    public void draw(Canvas canvas, Paint paint, int x, int y) {
        // do nothing
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
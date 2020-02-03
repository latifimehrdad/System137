package com.ngra.system137.utility.MaterialShowCaseView.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.ngra.system137.utility.MaterialShowCaseView.target.Target;

public interface Shape {

    /**
     * Draw shape on the canvas with the center at (x, y) using Paint object provided.
     */
    void draw(Canvas canvas, Paint paint, int x, int y);

    /**
     * Get width of the shape.
     */
    int getWidth();

    /**
     * Get height of the shape.
     */
    int getHeight();

    /**
     * Update shape bounds if necessary
     */
    void updateTarget(Target target);

    int getTotalRadius();

    void setPadding(int padding);
}
package com.ngra.system137.utility.MaterialShowCaseView;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.view.View;

public interface IAnimationFactory {

    void animateInView(View target, Point point, long duration, AnimationStartListener listener);

    void animateOutView(View target, Point point, long duration, AnimationEndListener listener);

    void animateTargetToPoint(MaterialShowcaseView showcaseView, Point point);


    public interface AnimationStartListener {
        void onAnimationStart();
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }
}
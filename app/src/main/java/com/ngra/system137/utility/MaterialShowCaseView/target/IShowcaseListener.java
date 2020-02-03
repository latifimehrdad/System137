package com.ngra.system137.utility.MaterialShowCaseView.target;

import com.ngra.system137.utility.MaterialShowCaseView.MaterialShowcaseView;

public interface IShowcaseListener {
    void onShowcaseDisplayed(MaterialShowcaseView showcaseView);
    void onShowcaseDismissed(MaterialShowcaseView showcaseView);
}
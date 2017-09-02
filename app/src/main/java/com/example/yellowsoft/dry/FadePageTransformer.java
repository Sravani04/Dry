package com.example.yellowsoft.dry;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by yellowsoft on 31/8/17.
 */

public  class FadePageTransformer implements ViewPager.PageTransformer {
    public void transformPage(View view, float position) {
        view.setAlpha(1 - Math.abs(position));
        if (position < 0) {
            view.setScrollX((int)((float)(view.getWidth()) * position));
        } else if (position > 0) {
            view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
        } else {
            view.setScrollX(0);
        }
    }
}

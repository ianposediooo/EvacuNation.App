package com.example.evacunation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

// Custom ViewPager that disables swiping with touch
public class NonSwipableViewPager extends ViewPager {

    public NonSwipableViewPager(Context context) {
        super(context);
    }

    public NonSwipableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // This method is called when a touch event is dispatched to the ViewPager.
    // By returning false, we prevent the ViewPager from handling the touch event
    // for swiping.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Return false to prevent swiping
        return false;
    }

    // This method is called to intercept touch events.
    // By returning false, we prevent the ViewPager from intercepting touch events
    // that could lead to swiping.
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Return false to prevent swiping
        return false;
    }

    // You can still call setCurrentItem to change pages programmatically
    // and the Tab Layout will still work to change pages.
}
package com.example.sphinx.mix.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.sphinx.mix.utils.AnimatorUtil;

/**
 * Created by Sphinx on 2017/3/17.
 * 组合控件下部的Behavior
 */
public class FooterBehavior extends FloatingActionButton.Behavior {
    private boolean isAnimatingOut = false;
    private OnStateChangedListener mOnStateChangedListener;

    //动画的
    private ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {
        @Override
        public void onAnimationStart(View view) {
            isAnimatingOut = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAnimatingOut = false;
            view.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(View arg0) {
            isAnimatingOut = false;
        }
    };

    private String TAG = "FooterBehavior";

    public interface OnStateChangedListener {
        void onChanged(boolean isShow);
    }

    public void setOnStateChangedListener(OnStateChangedListener mOnStateChangedListener) {
        this.mOnStateChangedListener = mOnStateChangedListener;
    }

    public FooterBehavior(Context context, AttributeSet attrs) {
        super();
    }

    /* //当 dependency instanceof AppBarLayout 返回TRUE，将会调用onDependentViewChanged（）方法
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return   dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //根据dependency top值的变化改变 child 的 translationY
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        Log.e(TAG, "onDependentViewChanged: " + translationY);
        return true;

    }*/

    //滚动的时候调用该方法,竖直方向处理事件
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimatingOut && child.getVisibility() == View.VISIBLE) {
            AnimatorUtil.scaleHide(child, listener);
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(false);
            }
        } else if ((dyConsumed < 0 || dyUnconsumed < 0) && child.getVisibility() != View.VISIBLE) {
            AnimatorUtil.scaleShow(child, null);
            if (mOnStateChangedListener != null) {
                mOnStateChangedListener.onChanged(true);
            }
        }
    }

    public static <V extends View> FooterBehavior from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("这个View不是CoordinatorLayout的子View");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof FooterBehavior)) {
            throw new IllegalArgumentException("这个View的Behaviro不是ScaleDownShowBehavior");
        }
        return (FooterBehavior) behavior;
    }

}

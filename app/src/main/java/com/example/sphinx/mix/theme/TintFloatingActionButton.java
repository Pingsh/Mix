package com.example.sphinx.mix.theme;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.example.sphinx.mix.utils.TintManager;

/**
 * Created by Sphinx on 2017/3/20.
 * 自定义FAB,与主题背景保持一致
 */

public class TintFloatingActionButton extends FloatingActionButton implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible {
    private AppCompatBackgroundHelper mBackgroundHelper;

    public TintFloatingActionButton(Context context) {
        this(context, null);
    }

    public TintFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public TintFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager tintManager = TintManager.get(context);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, mode);
        }
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundDrawableExternal(background);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundResId(resId);
        } else {
            super.setBackgroundResource(resId);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundColor(color);
        }
    }

}

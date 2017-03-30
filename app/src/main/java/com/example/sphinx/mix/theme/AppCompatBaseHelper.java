
package com.example.sphinx.mix.theme;

import android.util.AttributeSet;
import android.view.View;

import com.example.sphinx.mix.utils.TintManager;

public abstract class AppCompatBaseHelper {
    protected View mView;
    protected TintManager mTintManager;

    private boolean mSkipNextApply;

    public AppCompatBaseHelper(View view, TintManager tintManager) {
        mView = view;
        mTintManager = tintManager;
    }

    protected boolean skipNextApply() {
        if (mSkipNextApply) {
            mSkipNextApply = false;
            return true;
        }
        mSkipNextApply = true;
        return false;
    }

    protected void setSkipNextApply(boolean flag) {
        mSkipNextApply = flag;
    }

    abstract void loadFromAttribute(AttributeSet attrs, int defStyleAttr);

    public abstract void tint();
}

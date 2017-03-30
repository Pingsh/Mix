package com.example.sphinx.mix.theme;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.sphinx.mix.utils.TintManager;

/**
 * 自定义ImageView,为了在设置背景以及src的情况下能更改颜色
 */
public class TintImageView extends ImageView implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible
        , AppCompatImageHelper.ImageExtensible {
    private AppCompatBackgroundHelper mBackgroundHelper;
    private AppCompatImageHelper mImageHelper;

    public TintImageView(Context context) {
        this(context, null);
    }

    public TintImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TintManager tintManager = TintManager.get(context);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);

        mImageHelper = new AppCompatImageHelper(this, tintManager);
        mImageHelper.loadFromAttribute(attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (getBackground() != null) {
            invalidateDrawable(getBackground());
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

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (mImageHelper != null) {
            mImageHelper.setImageDrawable();
        }
    }

    @Override
    public void setImageResource(int resId) {
        if (mImageHelper != null) {
            mImageHelper.setImageResId(resId);
        } else {
            super.setImageResource(resId);
        }
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
    public void setImageTintList(int resId) {
        if (mImageHelper != null) {
            mImageHelper.setImageTintList(resId, null);
        }
    }

    @Override
    public void setImageTintList(int resId, PorterDuff.Mode mode) {
        if (mImageHelper != null) {
            mImageHelper.setImageTintList(resId, mode);
        }
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
        if (mImageHelper != null) {
            mImageHelper.tint();
        }
    }
}

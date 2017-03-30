package com.example.sphinx.mix.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sphinx.mix.R;

import java.lang.ref.WeakReference;

/**
 * Created by Sphinx on 2017/3/17.
 */

public class UIUtils {
    private static final int ID_LOADING_VIEW = 0xfffff0;
    private static final int ID_EMPTY_VIEW = 0xfffff1;
    private static final int ID_ERROR_VIEW = 0xfffff2;

    private static WeakReference<Toast> sToastRef = null;
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    // 获取默认的加载界面
    private static View getNoticeView(ViewGroup parent, Drawable srcDrawable, String tipStr, int tag) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notice, null);
        TextView tipText = (TextView) view.findViewById(R.id.text_notice_tip);
        tipText.setPadding(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0);
        tipText.setGravity(Gravity.CENTER);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_notice);
        if (TextUtils.isEmpty(tipStr)) {
            if (tag == ID_EMPTY_VIEW) {
                tipText.setText("暂无相关信息");
            } else if (tag == ID_ERROR_VIEW) {
                tipText.setText("加载出错");
            } else {
                tipText.setText("加载中");
            }
        } else {
            tipText.setText(tipStr);
        }
        imageView.setImageDrawable(srcDrawable);
        return view;
    }

    /**
     * 显示加载中提示界面
     * showLoadingView
     *
     * @param parent
     * @param tipStr
     * @since 1.0
     */
    public static void showLoadingView(ViewGroup parent, String tipStr) {
        if (parent == null) return;
        AnimationDrawable drawable = (AnimationDrawable) parent.getResources().getDrawable(R.drawable.ic_page_load_anim);
        View loadingView = getNoticeView(parent, drawable, tipStr, ID_LOADING_VIEW);

        drawable.start();
        showLoadingView(parent, loadingView);
    }

    // 隐藏parent下所有的子view
    public static void hideChildrenView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.GONE) {
                childAt.setVisibility(View.GONE);
            }
        }
    }

    // 显示parent下所有的子view
    public static void showChildrenView(final ViewGroup parent) {
        boolean hasViewChange = false;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.VISIBLE) {
                childAt.setVisibility(View.VISIBLE);
                hasViewChange = true;
            }
        }
        if (hasViewChange) {
            parent.setAnimationCacheEnabled(false);
            Animation showAnim = AnimationUtils.loadAnimation(parent.getContext(), android.R.anim.fade_in);
            showAnim.setStartTime(AnimationUtils.currentAnimationTimeMillis());
            showAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    parent.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            parent.clearAnimation();
            parent.startAnimation(showAnim);
        }
    }

    private static void removeNoticeView(final ViewGroup parent, int tag) {
        View view = parent.findViewWithTag(tag + parent.hashCode());

        if (view != null) {
            parent.removeView(view);
            view.setTag(null);
        }
    }

    public static void showLoadingView(ViewGroup parent, View loadingView) {
        removeNoticeView(parent, ID_LOADING_VIEW);
        removeNoticeView(parent, ID_EMPTY_VIEW);
        removeNoticeView(parent, ID_ERROR_VIEW);
        hideChildrenView(parent);
        addNoticeView(parent, loadingView, ID_LOADING_VIEW, null);
    }


    public static void hideAllNoticeView(ViewGroup parent) {
        if (parent == null) return;
        removeNoticeView(parent, ID_LOADING_VIEW);
        removeNoticeView(parent, ID_EMPTY_VIEW);
        removeNoticeView(parent, ID_ERROR_VIEW);
        showChildrenView(parent);
    }

    // 在parent中添加子view，并且监听子view点击事件
    private static void addNoticeView(ViewGroup parent, View child, int tag, View.OnClickListener listener) {
        hideAllNoticeView(parent);
        hideChildrenView(parent);
        child.setTag(tag + parent.hashCode());
        parent.addView(child, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (listener != null) {
            child.setOnClickListener(listener);
        } else {
            // 防止key事件穿透
            child.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void toast(Context context, String msg, int duration) {
        if (TextUtils.isEmpty(msg)) return;
        Toast t = null;
        if (sToastRef == null || sToastRef.get() == null) {
            t = Toast.makeText(context, msg, duration);
            sToastRef = new WeakReference<Toast>(t);
        } else {
            t = sToastRef.get();
            t.setText(msg);
            t.setDuration(duration);
        }
        t.show();
    }

    public static void toastShort(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void toastShort(Context context, int resId) {
        toast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void toastLong(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_LONG);
    }

    public static void toastLong(Context context, int resId) {
        toast(context, context.getString(resId), Toast.LENGTH_LONG);
    }

    /**
     * 显示一个右上角弹出的menu
     *
     * @param activity
     * @param contentView
     * @param width
     * @param x
     * @param y
     * @return
     */
    public static Dialog showTopRightMenu(Context activity, View contentView, int width, int x, int y) {
        Dialog dialog = new Dialog(activity, R.style.FullScreenDialog);
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = width == 0 ? UIUtils.dip2px(120) : width;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = x == 0 ? UIUtils.dip2px(5) : x;
        lp.y = y == 0 ? UIUtils.dip2px(48) : y;
        lp.verticalMargin = 0;
        lp.horizontalMargin = 0;
        lp.dimAmount = 0.15f;
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.topright_dialog);
        window.setGravity(Gravity.TOP | Gravity.RIGHT); // 此处可以设置dialog显示的位置
        dialog.show();
        return dialog;
    }

    /**
     * dip转换为px
     * dip2px
     *
     * @param dpValue
     * @return
     * @since 1.0
     */

    public static int dip2px(int dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

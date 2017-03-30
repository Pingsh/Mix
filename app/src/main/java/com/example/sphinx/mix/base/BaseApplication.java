package com.example.sphinx.mix.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.example.sphinx.mix.DaggerMainActivity;
import com.example.sphinx.mix.R;
import com.example.sphinx.mix.dagger.components.ApplicationComponent;
import com.example.sphinx.mix.dagger.components.DaggerApplicationComponent;
import com.example.sphinx.mix.dagger.modules.ApiHttpModule;
import com.example.sphinx.mix.dagger.modules.ApplicationModule;
import com.example.sphinx.mix.dagger.modules.DateModule;
import com.example.sphinx.mix.theme.ThemeHelper;
import com.example.sphinx.mix.utils.ThemeUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Sphinx on 2017/3/20.
 */

public class BaseApplication extends Application implements ThemeUtils.switchColor {
    private static Context context;
    private final static String ROBOTO_SLAB = "fonts/RobotoSlab-Regular.ttf";
    private static ApplicationComponent mComponent;
    private static BaseApplication mBaseApplication;

    public static Context getContext() {
        return context;
    }

    @Override

    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }


        context = this;

        mBaseApplication = this;
        mComponent = DaggerApplicationComponent.
                builder().
                apiHttpModule(new ApiHttpModule()).
                build();

        ThemeUtils.setSwitchColor(this);

        configureDefaultFont(ROBOTO_SLAB);

        // refWatcher = LeakCanary.install(this);
        //       LeakCanary.install(this);
//        initCatchException();
    }

    public static BaseApplication getBaseApplication() {
        return (BaseApplication) context.getApplicationContext();
    }

    public static ApplicationComponent getAppComponent() {
        return mComponent;
    }

    /**
     * 设置字体
     *
     * @param robotoSlab
     */
    private void configureDefaultFont(String robotoSlab) {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(robotoSlab)
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme != null) {
            colorId = getThemeColorId(context, colorId, theme);
        }
        return context.getResources().getColor(colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int originColor) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor;
        }
        String theme = getTheme(context);
        int colorId = -1;
        if (theme != null) {
            colorId = getThemeColor(context, originColor, theme);
        }
        return colorId != -1 ? getResources().getColor(colorId) : originColor;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_LIGHT) {
            return "green_light";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return "red";
        }
        return null;
    }

    private
    @ColorRes
    int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
           /* case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.playbarProgressColor:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());*/
        }
        return colorId;
    }

    private
    @ColorRes
    int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xd20000:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
        }
        return -1;
    }
}

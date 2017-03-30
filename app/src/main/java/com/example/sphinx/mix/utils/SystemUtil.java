package com.example.sphinx.mix.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * SystemUtil
 * <p>
 * Created by Howe on 2016/9/28.
 */
public class SystemUtil {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";

    private static String LOG_TAG = "SystemUtil";
    private static String sUserAgent;
    private static String sDeviceId;
    private static int sVersionCode = -1;
    private static String sVersionName;
    private static String sNavBarOverride;
    private static boolean sHasMIUI6;

    static {
        // Android allows a system property to override the presence of the navigation bar.
        // Used by the emulator.
        // See https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("getBaseApplication", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
                String miUIVersion = (String) m.invoke(null, "ro.miui.ui.version.name");
                sHasMIUI6 = "V6".equals(miUIVersion) || "V7".equals(miUIVersion) || "V8".equals(miUIVersion);
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
    }

    /**
     * 获取Android系统版本 "6.0" or "4.04".
     *
     * @return Android系统版本
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取Android系统SDK版本号
     *
     * @return Android系统SDK版本号
     */
    public static int getCurrentOsVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备的分辨率
     *
     * @return 设备的分辨率
     */
    public static String getDeviceMetrics() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels + "x" + metrics.heightPixels;
    }

    /**
     * 获取设备密度
     *
     * @return 设备密度
     */
    public static String getDensityDpi() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.densityDpi + "";
    }

    /**
     * 获取设备屏幕宽度
     *
     * @return 设备屏幕宽度
     */
    public static int getScreenWidth() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取设备屏幕高度
     *
     * @return 设备屏幕高度
     */
    public static int getScreenHeight() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 计算actionbar高度
     *
     * @param activity Activity
     * @return actionbar高度
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static float getActionBarHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedArray actionbarSizeTypedArray = activity
                    .obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
            float a = actionbarSizeTypedArray.getDimension(0, 0);
            actionbarSizeTypedArray.recycle();
            return a;
        }
        return 0;
    }

    /**
     * 计算状态栏高度
     *
     * @return 状态栏高度
     */
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

    /**
     * 计算页面高度
     *
     * @param activity Activity
     * @return 页面高度
     */
    public static int getContentHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int cntentHeight = frame.bottom - frame.top;
        return cntentHeight;
    }

    /**
     * 获取虚拟导航栏高度
     *
     * @param context Context
     * @return 虚拟导航栏高度
     */
    @TargetApi(14)
    public static int getNavigationBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar(context)) {
                String key;
                if (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    key = NAV_BAR_HEIGHT_RES_NAME;
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                }
                return getInternalDimensionSize(res, key);
            }
        }
        return result;
    }

    /**
     * 判断是否有虚拟导航栏
     *
     * @param context Context
     * @return 是否有虚拟导航栏
     */
    @TargetApi(14)
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static boolean isTintStatusBarAvailable(Activity activity) {
        boolean isTintStatusBarAvailable = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // check window flags
                int bits = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                if ((winParams.flags & bits) != 0 && win.getDecorView().getSystemUiVisibility() == uiOptions) {
                    isTintStatusBarAvailable = true;
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // check theme attrs
                int[] attrs = {android.R.attr.windowTranslucentStatus};
                TypedArray a = activity.obtainStyledAttributes(attrs);
                try {
                    isTintStatusBarAvailable = a.getBoolean(0, false);
                } finally {
                    a.recycle();
                }

                // check window flags
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                if ((winParams.flags & bits) != 0) {
                    isTintStatusBarAvailable = true;
                }
            }
        }
        return isTintStatusBarAvailable;
    }



    // only useful on miui system
    private static void setStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取并设置UserAgent
     *
     * @param context Context
     * @return UserAgent
     */
    public static String getDefaultUserAgent(Context context) {

        if (TextUtils.isEmpty(sUserAgent)) {
            sUserAgent = System.getProperty("http.agent", "") + getApplicationPackageName(context) + "/" + getVersionName(context);
        }
        return sUserAgent;
    }

    /**
     * 获取应用VersionCode
     *
     * @param context Context
     * @return 应用VersionCode
     */
    public static int getVersionCode(Context context) {
        if (sVersionCode == -1) {
            try {
                String packageName = getApplicationPackageName(context);
                sVersionCode = context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return sVersionCode;
    }

    /**
     * 获取应用程序版本号
     *
     * @param context Context
     * @return 应用程序版本号
     */
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(sVersionName)) {
            try {
                String packageName = getApplicationPackageName(context);
                sVersionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return sVersionName;
    }

    /**
     * 获取应用包名称
     *
     * @param context Context
     * @return 应用包名称
     */
    public static String getApplicationPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取Application的META-DATA
     *
     * @param context Context
     * @return Application的META-DATA
     */
    public static ApplicationInfo getApplicationMetaData(Context context) {
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }

    /**
     * 获取Application名
     *
     * @param context Context
     * @return Application名
     */
    public static String getApplicationName(Context context) {
        PackageInfo pkg = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            pkg = packageManager.getPackageInfo(getApplicationPackageName(context), 0);
            String appName = pkg.applicationInfo.loadLabel(packageManager).toString();
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
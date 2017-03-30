package com.example.sphinx.mix.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sphinx.mix.R;
import com.example.sphinx.mix.utils.UIUtils;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity<T> extends AppCompatActivity {
    protected Bundle savedInstanceState;
    private static final int PERMISSION_WRITE = 100;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        // 竖屏固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        initPermission();
        initExtraData();
        initData();
        initView();
    }

    protected abstract void initData();

    protected abstract void initView();

    private void initTop() {
        initStatusBar();
        initTitle(true, "Dagger");
    }

    protected void initTitle(boolean homeButtonEnable, String title) {
        getSupportActionBar().setHomeButtonEnabled(homeButtonEnable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeButtonEnable);
        getSupportActionBar().setTitle(title);
    }

    protected void initStatusBar() {

    }


    protected void initExtraData() {

    }

    private void initPermission() {
        PermissionGen.with(BaseActivity.this)
                .addRequestCode(PERMISSION_WRITE)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
        initExtraPermission();
    }

    /**
     * 需要动态申请的权限,或者初期没考虑到的权限
     */
    protected void initExtraPermission() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PERMISSION_WRITE)
    public void doSomething() {
        UIUtils.toastShort(BaseActivity.this, "权限申请成功");
    }

    @PermissionFail(requestCode = PERMISSION_WRITE)
    public void doFailSomething() {
        UIUtils.toastShort(BaseActivity.this, "权限申请失败,可能影响使用");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void startActivity(Intent intent, boolean useDefaultFlag) {
        if (useDefaultFlag) {
            super.startActivity(intent);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.startActivity(intent);
    }
}

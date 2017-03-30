package com.example.sphinx.mix;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.sphinx.mix.adapter.ListRecyclerAdapter;
import com.example.sphinx.mix.behavior.FooterBehavior;
import com.example.sphinx.mix.theme.ThemeHelper;
import com.example.sphinx.mix.utils.ThemeUtils;
import com.example.sphinx.mix.view.CardPickerDialog;

import java.util.ArrayList;

/**
 * 顶部带有视差特效的联动,将toolbar固定在顶部,底部栏和fab也做了behavior处理
 */
public class ZhiHuActivity extends AppCompatActivity implements CardPickerDialog.ClickListener {
    private String TAG = "ZhiHuActivity";
    private Context context = ZhiHuActivity.this;

    private FloatingActionButton fab;
    private RecyclerView rv;
    private Toolbar toolbar;
    private AppBarLayout appbar_layout;
    private ArrayList<String> mList;

    private boolean initialize = false;
    private BottomSheetBehavior mBottomSheetBehavior;

    private FooterBehavior.OnStateChangedListener onStateChangedListener = new FooterBehavior.OnStateChangedListener() {
        @Override
        public void onChanged(boolean isShow) {
            mBottomSheetBehavior.setState(
                    isShow ? BottomSheetBehavior.STATE_EXPANDED
                            : BottomSheetBehavior.STATE_COLLAPSED);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);

        initToolbar();
        initData();
        initView();
        initListener();

        FooterBehavior footerBehavior = FooterBehavior.from(fab);
        footerBehavior.setOnStateChangedListener(onStateChangedListener);
        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!initialize) {
            initialize = true;
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void initListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardPickerDialog dialog = new CardPickerDialog();
                dialog.setClickListener(ZhiHuActivity.this);
                dialog.show(getSupportFragmentManager(), "theme");
            }
        });
    }

    /**
     * 实现自定义Dialog中的接口,更改主题
     *
     * @param currentTheme
     */
    @Override
    public void onConfirm(int currentTheme) {

        if (ThemeHelper.getTheme(context) != currentTheme) {
            ThemeHelper.setTheme(context, currentTheme);
            ThemeUtils.refreshUI(context, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            //for global setting, just do once
                            if (Build.VERSION.SDK_INT >= 21) {
                                ActivityManager.TaskDescription taskDescription =
                                        new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                setTaskDescription(taskDescription);

                                getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                                appbar_layout.setBackgroundColor(ThemeUtils.getColorById(context, R.color.theme_color_primary));
                                fab.setBackgroundTintList(ColorStateList.valueOf(ThemeUtils.getColorById(context, R.color.theme_color_primary)));
                            }
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                        }
                    }
            );
        }

        changeTheme();
    }

    private void changeTheme() {
        //暂时不做处理
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("知乎联动");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        rv = (RecyclerView) findViewById(R.id.lv);

        appbar_layout = (AppBarLayout) findViewById(R.id.appbar_layout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        rv.setLayoutManager(gridLayoutManager);*/
        //两种Manager对子控件背景的处理不一样
        rv.setHasFixedSize(true);
        rv.setAdapter(new ListRecyclerAdapter(mList));
    }

    private void initData() {
        mList = new ArrayList<>();

        String item = "...I am item...";
        for (int i = 0; i < 100; i++) {
            mList.add(item + i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}

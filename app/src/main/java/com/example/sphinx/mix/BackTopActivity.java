package com.example.sphinx.mix;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.sphinx.mix.adapter.ListRecyclerAdapter;
import com.example.sphinx.mix.utils.AnimatorUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 快速回到顶部
 */
public class BackTopActivity extends AppCompatActivity {

    private FloatingActionButton FAB;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_top);

        List<String> list = initData();
        initView(list);
    }

    private void initView(List<String> list) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutManager.scrollToPosition(0);
                hideFAB();
            }
        });
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("我是第" + i + "个");
        }
        return list;
    }

    private boolean isInitializeFAB = false;


    /**
     * 初始化界面时隐藏
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInitializeFAB) {
            isInitializeFAB = true;
            hideFAB();
        }
    }

    private void hideFAB() {
        FAB.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorUtil.scaleHide(FAB, new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        FAB.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                    }
                });
            }
        }, 500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}

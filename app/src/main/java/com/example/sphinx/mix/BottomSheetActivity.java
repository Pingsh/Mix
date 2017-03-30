package com.example.sphinx.mix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sphinx.mix.adapter.ListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 从底部弹出覆盖整个界面Dialog
 */
public class BottomSheetActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBottomSheetControl;
    private Button btnBottomDialogControl;

    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetBehavior mBottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_sheet);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnBottomSheetControl = (Button) findViewById(R.id.btn_bottom_sheet_control);
        btnBottomDialogControl = (Button) findViewById(R.id.btn_bottom_dialog_control);

        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.tab_layout));

        initListener();
        initDialog();
    }

    private void initDialog() {

        mBottomSheetDialog = new BottomSheetDialog(BottomSheetActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null, false);
        mBottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("我是第" + i + "个");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListRecyclerAdapter adapter = new ListRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        setBehaviorCallback();

    }

    /**
     * 为弹出的dialog绑定BottomBehavior
     */
    private void setBehaviorCallback() {
        View view = mBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void initListener() {
        btnBottomSheetControl.setOnClickListener(this);
        btnBottomDialogControl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bottom_dialog_control:
                if (mBottomSheetDialog.isShowing()) {
                    mBottomSheetDialog.dismiss();
                } else {
                    mBottomSheetDialog.show();
                }
                break;
            case R.id.btn_bottom_sheet_control:
                //如果当前是收缩状态,变为延展状态;反之亦然

                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            default:
                break;
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

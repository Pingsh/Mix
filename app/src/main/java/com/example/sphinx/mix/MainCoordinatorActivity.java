package com.example.sphinx.mix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * 大坑: 25.0.0及以后的包,当child为gone时不做处理,continue.没有回调啦!!!
 */
public class MainCoordinatorActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnTop;
    private Button btnZhihu;
    private Button btnCover;
    private Button btnDelete;
    private Button btnVs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_coordinator);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTop = (Button) findViewById(R.id.btn_top);
        btnZhihu = (Button) findViewById(R.id.btn_zhihu);
        btnCover = (Button) findViewById(R.id.btn_cover);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnVs = (Button) findViewById(R.id.btn_vs);

        initListener();
    }

    private void initListener() {
        btnTop.setOnClickListener(this);
        btnZhihu.setOnClickListener(this);
        btnCover.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnVs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top:
                Intent intent_top = new Intent(MainCoordinatorActivity.this, BackTopActivity.class);
                startActivity(intent_top);
                break;
            case R.id.btn_zhihu:
                Intent intent_zhihu = new Intent(MainCoordinatorActivity.this, ZhiHuActivity.class);
                startActivity(intent_zhihu);
                break;
            case R.id.btn_cover:
                Intent intent_bottom = new Intent(MainCoordinatorActivity.this, BottomSheetActivity.class);
                startActivity(intent_bottom);
                break;
            case R.id.btn_delete:
                Intent intent_del = new Intent(MainCoordinatorActivity.this, SwipeDismissBehaviorActivity.class);
                startActivity(intent_del);
                break;
            case R.id.btn_vs:
                Intent intent_vs = new Intent(MainCoordinatorActivity.this,ViewSwitchActivity.class);
                startActivity(intent_vs);
                break;
            default:
                break;
        }
    }
}

package com.example.sphinx.flex;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private final Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_box_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        initNavigation();
        initListener();
    }


    private void initListener() {
        findViewById(R.id.remove_fab).setOnClickListener(this);
        findViewById(R.id.add_fab).setOnClickListener(this);
    }

    /**
     * 初始化Drawer中的导航栏,暂不做处理
     */
    private void initNavigation() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove_fab:
                Toast.makeText(mContext, "remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_fab:
                Toast.makeText(mContext, "add", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            //流式布局的空白处理

            Toast.makeText(MainActivity.this, "改变布局", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

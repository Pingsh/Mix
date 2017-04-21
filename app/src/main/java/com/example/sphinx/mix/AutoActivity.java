package com.example.sphinx.mix;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sphinx.mix.utils.UIUtils;
import com.example.sphinx.mix.view.TouchTextView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Arrays;

public class AutoActivity extends AutoLayoutActivity implements View.OnClickListener {
    private TextView tvSickbedName;
    private TextView tvSickbedAge;
    private TextView tvSickbedGender;
    private Button btnYwjj;
    private TextView tvSickbedAddress;
    private RelativeLayout rlHisname;
    private TextView tvFixname;
    private TextView tvHisname;
    private TextView tvFixjc;
    private TextView tvBednumber;
    private TextView tvFixyujiao;
    private TextView tvYjmoney;
    private TextView tvFixqf;
    private TextView tvQfje;
    private Button btnNewAdvice;
    private Button btnNewBed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        initToolbar();
        initView();
    }


    private void initView() {
        //通过代码设置字体大小
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getPercentWidthSize(yourPxVal));

        tvSickbedName = (TextView) findViewById(R.id.tv_sickbed_name);
        tvSickbedAge = (TextView) findViewById(R.id.tv_sickbed_age);
        tvSickbedGender = (TextView) findViewById(R.id.tv_sickbed_gender);
        btnYwjj = (Button) findViewById(R.id.btn_ywjj);
        tvSickbedAddress = (TextView) findViewById(R.id.tv_sickbed_address);
        rlHisname = (RelativeLayout) findViewById(R.id.rl_hisname);
        tvFixname = (TextView) findViewById(R.id.tv_fixname);
        tvHisname = (TextView) findViewById(R.id.tv_hisname);
        tvFixjc = (TextView) findViewById(R.id.tv_fixjc);
        tvBednumber = (TextView) findViewById(R.id.tv_bednumber);
        tvFixyujiao = (TextView) findViewById(R.id.tv_fixyujiao);
        tvYjmoney = (TextView) findViewById(R.id.tv_yjmoney);
        tvFixqf = (TextView) findViewById(R.id.tv_fixqf);
        tvQfje = (TextView) findViewById(R.id.tv_qfje);
        btnNewAdvice = (Button) findViewById(R.id.btn_new_advice);
        btnNewBed = (Button) findViewById(R.id.btn_new_bed);

        initListener();
    }

    private void initListener() {
        btnYwjj.setOnClickListener(this);
        btnNewAdvice.setOnClickListener(this);
        btnNewBed.setOnClickListener(this);

       /* findViewById(R.id.btn_ywjj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = 5, y = 7, u = 9, t = 6;
                UIUtils.toastShort(AutoActivity.this, "" +(x > y ? x + 2 : u > t ? u - 3 : t + 2)) ;
                test(4);
            }
        });*/
    }

    private void test(int n) {
        int k, i, j, a[][] = new int[n][n];
        k = 1;
        for (i = 0; i < n; i++) {
            if (i % 2 == 0) {
                for (j = 0; j < i || j == i; j++) {
                    a[i][j] = k++;
                }
                for (j = i - 1; j > 0 || j == 0; j--) {
                    a[j][i] = k++;
                }
            } else {
                for (j = 0; j < i || j == i; j++) {
                    a[j][i] = k++;
                }
                for (j = i - 1; j > 0 || j == 0; j--) {
                    a[i][j] = k++;
                }
            }
        }

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                Log.e("AutoActivity", "\t" + a[i][j]);
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_auto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ywjj:
                Integer[] ints = {1, 4, 8, 2, 3, 8, 6};
                Arrays.sort(ints, Integer::compareTo);
                StringBuilder print = new StringBuilder("");
                for (int i = 0; i < ints.length; i++) {
                    print.append(ints[i]);
                }
                UIUtils.toastShort(AutoActivity.this, print.toString());
                break;
            case R.id.btn_new_advice:
                SpannableStringBuilder ssb = new SpannableStringBuilder(tvSickbedAddress.getText());
                ssb.setSpan(new URLSpan("https://www.baidu.com"), 0,
                        tvSickbedAddress.getText().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
// 在单击链接时凡是有要执行的动作，都必须设置MovementMethod对象,使用这种方式的弊端是,会让设置的maxLine失效
//                tvSickbedAddress.setText(ssb);
//                tvSickbedAddress.setMovementMethod(LinkMovementMethod.getInstance());
                tvSickbedAddress.setOnTouchListener(new TouchTextView(ssb));
// 设置点击后的颜色，这里涉及到ClickableSpan的点击背景
                tvSickbedAddress.setHighlightColor(0xff8FABCC);

                break;
            case R.id.btn_new_bed:
                break;
            default:
                break;
        }
    }
}

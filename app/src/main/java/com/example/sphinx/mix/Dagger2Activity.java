package com.example.sphinx.mix;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sphinx.mix.dagger2.ApiHttpModule;
import com.example.sphinx.mix.dagger2.CalendarPresenterModule;
import com.example.sphinx.mix.dagger2.DaggerCalendarComponent;
import com.example.sphinx.mix.dagger2.presenter.CalendarPresenter;
import com.example.sphinx.mix.dagger2.presenter.CommonPresenter;
import com.example.sphinx.mix.dagger2.view.CommonView;
import com.example.sphinx.mix.databinding.ActivityDagger2Binding;
import com.example.sphinx.mix.entity.Calendar;

import javax.inject.Inject;

public class Dagger2Activity extends AppCompatActivity implements CommonView {

    private Button btnSearch;
    private EditText etDate;

    private Calendar mCalendar;

    @Inject
    CalendarPresenter mCalendarPresenter;
    private boolean isLoading;
    private RelativeLayout mSuccessLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDagger2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_dagger2);
        mCalendar = new Calendar();
        binding.setCalendar(mCalendar);

        DaggerCalendarComponent.
                builder().
                apiHttpModule(new ApiHttpModule()).
                calendarPresenterModule(new CalendarPresenterModule(this)).
                build().
                inject(this);

        initToolbar();
        initView();
    }

    private void initView() {
        etDate = (EditText) findViewById(R.id.et_date);

        btnSearch = (Button) findViewById(R.id.search_dagger);
        btnSearch.setEnabled(false);

        mSuccessLayout = (RelativeLayout) findViewById(R.id.rl_success);

        initListener();
    }

    private void initListener() {
        etDate.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    initData();
                    return true;
                }
                return false;
            }
        });

        etDate.addTextChangedListener(new TextWatcher() {

            //只在监测到数据变化时允许点击
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                btnSearch.setEnabled(enable);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void initData() {
        String date = etDate.getText().toString().trim();
        etDate.setText("");
        hideKeycode();
        if (checkInput(date)) {
            mCalendarPresenter.query(date, BuildConfig.JUHE_KEY, isLoading);
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideKeycode() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                Dagger2Activity.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean checkInput(String input) {
        if (isEmptyWord(input, true)) {
            return false;
        }
        return true;
    }

    private boolean isEmptyWord(String input, boolean withEmptyPoint) {
        if (TextUtils.isEmpty(input)) {
            if (withEmptyPoint) {
                Toast.makeText(Dagger2Activity.this, "请输入日期", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Dagger2);
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
    public void loading() {

    }

    @Override
    public void error(Throwable e) {

    }

    @Override
    public void success(Calendar result) {
        if (mSuccessLayout.getVisibility() == View.GONE) {
            mSuccessLayout.setVisibility(View.VISIBLE);
        }
        mCalendar.setResult(result.getResult());
    }

    @Override
    public void complete() {

    }

    @Override
    public void setPresenter(CommonPresenter presenter) {

    }
}

package com.example.sphinx.mix;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import com.example.sphinx.mix.base.BaseApplication;
import com.example.sphinx.mix.dagger.components.ApplicationComponent;
import com.example.sphinx.mix.databinding.ActivityDaggerMainBinding;
import com.example.sphinx.mix.entity.Calendar;
import com.example.sphinx.mix.searchmvp.dpresenter.SearchPresenter;
import com.example.sphinx.mix.searchmvp.dview.ISearchView;
import com.example.sphinx.mix.utils.UIUtils;

/**
 * Created by Sphinx on 2017/3/20.
 */

public class DaggerMainActivity extends AppCompatActivity implements View.OnClickListener, ISearchView {

    private RelativeLayout mRelativeLayout;
    private RelativeLayout mSuccessLayout;
    private EditText etDate;
    private Button btnSearch;

    SearchPresenter mPresenter;
    private ApplicationComponent mComponent;
    private ActivityDaggerMainBinding mBinding;
    private Calendar mCalendar;
    private boolean isLoading;
    private TextView tvLunar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dagger_main);

        mCalendar = new Calendar();
        mBinding.setCalendar(mCalendar);
        initToolbar();
        initView();

        //获取对象
        mComponent = BaseApplication.getBaseApplication().getAppComponent();
        mPresenter = (SearchPresenter) mComponent.getSearchPresenter();
        mPresenter.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unRegister();
    }

    private void initView() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_content);
        mSuccessLayout = (RelativeLayout) findViewById(R.id.rl_success);

        etDate = (EditText) findViewById(R.id.et_date);
        btnSearch = (Button) findViewById(R.id.search_dagger);
        btnSearch.setEnabled(false);

        tvLunar = (TextView) findViewById(R.id.tv_lunar);

        initListener();
    }

    private void initListener() {
        //不是在我们点击EditText的时候触发，也不是在我们对EditText进行编辑时触发，而是在我们编辑完之后点击软键盘上的回车键才会触发。
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

        btnSearch.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Dagger);
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
            case R.id.search_dagger:
                initData();
                break;
            default:
                break;
        }
    }

    private void initData() {
        String date = etDate.getText().toString().trim();
        etDate.setText("");
        hideKeycode();
        if (checkInput(date)) {
            mPresenter.query(date, BuildConfig.JUHE_KEY, isLoading);
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideKeycode() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                DaggerMainActivity.this.getCurrentFocus().getWindowToken(),
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
                Toast.makeText(DaggerMainActivity.this, "请输入日期", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return false;
    }


    @Override
    public void loading(boolean flag) {
       /* if (mSuccessLayout.getVisibility() == View.VISIBLE) {
            mSuccessLayout.setVisibility(View.GONE);
        }*/
        if (flag) {
            UIUtils.showLoadingView(mRelativeLayout, "加载中");
        } else {
            UIUtils.hideChildrenView(mRelativeLayout);
        }
    }

    @Override
    public void error(Throwable e) {
        UIUtils.toastShort(DaggerMainActivity.this, "加载出错");
    }

    @Override
    public void empty() {
        UIUtils.toastShort(DaggerMainActivity.this, "暂无相关信息");
    }

    @Override
    public void success(Calendar result) {
        if (mSuccessLayout.getVisibility() == View.GONE) {
            mSuccessLayout.setVisibility(View.VISIBLE);
        }
        mCalendar.setResult(result.getResult());
        //双向绑定，通过改变控件的属性，影响获取数据的对应属性。这里setText之后，无论怎么查询哪一天，对应的农历都是三月初八
//        tvLunar.setText("三月初八");
//        Log.e("测试", result.getResult().getData().getLunar());
    }

}

package com.example.sphinx.mix.dagger2.presenter;

import android.widget.Toast;

import com.example.sphinx.mix.DaggerMainActivity;
import com.example.sphinx.mix.base.BaseApplication;
import com.example.sphinx.mix.dagger.other.ApiCalendar;
import com.example.sphinx.mix.dagger2.view.CommonView;
import com.example.sphinx.mix.entity.Calendar;
import com.example.sphinx.mix.searchmvp.dmodel.BaseSubscribe;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sphinx on 2017/3/27.
 */

public class CalendarPresenter implements CommonPresenter {
    private ApiCalendar mApi;
    private BaseSubscribe<Calendar> mBaseSubscribe;
    private CommonView mCommonView;

    @Inject
    public CalendarPresenter(ApiCalendar api, CommonView commonView) {
        mApi = api;
        mCommonView = commonView;
//        setupListeners();
    }

    @Inject
    void setupListeners() {
        mCommonView.setPresenter(this);
    }

    @Override
    public void query(String date, String key, final boolean isRefreshing) {
        mBaseSubscribe = new BaseSubscribe<Calendar>() {
            @Override
            public void onNext(Calendar gsonBean) {
                if (!"0".equals(gsonBean.getError_code() + "")) {
                    Toast.makeText(BaseApplication.getContext(), "获取失败,错误码:" + gsonBean.getError_code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                //获取数据成功显示数据
                mCommonView.success(gsonBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Toast.makeText(BaseApplication.getContext(), "获取失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                mCommonView.error(e);
            }

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        };

        Observable<Calendar> observable = mApi.queryDate(date, key);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(mBaseSubscribe);
    }

}

package com.example.sphinx.mix.searchmvp.dpresenter;

import android.app.Activity;
import android.widget.Toast;

import com.example.sphinx.mix.DaggerMainActivity;
import com.example.sphinx.mix.base.BaseApplication;
import com.example.sphinx.mix.dagger.other.ApiCalendar;
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

public class SearchPresenter implements ISearchPresenter<Activity> {
    private ApiCalendar mApi;
    private BaseSubscribe<Calendar> mBaseSubscribe;
    private DaggerMainActivity mDaggerMainActivity;

    @Inject
    public SearchPresenter(ApiCalendar api) {
        mApi = api;
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
                mDaggerMainActivity.success(gsonBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Toast.makeText(BaseApplication.getContext(), "获取失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                mDaggerMainActivity.error(e);
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

    @Override
    public void register(Activity activity) {
        mDaggerMainActivity = (DaggerMainActivity) activity;
    }

    @Override
    public void unRegister() {
        if (mBaseSubscribe != null)
            mBaseSubscribe = null;
    }
}

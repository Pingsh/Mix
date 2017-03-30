package com.example.sphinx.mix.searchmvp.dmodel;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Sphinx on 2017/3/27.
 */

public abstract class BaseSubscribe<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        Log.i("BaseSub", "onError" + e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}

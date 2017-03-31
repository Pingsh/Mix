package com.example.sphinx.mix.dagger2.model;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Sphinx on 2017/3/31.
 */

public class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}

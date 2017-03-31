package com.example.sphinx.mix.dagger2.presenter;


/**
 * Created by Sphinx on 2017/3/30.
 * Presenter基类
 */

public interface BasePresenter {
    void query(String date, String key, boolean isRefreshing);
}

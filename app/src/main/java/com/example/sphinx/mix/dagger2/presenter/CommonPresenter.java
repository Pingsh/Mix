package com.example.sphinx.mix.dagger2.presenter;

/**
 * Created by Sphinx on 2017/3/30.
 */

public interface CommonPresenter extends BasePresenter {

    @Override
    void query(String date, String key, boolean isRefreshing);

}

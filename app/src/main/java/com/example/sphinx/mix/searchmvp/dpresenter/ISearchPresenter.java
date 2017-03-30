package com.example.sphinx.mix.searchmvp.dpresenter;

/**
 * Created by Sphinx on 2017/3/24.
 */

public interface ISearchPresenter<T> {
    void query(String date, String key, boolean isRefreshing);

    void register(T t);

    void unRegister();
}

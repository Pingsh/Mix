package com.example.sphinx.mix.searchmvp.dview;

import com.example.sphinx.mix.entity.Calendar;

/**
 * Created by Sphinx on 2017/3/16.
 */
//Activity需要实现的类,根据结果做相应的响应
public interface ISearchView {
    void loading(boolean flag);

    void error(Throwable e);

    void empty();

    void success(Calendar result);
}

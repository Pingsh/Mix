package com.example.sphinx.mix.dagger2.view;

import com.example.sphinx.mix.dagger2.presenter.CommonPresenter;
import com.example.sphinx.mix.entity.Calendar;

/**
 * Created by Sphinx on 2017/3/31.
 */

public interface CommonView extends BaseView<CommonPresenter> {
    void loading();

    void error(Throwable e);

    void success(Calendar result);

    void complete();

}

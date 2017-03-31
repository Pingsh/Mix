package com.example.sphinx.mix.dagger2;

import com.example.sphinx.mix.dagger2.view.CommonView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sphinx on 2017/3/30.
 */
@Module
public class CalendarPresenterModule {
    private final CommonView mView;

    public CalendarPresenterModule(CommonView view) {
        mView = view;
    }

    @Provides
    public CommonView provideView() {
        return mView;
    }
}

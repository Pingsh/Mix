package com.example.sphinx.mix.dagger.components;

import com.example.sphinx.mix.base.BaseApplication;
import com.example.sphinx.mix.dagger.modules.ApiHttpModule;
import com.example.sphinx.mix.dagger.modules.ApplicationModule;
import com.example.sphinx.mix.dagger.modules.DateModule;
import com.example.sphinx.mix.searchmvp.dpresenter.ISearchPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sphinx on 2017/3/27.
 * 管理所有的全局类实例
 */
@Singleton

//@Component(modules = {DateModule.class,ApplicationModule.class})

@Component(modules = {ApplicationModule.class, ApiHttpModule.class})
public interface ApplicationComponent {
    void inject(BaseApplication app);

    ISearchPresenter getSearchPresenter();

    /*
    BaseApplication getBaseApplication();

    ApiCalendar getApiCalendar();

    WarpApiModel getWarpApiModel();*/
}

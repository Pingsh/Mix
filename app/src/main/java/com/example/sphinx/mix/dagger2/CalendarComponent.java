package com.example.sphinx.mix.dagger2;

import com.example.sphinx.mix.Dagger2Activity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sphinx on 2017/3/30.
 */
@Singleton
@Component(modules = {ApplicationModule.class, CalendarPresenterModule.class, ApiHttpModule.class})
public interface CalendarComponent {
    void inject(Dagger2Activity activity);
}

package com.example.sphinx.mix.dagger.modules;

import android.content.Context;

import com.example.sphinx.mix.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sphinx on 2017/3/27.
 */

@Module
public class ApplicationModule {
    private BaseApplication context;

    public ApplicationModule(BaseApplication context){
        this.context = context;
    }

    @Provides
    @Singleton
    public BaseApplication provideApplication(){
        return context;
    }

}

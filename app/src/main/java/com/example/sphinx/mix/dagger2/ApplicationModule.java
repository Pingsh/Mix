package com.example.sphinx.mix.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * 仅用于提供上下文
 */
@Module
public final class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
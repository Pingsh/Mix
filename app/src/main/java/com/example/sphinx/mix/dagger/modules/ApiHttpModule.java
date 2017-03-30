package com.example.sphinx.mix.dagger.modules;

import com.example.sphinx.mix.BuildConfig;
import com.example.sphinx.mix.dagger.other.EQueryFrom;
import com.example.sphinx.mix.dagger.other.ApiCalendar;
import com.example.sphinx.mix.searchmvp.dpresenter.ISearchPresenter;
import com.example.sphinx.mix.searchmvp.dpresenter.SearchPresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sphinx on 2017/3/27.
 */


@Module
public class ApiHttpModule {
    @Provides
    @Singleton
    ApiCalendar provideApiCalendar() {
        return createService(EQueryFrom.WAN_NIAN_LI);
    }

    private <S> S createService(EQueryFrom type) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HttpUrl.parse(type.getUrl()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        builder.client(provideOkHttpClient());
        return (S) builder.build().create(type.getApiClass());
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
//            也可以使用facebook封装的OKHTTP3，此时new的是Ste...
            builder.addNetworkInterceptor(new HttpLoggingInterceptor());
        }
        return builder.build();
    }

    @Provides
    @Singleton
    public ISearchPresenter providersInteractor(ApiCalendar api) {
        return new SearchPresenter(api);
    }

}

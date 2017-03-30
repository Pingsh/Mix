package com.example.sphinx.mix.dagger.modules;

import com.example.sphinx.mix.dagger.other.ApiClient;
import com.example.sphinx.mix.dagger.other.ApiCalendar;
import com.example.sphinx.mix.searchmvp.dpresenter.ISearchPresenter;
import com.example.sphinx.mix.searchmvp.dpresenter.SearchPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Sphinx on 2017/3/29.
 */
@Module
public class DateModule {
    @Provides
    public Retrofit providesApiClient() {
        return ApiClient.getInstance();
    }

    @Provides
    public ApiCalendar providersApi(Retrofit retrofit) {
        return retrofit.create(ApiCalendar.class);
    }

    @Provides
    public ISearchPresenter providersInteractor(ApiCalendar api) {
        return new SearchPresenter(api);
    }
}

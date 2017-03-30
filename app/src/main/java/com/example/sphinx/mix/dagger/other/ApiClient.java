package com.example.sphinx.mix.dagger.other;

import com.example.sphinx.mix.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建Retrofit
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(){
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            OkHttpClient.Builder builder = okHttpClient.newBuilder();
            builder.retryOnConnectionFailure(true);
            GsonConverterFactory factory = GsonConverterFactory.create();
            retrofit = new Retrofit.Builder().client(okHttpClient)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

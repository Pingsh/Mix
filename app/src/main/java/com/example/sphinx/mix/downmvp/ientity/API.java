package com.example.sphinx.mix.downmvp.ientity;

import com.example.sphinx.mix.entity.LoginRequest;
import com.example.sphinx.mix.entity.LoginResponse;
import com.example.sphinx.mix.entity.RegisterRequest;
import com.example.sphinx.mix.entity.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by Sphinx on 2017/3/16.
 */

public interface API {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);
}

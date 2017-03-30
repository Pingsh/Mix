package com.example.sphinx.mix.dagger.other;

import com.example.sphinx.mix.entity.Calendar;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sphinx on 2017/3/27.
 */

public interface ApiCalendar {
    //day后面不要加 /
    @GET("calendar/day")
    Observable<Calendar> queryDate(
            @Query("date") String date,
            @Query("key")String key
    );
}

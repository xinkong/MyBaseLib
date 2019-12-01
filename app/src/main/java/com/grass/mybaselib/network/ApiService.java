package com.grass.mybaselib.network;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 *
 */
public interface ApiService {
    @GET("api/user/queryLogisticsAppInfo")
    Flowable<String> getUpdateInfo();
}

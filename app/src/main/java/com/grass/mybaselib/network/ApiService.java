package com.grass.mybaselib.network;

import com.grass.mybaselib.data.bean.BaseEntity;
import com.grass.mybaselib.data.bean.Detail;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 *
 */
public interface ApiService {
    @POST("ppy-logis-driver-api/drivers/wallet/detail")
    @Streaming
    Flowable<BaseEntity<Detail>> getUpdateInfo(@Body Map params);

    @POST("ppy-logis-driver-api/drivers/wallet/detail")
    @Streaming
    Flowable<BaseEntity<Detail>> getUpdateInfo2();
}

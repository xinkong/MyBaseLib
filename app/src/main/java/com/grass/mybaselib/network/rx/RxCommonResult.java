package com.grass.mybaselib.network.rx;

import com.grass.mybaselib.data.bean.BaseEntity;
import com.grass.parent.http.ServerException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class RxCommonResult {
    /**
     * 统一返回结果处理Data情况
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseEntity<T>, T> handleResultData() {
        return httpResponseFlowable -> httpResponseFlowable.flatMap(new Function<BaseEntity<T>, Flowable<T>>() {
            @Override
            public Flowable<T> apply(BaseEntity<T> response) {
                if (response.statusCode == 2000) {
                    return createData(response.body);
                } else {
                    return Flowable.error(new ServerException(response.statusCode,response.msg));
                }
            }
        });
    }


    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }
}

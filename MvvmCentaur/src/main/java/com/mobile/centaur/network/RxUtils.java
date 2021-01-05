package com.mobile.centaur.network;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * created by mengbenming on 2018/9/15
 *
 * @class describe
 */
public class RxUtils {

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<BaseResponse<T>, T> handleResult() {      //compse判断结果
        return new FlowableTransformer<BaseResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseResponse<T> response) throws Exception {
                        if (BaseResponse.STATUS_SUCCESS.equals(response.getCode())) {
                            return createDate(response.getData());
                        } else
                            return Flowable.error(new ApiException(Integer.valueOf(response.getCode()), response.getMsg()));
                    }
                });
            }
        };

    }

    /**
     * 生成Flowable
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createDate(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) {
                try {
                    if (t != null) {
                        emitter.onNext(t);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {       //compse简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> flowable) {
//                        return flowable.subscribeOn(Schedulers.newThread())//每次都是新的线程
                return flowable.subscribeOn(Schedulers.io())//现场池复用，异步执行阻塞io
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}

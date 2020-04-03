package com.wangj.common.factory.data;

import androidx.annotation.StringRes;

public interface DataSource {


    /**
     * 同时包裹了成功与失败的回调接口
     *
     * @param <T>  任意类型
     */
    interface Callback<T> extends SuccessCallback<T>, FailedCallback {

    }

    /**
     * 只关注成功的接口
     *
     * @param <T>  任意类型
     */
    interface SuccessCallback<T> {
        //数据加载成功
        void onDataLoaded(T user);
    }

    /**
     * 只关注失败的借口
     */
    interface FailedCallback {
        //数据加载失败，网络请求失败
        void onDataNotAvailable(@StringRes int strRes);
    }

}

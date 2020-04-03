package com.wangj.factory.net;

import com.wangj.common.Common;
import com.wangj.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的封装
 */
public class NetWork {

    /**
     * 构建一个Retrofit
     *
     * @return
     */
    public static Retrofit getRetrofit() {

        //得到OK Client
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder();


        //192.168.31.160
        //设置电脑连接
        Retrofit retrofit = builder.baseUrl(Common.Constance.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();


        return retrofit;

    }

}
